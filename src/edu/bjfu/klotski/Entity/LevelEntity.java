package edu.bjfu.klotski.Entity;

public class LevelEntity {

	private int id;
	private int type;
	private String layoutText;
	private String layoutXML;
	private String layoutJSON;
	private int oepned; 
	
	private byte[] image;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getLayoutText() {
		return layoutText;
	}

	public void setLayoutText(String layoutText) {
		this.layoutText = layoutText;
	}

	public int getOepned() {
		return oepned;
	}

	public void setOepned(int oepned) {
		this.oepned = oepned;
	}

	public String getLayoutXML() {
		return layoutXML;
	}

	public void setLayoutXML(String layoutXML) {
		this.layoutXML = layoutXML;
	}

	public String getLayoutJSON() {
		return layoutJSON;
	}

	public void setLayoutJSON(String layoutJSON) {
		this.layoutJSON = layoutJSON;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	

}
