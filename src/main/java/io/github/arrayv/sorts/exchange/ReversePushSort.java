package io.github.arrayv.sorts.exchange;

import io.github.arrayv.main.ArrayVisualizer;
import io.github.arrayv.sorts.templates.Sort;

/*

CODED FOR ARRAYV BY PCBOYGAMES

------------------------------
- SORTING ALGORITHM MADHOUSE -
------------------------------

*/
final public class ReversePushSort extends Sort {
    public ReversePushSort(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);
        this.setSortListName("Reverse Push");
        this.setRunAllSortsName("Reverse Push Sort");
        this.setRunSortName("Reverse Pushsort");
        this.setCategory("Exchange Sorts");

        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.setBogoSort(false);
    }

    @Override
    public void runSort(int[] array, int currentLength, int bucketCount) {
        boolean anyswaps = true;
        int i = currentLength;
        int gap = 1;
        while (anyswaps) {
            anyswaps = false;
            i = currentLength;
            gap = 1;
            while (i - gap > 0) {
                Highlights.markArray(1, (i - 1) - gap);
                Highlights.markArray(2, i - 1);
                Delays.sleep(0.01);
                if (Reads.compareIndices(array, (i - 1) - gap, i - 1, 0.5, true) > 0) {
                    for (int j = 1; j <= gap; j++)
                        Writes.swap(array, i - 1, (i - 1) - j, 0.01, true, false);
                    anyswaps = true;
                    gap++;
                } else
                    i--;
            }
        }
    }
}