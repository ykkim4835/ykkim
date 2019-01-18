package com.meta.component;

public class Queue {
	
	Node front;
	Node rear;
	
	public Queue() {
		front = null;
		rear = null;
	}
	
	public boolean isEmpty() {
		if(front == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public void enqueue(int data) {
		Node newNode = new Node();
		newNode.next = null;
		newNode.data = data;
		
		if(isEmpty()) {
			front = newNode;
			rear = newNode;
		} else {
			rear.next = newNode;
			rear = newNode;
		}
	}
	
	public void dequeue() {
		if(isEmpty()) {
			System.out.println("데이터가 없습니다.");
		} else {
			front = front.next;		
		}
	}
	
	public int peek() {
		if(isEmpty()) {
			return -1;
		} else {
			return front.data;			
		}
	}
}
