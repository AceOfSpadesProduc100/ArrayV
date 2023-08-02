package io.github.arrayv.sorts.bogo;

import io.github.arrayv.main.ArrayVisualizer;
import io.github.arrayv.sorts.templates.BogoSorting;

/*

CODED FOR ARRAYV BY PCBOYGAMES

------------------------------
- SORTING ALGORITHM MADHOUSE -
------------------------------

It's one simple change that makes it work twice as hard.

*/
public final class ZootSort extends BogoSorting {
    public ZootSort(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);
        this.setSortListName("Zoot");
        this.setRunAllSortsName("Zoot Sort");
        this.setRunSortName("Zootsort");
        this.setCategory("Bogo Sorts");

        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(true);
        this.setUnreasonableLimit(10);
        this.setBogoSort(true);
    }

    @Override
    public void runSort(int[] array, int currentLength, int bucketCount) {
        while (!isArraySorted(array, currentLength)) {
            bogoSwap(array, 0, currentLength, false);
            Writes.reversal(array, 0, currentLength - 1, delay, true, false);
        }
    }
}