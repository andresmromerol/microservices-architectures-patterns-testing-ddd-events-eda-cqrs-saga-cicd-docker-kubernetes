package com.amr.shop.cmmj.common_java_context.shared.bus.query;

public interface IQueryHandler<Q extends IQuery, R extends IResponse> {
  R handle(Q query);
}
