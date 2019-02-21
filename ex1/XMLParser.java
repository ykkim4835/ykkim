package com.meta.xmlparsing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

// XMLParsing Ŭ����
public class XMLParser {

	ArrayList<BaseDTO> baseList = null;
	ArrayList<FDTO> fList = null;
	ArrayList<PDTO> pList = null;

	public XMLParser() {
		this.baseList = new ArrayList<>();
		this.fList = new ArrayList<>();
		this.pList = new ArrayList<>();
	}
	
	// ���� ���� �޼ҵ�
	public void execute() throws XPathExpressionException, IOException, SAXException, ParserConfigurationException {
		saveBaseXML();

		for (int i = 0; i < baseList.size(); i++) {
			String file_id = baseList.get(i).getFile_id();
			saveFXML(file_id);
			savePXML(file_id);
			setOverFifteen();
			setEqualP_id();
			setLicense_id();
			makeXML(file_id);
			init();
		}
	}

	// xml�� ��� <ROW>�� �о���� ���� �޼ҵ�
	private NodeList setRowList(BufferedReader bufferedReader)
			throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
		String str = null;
		StringBuilder sb = new StringBuilder();

		// ������ ���� �� �о�� ��ü xml �ڵ带 xml ������ �����Ѵ�.
		while ((str = bufferedReader.readLine()) != null) {
			str = str.trim();
			sb.append(str);
		}
		bufferedReader.close();
		String xml = sb.toString(); // �Ѱ��� xml ���Ͽ��ִ� ��ü �ڵ�

