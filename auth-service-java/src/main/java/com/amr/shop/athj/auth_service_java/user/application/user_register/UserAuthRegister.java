package com.amr.shop.athj.auth_service_java.user.application.user_register;

import com.amr.shop.athj.auth_service_java.user.application.encrypt_password.EncryptPasswordQry;
import com.amr.shop.athj.auth_service_java.user.application.encrypt_password.EncryptPasswordRes;
import com.amr.shop.athj.auth_service_java.user.domain.UserAuthEmailAlreadyExistsException;
import com.amr.shop.athj.auth_service_java.user.domain.UserModel;
import com.amr.shop.athj.auth_service_java.user.domain.ports.IUserAuthPersistencePort;
import com.amr.shop.athj.auth_service_java.user_vw.application.user_search_by_email.UserSearchByEmailQry;
import com.amr.shop.athj.auth_service_java.user_vw.application.user_search_by_email.UserSearchByEmailRes;
import com.amr.shop.cmmj.common_java_context.services.auth.RoleEnum;
import com.amr.shop.cmmj.common_java_context.services.user.UserStatusEnum;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryBus;
import java.util.Set;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserAuthRegister {
    private final IUserAuthPersistencePort userAuthPersistencePort;
    private final IQueryBus queryBus;

    @Autowired
    public UserAuthRegister(IUserAuthPersistencePort userAuthPersistencePort, IQueryBus queryBus) {
        this.userAuthPersistencePort = userAuthPersistencePort;
        this.queryBus = queryBus;
    }

    public void execute(UUID id, String name, String email, String password, String phone, Set<RoleEnum> roles) {
        log.debug("Executing user auth register with id: {}", id);

        ensureNotEmailExists(email);

        log.debug("Creating user auth model with id: {}", id);
        UserModel auth =
                UserModel.create(id, name, email, getEncryptedPasswords(password), UserStatusEnum.ACTIVE, phone, roles);

        log.debug("Saving user auth model with id: {}", id);
        userAuthPersistencePort.save(auth);
    }

    private String getEncryptedPasswords(String password) {
        log.debug("Getting encrypted passwords for password: {}", password);
        EncryptPasswordRes res = queryBus.ask(new EncryptPasswordQry(password));
        return res.encryptedPassword();
    }

    private void ensureNotEmailExists(String email) {
        log.debug("Ensuring not email exists with email: {}", email);
        UserSearchByEmailRes ask = queryBus.ask(new UserSearchByEmailQry(email));
        if (!ask.isEmpty()) {
            log.error("Email already exists with email: {}", email);
            throw new UserAuthEmailAlreadyExistsException(email);
        }
    }
}
