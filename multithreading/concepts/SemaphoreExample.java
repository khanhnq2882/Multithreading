package multithreading.concepts;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

class Downloader {
    // Semaphore allows up to 3 threads to load data simultaneously
    private static Semaphore semaphore = new Semaphore(3, true);

    public static void download() {
        try {
            // require permit from semaphore. if permit is available, thread will download data
            semaphore.acquire();
            downloadData();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // after data is downloaded, need to release permit to avoid deadlock and waste of resources
            semaphore.release();
        }
    }

    private static void downloadData() {
        try {
            System.out.println("Downloading data from the web...");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

public class SemaphoreExample {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        // 12 threads
        for (int i=0; i<12; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    Downloader.download();
                }
            });
        }
        service.shutdown();
    }
}
