package fordtest;

import java.time.LocalDate;

public class ManualGroceryTest {
    /**
     * manual test - to check non redirected console works
     */
    public static void main(String args[]) {
        System.out.println("example:");
        System.out.println("add bread");
        System.out.println("add soup");
        System.out.println("price " + LocalDate.now());
        try {
            (new Grocery()).priceFromSystemIn();
        } catch (Exception ex) {
            System.out.println("failed on " + ex);
        }
    }

}
