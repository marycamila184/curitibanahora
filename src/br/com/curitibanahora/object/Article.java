package br.com.curitibanahora.object;

public class Article {
	private String title = "title";
	private String category = "category";
	private String pubdate = "pubDate";
	private String description = "description";
	private String link = "link";
	private String image = "image";
	private String idcurrentpost = "idcurrentpost";

	public Article(String tITLE, String cATEGORY, String pUBDATE,
			String dESCRIPTION, String lINK, String iMAGE, String iDCURRENTPOST) {
		super();
		title = tITLE;
		category = cATEGORY;
		String aux = pUBDATE;
		String auxF = "";
		String auxFin = "";
		if (aux.contains("Jan")) {
			auxF = aux.replace("Jan", "01");

		} else if (aux.contains("Feb")) {
			auxF = aux.replace("Feb", "02");

		} else if (aux.contains("Mar")) {
			auxF = aux.replace("Mar", "03");

		} else if (aux.contains("Apr")) {
			auxF = aux.replace("Apr", "04");

		} else if (aux.contains("May")) {
			auxF = aux.replace("Oct", "05");

		} else if (aux.contains("Jun")) {
			auxF = aux.replace("Jun", "06");

		} else if (aux.contains("Jul")) {
			auxF = aux.replace("Jul", "07");

		} else if (aux.contains("Aug")) {
			auxF = aux.replace("Aug", "08");

		} else if (aux.contains("Sep")) {
			auxF = aux.replace("Sep", "09");

		} else if (aux.contains("Oct")) {
			auxF = aux.replace("Oct", "10");

		} else if (aux.contains("Nov")) {
			auxF = aux.replace("Nov", "11");

		} else if (aux.contains("Dec")) {
			auxF = aux.replace("Dec", "12");

		} else {
			auxF = " ";
		}
		auxFin = auxF.replace(" ", "/");
		pubdate = auxFin;
		description = dESCRIPTION;
		link = lINK;
		image = iMAGE;
		idcurrentpost = iDCURRENTPOST;
	}

	public String getTITLE() {
		return title;
	}

	public void setTITLE(String tITLE) {
		title = tITLE;
	}

	public String getCATEGORY() {
		return category;
	}

	public void setCATEGORY(String cATEGORY) {
		category = cATEGORY;
	}

	public String getPUBDATE() {
		return pubdate;
	}

	public void setPUBDATE(String pUBDATE) {
		pubdate = pUBDATE;
	}

	public String getDESCRIPTION() {
		return description;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		description = dESCRIPTION;
	}

	public String getLINK() {
		return link;
	}

	public void setLINK(String lINK) {
		link = lINK;
	}

	public String getIMAGE() {
		return image;
	}

	public void setIMAGE(String iMAGE) {
		image = iMAGE;
	}

	public String getIDCURRENTPOST() {
		return idcurrentpost;
	}

	public void setIDCURRENTPOST(String iDCURRENTPOST) {
		idcurrentpost = iDCURRENTPOST;
	}

}
