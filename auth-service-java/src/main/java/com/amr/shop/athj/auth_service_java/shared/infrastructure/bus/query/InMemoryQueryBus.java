package com.amr.shop.athj.auth_service_java.shared.infrastructure.bus.query;

import com.amr.shop.cmmj.common_java_context.shared.bus.query.*;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class InMemoryQueryBus implements IQueryBus {

    private final QueryHandlersLocal information;

    private final ApplicationContext context;

    public InMemoryQueryBus(QueryHandlersLocal information, ApplicationContext context) {

        this.information = information;
        this.context = context;
    }

    @Override
    public IResponse ask(IQuery query) throws QueryHandlerExecutionException {

        try {
            Class<? extends IQueryHandler> queryHandlerClass = information.search(query.getClass());

            IQueryHandler handler = context.getBean(queryHandlerClass);

            return handler.handle(query);
        } catch (Throwable error) {
            throw new QueryHandlerExecutionException(error);
        }
    }
}
