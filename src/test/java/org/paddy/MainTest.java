package org.paddy;

import java.net.URL;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MainTest {
    /**
     * config.json looks like this
     * <pre>
     * {
     * "api_version": "42",
     * "baseURI": "https://###URI###/services/apexrest/###PACKAGE###/###CLASSNAME###",
     * "client_id": "###Consumer Key###",
     * "client_secret": "###Consumer Secret###",
     * "my_domain": "###MY_DOMAIN###",
     * "password": "###PASSWORD###",
     * "security_token": "###SECURITY_TOKEN###",
     * "site": "###SUBDOMAIN_SITE###.salesforce.com",
     * "username": "###USERNAME###",
     * }
     * </pre>
     */
    @Test
    public void testMain() {
        URL url = Main.class.getResource("/config.json");
        if (url != null) {
            String resourceName = url.getFile();
            assertThat(resourceName).isNotEmpty();
            String[] results = AbstractMainTests.executeMain("org.paddy.Main", new String[]{"-f", resourceName});
            for (String res : results) {
                assertThat(res).isEqualTo("");
            }
        }
    }
}
