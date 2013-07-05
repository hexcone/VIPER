package viper.entity;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class XmlFile {

	private String rootDir;
	private Date dateProfiled;
	private ArrayList<Entry> entry;

	public String getRootDir() {
		return rootDir;
	}

	@XmlElement
	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}

	public Date getDateProfiled() {
		return dateProfiled;
	}

	@XmlElement
	public void setDateProfiled(Date dateProfiled) {
		this.dateProfiled = dateProfiled;
	}

	public ArrayList<Entry> getEntry() {
		return entry;
	}

	@XmlElement
	public void setEntry(ArrayList<Entry> entry) {
		this.entry = entry;
	}

}
