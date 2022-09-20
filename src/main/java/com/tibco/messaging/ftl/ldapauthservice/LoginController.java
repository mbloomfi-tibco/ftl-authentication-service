package com.tibco.messaging.ftl.ldapauthservice;

import com.tibco.messaging.ftl.ldap.LDAPClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class LoginController {

        LDAPClient ldapClient = new LDAPClient();


        @PostMapping("/login")
        public FtlAuthenticationResponse authenticate(@RequestBody FtlAuthenticationRequest authRequest) {

                System.out.println("password:" + authRequest.getPassword());
                System.out.println("userName:" + authRequest.getUsername());
                System.out.println("realm" + authRequest.getMeta().getRealm());
                System.out.println("appname");
                FtlAuthenticationResponse ftlAuthenticationResponse = new FtlAuthenticationResponse();


           /**     if (authRequest.getPassword().compareTo("ZnJlZA==") == 0) {
                        ftlAuthenticationResponse.setAuthenticated(true);
                } else {
                        ftlAuthenticationResponse.setAuthenticated(false);
                }
           **/
                try {
                     ldapClient.authenticate(authRequest.getUsername(), authRequest.getPassword());
                        ftlAuthenticationResponse.setAuthenticated(true);
                } catch  (Exception ex){
                        System.out.println("Failed to log in user " + authRequest.getPassword());
                        ftlAuthenticationResponse.setAuthenticated(false);
                }
                // LDAP Query..  Map ftl-admin .. EMS Admin Group..

                ArrayList roles = new ArrayList<String>();

                roles.add("ftl-internal");
                roles.add("ftl-admin");
                roles.add("ftl");

                ftlAuthenticationResponse.setRoles(roles);

                return ftlAuthenticationResponse;
        }
}
