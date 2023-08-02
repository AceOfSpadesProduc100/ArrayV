package io.github.arrayv.sorts.exchange;

import io.github.arrayv.main.ArrayVisualizer;
import io.github.arrayv.sorts.templates.Sort;

/*

CODED FOR ARRAYV BY PCBOYGAMES

------------------------------
- SORTING ALGORITHM MADHOUSE -
------------------------------

*/
final public class SirbbleSort extends Sort {
    public SirbbleSort(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);
        this.setSortListName("Sirbble");
        this.setRunAllSortsName("Sirbble Sort");
        this.setRunSortName("Sirbble Sort");
        this.setCategory("Exchange Sorts");

        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.setBogoSort(false);
    }

    protected int circle(int[] array, int a, int b) {
        int left = a;
        int right = b;
        while (left < right) {
            if (Reads.compareIndices(array, left, right, 0.1, true) > 0)
                Writes.swap(array, left, right, 0.1, true, false);
            left++;
            right--;
        }
        return left - 1;
    }

    @Override
    public void runSort(int[] array, int currentLength, int bucketCount) {
        for (int i = 0; i < currentLength; i++) {
            int mid = circle(array, i, currentLength - 1);
            for (int j = mid; j >= i; j--)
                if (Reads.compareIndices(array, j, j + 1, 0.1, true) > 0)
                    Writes.swap(array, j, j + 1, 0.1, true, false);
        }
    }
}