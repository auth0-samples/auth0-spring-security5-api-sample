package com.auth0.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Simple domain object for our API to return a message.
 * Uses Lombok to remove boilerplate code, see https://projectlombok.org/ for more info.
 */
@AllArgsConstructor
@Getter
public class Message {
    private final String message;
}
