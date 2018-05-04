package model;

import java.util.List;

import javax.imageio.IIOImage;

public abstract class Promo {
	private String name;
	private String description;
	private List<IIOImage> images;
	private String activity;
	//private RegisteredUser buyer;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<IIOImage> getImages() {
		return images;
	}
	public void setImages(List<IIOImage> images) {
		this.images = images;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	
	
	
	
	
}
