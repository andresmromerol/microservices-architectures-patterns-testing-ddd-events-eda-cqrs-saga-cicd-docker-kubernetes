package com.amr.shop.cmmj.common_java_context.shared.bus.query;

import com.amr.shop.cmmj.common_java_context.shared.enums.ApplicationValidationEnum;

public class QueryNotRegisteredException extends Exception {

    public QueryNotRegisteredException(Class<? extends IQuery> query) {

        super(String.format(ApplicationValidationEnum.QUERY_EXCEPTION.getMessage(), query.toString()));
    }
}
