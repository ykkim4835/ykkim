package com.meta.component;

import java.io.IOException;
import java.util.Scanner;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class StrInputOutput {
	
	private StrInput strInput;
	private StrOutput strOutput;
	private CountWord countWord;

	public CountWord getCountWord() {
		return countWord;
	}
	public void setCountWord(CountWord countWord) {
		this.countWord = countWord;
	}
	public StrInput getStrInput() {
		return strInput;
	}
	public void setStrInput(StrInput strInput) {
		this.strInput = strInput;
	}
	public StrOutput getStrOutput() {
		return strOutput;
	}
	public void setStrOutput(StrOutput strOutput) {
		this.strOutput = strOutput;
	}
	public void exexcute() throws IOException {
		String classpath = null;
				
		System.out.println("메뉴를 선택해주세요.");
		System.out.println("1. 키보드 입력 -> 콘솔 출력");
		System.out.println("2. 키보드 입력 -> 파일 저장");
		System.out.println("3. 파일 입력 -> 콘솔 출력");
		System.out.println("4. 파일 입력 -> 파일 저장");
		System.out.print("입력: ");
		Scanner sc = new Scanner(System.in);
		int choice = sc.nextInt();

		if (choice == 1) {
			classpath = "keyboardToConsoleCTX.xml";
		} else if (choice == 2) {
			classpath = "classpath:keyboardToFileCTX.xml";
		} else if (choice == 3) {
			classpath = "classpath:fileToConsoleCTX.xml";
		} else if (choice == 4) {
			classpath = "classpath:fileToFileCTX.xml";
		}

		AbstractApplicationContext ctx = new GenericXmlApplicationContext(classpath);
		StrInputOutput strInputOutput = ctx.getBean("strInputOutput", StrInputOutput.class);

		StrInput strInput = strInputOutput.getStrInput();
		String str = strInput.execute();

		CountWord countWord = strInputOutput.getCountWord();
		int strLength = countWord.execute(str);

		StrOutput strOutput = strInputOutput.getStrOutput();
		strOutput.execute(strLength);

		ctx.close();
		sc.close();
	}
}
