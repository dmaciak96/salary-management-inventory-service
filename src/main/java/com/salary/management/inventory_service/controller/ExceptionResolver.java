package com.salary.management.inventory_service.controller;

import com.salary.management.inventory_service.exception.BalanceGroupMemberNotFoundException;
import com.salary.management.inventory_service.exception.BalanceGroupNotFoundException;
import com.salary.management.inventory_service.exception.ExpenseNotFoundException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

@Component
public class ExceptionResolver extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (isNotFoundException(ex)) {
            return GraphqlErrorBuilder.newError()
                    .errorType(ErrorType.NOT_FOUND)
                    .message(ex.getMessage())
                    .path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation())
                    .build();
        }
        return super.resolveToSingleError(ex, env);
    }

    private boolean isNotFoundException(Throwable ex) {
        return ex instanceof BalanceGroupNotFoundException ||
                ex instanceof BalanceGroupMemberNotFoundException ||
                ex instanceof ExpenseNotFoundException;
    }
}
