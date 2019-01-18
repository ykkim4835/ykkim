package com.meta.main;

import com.meta.component.LinkedList;

public class MainClass {

	public static void main(String[] args) {
		LinkedList list = new LinkedList();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(6);
		list.add(7);
		list.add(8);
		
		
		System.out.println("사이즈: " + list.size());
		for(int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		
		list.remove(5);
		
		System.out.println("==============삭제 후==============");
		System.out.println("사이즈: " + list.size());
		for(int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		
		list.set(5, 2);
		
		System.out.println("==============수정 후==============");
		System.out.println("사이즈: " + list.size());
		for(int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
	
}
