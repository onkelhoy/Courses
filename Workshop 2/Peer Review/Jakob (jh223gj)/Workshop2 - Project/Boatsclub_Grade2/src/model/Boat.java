package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


@XmlAccessorType(XmlAccessType.FIELD)
public class Boat {
	
	public enum Boatstype{
		Sailboat,
		Motorboat,
		Kayak,
		Other
	}
	
	private int id;
	private Boatstype type;
	private double length;
	
	public Boatstype getType() {
		return type;
	}
	public void setType(Boatstype type) {
		this.type = type;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
