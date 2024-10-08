package interthread.communication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class LockWorker {

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void produce() throws InterruptedException {
        lock.lock();
        System.out.println("Producer method...");
        // wait()
        condition.await();
        System.out.println("Again the producer method...");
    }

    public void consume() throws InterruptedException {
        // we want to make sure that we start with the producer()
        Thread.sleep(3000);
        lock.lock();
        System.out.println("Consumer method...");
        Thread.sleep(10000);
        // notify()
        condition.signal();
        lock.unlock();
    }
}

public class App5 {
    public static void main(String[] args) {
        LockWorker lockWorker = new LockWorker();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lockWorker.produce();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lockWorker.consume();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
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
    }
}
