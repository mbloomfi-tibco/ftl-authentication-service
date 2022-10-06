package com.tibco.messaging.ftl.authservice.ldap.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


import java.util.Properties;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;

@Component
public class SimpleLDAPClient {
    private DirContext getDirContext() throws NamingException {
        DirContext dirContext = new InitialDirContext();
      //  dirContext.addToEnvironment(Context.PROVIDER_URL, env.getRequiredProperty("LDAPConfig.providerURL"));
     //   dirContext.addToEnvironment(Context.SECURITY_PRINCIPAL, env.getRequiredProperty("LDAPConfig.principle"));
     //   dirContext.addToEnvironment(Context.SECURITY_CREDENTIALS, env.getRequiredProperty("LDAPConfig.password"));
          dirContext.addToEnvironment(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
          dirContext.addToEnvironment(Context.PROVIDER_URL,"ldap://localhost:10389");
          dirContext.addToEnvironment(Context.SECURITY_PRINCIPAL,"uid=admin, ou=system");
          dirContext.addToEnvironment(Context.SECURITY_CREDENTIALS, "secret");
        return dirContext;
    }

    DirContext connection;
    Logger logger = LoggerFactory.getLogger(SimpleLDAPClient.class);

    public SimpleLDAPClient() {
        connect();
    }

    public void connect() {
        try {

            connection = getDirContext();

            System.out.println("LDAP Connection Made " + connection);
        } catch (AuthenticationException ex) {
            System.out.println(ex.getMessage());
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void getAllUsers() throws NamingException {
        String searchFilter = "(objectClass=inetOrgPerson)";
        String[] reqAtt = { "cn", "sn" };
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        controls.setReturningAttributes(reqAtt);

        NamingEnumeration users = connection.search("ou=users,ou=system", searchFilter, controls);

        SearchResult result = null;
        while (users.hasMore()) {
            result = (SearchResult) users.next();
            Attributes attr = result.getAttributes();
            String name = attr.get("cn").get(0).toString();
            //deleteUserFromGroup(name,"Administrators");
            System.out.println(attr.get("cn"));
            System.out.println(attr.get("sn"));
        }

    }

    public void addUser() {
        Attributes attributes = new BasicAttributes();
        Attribute attribute = new BasicAttribute("objectClass");
        attribute.add("inetOrgPerson");

        attributes.put(attribute);
        // user details
        attributes.put("sn", "Ricky");
        try {
            connection.createSubcontext("cn=Tommy,ou=users,ou=system", attributes);
            System.out.println("success");
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void addUserToGroup(String username, String groupName)
    {
        ModificationItem[] mods = new ModificationItem[1];
        Attribute attribute = new BasicAttribute("uniqueMember","cn="+username+",ou=users,ou=system");
        mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, attribute);
        try {
            connection.modifyAttributes("cn="+groupName+",ou=groups,ou=system", mods);
            System.out.println("success");
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void deleteUser()
    {
        try {
            connection.destroySubcontext("cn=Tommy,ou=users,ou=system");
            System.out.println("success");
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void deleteUserFromGroup(String username, String groupName)
    {
        ModificationItem[] mods = new ModificationItem[1];
        Attribute attribute = new BasicAttribute("uniqueMember","cn="+username+",ou=users,ou=system");
        mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, attribute);
        try {
            connection.modifyAttributes("cn="+groupName+",ou=groups,ou=system", mods);
            System.out.println("success");
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void searchUserByUID(String user) throws NamingException {
        String searchFilter = "(|(uid=1)(uid=2)(cn=" + user + " ))"; // or condition
        String[] reqAtt = { "cn", "sn","uid" };
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        controls.setReturningAttributes(reqAtt);

        NamingEnumeration users = connection.search("ou=users,ou=system", searchFilter, controls);

        SearchResult result = null;
        while (users.hasMore()) {
            result = (SearchResult) users.next();
            Attributes attr = result.getAttributes();
            String name = attr.get("cn").get(0).toString();
            //deleteUserFromGroup(name,"Administrators");
            System.out.println(attr.get("cn"));
            System.out.println(attr.get("sn"));
            System.out.println(attr.get("uid"));
        }

    }

    /* use this to authenticate any existing user */
    public static boolean authenticateUser(String username, String password)
    {
        try {
            Properties env = new Properties();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, "ldap://localhost:10389");
            env.put(Context.SECURITY_PRINCIPAL, "cn="+username+",ou=users,ou=system");  //check the DN correctly
            env.put(Context.SECURITY_CREDENTIALS, password);

            DirContext con = new InitialDirContext(env);
            System.out.println("success");
            con.close();
            return true;
        }catch (Exception e) {
            System.out.println("failed: "+e.getMessage());
            return false;
        }
    }

    /* use this to update user password */
    public void updateUserPassword(String username, String password) {
        try {
            String dnBase=",ou=users,ou=system";
            ModificationItem[] mods= new ModificationItem[1];
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userPassword", password));// if you want, then you can delete the old password and after that you can replace with new password
            connection.modifyAttributes("cn="+username +dnBase, mods);//try to form DN dynamically
            System.out.println("success");
        }catch (Exception e) {
            System.out.println("failed: "+e.getMessage());
        }
    }

}
