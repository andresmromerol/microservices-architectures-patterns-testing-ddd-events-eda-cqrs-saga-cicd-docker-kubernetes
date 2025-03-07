package com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.token;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokenJpaRepository extends JpaRepository<TokenJpa, Integer> {
    @Query(
            value =
                    """
            SELECT t.*
            FROM auth_tokens t
            INNER JOIN auth_users u ON t.user_id = u.id
            WHERE u.id = UNHEX(REPLACE(:id, '-', ''))
            AND (t.expired = FALSE OR t.revoked = FALSE)
            """,
            nativeQuery = true)
    Set<TokenJpa> findAllValidTokenByUser(@Param("id") UUID id);

    Optional<TokenJpa> findByToken(String token);
}
