package forkjoin.framework;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SimpleRecursiveTask extends RecursiveTask<Double> {

    private double num;

    public SimpleRecursiveTask(double num) {
        this.num = num;
    }

    @Override
    protected Double compute() {
        if (num > 100) {
            // parallel: we split the problem into 2 sub-problems
            System.out.println("Parallel execution so split the task..." +num);

            SimpleRecursiveTask task1 = new SimpleRecursiveTask(num/2);
            SimpleRecursiveTask task2 = new SimpleRecursiveTask(num/2);

            // we add the tasks to the thread pool (parallel)
            task1.fork();
            task2.fork();

            // wait for these tasks to be finished
            double subSolution = 0;
            subSolution += task1.join();
            subSolution += task2.join();
            return subSolution;
        } else {
            // the problem is too small - we can use sequential approach
            System.out.println("The task is small... we can execute it sequentially: "+num);
            return 2 * num;
        }
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        SimpleRecursiveTask simpleRecursiveTask = new SimpleRecursiveTask(10000);
        System.out.println(pool.invoke(simpleRecursiveTask));
    }

}
