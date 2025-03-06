package com.amr.shop.athj.auth_service_java.user.application.encrypt_password;

import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EncryptPasswordHdr implements IQueryHandler<EncryptPasswordQry, EncryptPasswordRes> {
    private final EncryptPassword encryptPassword;

    @Autowired
    public EncryptPasswordHdr(EncryptPassword encryptPassword) {
        this.encryptPassword = encryptPassword;
    }

    @Override
    public EncryptPasswordRes handle(EncryptPasswordQry query) {
        return encryptPassword.execute(query.password());
    }
}
