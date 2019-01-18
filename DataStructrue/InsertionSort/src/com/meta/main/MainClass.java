package com.meta.main;

import com.meta.component.InsertionSort;

public class MainClass {

	public static void main(String[] args) {
		InsertionSort insertionSort = new InsertionSort();

		int arr[] = { 5, 10, 9, 100, 13, 21, 89 };

		insertionSort.insertionSort(arr, arr.length);

		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
	}

}
