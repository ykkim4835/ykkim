package com.meta.component;

public class InsertionSort {

	public void insertionSort(int arr[], int len) {
		int i, j;
		int data;

		for (i = 1; i < len; i++) {
			data = arr[i];
			for (j = i - 1; j >= 0; j--) {
				if (arr[j] > data) {
					arr[j + 1] = arr[j];
				} else {
					break;
				}
			}
			arr[j + 1] = data;
		}
	}

}
