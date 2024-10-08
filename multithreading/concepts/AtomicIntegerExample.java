package multithreading.concepts;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerExample {

    private static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {
        Thread t1 = initializeThread();
        Thread t2 = initializeThread();
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Counter: "+counter);
    }

    public static void increment() {
        for (int i=0; i<10000; i++) {
            counter.getAndIncrement();
        }
    }

    public static Thread initializeThread() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                increment();
            }
        });
    }
}
