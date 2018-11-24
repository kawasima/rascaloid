package net.unit8.rascaloid;

import enkan.Application;
import enkan.application.WebApplication;
import enkan.config.ApplicationFactory;
import enkan.endpoint.ResourceEndpoint;
import enkan.middleware.*;
import enkan.middleware.jpa.EntityManagerMiddleware;
import enkan.middleware.jpa.NonJtaTransactionMiddleware;
import enkan.security.bouncr.BouncrBackend;
import enkan.system.inject.ComponentInjector;
import enkan.util.Predicates;
import kotowari.inject.ParameterInjector;
import kotowari.inject.parameter.*;
import kotowari.middleware.RoutingMiddleware;
import kotowari.middleware.SerDesMiddleware;
import kotowari.restful.middleware.ResourceInvokerMiddleware;
import kotowari.routing.Routes;
import net.unit8.rascaloid.middleware.WebJarsMiddleware;
import net.unit8.rascaloid.resource.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static enkan.util.BeanBuilder.builder;

public class RascaloidApplicationFactory implements ApplicationFactory {
    @Override
    public Application create(ComponentInjector injector) {
        WebApplication app = new WebApplication();

        Routes routes = Routes.define(r -> {
            r.all("/projects").to(ProjectsResource.class);
            r.all("/project/:projectId").to(ProjectResource.class);

            r.all("/project/:projectId/iterations").to(IterationsResource.class);
            r.all("/project/:projectId/iteration/:iterationId").to(IterationResource.class);

            r.all("/project/:projectId/iteration/:iterationId/stories").to(IterationStoriesResource.class);

            //r.all("/project/:projectId/stories").to()
        }).compile();

        List<ParameterInjector<?>> parameterInjectors = List.of(
                new HttpRequestInjector(),
                new ParametersInjector(),
                new SessionInjector(),
                new FlashInjector<>(),
                new PrincipalInjector(),
                new ConversationInjector(),
                new ConversationStateInjector(),
                new LocaleInjector(),
                new EntityManagerInjector()
        );

        app.use(new DefaultCharsetMiddleware<>());
        app.use(Predicates.none(), new ServiceUnavailableMiddleware<>(new ResourceEndpoint("/public/html/503.html")));
        app.use(new ParamsMiddleware<>());
        app.use(new MultipartParamsMiddleware<>());
        app.use(new NestedParamsMiddleware<>());
        app.use(builder(new ContentNegotiationMiddleware<>())
                .set(ContentNegotiationMiddleware::setAllowedLanguages,
                        new HashSet<>(Arrays.asList("en", "ja")))
                .build());
        app.use(builder(new CorsMiddleware<>())
                .set(CorsMiddleware::setHeaders,
                        new HashSet<>(Arrays.asList("X-Bouncr-Credential", "Content-Type")))
                .build());
        BouncrBackend bouncrBackend = new BouncrBackend();
        injector.inject(bouncrBackend);

        app.use(new AuthenticationMiddleware<>(Collections.singletonList(bouncrBackend)));
        app.use(new WebJarsMiddleware<>());
        app.use(new ResourceMiddleware<>());

        app.use(new RoutingMiddleware<>(routes));
        app.use(new EntityManagerMiddleware<>());
        app.use(new NonJtaTransactionMiddleware<>());
        // For development; If the authenticated user is unregistered, register the user automatically.
        app.use(new SerDesMiddleware<>());
        app.use(builder(new ResourceInvokerMiddleware<>(injector))
                .set(ResourceInvokerMiddleware::setParameterInjectors, parameterInjectors)
                .build());
        return app;
    }
}
