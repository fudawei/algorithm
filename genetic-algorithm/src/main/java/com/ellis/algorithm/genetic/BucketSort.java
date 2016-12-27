package com.ellis.algorithm.genetic;

public class BucketSort {

	public void sort(int[] keys, int from, int len, int max) {
		int[] temp = new int[len];
		int[] count = new int[max];

		for (int i = 0; i < len; i++) {
			count[keys[from + i]]++;
		}

		// calculate position info
		for (int i = 1; i < max; i++) {
			count[i] = count[i] + count[i - 1];// 这意味着有多少数目小于或等于i，因此它也是position+
			// 1
		}
		System.arraycopy(keys, from, temp, 0, len);
		for (int k = len - 1; k >= 0; k--) { // 从最末到开头保持稳定性
			keys[--count[temp[k]]] = temp[k];// position +1 =count
		}
	}
}
