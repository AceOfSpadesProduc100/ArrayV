package io.github.arrayv.sorts.exchange;

import io.github.arrayv.main.ArrayVisualizer;
import io.github.arrayv.sorts.templates.Sort;

final public class GnomePopSort extends Sort {
    public GnomePopSort(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);
        this.setSortListName("Gnome Pop");
        this.setRunAllSortsName("Gnome Pop Sort");
        this.setRunSortName("Gnome Popsort");
        this.setCategory("Exchange Sorts");

        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.setBogoSort(false);
    }

    protected void gnome(int[] array, int start, int end, boolean invert) {
        int c = invert ? -1 : 1;
        for (int i = start + 1; i < end; i++) {
            int j = i - 1;
            while (j >= start && Reads.compareIndices(array, j, j + 1, 0.5, true) == c) {
                Writes.swap(array, j, j + 1, 0.1, true, false);
                j--;
            }
        }
    }

    protected void pop(int[] array, int start, int end, int order, boolean invert) {
        if (start >= end)
            return;
        if (end - start <= 4 || order < 1) {
            this.gnome(array, start, end, !invert);
            return;
        }
        int quart = (end - start + 1) / 4;
        if (order == 1) {
            Writes.recursion();

            this.gnome(array, start, start + quart, invert);
            Writes.recursion();
            this.gnome(array, start + quart, end - 2 * quart, !invert);
            Writes.recursion();
            this.gnome(array, start, end - 2 * quart, !invert);
            Writes.recursion();
            this.gnome(array, end - 2 * quart, end - quart, !invert);
            Writes.recursion();
            this.gnome(array, end - quart, end, invert);
            Writes.recursion();
            this.gnome(array, end - 2 * quart, end, invert);
            Writes.recursion();
            this.gnome(array, start, end, invert);
        } else {
            Writes.recordDepth(order - 1);
            Writes.recursion();

            this.pop(array, start, start + quart, order - 1, invert);
            Writes.recursion();
            this.pop(array, start + quart, end - 2 * quart, order - 1, !invert);
            Writes.recursion();
            this.pop(array, start, end - 2 * quart, order - 1, !invert);
            Writes.recursion();
            this.pop(array, end - 2 * quart, end - quart, order - 1, !invert);
            Writes.recursion();
            this.pop(array, end - quart, end, order - 1, invert);
            Writes.recursion();
            this.pop(array, end - 2 * quart, end, order - 1, invert);
            Writes.recursion();
            this.pop(array, start, end, order - 1, invert);
        }
    }

    @Override
    public void runSort(int[] array, int currentLength, int bucketCount) {
        this.pop(array, 0, currentLength, 1, false);
    }
}