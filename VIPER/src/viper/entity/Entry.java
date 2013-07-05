package viper.entity;

import javax.xml.bind.annotation.XmlElement;

public class Entry {
	String fullpath;
	String type;
	String extension;
	int size;

	// image
	int res;
	int height;
	int width;

	// audio
	int releaseDate;
	double duration;
	String channelType;
	String artist;
	int sampleRate;
	String genre;

	public int getRes() {
		return res;
	}

	public void setRes(int res) {
		this.res = res;
	}

	public int getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(int releaseDate) {
		this.releaseDate = releaseDate;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public int getSampleRate() {
		return sampleRate;
	}

	public void setSampleRate(int sampleRate) {
		this.sampleRate = sampleRate;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

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
