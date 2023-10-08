package io.github.arrayv.sorts.insert;

import io.github.arrayv.main.ArrayVisualizer;
import io.github.arrayv.sortdata.SortMeta;
import io.github.arrayv.sorts.templates.Sort;

/*

CODED FOR ARRAYV BY PCBOYGAMES
IN COLLABORATION WITH CONTROL AND MG-2018

------------------------------
- SORTING ALGORITHM MADHOUSE -
------------------------------

*/
@SortMeta(name = "Par(x) Shell (PCBoy Version)")
public final class PCBoysParShellSort extends Sort {

	// Mess with these and see what you can come up with.
	// Both of these are used in line 116.
	double mult = 1.5;

	protected double threshold(int x) {
		return Math.sqrt(x);
	}

	// The algorithm itself.

	public PCBoysParShellSort(ArrayVisualizer arrayVisualizer) {
		super(arrayVisualizer);
	}

	int lastgap;

	protected int stablereturn(int a) {
		return arrayVisualizer.doingStabilityCheck() ? arrayVisualizer.getStabilityValue(a) : a;
	}

	protected int par(int[] array, int len) {
		boolean[] max = new boolean[len];
		int maximum = stablereturn(array[0]);
		for (int i = 1; i < len; i++) {
			if (stablereturn(array[i]) > maximum) {
				maximum = stablereturn(array[i]);
				max[i] = true;
			}
		}
		int i = len - 1;
		int p = 1;
		int j = len - 1;
		while (j >= 0 && i >= p) {
			while (!max[j] && j > 0)
				j--;
			maximum = stablereturn(array[j]);
			while (maximum <= stablereturn(array[i]) && i >= p)
				i--;
			if (stablereturn(array[j]) > stablereturn(array[i]) && p < i - j)
				p = i - j;
			j--;
		}
		return p;
	}

	protected void shellPass(int[] array, int currentLength, int gap, int par) {
		if (gap >= lastgap)
			return;
		if (gap == lastgap - 1 && gap != 1)
			return;
		lastgap = gap;
		arrayVisualizer.setExtraHeading(" / Par(X): " + par + " / Gap: " + gap);
		for (int h = gap, i = h; i < currentLength; i++) {
			int v = array[i];
			int j = i;
			boolean w = false;
			Highlights.markArray(1, j);
			Highlights.markArray(2, j - h);
			Delays.sleep(0.25);
			while (j >= h && Reads.compareValues(array[j - h], v) == 1) {
				Highlights.markArray(1, j);
				Highlights.markArray(2, j - h);
				Delays.sleep(0.25);
				Writes.write(array, j, array[j - h], 0.25, true, false);
				j -= h;
				w = true;
			}
			if (w) {
				Writes.write(array, j, v, 0.25, true, false);
			}
		}
	}

	public static int validateAnswer(int answer) {
		if (answer < 1)
			return 1;
		return answer;
	}

	@Override
	public void runSort(int[] array, int currentLength, int constantdiv) throws Exception {
		double truediv = constantdiv;
		int lastpar = currentLength;
		lastgap = currentLength;
		while (true) {
			int par = this.par(array, currentLength);
			int passpar = par;
			if (par >= lastpar)
				par = lastpar - (int) truediv;
			if (par / (int) truediv <= 1) {
				shellPass(array, currentLength, 1, par);
				break;
			}
			shellPass(array, currentLength, (int) ((par / (int) truediv) + par % (int) truediv), passpar);
			if (lastpar - par <= threshold(lastpar))
				truediv *= mult;
			lastpar = par;
		}
		arrayVisualizer.setExtraHeading("");
	}
}