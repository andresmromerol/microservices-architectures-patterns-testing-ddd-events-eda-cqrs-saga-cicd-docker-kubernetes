package com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.user;

import java.util.UUID;

public interface IUserJpaDao {

  UserJpa getReferenceToUser(UUID userId);
}
