package com.meta.main;

import java.io.IOException;

import com.meta.component.CountWord;
import com.meta.component.FileOutput;
import com.meta.component.KeyboardInput;
import com.meta.component.StrInput;
import com.meta.component.StrOutput;

public class KeyboardToFile {
	
	public static void main(String[] args) throws IOException {
		StrInput input = new KeyboardInput();
		String str = input.execute();
		
		CountWord countWord = new CountWord();
		int strLength = countWord.execute(str);
		
		StrOutput output = new FileOutput();
		output.execute(strLength);
	}
	
}
