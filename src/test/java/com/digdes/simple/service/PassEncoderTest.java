package com.digdes.simple.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PassEncoderTest {
    @Autowired
    PassEncoder pe;

    @DisplayName("Password encoding result exists")
    void encode() {
        String password = "password";
        String result = pe.encode(password);
        Assertions.assertNotNull(result);
    }
}
