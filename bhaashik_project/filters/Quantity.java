package bhaashik.filters;

import bhaashik.GlobalProperties;

/**
*/
public class Quantity implements Cloneable
{
	BhaashikNumber number;
	Unit unit;
	
	public Quantity()
	{
	}
	
	public BhaashikNumber getNumber()
	{
		return number;
	}
	
	public void setNumber(BhaashikNumber n)
	{
		number = n;
	}
	
	public Unit getUnit() 
	{
		return unit;
	}
	
	public void setUnit(Unit u) 
	{
		unit = u;
	}
	
	public Object clone()
	{
		try
		{
			Quantity obj = (Quantity) super.clone();

			obj.number = (BhaashikNumber) number.clone();
			obj.unit = (Unit) unit.clone();

			return obj;
		}
		catch (CloneNotSupportedException e)
		{
			throw new InternalError(GlobalProperties.getIntlString("But_the_class_is_Cloneable!!!"));
		}
	}
}
