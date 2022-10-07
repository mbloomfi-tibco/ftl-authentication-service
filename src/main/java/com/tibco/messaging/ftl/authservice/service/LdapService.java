package com.tibco.messaging.ftl.authservice.service;

import com.tibco.messaging.ftl.authservice.config.LDAPConfig;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Properties;

@Service
public class LdapService {

    LDAPConfig ldapConfig;

    public LdapService(LDAPConfig ldapConfig) {
        this.ldapConfig = ldapConfig;
    }

    public boolean authenticateUser(String username, String password)
    {
        try {

            Properties env = new Properties();
            env.put(Context.INITIAL_CONTEXT_FACTORY, ldapConfig.getIntialContextFactory());
            env.put(Context.PROVIDER_URL,ldapConfig.getProviderURL());
            env.put(Context.SECURITY_PRINCIPAL, "cn="+username+","+ ldapConfig.getUserPrinciple());  //check the DN correctly
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

}
