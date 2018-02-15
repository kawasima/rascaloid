package net.unit8.rascaloid;

import enkan.Application;
import enkan.application.WebApplication;
import enkan.config.ApplicationFactory;
import enkan.endpoint.ResourceEndpoint;
import enkan.middleware.*;
import enkan.middleware.doma2.DomaTransactionMiddleware;
import enkan.security.bouncr.BouncrBackend;
import enkan.system.inject.ComponentInjector;
import enkan.util.Predicates;
import kotowari.middleware.*;
import kotowari.routing.Routes;
import net.unit8.rascaloid.controller.*;
import net.unit8.rascaloid.middleware.AuthorizeControllerMethodMiddleware;
import net.unit8.rascaloid.middleware.UserAutoRegisterMiddleware;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static enkan.util.BeanBuilder.builder;

public class RascaloidApplicationFactory implements ApplicationFactory {
    @Override
    public Application create(ComponentInjector injector) {
        WebApplication app = new WebApplication();

        Routes routes = Routes.define(r -> {
            r.get("/projects").to(ProjectController.class, "list");
            r.post("/projects").to(ProjectController.class, "create");
            r.put("/project/:projectId").to(ProjectController.class, "update");
            r.delete("/project/:projectId").to(ProjectController.class, "delete");
            r.get("/project/:projectId").to(ProjectController.class, "show");

            r.get("/project/:projectId/stories").to(StoryController.class, "list");
            r.post("/project/:projectId/stories").to(StoryController.class, "create");

            r.get("/story/:storyId").to(StoryController.class, "show");
            r.put("/story/:storyId").to(StoryController.class, "update");
            r.delete("/story/:storyId").to(StoryController.class, "delete");

            r.get("/story/:storyId/tasks").to(DevelopmentTaskController.class, "list");
            r.get("/story/:storyId/tasks/byStatus").to(DevelopmentTaskController.class, "listByStatus");
            r.post("/story/:storyId/tasks").to(DevelopmentTaskController.class, "create");
            r.get("/task/:taskId").to(DevelopmentTaskController.class, "show");
            r.put("/task/:taskId").to(DevelopmentTaskController.class, "update");
            r.delete("/task/:taskId").to(DevelopmentTaskController.class, "delete");

            r.get("/project/:projectId/iterations").to(IterationController.class, "list");
            r.post("/project/:projectId/iterations").to(IterationController.class, "create");

            r.get("/iteration/:iterationId").to(IterationController.class, "show");
            r.put("/iteration/:iterationId/addStory/:storyId").to(IterationController.class, "addStory");
            r.delete("/iteration/:iterationId/removeStory/:storyId").to(IterationController.class, "removeStory");
            r.get("/iteration/:iterationId/kanban").to(IterationController.class, "kanban");

            r.get("/taskStatus").to(TaskStatusController.class, "list");
            r.post("/taskStatus").to(TaskStatusController.class, "create");
        }).compile();

        app.use(new DefaultCharsetMiddleware<>());
        app.use(Predicates.none(), new ServiceUnavailableMiddleware<>(new ResourceEndpoint("/public/html/503.html")));
        app.use(new ContentTypeMiddleware<>());
        app.use(new ParamsMiddleware<>());
        app.use(new MultipartParamsMiddleware<>());
        app.use(new MethodOverrideMiddleware<>());
        app.use(new NormalizationMiddleware<>());
        app.use(new NestedParamsMiddleware<>());
        app.use(new CookiesMiddleware<>());

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
        app.use(new ResourceMiddleware<>());
        app.use(new RenderTemplateMiddleware<>());
        app.use(new RoutingMiddleware<>(routes));
        app.use(new AuthorizeControllerMethodMiddleware<>());
        app.use(new DomaTransactionMiddleware<>());
        // For development; If the authenticated user is unregistered, register the user automatically.
        app.use(new UserAutoRegisterMiddleware<>());
        app.use(new FormMiddleware<>());
        app.use(new SerDesMiddleware<>());
        app.use(new ValidateBodyMiddleware<>());
        app.use(new ControllerInvokerMiddleware<>(injector));

        return app;
    }
}
