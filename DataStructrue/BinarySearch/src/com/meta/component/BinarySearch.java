package com.meta.component;

public class BinarySearch {

	// �����Ͱ� ������� �����̵� �迭������ ��ȿ
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
					last = mid - 1; // -1 ������ Ž�� ����� ������ �� ���� ������ ��������
				} else {
					first = mid + 1; // +1 ������ Ž�� ����� ������ �� ���� ������ ��������
				}
			}
		}

		return -1;
	}

}
