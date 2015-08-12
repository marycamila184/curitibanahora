package br.com.curitibanahora.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import br.com.curitibanahora.config.Config;
import br.com.curitibanahora.object.Article;

public class SaxGestionnaire {

	public SaxGestionnaire() {
	}

	public static ArrayList<Article> getArticles() {

		SAXParserFactory fabrique = SAXParserFactory.newInstance();
		SAXParser parseur = null;
		ArrayList<Article> articles = null;
		try {
			parseur = fabrique.newSAXParser();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		URL url = null;
		try {
			url = new URL(Config.URLXmlWordPress);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();

		}

		DefaultHandler handler = new ArticleParser();
		try {
			parseur.parse(url.openConnection().getInputStream(), handler);
			articles = ((ArticleParser) handler).getData();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return articles;
	}


}
