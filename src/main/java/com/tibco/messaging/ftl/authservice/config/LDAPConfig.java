package com.tibco.messaging.ftl.authservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LDAPConfig {

    @Value("${ldap.password}")
    String password;

    @Value("${ldap.providerURL}")
    String providerURL;

    @Value("${ldap.adminPrinciple}")
    String adminPrinciple;

    @Value("${ldap.userPrinciple}")
    String userPrinciple;
    String intialContextFactory = "com.sun.jndi.ldap.LdapCtxFactory";

    public String getPassword() {
        return password;
    }

    public String getAdminPrinciple() {
        return adminPrinciple;
    }

    public String getUserPrinciple() {
        return userPrinciple;
    }

    public String getProviderURL() {
        return providerURL;
    }

    public String getIntialContextFactory() {
        return intialContextFactory;
    }
}
