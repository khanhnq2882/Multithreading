package forkjoin.framework;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class SimpleRecursiveAction extends RecursiveAction {

    private int simulatedWork;

    public SimpleRecursiveAction(int simulatedWork) {
        this.simulatedWork = simulatedWork;
    }

    @Override
    protected void compute() {
        // if the task is too large then we split it and execute the tasks in parallel
        if( simulatedWork > 100 ) {
            System.out.println("Parallel execution and split the tasks..." + simulatedWork);
            SimpleRecursiveAction simpleRecursiveAction1 = new SimpleRecursiveAction(simulatedWork/2);
            SimpleRecursiveAction simpleRecursiveAction2 = new SimpleRecursiveAction(simulatedWork/2);
            simpleRecursiveAction1.fork();
            simpleRecursiveAction2.fork();
        } else {
            System.out.println("No need for parallel execution, sequential is OK for this task..." + simulatedWork);
        }
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        SimpleRecursiveAction action = new SimpleRecursiveAction(800);
        pool.invoke(action);
    }
}
