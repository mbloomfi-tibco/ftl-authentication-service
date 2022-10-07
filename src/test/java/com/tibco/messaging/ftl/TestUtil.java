package com.tibco.messaging.ftl;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class TestUtil
{
    public String getEncodedPassword(String password) {
       return Base64.getEncoder().encodeToString(password.getBytes());
    }


    @Test
    void name() {
        TestUtil testUtil = new TestUtil();


        System.out.println("Encoded Password:" + testUtil.getEncodedPassword("test"));

        String password = new String(Base64.getDecoder().decode(testUtil.getEncodedPassword("test")), StandardCharsets.UTF_8);
        System.out.println("decoded Password:" + password);

    }
}
