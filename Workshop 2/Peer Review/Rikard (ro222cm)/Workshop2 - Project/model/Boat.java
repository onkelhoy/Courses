package model;

public class Boat 
{	
	private BoatType type;
	private int length; // in feet
	enum BoatType {SAILBOAT, MOTORSAILER, KAYAK, OTHER};
	
	public Boat() {}
	
	public Boat(int length, int type)
	{
		this.length = length;
		setType(type);
	}
	
	public void setLength(int length)
	{
		this.length = length;
	}
	
	public void setType(int type)
	{
		switch (type)
		{
			case 1:
				this.type = BoatType.SAILBOAT;
				break;
			case 2:
				this.type = BoatType.MOTORSAILER;
				break;
			case 3:
				this.type = BoatType.KAYAK;
				break;
			default:
				this.type = BoatType.OTHER;
				break;
		}
	}
	
	public int getLength()
	{
		return(length);
	}
	
	public char getType()
	{
		switch (type)
		{
			case SAILBOAT:
				return('s');
			case MOTORSAILER:
				return('m');
			case KAYAK:
				return('k');
		}
		return('o');
	}
	
	@Override
	public String toString()
	{
		if (type == BoatType.SAILBOAT)
			return("Sailboat, " + length + " ft");
		if (type == BoatType.MOTORSAILER)
			return("Motorsailer, " + length + " ft");
		if (type == BoatType.KAYAK)
			return("Kayak/Canoe, " + length + " ft");
		if (type == BoatType.OTHER)
			return("Other, " + length + " ft");
			
		return("Unknown type, " + length + " ft");
	}
}

