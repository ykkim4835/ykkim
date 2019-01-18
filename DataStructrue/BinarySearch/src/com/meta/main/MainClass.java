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
			System.out.println("입력한 숫자(" + target + ")" + "는 arr[" + result + "] 에 있습니다.");
		} else {
			System.out.println("입력한 숫자(" + target + ")" + "를 찾을 수 없습니다.");
		}

		sc.close();
	}

}
