package com.meta.xmlparsing;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

// ���� Ŭ����
public class MainClass {

	public static void main(String[] args)
			throws XPathExpressionException, IOException, SAXException, ParserConfigurationException {
		XMLParser xmlParser = new XMLParser();
//		xmlParser.execute(); // ����
		xmlParser.checkTimeAndMemory(); // �ð�, �޸� ��뷮 üũ
	}

}
