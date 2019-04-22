import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.*;
public class MainTest {
    /** config.json looks like this
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
        URL url = Main.class.getResource("../resources/config.json");
        if(url != null) {
            String resourceName = url.getFile();
            String[] results = AbstractMainTests.executeMain("Main", new String[]{"-f", resourceName});
            for (String res : results) {
                assertEquals("", res);
            }
        }
    }
}
