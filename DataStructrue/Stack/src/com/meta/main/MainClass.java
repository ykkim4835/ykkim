package com.meta.main;

import com.meta.component.Stack;

public class MainClass {
	
	public static void main(String[] args) {
		Stack stack = new Stack();
		
		stack.push(1);
		stack.push(2);
		stack.push(3);
		
		stack.Pop();
		stack.Pop();
		
		if(stack.peek() == -1) {
			System.out.println("�����Ͱ� �����ϴ�.");
		} else {
			System.out.println(stack.peek());
		}
	}

}
