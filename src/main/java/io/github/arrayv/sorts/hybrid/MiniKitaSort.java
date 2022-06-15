package io.github.arrayv.sorts.hybrid;

import io.github.arrayv.main.ArrayVisualizer;
import io.github.arrayv.sorts.templates.Sort;
import io.github.arrayv.sorts.insert.BlockInsertionSort;

final public class MiniKitaSort extends Sort {

	public MiniKitaSort(ArrayVisualizer arrayVisualizer) {
		super(arrayVisualizer);

		this.setSortListName("Mini Kita");
		this.setRunAllSortsName("Mini Kita Sort");
		this.setRunSortName("Mini Kitasort");
		this.setCategory("Hybrid Sorts");
		this.setBucketSort(false);
		this.setRadixSort(false);
		this.setUnreasonablySlow(false);
		this.setUnreasonableLimit(0);
		this.setBogoSort(false);
	}

	private int block;
	private static final int minbin = 16;
	private BlockInsertionSort small;

	private void inc(int[] val, int[] tag, int[] tagloc, int[] offs, int bnd, int indice) {
		Reads.addComparison();
		Reads.addComparison();
		if ((val[indice] - offs[indice]) % block > (++val[indice] - offs[indice]) % block)
			if (++tagloc[indice] >= bnd)
				Writes.write(val, indice, -1, 1, false, true);
			else
				Writes.write(val, indice, offs[indice] + tag[tagloc[indice]] * block, 1, false, true);
	}

	private void merge(int[] array, int[] buf, int start, int mid, int end) {
		Writes.arraycopy(array, mid, buf, 0, end - mid, 1, false, true);
		int l = mid - 1, r = end - mid - 1, t = end;
		while (l >= start && r >= 0) {
			t--;
			if (Reads.compareValues(array[l], buf[r]) > 0)
				Writes.write(array, t, array[l--], 1, true, false);
			else
				Writes.write(array, t, buf[r--], 1, true, false);
		}
		while (r >= 0)
			Writes.write(array, --t, buf[r--], 1, true, false);
	}

	private void blockmerge(int[] array, int[] buf, int[] tags, int[] tags2, int start, int a, int m, int b) {
		int mid = start + (m - a) * block, c = 0, l = 0, tag = 0;
		int[] offsets = new int[] { start, mid },
				ptrs = new int[] { start + tags[a] * block, mid + tags[m] * block },
				bufs = new int[] { ptrs[0], ptrs[1] },
				cnts = new int[2],
				tagptrs = new int[] { a, m },
				tagbufs = new int[] { a, m };
		Writes.changeAllocAmount(
				offsets.length + ptrs.length + bufs.length + cnts.length + tagptrs.length + tagbufs.length);
		for (int i = 0; i < 2 * block; i++) {
			if (Reads.compareValues(tagptrs[0], m) < 0 && (Reads.compareValues(tagptrs[1], b) == 0
					|| Reads.compareIndices(array, ptrs[0], ptrs[1], 0.25, true) <= 0)) {
				Writes.write(buf, i, array[ptrs[0]], 0.5, false, true); //
				inc(ptrs, tags, tagptrs, offsets, m, 0);
				Writes.changeAuxWrites(1);
				cnts[0]++;
			} else {
				Writes.write(buf, i, array[ptrs[1]], 0.5, false, true);
				inc(ptrs, tags, tagptrs, offsets, b, 1);
				Writes.changeAuxWrites(1);
				cnts[1]++;
			}
		}
		while (Reads.compareValues(tagptrs[0], m) < 0 || Reads.compareValues(tagptrs[1], b) < 0) {
			l = Reads.compareIndices(cnts, 0, 1, 0, false) < 0 ? 1 : 0;
			for (c = 0; c++ < block;) {
				if (Reads.compareValues(tagptrs[0], m) < 0
						&& (Reads.compareValues(tagptrs[1], b) == 0
								|| Reads.compareIndices(array, ptrs[0], ptrs[1], 0.25, true) <= 0)) {
					Writes.write(array, bufs[l], array[ptrs[0]], 0.5, true, false);
					inc(ptrs, tags, tagptrs, offsets, m, 0);
					cnts[0]++;
					Writes.changeAuxWrites(1);
				} else {
					Writes.write(array, bufs[l], array[ptrs[1]], 0.5, true, false);
					inc(ptrs, tags, tagptrs, offsets, b, 1);
					cnts[1]++;
					Writes.changeAuxWrites(1);
				}
				inc(bufs, tags, tagbufs, offsets, l > 0 ? b : m, l);
			}
			Writes.changeAuxWrites(1);
			cnts[l] -= block;
			Writes.write(tags2, tag++, tags[tagbufs[l] - 1] + (m - a) * l, 1, false, true);
		}
		int w = 0, t = a;
		while (cnts[0] > 0) {
			Writes.arraycopy(buf, w, array, start + tags[tagbufs[0]] * block, block, 1, true, false);
			Writes.write(tags, a++, tags[tagbufs[0]++], 1, false, true);
			w += block;
			cnts[0] -= block;
			Writes.changeAuxWrites(1);
		}
		while (cnts[1] > 0) {
			Writes.arraycopy(buf, w, array, mid + tags[tagbufs[1]] * block, block, 1, true, false);
			Writes.write(tags, a++, tags[tagbufs[1]++] + m - t, 1, false, true);
			w += block;
			cnts[1] -= block;
			Writes.changeAuxWrites(1);
		}
		Writes.arraycopy(tags2, 0, tags, a, tag, l, true, false);
		Writes.changeAllocAmount(
				-(offsets.length + ptrs.length + bufs.length + cnts.length + tagptrs.length + tagbufs.length));
	}

