package parallelization;

public class MergeSort {
    private int[] nums;
    //merge sort is not an in-place algorithm
    private int[] tempArray;

    public MergeSort(int[] nums) {
        this.nums = nums;
        this.tempArray = new int[nums.length];
    }

    public void sort() {
        mergeSort(0, nums.length-1);
    }

    private void mergeSort(int low, int high) {
        // base case
        if (low >= high) {
            return;
        }

        // middle item
        int middle = (low + high) / 2;

        // we keep splitting the problem into smaller and smaller sub-problems
        // until a given array contains just 1 item
        mergeSort(low, middle);
        mergeSort(middle+1, high);

        // we have to combine the sub-solutions
        merge(low, middle, high);
    }

    private void merge(int low, int middle, int high) {

        // Copy nums[i] -> temp[i]
        for (int i = low; i <= high; i++) {
            tempArray[i] = nums[i];
        }

        int i = low;
        int j = middle + 1;
        int k = low;

        // Copy the smallest values from either the left or the right side back
        // to the original array
        while ((i <= middle) && (j <= high)) {
            if (tempArray[i] <= tempArray[j]) {
                nums[k] = tempArray[i];
                i++;
            } else {
                nums[k] = tempArray[j];
                j++;
            }
            k++;
        }

        // Copy the rest of the left side of the array into the target array
        while (i <= middle) {
            nums[k] = tempArray[i];
            k++;
            i++;
        }
    }

    public void showArray() {
        for (int i=0; i<nums.length; i++) {
            System.out.print(nums[i]+" ");
        }
    }
}
