package ctra.types;

import java.util.ResourceBundle;

public enum Text
{
	
	SHOW_INTEGRAL("SHOW_INTEGRAL"),
	FINISH_RACE("FINISH_RACE"),
	CANNOT_JION_WARNING("CANNOT_JION_WARNING"),
	CHANGE_MAP("CHANGE_MAP"),
	GET_RESULT("GET_RESULT"),
	WELCOME_BUTTON("WELCOME_BUTTON"),
	PENALTY_ALART("PENALTY_ALART"),
	MSG_NEXT_TRACK("MSG_NEXT_TRACK"),
	DNF_WARNING("DNF_WARNING"),
	Car_Restriction("Car_Restriction"),
	CANNOT_AI("CANNOT_AI");
	
	
	final String text;
	String buf;
	
	Text(String key)
	{
		ResourceBundle rsb = ResourceBundle.getBundle("text");
		text=rsb.getString(key);
		buf=text;
	}
	
	public void setParameter(int pos,int i)
	{
		setParameter(pos, i+"");
	}
	
	
	public void setParameter(int pos,String s)
	{
		if(buf.indexOf("$"+pos)<0)
		{
			buf=text;
		}
		buf=buf.replaceAll("\\$"+pos, s);
	}
	
	public String getText()
	{
		return buf;
	}
	
}
