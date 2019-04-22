import org.junit.Assert;
import org.junit.Test;
import org.paddy.sfObjects.Account;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
public class AccountTest {
    @Test
    public void testAccount() {
        Account account0 = new Account("Accountname");
        Account account1 = null;
        /*
        try {
            account1 = account0.clone();
        }
        catch (CloneNotSupportedException cnse) {
            System.out.println(cnse);
        }
        assertThat(account0.getName(), equalTo(account1.getName()));
        */
        Assert.assertNotNull(account0);
    }
}
