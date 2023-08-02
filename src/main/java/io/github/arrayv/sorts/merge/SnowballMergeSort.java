package io.github.arrayv.sorts.merge;

import io.github.arrayv.main.ArrayVisualizer;
import io.github.arrayv.sorts.templates.Sort;

/*

CODED FOR ARRAYV BY PCBOYGAMES

------------------------------
- SORTING ALGORITHM MADHOUSE -
------------------------------

*/
final public class SnowballMergeSort extends Sort {
    public SnowballMergeSort(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);
        this.setSortListName("Snowball Merge");
        this.setRunAllSortsName("Snowball Merge Sort");
        this.setRunSortName("Snowball Mergesort");
        this.setCategory("Impractical Sorts");
        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(true);
        this.setUnreasonableLimit(8192);
        this.setBogoSort(false);
    }

    protected void snowballs(int[] array, int start, int end) {
        int gap = 0;
        int begin = start;
        while (gap != 1) {
            gap = 1;
            for (int i = begin - 1 > start ? begin - 1 : start; i + gap < end; i++) {
                if (Reads.compareIndices(array, i, i + gap, 0.001, true) > 0) {
                    if (gap > 2)
                        Writes.reversal(array, i, i + gap, 0.01, true, false);
                    else
                        Writes.swap(array, i, i + gap, 0.01, true, false);
                    if (gap == 1)
                        begin = i;
                    gap *= 2;
                }
            }
        }
    }

    @Override
    public void runSort(int[] array, int currentLength, int bucketCount) {
        int len = 2;
        int index = 0;
        while (len < currentLength) {
            index = 0;
            while (index + len <= currentLength) {
                if (len == 2) {
                    if (Reads.compareIndices(array, index, index + 1, 0.001, true) > 0)
                        Writes.swap(array, index, index + 1, 0.1, true, false);
                } else
                    snowballs(array, index, index + len);
                index += len;
            }
            if (index != currentLength)
                snowballs(array, index, currentLength);
            len *= 2;
        }
        snowballs(array, 0, currentLength);
    }
}