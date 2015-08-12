package br.com.curitibanahora.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import br.com.curitibanahora.object.Article;
public class ArticleParser extends DefaultHandler {
	private final String ITEM = "item";
	private final String TITLE = "title";
	private final String CATEGORY = "category";
	private final String PUBDATE = "pubDate";
	private final String DESCRIPTION = "description";
	private final String LINK = "link";
	private final String IMAGE = "image";
	private final String IDCURRENTPOST = "idcurrentpost";

	private String tempID;
	private String tempTitre;
	private String tempPicture;
	private String tempCat;
	private String tempPubDate;
	private String tempDescri;
	private String tempLink;

	private ArrayList<Article> posts = new ArrayList<Article>();
	private boolean inItem;
	private StringBuffer buffer;

	@Override
	public void processingInstruction(String target, String data)
			throws SAXException {
		super.processingInstruction(target, data);
	}

	public ArticleParser() {
		super();
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {

		buffer = new StringBuffer();

		if (localName.equalsIgnoreCase(ITEM)) {
			inItem = true;
		}

	}

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {

		if (localName.equalsIgnoreCase(IDCURRENTPOST)) {
			if (inItem) {

				tempID = buffer.toString();
				buffer = null;
			}
		}
		
		if (localName.equalsIgnoreCase(TITLE)) {
			if (inItem) {

				tempTitre = buffer.toString();
				buffer = null;
			}
		}
		
		if (localName.equalsIgnoreCase(CATEGORY)) {
			if (inItem) {

				tempCat = buffer.toString();
				buffer = null;
			}
		}
		
		if (localName.equalsIgnoreCase(PUBDATE)) {
			if (inItem) {

				tempPubDate = buffer.toString();
				buffer = null;
			}
		}
		
		if (localName.equalsIgnoreCase(DESCRIPTION)) {
			if (inItem) {

				tempDescri = buffer.toString();
				buffer = null;
			}
		}
		
		if (localName.equalsIgnoreCase(LINK)) {
			if (inItem) {

				tempLink = buffer.toString();
				buffer = null;
			}
		}
		
		if (localName.equalsIgnoreCase(IMAGE)) {
			if (inItem) {

				tempPicture = buffer.toString();
				buffer = null;
			}
		}

		if (localName.equalsIgnoreCase(ITEM)) {
			if (inItem) {

				Article evennement = new Article(tempTitre, tempCat,
						tempPubDate, tempDescri, tempLink, tempPicture, tempID);
				posts.add(evennement);
			}
		}

	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String lecture = new String(ch, start, length);
		if (buffer != null) {
			buffer.append(lecture);
		}
	}

	public ArrayList<Article> getData() {

		return posts;

	}

}