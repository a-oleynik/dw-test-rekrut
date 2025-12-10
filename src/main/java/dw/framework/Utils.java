package dw.framework;

public class Utils {
    // The method is used for scrollLeftUntilElementPresentV2.
    // Need to check if we can use only awaitility and remove this one
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
