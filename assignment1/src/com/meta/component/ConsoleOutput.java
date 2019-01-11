package com.meta.component;

public class ConsoleOutput implements StrOutput{
	
	@Override
	public void execute(int strLength) {
		System.out.println(strLength);
	}

}
