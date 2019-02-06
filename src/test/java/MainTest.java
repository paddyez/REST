import org.junit.Test;
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
        String resourceName = Main.class.getResource("../resources/config.json").getFile();
        String[] results = AbstractMainTests.executeMain("Main", new String[]{"-f", resourceName});
        int i = 0;
        for (String res: results) {
                assertEquals("", res);
        }
    }
}
