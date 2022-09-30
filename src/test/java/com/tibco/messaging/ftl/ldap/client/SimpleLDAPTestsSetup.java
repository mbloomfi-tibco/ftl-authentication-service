package com.tibco.messaging.ftl.ldap.client;

import com.tibco.messaging.ftl.authservice.ldap.client.SimpleLDAPClient;
import org.junit.jupiter.api.Test;

import javax.naming.NamingException;

public class SimpleLDAPTestsSetup {

    @Test
    public void setupUserGroups() {
        SimpleLDAPClient simpleLDAPClient = new SimpleLDAPClient();
        simpleLDAPClient.addUserToGroup("guest","ftl");
        simpleLDAPClient.addUserToGroup("guest","ftl-admin");
        simpleLDAPClient.addUserToGroup("guest","ftl-internal");

    }

    @Test
    public void getAllUsers(){
        SimpleLDAPClient simpleLDAPClient = new SimpleLDAPClient();
        try {
            simpleLDAPClient.getAllUsers();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void searchUsers(){
        SimpleLDAPClient simpleLDAPClient = new SimpleLDAPClient();
        try {
            simpleLDAPClient.searchUserByUID("guest");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }



}
