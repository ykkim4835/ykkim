package com.meta.main;

import com.meta.component.BubbleSort;

public class MainClass {

	public static void main(String[] args) {
		BubbleSort bubbleSort = new BubbleSort();

		int arr[] = { 5, 10, 9, 100, 13, 21, 89 };

		bubbleSort.bubbleSort(arr, arr.length);

		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
	}

}
