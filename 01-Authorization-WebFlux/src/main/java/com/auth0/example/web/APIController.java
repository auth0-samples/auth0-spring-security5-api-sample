package com.auth0.example.web;

import com.auth0.example.model.Message;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Handles requests to "/api" endpoints.
 * @see com.auth0.example.security.SecurityConfig to see how these endpoints are protected.
 */
@RestController
@RequestMapping(path = "api", produces = MediaType.APPLICATION_JSON_VALUE)
// For simplicity of this sample, allow all origins. Real applications should configure CORS for their use case.
@CrossOrigin("*")
public class APIController {

    @GetMapping(value = "/public")
    public Mono<Message> publicEndpoint() {
        return Mono.just(new Message("All good. You DO NOT need to be authenticated to call /api/public."));
    }

    @GetMapping(value = "/private")
    public Mono<Message> privateEndpoint() {
        return Mono.just(new Message("All good. You can see this because you are Authenticated."));
    }

    @GetMapping(value = "/private-scoped")
    public Mono<Message> privateScopedEndpoint() {
        return Mono.just(new Message("All good. You can see this because you are Authenticated with a Token granted the 'read:messages' scope"));
    }
}
