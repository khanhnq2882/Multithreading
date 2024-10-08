package parallelization;

import java.util.Random;

public class ParallelMergeSortMain {
    public static Random random = new Random();

    public static void main(String[] args) throws Throwable {
        int numOfThreads  = Runtime.getRuntime().availableProcessors();
        System.out.println("Number of threads/cores: " + numOfThreads);
        System.out.println("");
        int[] numbers = createRandomArray();
        ParallelMergeSort sort = new ParallelMergeSort(numbers);
        long startTime1 = System.currentTimeMillis();
        sort.parallelMergeSort(0, numbers.length-1, numOfThreads);
        sort.showResult();
        long endTime1 = System.currentTimeMillis();
        System.out.printf("Time taken for 100 000 000 elements parallel =>  %6d ms \n", endTime1 - startTime1);
        System.out.println("");

        startTime1 = System.currentTimeMillis();
        sort.mergeSort(0,numbers.length-1);
        endTime1 = System.currentTimeMillis();
        System.out.printf("Time taken for 100 000 000 elements sequential =>  %6d ms \n", endTime1 - startTime1);
        System.out.println("");
    }

    public static int[] createRandomArray() {
        int[] a = new int[10];
        for (int i = 0; i < 10; i++) {
            a[i] = random.nextInt(1000000000);
        }
        return a;
    }
}
