package com.meta.component;

public class ConsoleOutput implements WordOutput{
	
	@Override
	public void execute(int strLength) {
		System.out.println(strLength);
	}

}
