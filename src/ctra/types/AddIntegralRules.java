package ctra.types;

import java.util.HashMap;

import org.apache.log4j.Logger;


class AddIntegralRules
{
	
	Logger	log	= Logger.getLogger(this.getClass());

	private String					reason;
	private int						base;
	private HashMap<String, Float>	additionsMap	= new HashMap<String, Float>();
	
	AddIntegralRules(String reason)
	{
		this.reason = reason;
		
	}
	
	public void putAdditions(String name,float addition)
	{
		additionsMap.put(name, addition);
	}
	
	public float getAddition(String additionName)
	{
		Float addition = additionsMap.get(additionName);
		if (addition != null)
		{
			return addition;
		} else
		{
			return 0;
		}
		
	}
	public void setBase(int base)
	{
		this.base=base;
	}
	
	

	public String getReason()
	{
		return reason;
	}

	public int getBase()
	{
		return base;
	}
	
}
