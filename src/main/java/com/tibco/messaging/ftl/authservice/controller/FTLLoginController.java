package com.tibco.messaging.ftl.authservice.controller;

import com.tibco.messaging.ftl.authservice.ldap.client.SimpleLDAPClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;

@Controller
public class LoginController {

        //   LdapClient ldapClient = new LdapClient();

        SimpleLDAPClient ldapClient = new  SimpleLDAPClient();

        @PostMapping("/login")
        public FtlAuthenticationResponse authenticate(@RequestBody FtlAuthenticationRequest authRequest) {

                System.out.println("password:" + authRequest.getPassword());
                System.out.println("userName:" + authRequest.getUsername());
                System.out.println("realm" + authRequest.getMeta().getRealm());

                System.out.println("password-dec:" + authRequest.getPasswordDec());

                FtlAuthenticationResponse ftlAuthenticationResponse = new FtlAuthenticationResponse();

                ArrayList roles = new ArrayList<String>();

                //Use this block of code to do a simple check and if you don't have LDAP.  You will need ot comment out the LDAP Section

               /**     if (authRequest.getPassword().compareTo("ZnJlZA==") == 0) {
                        ftlAuthenticationResponse.setAuthenticated(true);
                } else {
                        ftlAuthenticationResponse.setAuthenticated(false);
                }
                **/

                try {
                          ftlAuthenticationResponse.setAuthenticated(ldapClient.authenticateUser(authRequest.getUsername(),authRequest.getPasswordDec()));

                } catch  (Exception ex){
                        System.out.println("Failed to log in user " + authRequest.getPassword());
                        ex.printStackTrace();
                        ftlAuthenticationResponse.setAuthenticated(false);
                }

                if (ftlAuthenticationResponse.isAuthenticated()) {
                      //  roles.add("ftl-internal");
                        roles.add("ftl-admin");
                       //   roles.add("ftl");
                        roles.add("ftl-guest");
                        roles.add("ftl-admin");

                }

                // LDAP Query..  Map ftl-admin .. EMS Admin Group..

                ftlAuthenticationResponse.setRoles(roles);

                return ftlAuthenticationResponse;
        }
}
