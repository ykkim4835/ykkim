package com.meta.main;

import java.io.IOException;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.meta.component.CountWord;
import com.meta.component.StrInput;
import com.meta.component.StrInputOutput;
import com.meta.component.StrOutput;

public class MainClass {

	public static void main(String[] args) throws IOException {
		StrInputOutput strInputOutput = new StrInputOutput();
		strInputOutput.exexcute();
	}

}
