package com.tibco.messaging.ftl.authservice.model;

import java.util.ArrayList;

public class FTLAuthenticationResponse {

    /*
     * We should generate output like this:
     * {
     *      "authenticated":true,
     *      "roles":["role1", "role2", ...]
     * }
     *
     * If access is denied, then "authenticated" should be false, and there is no
     * need for a "roles" field.
     */

      public boolean isAuthenticated() {
            return authenticated;
      }

      public void setAuthenticated(boolean authenticated) {
            this.authenticated = authenticated;
      }

      public ArrayList<String> getRoles() {
            return roles;
      }

      public void setRoles(ArrayList<String> roles) {
            this.roles = roles;
      }

      private boolean authenticated ;
      private ArrayList<String> roles;
}
