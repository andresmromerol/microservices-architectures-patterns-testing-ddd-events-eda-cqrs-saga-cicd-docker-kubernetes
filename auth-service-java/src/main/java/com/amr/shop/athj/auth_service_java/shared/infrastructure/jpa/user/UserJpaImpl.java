package com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserJpaImpl implements IUserJpaDao {

  @PersistenceContext private EntityManager entityManager;

  @Autowired
  public UserJpaImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public UserJpa getReferenceToUser(UUID userId) {
    return entityManager.getReference(UserJpa.class, userId);
  }
}
