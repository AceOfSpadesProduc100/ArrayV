package io.github.arrayv.sorts.bogo;

import io.github.arrayv.main.ArrayVisualizer;
import io.github.arrayv.sorts.templates.BogoSorting;

public final class LessBodoSort extends BogoSorting {
  public LessBodoSort(ArrayVisualizer arrayVisualizer) {
    super(arrayVisualizer);

    this.setSortListName("Less Bodo");
    this.setRunAllSortsName("Less Bodo Sort (Buffed Boko Sort)");
    this.setRunSortName("Less Bodo Sort");
    this.setCategory("Bogo Sorts");

    this.setBucketSort(false);
    this.setRadixSort(false);
    this.setUnreasonablySlow(true);
    this.setUnreasonableLimit(2048);
    this.setBogoSort(true);
  }

  public void runSort(int[] array, int length, int bucketCount) {
    int s = 0;
    while (s < length - 1 && !isRangeSorted(array, s, length, false, true)) {
      int index = BogoSorting.randInt(s, length - 1),
          index2 = BogoSorting.randInt(0, length - 1);
      while (index < length - 1 && Reads.compareIndices(array, index, index2, 0.5, true) == 1) {
        Writes.swap(array, index, ++index, 0.075, true, false);
        index2 = BogoSorting.randInt(0, length - 1);
      }
      for (int i = s; i < length; i++) {
        boolean brk = false;
        for (int j = i + 1; j < length; j++) {
          if (Reads.compareIndices(array, i, j, 0.5, true) > 0) {
            brk = true;
            break;
          }
        }
        if (brk)
          break;
        s++;
      }
    }
  }
}