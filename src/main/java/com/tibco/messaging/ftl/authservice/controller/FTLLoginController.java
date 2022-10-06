package com.tibco.messaging.ftl.authservice.controller;

import com.tibco.messaging.ftl.authservice.ldap.client.SimpleLDAPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class FTLLoginController {

        Logger logger = LoggerFactory.getLogger(FTLLoginController.class);

        SimpleLDAPClient ldapClient = new SimpleLDAPClient();

        @Value("${FTLLoginController.authentication.mode}")
        private String authenticationMode;

        @PostMapping("/login")
        public FtlAuthenticationResponse authenticate(@RequestBody FtlAuthenticationRequest authRequest) {

                System.out.println("Authentication mode is set to: " + authenticationMode);

                logger.info("Authenicating:" + authRequest.getUsername() + " for realm " + authRequest.getMeta().getRealm() + " for app name " + authRequest.getMeta().getAppname());

                FtlAuthenticationResponse ftlAuthenticationResponse = new FtlAuthenticationResponse();

                ArrayList roles = new ArrayList<String>();

                // This block of code would be replaced by your own LDAP / Security mechanism.
                // If you don't have a local ldap then for a simple test change LDAP in the application properties to a different value. "Simple" for example.

                if (authenticationMode.compareTo("LDAP")==0) {
                        try {
                                ftlAuthenticationResponse.setAuthenticated(ldapClient.authenticateUser(authRequest.getUsername(), authRequest.getPasswordDec()));

                        } catch (Exception ex) {
                                logger.error("Failed to log in user " + authRequest.getUsername());
                                ftlAuthenticationResponse.setAuthenticated(false);
                        }
                } else {
                        if (authRequest.getPassword().compareTo("ZnJlZA==") == 0) {
                                ftlAuthenticationResponse.setAuthenticated(true);
                        } else {
                                ftlAuthenticationResponse.setAuthenticated(false);
                        }
                }
                if (ftlAuthenticationResponse.isAuthenticated()) {
                        logger.info("User " + authRequest.getUsername() + " authenticated");
                      //  roles.add("ftl-internal");
                        roles.add("ftl-guest");
                      //  roles.add("ftl-admin");
                       //   roles.add("ftl");
                } else {
                        logger.warn("User " + authRequest.getUsername() + " failed to authenticate");
                }

                // LDAP Query..  Map ftl-admin .. EMS Admin Group..

                ftlAuthenticationResponse.setRoles(roles);

                return ftlAuthenticationResponse;
        }
}
