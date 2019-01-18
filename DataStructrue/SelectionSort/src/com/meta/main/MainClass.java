package com.meta.main;

import com.meta.component.SelectionSort;

public class MainClass {

	public static void main(String[] args) {
		SelectionSort selectionSort = new SelectionSort();
		int[] arr = { 1, 90, 8, 20, 6, 150, 3 };

		selectionSort.selectionSort(arr, arr.length);
		
		for(int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
	}

}
