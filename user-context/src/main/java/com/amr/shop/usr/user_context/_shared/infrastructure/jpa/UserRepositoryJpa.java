package com.amr.shop.usr.user_context._shared.infrastructure.jpa;

import com.amr.shop.cmmj.common_java_context.services.user.vo.EmailVo;
import com.amr.shop.usr.user_context.user.domain.IUserPersistencePort;
import com.amr.shop.usr.user_context.user.domain.UserModel;
import com.amr.shop.usr.user_context.user.domain.UserNullModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryJpa implements IUserPersistencePort {
  @PersistenceContext private EntityManager entityManager;

  @Override
  @Transactional
  public void save(UserModel user) {
    UserJpa userJpa = UserJpaMapper.toJpa(user);
    entityManager.persist(userJpa);
  }

  @Override
  public Optional<UserModel> findByEmail(EmailVo email) {
    try {
      UserJpa userJpa =
          entityManager
              .createQuery("SELECT u FROM UserJpa u WHERE u.email = :email", UserJpa.class)
              .setParameter("email", email.getValue())
              .getSingleResult();
      UserJpaToDomainVisitor visitor = new UserJpaToDomainVisitor();
      userJpa.accept(visitor);
      return Optional.of(visitor.getResult());
    } catch (jakarta.persistence.NoResultException e) {
      return Optional.of(new UserNullModel());
    }
  }
}
