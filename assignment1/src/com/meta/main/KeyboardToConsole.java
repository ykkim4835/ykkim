package com.meta.main;

import com.meta.component.CommonInput;
import com.meta.component.CommonOutput;
import com.meta.component.CountWord;

public class KeyboardToConsole {
	
	public static void main(String[] args) {
		CommonInput commonInput = new CommonInput();
		String str = commonInput.keyboard();
		
		CountWord countWord = new CountWord();
		int strLength = countWord.execute(str);
		
		CommonOutput commonOutput = new CommonOutput();
		commonOutput.console(strLength);
	}

}
