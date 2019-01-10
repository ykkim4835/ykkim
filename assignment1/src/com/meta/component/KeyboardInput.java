package com.meta.component;

import java.util.Scanner;

public class KeyboardInput implements WordInput {
	
	@Override
	public String execute() {
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		
		sc.close();
		
		return str;
	}

}
