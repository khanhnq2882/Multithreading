package parallelization;

public class MergeSortMain {
    public static void main(String[] args) {
        int nums[] = {5, -1, 0, 7, 2, 3, 2, 1, 0, 1, 2};
        MergeSort mergeSort = new MergeSort(nums);
        mergeSort.sort();
        mergeSort.showArray();
    }
}
