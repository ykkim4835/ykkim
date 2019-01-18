package com.meta.component;

public class LinkedList {

	Node head;
	Node tail;
	Node cur;
	Node before;
	int numOfData;

	public LinkedList() {
		head = new Node();
		tail = head;
		cur = null;
		before = null;
		head.next = null;
		tail.next = null;
		numOfData = 0;
	}

	public void add(int readData) {
		Node newNode = new Node();
		newNode.data = readData;
		newNode.next = null;
		tail.next = newNode;
		tail = newNode;

		newNode.idx = numOfData;
		numOfData++;
	}
	
	public void set(int idx, int readData) {
		get(idx);
		cur.data = readData;
	}

	public int get(int idx) {
		before = head;
		cur = head.next;

		while (true) {
			if (cur.idx == idx) {
				break;
			}
			before = cur;
			cur = cur.next;
		}
		return cur.data;
	}
	
	public void remove(int idx) {
		before = head;
		cur = head.next;
		
		while (true) {
			if (cur.idx == idx) {
				break;
			}
			before = cur;
			cur = cur.next;
		}
		before.next = cur.next;
		cur = before;
		
		while(cur.next != null) { // 삭제후 인덱스를 당겨주기 위한 로직
			cur = cur.next;
			cur.idx--;
		}
		numOfData--;
	}

	public int size() {
		return numOfData;
	}

}
