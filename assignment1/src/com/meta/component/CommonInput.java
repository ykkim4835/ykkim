package com.meta.component;

public class CommonInput {
	
	KeyboardInput keyboardInput;
	FileInput fileInput;
	
	public CommonInput() {
		keyboardInput = new KeyboardInput();
		fileInput = new FileInput();
	}
	
	public String keyboard() {
		String str = keyboardInput.execute();
		
		return str;
	}
	
	public String file() throws Exception {
		String str = fileInput.execute();
		
		return str;
	}
	
}
