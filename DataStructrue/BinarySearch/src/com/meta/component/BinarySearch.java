package com.meta.component;

public class BinarySearch {

	// 데이터가 순서대로 정렬이된 배열에서만 유효
	public int binarySearch(int arr[], int len, int target) {
		int first = 0;
		int last = len - 1;
		int mid;

		while (first <= last) {
			mid = (first + last) / 2;
			if (target == arr[mid]) {
				return mid;
			} else {
				if (target < arr[mid]) {
					last = mid - 1; // -1 이유는 탐색 대상이 없었을 때 무한 루프를 막기위함
				} else {
					first = mid + 1; // +1 이유는 탐색 대상이 없었을 때 무한 루프를 막기위함
				}
			}
		}

		return -1;
	}

}
