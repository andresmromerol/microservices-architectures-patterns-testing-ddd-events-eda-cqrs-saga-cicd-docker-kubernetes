package com.amr.shop.athj.auth_service_java;

import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health-check")
@Slf4j
public class AuthStatusHealthCheckController {

    @GetMapping
    public ResponseEntity<HashMap<String, String>> healthCheck() {

        HashMap<String, String> status = new HashMap<>();
        status.put("bounded_context", "auth-service-java");
        status.put("status", "ok");
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