		// XML Document ��ü ����
		InputSource is = new InputSource(new StringReader(xml));
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);

		// xpath ����
		XPath xpath = XPathFactory.newInstance().newXPath();

		// <ROW> ��������
		NodeList rowList = (NodeList) xpath.evaluate("//ROWS/ROW", document, XPathConstants.NODESET);

		return rowList;
	}

	// T_BASEFILE_TB.xml������ <ROW>�� �ڽ� �±׵��� ���� BaseDTO�� ���� �� baseList(ArrayList)�� �����ϴ� �޼ҵ�
	private void saveBaseXML()
			throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader("./xmlFile/base/T_BASEFILE_TB.xml"));
		NodeList rowList = setRowList(bufferedReader); // xml�� ��� <ROW> ���� �о�´�.

		BaseDTO baseDTO = null;
		ArrayList<String> tempList = null;
		NodeList childList = null;

		for (int i = 0; i < rowList.getLength(); i++) { // <ROW>�� ���� ��ŭ �ݺ��Ѵ�.
			tempList = new ArrayList<>();
			childList = rowList.item(i).getChildNodes(); // <ROW>�� �ڽ� �±׵��� �����Ѵ�.
			for (int j = 0; j < childList.getLength(); j++) { // �ڽ� �±׵��� ���� ��ŭ �ݺ��Ѵ�.
				if (!(childList.item(j).getNodeName().equals("#text"))) { // �ڽ� �±׵��� �̸��� �����ͼ� ���Ѵ�.
					tempList.add(childList.item(j).getTextContent()); // �ڽ� �±��� ������ �����Ѵ�.
				}
			}
			// tempList�� ����� �ڽ� �±׵��� ���� DTO�� �����Ѵ�.
			baseDTO = new BaseDTO();
			baseDTO.setFile_id(tempList.get(0));
			baseDTO.setFile_name(tempList.get(1));
			baseDTO.setFile_ext(tempList.get(2));
			baseDTO.setFile_path(tempList.get(3));
			baseDTO.setReal_path(tempList.get(4));
			baseDTO.setCopy_path(tempList.get(5));
			baseDTO.setStatus(tempList.get(6));
			baseDTO.setStart_time(tempList.get(7));
			baseDTO.setEnd_time(tempList.get(8));
			baseDTO.setTarget_file_count(tempList.get(9));
			baseDTO.setExclusion_count(tempList.get(10));

			baseList.add(baseDTO);
		}
	}
	
	// P_idx_TB.xml������ <ROW>�� �ڽ� �±׵��� ���� FDTO�� ���� �� fList(ArrayList)�� �����ϴ� �޼ҵ�
	private void saveFXML(String idx)
			throws IOException, XPathExpressionException, SAXException, ParserConfigurationException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader("./xmlFile/f/F_" + idx + "_TB.xml"));
		NodeList rowList = setRowList(bufferedReader);

		FDTO fDTO = null;
		ArrayList<String> tempList = null;
		NodeList childList = null;

		for (int i = 0; i < rowList.getLength(); i++) {
			tempList = new ArrayList<>();
			childList = rowList.item(i).getChildNodes();
			for (int j = 0; j < childList.getLength(); j++) {
				if (!(childList.item(j).getNodeName().equals("#text"))) {
					tempList.add(childList.item(j).getTextContent());
				}
			}
			fDTO = new FDTO();
			fDTO.setRowId(tempList.get(0));
			fDTO.setVolume(tempList.get(1));
			fDTO.setFile_name(tempList.get(2));
			fDTO.setRelease_name(tempList.get(3));
			fDTO.setSimilar_rate(tempList.get(4));
			fDTO.setFile_path(tempList.get(5));
			fDTO.setP_id(tempList.get(6));
			fDTO.setExclusion(tempList.get(7));
			fDTO.setComment(tempList.get(8));

			fList.add(fDTO);
		}
	}
	
	// P_idx_TB.xml������ <ROW>�� �ڽ� �±׵��� ���� PDTO�� ���� �� pList(ArrayList)�� �����ϴ� �޼ҵ�
	private void savePXML(String idx)
			throws IOException, XPathExpressionException, SAXException, ParserConfigurationException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader("./xmlFile/p/P_" + idx + "_TB.xml"));
		NodeList rowList = setRowList(bufferedReader);

		PDTO pDTO = null;
		ArrayList<String> tempList = null;
		NodeList childList = null;

		for (int i = 0; i < rowList.getLength(); i++) {
			tempList = new ArrayList<>();
			childList = rowList.item(i).getChildNodes();
			for (int j = 0; j < childList.getLength(); j++) {
				if (!(childList.item(j).getNodeName().equals("#text"))) {
					tempList.add(childList.item(j).getTextContent());
				}
			}
			pDTO = new PDTO();
			pDTO.setP_id(tempList.get(0));
			pDTO.setProject_name(tempList.get(1));
			pDTO.setLicense_id(tempList.get(2));
			pDTO.setLicense_name(tempList.get(3));
			pDTO.setLicense_display(tempList.get(4));
			pDTO.setSize(tempList.get(5));
			pDTO.setTopic_cd(tempList.get(6));
			pDTO.setTopic_name(tempList.get(7));

			pList.add(pDTO);
		}
	}
	
	// similar_rate / 100 > 15 ����Ȯ�� �޼ҵ�
	private void setOverFifteen() {
		for (int i = 0; i < fList.size(); i++) {
			int intSR = Integer.parseInt(fList.get(i).getSimilar_rate());
			int result = intSR / 100;
			// ���ǿ� �����ϸ� overFifteen������ true�� ����
			// ����: similar_rate / 100 > 15
			if (result > 15) {
				fList.get(i).setOverFifteen(true);
			}
		}
	}
	
	// fP_id == pP_id ���� Ȯ�� �޼ҵ�
	private void setEqualP_id() {
		for (int i = 0; i < fList.size(); i++) {
			String fP_id = fList.get(i).getP_id();
			for (int j = 0; j < pList.size(); j++) {
				String pP_id = pList.get(j).getP_id();
				// fFile�� p_id�� pFile�� p_id���� ���Ͽ� �������� ������ equalP_id�� true�� ����
				if (!(fP_id.equals("")) && fP_id.equals(pP_id)) { // fP_id�� ���� �ְ� fP_id == pP_id �̸�
					fList.get(i).setEqualP_id(true);
					break;
				}
			}
		}
	}
	
	// overFifteen�� equalP_id ������ �Ѵ� true�� fList�� �ε��� ���� ������� �޼ҵ�
	private ArrayList<Integer> getTrueIdx() {
		ArrayList<Integer> tempList = new ArrayList<>();
		for (int i = 0; i < fList.size(); i++) {
			// overFifteen�� equalP_id ������ �Ѵ� true�϶� ����
			// ���� ����: similar_rate / 100 > 15 �̰�
			// fFile�� p_id �� pFile�� p_id�� �������� ������ ����
			if (fList.get(i).isOverFifteen() && fList.get(i).isEqualP_id()) {
				tempList.add(i);
			}
		}

		return tempList;
	}
	
	// overFifteen�� equalP_id ������ �Ѵ� true �϶� lincense_id ���� comment�� �ִ� �޼ҵ�
	private void setLicense_id() {
		ArrayList<Integer> tempList = getTrueIdx();
		for (int i = 0; i < tempList.size(); i++) {
			// idx�������� fList�� overFifteen�� equalP_id ������ �Ѵ� true �ε��� ���� ����ִ�.
			int idx = tempList.get(i);
			String fP_id = fList.get(idx).getP_id(); // idx����
			for (int j = 0; j < pList.size(); j++) {
				String pP_id = pList.get(j).getP_id();
				// fFile�� pFile�� p_id �� �� ������ comment�� lincense_id�� �־��ش�.
				if (fP_id.equals(pP_id)) {
					String lincense_id = pList.get(j).getLicense_id();
					fList.get(idx).setComment(lincense_id);
					break;
				}
			}
		}
	}
	
	// xml ���� ���� �޼ҵ�
	private void makeXML(String fileName) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			Document doc = docBuilder.newDocument();
			doc.setXmlStandalone(true);
			
			Element table = doc.createElement("TABLE");
			doc.appendChild(table);
			
			Element metadata = doc.createElement("METADATA");
			table.appendChild(metadata);

			Element name = doc.createElement("NAME");
			name.appendChild(doc.createTextNode("T_" + fileName + "_TB"));
			metadata.appendChild(name);

			Element col = doc.createElement("COL");
			col.appendChild(doc.createTextNode("ROWID"));
			metadata.appendChild(col);

			col = doc.createElement("COL");
			col.appendChild(doc.createTextNode("VOLUME"));
			metadata.appendChild(col);

			col = doc.createElement("COL");
			col.appendChild(doc.createTextNode("FILE_NAME"));
			metadata.appendChild(col);

			col = doc.createElement("COL");
			col.appendChild(doc.createTextNode("RELEASE_NAME"));
			metadata.appendChild(col);

			col = doc.createElement("COL");
			col.appendChild(doc.createTextNode("SIMILAR_RATE"));
			metadata.appendChild(col);

			col = doc.createElement("COL");
			col.appendChild(doc.createTextNode("FILE_PATH"));
			metadata.appendChild(col);

			col = doc.createElement("COL");
			col.appendChild(doc.createTextNode("P_ID"));
			metadata.appendChild(col);

			col = doc.createElement("COL");
			col.appendChild(doc.createTextNode("EXCLUSION"));
			metadata.appendChild(col);

			col = doc.createElement("COL");
			col.appendChild(doc.createTextNode("COMMENT"));
			metadata.appendChild(col);

			Element rows = doc.createElement("ROWS");
			table.appendChild(rows);

			Element row = null;
			Element rowId = null;
			Element volume = null;
			Element file_name = null;
			Element release_name = null;
			Element similar_rate = null;
			Element file_path = null;
			Element p_id = null;
			Element exclusion = null;
			Element comment = null;

			// element�� ����
			for (int i = 0; i < fList.size(); i++) {
				row = doc.createElement("ROW");
				rows.appendChild(row);

				rowId = doc.createElement("ROWID");
				rowId.appendChild(doc.createTextNode(fList.get(i).getRowId()));
				row.appendChild(rowId);

				volume = doc.createElement("VOLUME");
				volume.appendChild(doc.createTextNode(fList.get(i).getVolume()));
				row.appendChild(volume);

				file_name = doc.createElement("FILE_NAME");
				file_name.appendChild(doc.createTextNode(fList.get(i).getFile_name()));
				row.appendChild(file_name);

				release_name = doc.createElement("RELEASE_NAME");
				release_name.appendChild(doc.createTextNode(fList.get(i).getRelease_name()));
				row.appendChild(release_name);

				similar_rate = doc.createElement("SIMILAR_RATE");
				similar_rate.appendChild(doc.createTextNode(fList.get(i).getSimilar_rate()));
				row.appendChild(similar_rate);

				file_path = doc.createElement("FILE_PATH");
				file_path.appendChild(doc.createTextNode(fList.get(i).getFile_path()));
				row.appendChild(file_path);

				p_id = doc.createElement("P_ID");
				p_id.appendChild(doc.createTextNode(fList.get(i).getP_id()));
				row.appendChild(p_id);

				exclusion = doc.createElement("EXCLUSION");
				exclusion.appendChild(doc.createTextNode(fList.get(i).getExclusion()));
				row.appendChild(exclusion);

				comment = doc.createElement("COMMENT");
				comment.appendChild(doc.createTextNode(fList.get(i).getComment()));
				row.appendChild(comment);
			}
			
			// XML ���Ϸ� ����
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(
					new BufferedWriter(new FileWriter("./xmlFile/t/T_" + fileName + "_TB.xml")));
			transformer.transform(source, result);
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// fList, pList ���� ArrayList ������ ���� �ʱ�ȭ
	private void init() {
		fList.clear();
		pList.clear();
	}
	
	// �ð��� �޸� ��뷮 üũ�� ���� �޼ҵ�
	public void checkTimeAndMemory() throws XPathExpressionException, IOException, SAXException, ParserConfigurationException {
		System.gc();
		// ���� �޸�
		long preUseMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		
		long start = System.currentTimeMillis(); // ���� �ð�
		execute(); // ���� ���� �޼ҵ�
		long end = System.currentTimeMillis(); // ���� �ð�
		System.out.println("���� �ð� : " + (end - start) + "ms");
		
		System.gc();
		// ���� �� �޸�
		long aftUseMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long useMemory = (preUseMemory - aftUseMemory);
		System.out.println("�޸� ��뷮: " + useMemory);
	}

}
