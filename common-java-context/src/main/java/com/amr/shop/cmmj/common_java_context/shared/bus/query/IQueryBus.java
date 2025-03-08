package com.amr.shop.cmmj.common_java_context.shared.bus.query;

public interface IQueryBus {
  <R> R ask(IQuery query) throws QueryHandlerExecutionException;
}
