package ctra.types;

import java.util.HashMap;
import java.util.ResourceBundle;

public class Rank
{
	private static HashMap<Integer,Integer> rankMap=new  HashMap<Integer,Integer>();
	
	static{
		ResourceBundle rsb = ResourceBundle.getBundle("rank");
		for(int i=1;i<rsb.keySet().size();i++)
		{
			int needIntegral=Integer.parseInt(rsb.getString("RANK_"+i));
			rankMap.put(i, needIntegral);
		}
	}
	
	public static int getRank(int integral)
	{
		for(int i:rankMap.keySet())
		{
			if(integral<rankMap.get(i)) return i-1;
		}
		
		return 0;
	}
}
