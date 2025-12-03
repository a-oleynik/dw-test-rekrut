package dw.pageobject;

public class Wait {


    /**
     * Waits for n-seconds
     *
     * @param sec to wait
     */
    public static void staticWait(int sec) {
        System.out.println("Wait seconds: " + sec);
        long t0, t1;

        t0 = System.currentTimeMillis();
        do {
            t1 = System.currentTimeMillis();
        } while ((t1 - t0) < (sec * 1000));
    }


    /**
     * Waits for n-seconds
     *
     * @param sec to wait
     */
    public static void staticWait(double sec) {
        long t0, t1;

        t0 = System.currentTimeMillis();
        do {
            t1 = System.currentTimeMillis();
        } while ((t1 - t0) < (sec * 1000));
    }
}
