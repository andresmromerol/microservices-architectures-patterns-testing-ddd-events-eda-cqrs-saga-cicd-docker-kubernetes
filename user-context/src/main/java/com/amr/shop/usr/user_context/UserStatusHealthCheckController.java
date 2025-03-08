package com.amr.shop.usr.user_context;

import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health-check")
@Slf4j
public class UserStatusHealthCheckController {

    @GetMapping
    public ResponseEntity<HashMap<String, String>> healthCheck(
            @RequestHeader("X-User-Email") String emailHeader,
            @RequestHeader("X-User-Name") String nameHeader,
            @RequestHeader("X-User-Id") String idHeader,
            @RequestHeader("X-Correlation-Id") String monitorOrgIdHeader,
            @RequestHeader("X-User-Roles") String rolesHeader) {

        log.debug("user-context - status ok");
        HashMap<String, String> status = new HashMap<>();
        status.put("bounded_context", "user-context");
        status.put("status", "ok");
        status.put("X-User-Email", emailHeader);
        status.put("X-User-Name", nameHeader);
        status.put("X-User-Id", idHeader);
        status.put("X-User-Roles", rolesHeader);
        status.put("X-Correlation-Id", monitorOrgIdHeader);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
