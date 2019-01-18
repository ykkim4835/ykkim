package com.meta.component;

public class SelectionSort {

	public void selectionSort(int arr[], int len) {
		int maxIdx;
		int temp;

		for (int i = 0; i < len - 1; i++) {
			maxIdx = i;
			for (int j = i + 1; j < len; j++) {
				if (arr[j] < arr[maxIdx]) { // �켱������ ���� �� ã��
					maxIdx = j;
				}
			}

			temp = arr[i];
			arr[i] = arr[maxIdx];
			arr[maxIdx] = temp;
		}
	}

}
