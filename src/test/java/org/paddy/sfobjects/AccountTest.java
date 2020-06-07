package org.paddy.sfobjects;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountTest {
    @Test
    public void testAccount() {
        final Account account = new Account();
        account.setName("Accountname");
        assertThat(account).isNotNull();
        /*
        Account account1 = null;
        try {
            account1 = account0.clone();
        }
        catch (CloneNotSupportedException cnse) {
            System.out.println(cnse);
        }
        assertThat(account0.getName()).isEqualTo(account1.getName());
        */
    }
}
