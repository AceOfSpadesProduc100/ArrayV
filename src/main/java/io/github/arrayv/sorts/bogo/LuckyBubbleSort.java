package io.github.arrayv.sorts.bogo;

import io.github.arrayv.main.ArrayVisualizer;
import io.github.arrayv.sorts.templates.BogoSorting;

/*

PORTED TO ARRAYV BY PCBOYGAMES

------------------------------
- SORTING ALGORITHM MADHOUSE -
------------------------------

*/
public final class LuckyBubbleSort extends BogoSorting {
    public LuckyBubbleSort(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);
        this.setSortListName("Lucky Bubble");
        this.setRunAllSortsName("Lucky Bubble Sort");
        this.setRunSortName("Lucky Bubblesort");
        this.setCategory("Bogo Sorts");

        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(true);
        this.setUnreasonableLimit(8192);
        this.setBogoSort(true);
        this.setQuestion("Enter the luck for this sort:", 50);
    }

    @Override
    public int validateAnswer(int answer) {
        if (answer < 1 || answer > 100)
            return 50;
        return answer;
    }

    @Override
    public void runSort(int[] array, int currentLength, int luck) {
        boolean anyswaps = true;
        while (anyswaps) {
            anyswaps = false;
            for (int i = 0; i < currentLength - 1; i++) {
                if (Reads.compareIndices(array, i, i + 1, 0.01, true) > 0) {
                    anyswaps = true;
                    if (randInt(1, 101) <= luck)
                        Writes.swap(array, i, i + 1, 0.01, true, false);
                }
            }
        }
    }
}
