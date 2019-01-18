package com.meta.main;

import com.meta.component.Queue;

public class MainClass {
	
	public static void main(String[] args) {
		Queue queue = new Queue();
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);
		
		queue.dequeue();
		queue.dequeue();
		
		if(queue.peek() == -1) {
			System.out.println("�����Ͱ� �����ϴ�.");
		} else {
			System.out.println(queue.peek());
		}
	}
	
}
