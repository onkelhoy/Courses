package model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class Member {
	
	private int id;
	private String name;
	private String personal_number;
	@XmlElement(name="boat")
	private ArrayList<Boat> boats;
	
	public Member(){
		boats = new ArrayList<Boat>();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPersonal_number() {
		return personal_number;
	}
	public void setPersonal_number(String personal_number) {
		this.personal_number = personal_number;
	}
	public void addBoat(Boat b){
		String b_id = "" + id + boats.size();
		b.setId(Integer.parseInt(b_id));
		boats.add(b);
	}
	public void editBoat(Boat b){
		for (int i=0;i<boats.size();i++){
			boats.set(getIndexOfBoat(b.getId()), b);
		}
	}
	public void deleteBoat(int b_id){
		if (!boats.remove(getBoat(b_id))) System.err.println("Boat not found!");; 
	}
	
	public Iterable<Boat> getBoats(){
		return boats;
	}
	
	private int getIndexOfBoat(int b_id){
		for (int i=0;i<boats.size();i++){
			if (boats.get(i).getId() == b_id) return i;
		}
		return -99;
	}
	
	public boolean existBoat(int b_id){
		return (getIndexOfBoat(b_id) != -99);
	}
	
	public Boat getBoat(int b_id){
		return boats.get(getIndexOfBoat(b_id));
	}
	

}
