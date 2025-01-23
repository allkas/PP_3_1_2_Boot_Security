package ru.kata.spring.boot_security.demo.model;

import lombok.Getter;

@Getter
public enum Roles {
    ROLE_ADMIN("1"),
    ROLE_USER("2");

    private final String value;

    Roles(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}

