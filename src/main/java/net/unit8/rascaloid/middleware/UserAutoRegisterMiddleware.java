package net.unit8.rascaloid.middleware;

import enkan.MiddlewareChain;
import enkan.annotation.Middleware;
import enkan.component.doma2.DomaProvider;
import enkan.data.HttpRequest;
import enkan.data.HttpResponse;
import enkan.data.PrincipalAvailable;
import enkan.middleware.AbstractWebMiddleware;
import enkan.security.bouncr.UserPermissionPrincipal;
import net.unit8.rascaloid.dao.UserDao;
import net.unit8.rascaloid.entity.User;

import javax.inject.Inject;

import static enkan.util.BeanBuilder.builder;
@Middleware(name = "userAutoRegister", dependencies = "authentication")
public class UserAutoRegisterMiddleware<NRES> extends AbstractWebMiddleware<HttpRequest, NRES> {
    @Inject
    private DomaProvider daoProvider;

    @Override
    public HttpResponse handle(HttpRequest request, MiddlewareChain<HttpRequest, NRES, ?, ?> chain) {
        UserPermissionPrincipal principal = (UserPermissionPrincipal) PrincipalAvailable.class.cast(request).getPrincipal();
        if (principal != null) {
            UserDao userDao = daoProvider.getDao(UserDao.class);
            User user = userDao.findByAccount(principal.getName());
            if (user == null) {
                user = builder(new User())
                        .set(User::setAccount, principal.getName())
                        .build();
                userDao.insert(user);
            }
        }
        return (HttpResponse) chain.next(request);
    }
}
