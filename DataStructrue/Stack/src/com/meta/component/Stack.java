package com.meta.component;

public class Stack {
	
	Node head;
	
	public Stack() {
		head = null;
	}
	
	public boolean isEmpty() {
		if(head == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public void push(int data) {
		Node newNode = new Node();
		newNode.data = data;
		newNode.next = head;		
		head = newNode;
	}
	
	public void Pop() {
		if(isEmpty()) {
			System.out.println("데이터가 없습니다.");
		} else {
			head = head.next;			
		}
	}
	
	public int peek() {
		if(isEmpty()) {
			return -1;
		} else {
			return head.data;			
		}
	}
	
}
