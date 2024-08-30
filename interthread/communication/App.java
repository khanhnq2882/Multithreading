package interthread.communication;

public class App {

    public static int counter1 = 0;
    public static int counter2 = 0;

    // Initialize new Object lock1 to create a monitor locking for first thread
    public static final Object lock1 = new Object();
    // Initialize new Object lock2 to create a monitor locking for second thread
    public static final Object lock2 = new Object();
    // -> to avoid both threads sharing the same monitor locking of App class (class level locking)

    public static synchronized void increment1() {
//        synchronized (App.class) {
//            counter1++;
//        } don't recommend this
        synchronized (lock1) {
            counter1++;
        }
    }

    public static synchronized void increment2() {
//        synchronized (App.class) {
//            counter2++;
//        } don't recommend this
        synchronized (lock2) {
            counter2++;
        }
    }

    public static void process() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<100; ++i)
                    increment1();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<100; ++i) {
                    increment2();
                }
            }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("The counter1 is: "+counter1);
        System.out.println("The counter2 is: "+counter2);
    }

    public static void main(String[] args) {
        process();
    }
}
