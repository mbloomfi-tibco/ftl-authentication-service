package com.tibco.messaging.ftl.ldap.client;

import com.tibco.messaging.ftl.ldap.LDAPClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.hamcrest.Matchers;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.List;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("testlive")
@ContextConfiguration(classes = {TestConfig.class}, loader = AnnotationConfigContextLoader.class)

public class ldapclientTests {

    private static final String USER2 = "TEST02";
    private static final String USER3 = "TEST03";
    private static final String USER4 = "TEST04";

    private static final String USER2_PWD = "TEST02";
    private static final String USER3_PWD = "TEST03";
    private static final String USER4_PWD = "TEST04";

    private static final String SEARCH_STRING = "TEST*";

    @Autowired
    private LDAPClient ldapClient;

    @Test
    public void givenLdapClient_whenCorrectCredentials_thenSuccessfulLogin() {
        ldapClient.authenticate(USER3, USER3_PWD);
    }

    /**
    @Test(expected = AuthenticationException.class)
    public void givenLdapClient_whenIncorrectCredentials_thenFailedLogin() {
        ldapClient.authenticate(USER3, USER2_PWD);
    }
**/

    @Test
    public void givenLdapClient_whenCorrectSearchFilter_thenEntriesReturned() {
        List<String> users = ldapClient.search(SEARCH_STRING);
        assertThat(users, Matchers.containsInAnyOrder(USER2, USER3));
    }

    @Test
    public void givenLdapClientNotExists_whenDataProvided_thenNewUserCreated() {
        ldapClient.create(USER4, USER4_PWD);
        ldapClient.authenticate(USER4, USER4_PWD);
    }

    @Test
    public void givenLdapClientExists_whenDataProvided_thenExistingUserModified() {
        ldapClient.modify(USER2, USER3_PWD);
        ldapClient.authenticate(USER2, USER3_PWD);
    }
}
