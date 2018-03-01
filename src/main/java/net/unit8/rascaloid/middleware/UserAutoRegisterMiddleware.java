package net.unit8.rascaloid.middleware;

import enkan.MiddlewareChain;
import enkan.annotation.Middleware;
import enkan.component.doma2.DomaPrividerUtils;
import enkan.component.doma2.DomaProvider;
import enkan.data.HttpRequest;
import enkan.data.HttpResponse;
import enkan.data.PrincipalAvailable;
import enkan.middleware.AbstractWebMiddleware;
import enkan.security.bouncr.UserPermissionPrincipal;
import net.unit8.rascaloid.dao.UserDao;
import net.unit8.rascaloid.entity.User;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.ConfigSupport;
import org.seasar.doma.jdbc.tx.EnkanLocalTransactionDataSource;
import org.seasar.doma.jdbc.tx.LocalTransactionManager;
import org.seasar.doma.jdbc.tx.TransactionManager;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;

import static enkan.util.BeanBuilder.builder;
@Middleware(name = "userAutoRegister", dependencies = "authentication")
public class UserAutoRegisterMiddleware<NRES> extends AbstractWebMiddleware<HttpRequest, NRES> {
    @Inject
    private DomaProvider daoProvider;

    private TransactionManager tm;

    @PostConstruct
    private void init() {
        Config defaultConfig = DomaPrividerUtils.getDefaultConfig(daoProvider);
        DataSource ds = defaultConfig.getDataSource(); // returns LocalTransactionDataSource
        if (ds instanceof EnkanLocalTransactionDataSource) {
            EnkanLocalTransactionDataSource ltds = (EnkanLocalTransactionDataSource) ds;
            tm = new LocalTransactionManager(ltds.getLocalTransaction(ConfigSupport.defaultJdbcLogger));
        }
    }

    @Override
    public HttpResponse handle(HttpRequest request, MiddlewareChain<HttpRequest, NRES, ?, ?> chain) {
        UserPermissionPrincipal principal = (UserPermissionPrincipal) PrincipalAvailable.class.cast(request).getPrincipal();
        if (principal != null) {
            UserDao userDao = daoProvider.getDao(UserDao.class);
            User user = userDao.findByAccount(principal.getName());
            if (user == null) {
                tm.required(() -> {
                    userDao.insert(builder(new User())
                            .set(User::setAccount, principal.getName())
                            .build());
                });
            }
        }
        return (HttpResponse) chain.next(request);
    }
}
