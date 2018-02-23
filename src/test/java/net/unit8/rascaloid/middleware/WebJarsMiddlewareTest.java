package net.unit8.rascaloid.middleware;

import enkan.MiddlewareChain;
import enkan.chain.DefaultMiddlewareChain;
import enkan.data.DefaultHttpRequest;
import enkan.data.HttpRequest;
import enkan.util.Predicates;
import org.junit.jupiter.api.Test;

import static enkan.util.BeanBuilder.*;

public class WebJarsMiddlewareTest {
    @Test
    public void test() {
        WebJarsMiddleware<Void> middleware = new WebJarsMiddleware<>();
        HttpRequest request = builder(new DefaultHttpRequest())
            .set(HttpRequest::setRequestMethod, "GET")
            .set(HttpRequest::setUri, "/webjars/swagger-ui/index.html")
            .build();

        MiddlewareChain<HttpRequest, Void, ?, ?> chain = new DefaultMiddlewareChain<>(Predicates.any(),
            "void",
            (request1, chain1) -> null);
        System.out.println(middleware.handle(request, chain));
    }
}
