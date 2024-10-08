package forkjoin.framework;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class PrintingInteger extends RecursiveAction {

    private List<Integer> nums;

    public PrintingInteger(List<Integer> nums) {
        this.nums = nums;
    }

    @Override
    protected void compute() {
        // the problem is small enough (containing 2 items) so we use
        // standard sequential print operation
        if(nums.size() < 2) {
            for(Integer num : nums)
                System.out.print(num+" ");
        } else {
            // otherwise we split the problem into 2 sub-problems
            // [a,b,c] --> [a] and [b,c]
            // [a,b,c,d] --> [a,b] and [c,d]
            List<Integer> left = nums.subList(0, nums.size()/2);
            List<Integer> right = nums.subList(nums.size()/2,  nums.size());

            PrintingInteger action1 = new PrintingInteger(left);
            PrintingInteger action2 = new PrintingInteger(right);

            // it means these actions are thrown into the pool
            // fork-join assigns different threads to different tasks
            // so these will be executed with different treads
            invokeAll(action1, action2);
        }
    }

    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(1,2,3,4,5,6,7,8,9);
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        PrintingInteger action = new PrintingInteger(integers);
        pool.invoke(action);
    }
}
