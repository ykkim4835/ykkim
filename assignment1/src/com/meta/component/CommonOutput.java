package com.meta.component;

import java.io.IOException;

public class CommonOutput {
	
	ConsoleOutput consoleOutput;
	FileOutput fileOutput;
	
	public CommonOutput() {
		consoleOutput = new ConsoleOutput();
		fileOutput = new FileOutput();
	}
	
	public void console(int strLength) {
		consoleOutput.execute(strLength);
	}
	
	public void file(int strLength) throws IOException {
		fileOutput.execute(strLength);
	}

}
