package threads.manipulation;

class Worker1 implements Runnable {
    @Override
    public void run() {
        for (int i=0 ;i<10; i++) {
            System.out.println("Worker1 :" + i);
        }
    }
}

class Worker2 implements Runnable {
    @Override
    public void run() {
        for (int i=0 ;i<10; i++) {
            System.out.println("Worker2 :" + i);
        }
    }
}

public class App3 {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Worker1());
        Thread t2 = new Thread(new Worker2());
        t1.setPriority(6);
        t2.setPriority(Thread.MAX_PRIORITY);
        t1.start();
        t2.start();
        System.out.println("This is in the main thread...");
    }
}