	private void index(int[] array, int[] tmp, int[] table, int a, int b) {
		for (int i = a, j = 0; i < b; i += block, j++) {
			if (Reads.compareOriginalValues(j, table[j]) == 0)
				continue;
			Writes.arraycopy(array, i, tmp, 0, block, 0, false, true);
			int k = j, n = table[j];
			do {
				Writes.arraycopy(array, a + n * block, array, a + k * block, block, 1, true, false);
				Writes.write(table, k, k, 1, true, false);
				k = n;
				n = table[n];
			} while (Reads.compareOriginalValues(j, n) != 0);
			Writes.arraycopy(tmp, 0, array, a + k * block, block, 1, true, false);
			Writes.write(table, k, k, 1, true, false);
		}
	}

	private void kita(int[] array, int a, int c) {
		small = new BlockInsertionSort(arrayVisualizer);
		if (c - a <= 32) {
			small.customInsertSort(array, a, c, 0.5, false);
			return;
		}
		int sl = (31 - Integer.numberOfLeadingZeros(c - a)) / 2, s = block = 1 << sl;
		int b = c - (c - a) % s, k = (c - a) / s, B[] = Writes.createExternalArray(2 * s),
				t0[] = Writes.createExternalArray(k), t1[] = Writes.createExternalArray(k);
		for (int i = a; i < b; i += minbin) {
			int j = Math.min(i + minbin, b);
			small.customInsertSort(array, i, j, 0.5, false);
		}
		for (int i = 0; i < k; i++)
			Writes.write(t0, i, i % 4, 0, false, true);
		for (int j = minbin; j < b - a; j *= 2) {
			for (int i = a; i + j < b; i += 2 * j) {
				int p = Math.min(i + 2 * j, b);
				if (j > 2 * s)
					blockmerge(array, B, t0, t1, i, (i - a) / s, (i + j - a) / s, (p - a) / s);
				else
					merge(array, B, i, i + j, p);
			}
		}
		index(array, B, t0, a, b);
		Writes.deleteExternalArrays(t0, t1);
		if (b < c) {
			kita(array, b, c);
			merge(array, B, a, b, c);
		}
		Writes.deleteExternalArray(B);
	}

	@Override
	public void runSort(int[] array, int currentLength, int bucketCount) {
		kita(array, 0, currentLength);
	}
}
