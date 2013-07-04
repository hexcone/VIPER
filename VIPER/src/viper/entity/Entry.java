package viper.entity;

import javax.xml.bind.annotation.XmlElement;

public class Entry {
	String fullpath;
	String type;
	String extension;
	int size;
	int height;
	int width;

	public String getFullpath() {
		return fullpath;
	}

	@XmlElement
	public void setFullpath(String fullpath) {
		this.fullpath = fullpath;
	}

	public String getType() {
		return type;
	}

	@XmlElement
	public void setType(String type) {
		this.type = type;
	}

	public String getExtension() {
		return extension;
	}

	@XmlElement
	public void setExtension(String extension) {
		this.extension = extension;
	}

	public int getSize() {
		return size;
	}

	@XmlElement
	public void setSize(int size) {
		this.size = size;
	}

	public int getHeight() {
		return height;
	}

	@XmlElement
	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	@XmlElement
	public void setWidth(int width) {
		this.width = width;
	}
}
