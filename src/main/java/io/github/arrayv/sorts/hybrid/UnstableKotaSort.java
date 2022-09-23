package io.github.arrayv.sorts.hybrid;

import io.github.arrayv.main.ArrayVisualizer;
import io.github.arrayv.sorts.templates.BlockMergeSorting;
import io.github.arrayv.sorts.insert.BlockInsertionSort;

/*
 * 
MIT License

Copyright (c) 2021 aphitorite

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 *
 */

final public class UnstableKotaSort extends BlockMergeSorting {
	public UnstableKotaSort(ArrayVisualizer arrayVisualizer) {
		super(arrayVisualizer);

		this.setSortListName("Unstable Kota");
		this.setRunAllSortsName("Unstable Kota Sort");
		this.setRunSortName("Unstable Kotasort");
		this.setCategory("Hybrid Sorts");
		this.setBucketSort(false);
		this.setRadixSort(false);
		this.setUnreasonablySlow(false);
		this.setUnreasonableLimit(0);
		this.setBogoSort(false);
	}

	@Override
	protected void binaryInsertion(int[] array, int a, int b) {
		BlockInsertionSort smallSort = new BlockInsertionSort(this.arrayVisualizer);
		smallSort.customInsertSort(array, a, b, 0.25, false);
	}

	@Override
	protected boolean boundCheck(int[] array, int a, int m, int b) {
		return Reads.compareIndices(array, m - 1, m, 1, true) <= 0;
	}

	private void blockSelect(int[] array, int a, int b, int bLen) {
		for (; a < b; a += bLen) {
			int min = a;

			for (int i = a + bLen; i < b; i += bLen) {
				int cmp = Reads.compareIndices(array, i, min, 1, true);

				if (cmp == -1 || (cmp == 0 && Reads.compareIndices(array, i + bLen - 1, min + bLen - 1, 1, true) < 0))
					min = i;
			}
			if (a != min)
				this.multiSwap(array, a, min, bLen);
		}
	}

	private void blockMerge(int[] array, int a, int m, int b, int bLen) {
		if (this.boundCheck(array, a, m, b))
			return;

		int bufLen = 2 * bLen;
		int a1 = this.rightBinSearch(array, a, m, array[m]);

		if (m - a1 <= bLen)
			a = m - bufLen;
		else
			a = a1 - (a1 - a) % bLen;

		int r1 = 0, r2 = Math.min(b - m, bufLen);

		while (r1 < r2) {
			int ml = (r1 + r2) / 2;

			if (Reads.compareIndices(array, m + ml, a + (bufLen - ml) - 1, 1, true) > 0)
				r2 = ml;
			else
				r1 = ml + 1;
		}
		int l = bufLen - r1, r = r1, i = a + l, j = m + r;

		boolean left = l >= r;
		int k = left ? i - l : j - r;

		int c = bLen;

		while (i < m && j < b) {
			if (Reads.compareIndices(array, i, j, 1, true) <= 0) {
				Writes.swap(array, k++, i++, 1, true, false);
				l++;
			} else {
				Writes.swap(array, k++, j++, 1, true, false);
				r++;
			}
			if (--c == 0) {
				if (left)
					l -= bLen;
				else
					r -= bLen;

				left = l >= r;
				k = left ? i - l : j - r;

				c = bLen;
			}
		}
		while (i < m) {
			Writes.swap(array, k++, i++, 1, true, false);
			l++;

			if (--c == 0) {
				if (left)
					l -= bLen;
				else
					r -= bLen;

				left = l >= r;
				k = left ? i - l : j - r;

				c = bLen;
			}
		}
		while (j < b) {
			Writes.swap(array, k++, j++, 1, true, false);
			r++;

			if (--c == 0) {
				if (left)
					l -= bLen;
				else
					r -= bLen;

				c = bLen;
				break;
			}
		}
		int rem = bLen - c, b1 = Math.min(j, b - rem);

		this.multiSwap(array, k - rem, b1, rem); // swap remainder to end (r buffer)
		r -= rem;

		// l and r buffers are divisible by bLen
		this.multiSwap(array, m - l, a, l); // swap l buffer to front
		this.multiSwap(array, b1 - r, a + l, r); // swap r buffer to front
		this.binaryInsertion(array, a, a + bufLen); // sort buffer

		this.blockSelect(array, a + bufLen, b1, bLen);
	}

	@Override
	public void runSort(int[] array, int length, int bucketCount) {
		int a = 0, b = length, j = this.MRUN;

		if (this.buildRuns(array, a, b))
			return;

		int s = 4, c = 2;

		for (; j < b; j *= 2) {
			if (--c == 0) {
				s *= 2;
				c = 2;
			}
			for (int i = a; i + j < b; i += 2 * j)
				this.blockMerge(array, i, i + j, Math.min(i + 2 * j, b), s);
		}
	}
}