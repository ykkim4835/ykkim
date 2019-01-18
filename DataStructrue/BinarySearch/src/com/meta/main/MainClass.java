package com.meta.main;

import java.util.Scanner;

import com.meta.component.BinarySearch;

public class MainClass {

	public static void main(String[] args) {

		int[] arr = { 1, 3, 5, 10, 22, 29, 31 };

		Scanner sc = new Scanner(System.in);
		int target = sc.nextInt();

		BinarySearch bSearch = new BinarySearch();
		int result = bSearch.binarySearch(arr, arr.length, target);

		if (result != -1) {
			System.out.println("�Է��� ����(" + target + ")" + "�� arr[" + result + "] �� �ֽ��ϴ�.");
		} else {
			System.out.println("�Է��� ����(" + target + ")" + "�� ã�� �� �����ϴ�.");
		}

		sc.close();
	}

}
