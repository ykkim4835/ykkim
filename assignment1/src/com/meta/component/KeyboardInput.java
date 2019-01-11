package com.meta.component;

import java.util.Scanner;

public class KeyboardInput implements StrInput {
	
	@Override
	public String execute() {
		Scanner sc = new Scanner(System.in);
		System.out.print("���ڿ��� �Է����ּ���: ");
		String str = sc.nextLine();
		
		sc.close();
		
		return str;
	}

}
