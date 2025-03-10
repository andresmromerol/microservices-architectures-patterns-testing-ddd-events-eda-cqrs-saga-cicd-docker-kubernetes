package com.amr.shop.usr.user_context._shared.infrastructure.jpa;

import com.amr.shop.cmmj.common_java_context.services.auth.RoleEnum;
import com.amr.shop.cmmj.common_java_context.services.user.vo.EmailVo;
import com.amr.shop.usr.user_context._shared.infrastructure.jpa.administrator.UserAdministratorJpa;
import com.amr.shop.usr.user_context._shared.infrastructure.jpa.customer.UserCustomerJpa;
import com.amr.shop.usr.user_context.user.domain.IUserPersistencePort;
import com.amr.shop.usr.user_context.user.domain.UserModel;
import com.amr.shop.usr.user_context.user.domain.UserNullModel;
import com.amr.shop.usr.user_context.user.domain.UserUnknownRoleException;
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
  public Optional<UserModel> findByEmailAndRole(EmailVo email, RoleEnum role) {
    try {
      UserJpa userJpa =
          entityManager
              .createQuery(
                  "SELECT u FROM UserJpa u WHERE u.email = :email AND TYPE(u) = :userType",
                  UserJpa.class)
              .setParameter("email", email.getValue())
              .setParameter("userType", resolveUserType(role))
              .getSingleResult();

      UserJpaToDomainVisitor visitor = new UserJpaToDomainVisitor();
      userJpa.accept(visitor);
      return Optional.of(visitor.getResult());
    } catch (jakarta.persistence.NoResultException e) {
      return Optional.of(new UserNullModel());
    }
  }

  private Class<? extends UserJpa> resolveUserType(RoleEnum role) {
    return switch (role.getName()) {
      case "ADMIN" -> UserAdministratorJpa.class;
      case "CUSTOMER" -> UserCustomerJpa.class;
      default -> throw new UserUnknownRoleException(role.getName());
    };
  }
}
