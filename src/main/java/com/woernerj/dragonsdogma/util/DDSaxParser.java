package com.woernerj.dragonsdogma.util;

import java.io.IOException;
import java.io.Writer;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.codehaus.stax2.XMLStreamReader2;

public class DDSaxParser {

	private int baseIndentation = -1;
	private boolean startRead = false;
	private boolean continueRead = true;

	private XMLStreamReader2 reader;
	private Writer writer;

	public DDSaxParser(XMLStreamReader2 reader, Writer writer) {
		this.reader = reader;
		this.writer = writer;
	}

	public void parse() throws XMLStreamException, IOException {
		while (reader.hasNext()) {
			int event = reader.next();
			switch (event) {
			case XMLEvent.START_ELEMENT:
				startElement(reader);
					break;
			case XMLEvent.CHARACTERS:
				characters(reader);
				break;
			case XMLEvent.END_ELEMENT:
				endElement(reader);
				break;
			default:
			}
			if (!continueRead) {
				System.out.println("Exiting early");
				return;
			}
		}
	}

	private void startElement(XMLStreamReader2 reader) throws XMLStreamException, IOException {
		String attr = reader.getAttributeValue(null, "name");

		if (attr != null) {
			switch (attr) {
			case "mPlayerDataManual":
				baseIndentation = reader.getDepth();
				startRead = true;
				System.out.println("Set indent");
				System.out.println(attr);
			default:
			}
		}
		if (startRead) {
			StringBuffer sb = new StringBuffer();
			sb.append("<");
			sb.append(reader.getLocalName().toString());
			
			for (int i = 0; i < reader.getAttributeCount(); i++) {
				sb.append(" ").append(reader.getAttributeLocalName(i));
				sb.append("=\"").append(reader.getAttributeValue(i)).append("\"");
			}
			
			sb.append(">");
			writer.write(sb.toString());
		}
	}

	private void endElement(XMLStreamReader2 reader) throws XMLStreamException, IOException {
		if (startRead) {
			StringBuffer sb = new StringBuffer();
			sb.append("</");
			sb.append(reader.getLocalName().toString());
			sb.append(">");
			writer.write(sb.toString());
		}
		if (reader.getDepth() <= baseIndentation) {
			continueRead = false;
		}
	}

	private void characters(XMLStreamReader2 reader) throws XMLStreamException, IOException {
		if (startRead) {
			reader.getText(writer, true);
		}
	}

	@Override
	public String toString() {
		return writer.toString().replaceAll("&", "&amp;");
	}
}
