package threads.manipulation;

class DaemonWorker implements Runnable {
    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Daemon thread is running...");
        }
    }
}

class NormalWorker implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Normal thread finishes execution...");
    }
}

public class App2 {
    public static void main(String[] args) {
        // daemon thread
        Thread t1 = new Thread(new DaemonWorker());
        // worker thread
        Thread t2 = new Thread(new NormalWorker());
        t1.setDaemon(true);
        t1.start();
        t2.start();
    }
}
