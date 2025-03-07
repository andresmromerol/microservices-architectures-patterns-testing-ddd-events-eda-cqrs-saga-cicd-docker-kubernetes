package com.amr.shop.athj.auth_service_java.user.domain.ports;

import java.util.Date;

public interface IClaimPort {
    String extractUsername(String token);

    Date extractExpiration(String token);
}
