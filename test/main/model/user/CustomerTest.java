package main.model.user;

import org.junit.jupiter.api.*;

import java.util.GregorianCalendar;

/**
 * Tests for the Customer class.
 *
 * @author Manuel Gallina
 */
class CustomerTest {

    private Customer testCustomer1;
    private Customer testCustomer2;
    private Customer testCustomer3;

    @BeforeEach
    void setUp() {
        GregorianCalendar testSubscriptionDate1 = new GregorianCalendar();
        testSubscriptionDate1.add(GregorianCalendar.YEAR, -5);
        testSubscriptionDate1.add(GregorianCalendar.DAY_OF_YEAR, 5);

        GregorianCalendar testSubscriptionDate3 = new GregorianCalendar();
        testSubscriptionDate3.add(GregorianCalendar.YEAR, -6);

        testCustomer1 = new Customer(
                "Mario",
                "Rossi",
                "mariorossi",
                "12345",
                new GregorianCalendar(1980, 1, 1),
                testSubscriptionDate1);
        testCustomer2 = new Customer(
                "Giorgio",
                "Franchi",
                "giorgiofranchi",
                "54321",
                new GregorianCalendar(2005, 1, 1),
                new GregorianCalendar());
        testCustomer3 = new Customer(
                "Luigi",
                "Verdi",
                "luigiverdi",
                "00000",
                new GregorianCalendar(1995, 1, 1),
                testSubscriptionDate3);
    }

    @Test
    void isOfAge() {
        Assertions.assertTrue(testCustomer1.isOfAge());
        Assertions.assertFalse(testCustomer2.isOfAge());
    }

    @Test
    void canRenew() {
        Assertions.assertTrue(testCustomer1.canRenew());
        Assertions.assertFalse(testCustomer2.canRenew());
    }

    @Test
    void hasExpired() {
        Assertions.assertTrue(testCustomer3.hasExpired());
        Assertions.assertFalse(testCustomer1.hasExpired());
        Assertions.assertFalse(testCustomer2.hasExpired());
    }

    @Test
    void daysLeftToRenew() {
        Assertions.assertTrue(testCustomer1.daysLeftToRenew() >= 4 && testCustomer1.daysLeftToRenew() <= 5);
    }
}