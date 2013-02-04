package ctra.types;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class IntegralAdder
{
	Logger										log	= Logger.getLogger(this
															.getClass());
	private HashMap<String, AddIntegralRules>	addIntegralRulesMap;
	
	@SuppressWarnings("unchecked")
	public IntegralAdder(String rulesFile) throws JDOMException
	{
		
		addIntegralRulesMap = new HashMap<String, AddIntegralRules>();
		
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(rulesFile);
		Element root = doc.getRootElement();
		
		List<Element> addIntegralRulesList = root.getChildren();
		
		for (Element aRulesElement : addIntegralRulesList)
		{
			String reason = aRulesElement.getName();
			AddIntegralRules rules = new AddIntegralRules(reason);
			
			int base = Integer.parseInt(aRulesElement.getChildTextTrim("base"));
			rules.setBase(base);
			List<Element> additionsList = aRulesElement.getChild("additions")
					.getChildren();
			for (Element e : additionsList)
			{
				String name = e.getName();
				String value = e.getTextTrim();
				if (value.equals(""))
					value = "0";
				float addition = Float.parseFloat(value);
				rules.putAdditions(name, addition);
			}
			addIntegralRulesMap.put(reason, rules);
			
		}
		
	}
	
	public int sumRaceWin(String reason, int averageRankGap, int numPlayers,
			int position)
	{
		AddIntegralRules rules = addIntegralRulesMap.get(reason);
		
		if (rules == null)
		{
			return 0;
		} else
		{
			
			float winRequire = numPlayers * rules.getAddition("winPercentage");
			if (position <= winRequire)
			{
				int positionGap = numPlayers - position;
				String f = rules.getBase() + " + " +rules.getAddition("winBase") + " + " + positionGap + " X "
						+ rules.getAddition("positionGap") +  " + "
						+ averageRankGap + " X "
						+ rules.getAddition("averageRankGap");
				
				log.debug(f);
				
				int addition=(int) (positionGap
				* rules.getAddition("positionGap") + averageRankGap
				* rules.getAddition("averageRankGap"));
				
				if(addition>0)
				{
					return (int) (rules.getBase()+rules.getAddition("winBase")+addition);
				}else{
					return (int) (rules.getBase()+rules.getAddition("winBase"));
				}
				
				
			}
			else if(numPlayers>1){
				
				log.debug("position:"+position+",winRequire:"+winRequire);
				return rules.getBase();
			}
			else{
				return 0;
			}
			
		}
		
	}
	
	public int sum(String reason, int numPlayers, int rank)
	{
		AddIntegralRules rules = addIntegralRulesMap.get(reason);
		
		if (rules == null)
		{
			return 0;
		} else
		{
			String f = rules.getBase() + " + " + numPlayers + " X "
					+ rules.getAddition("numPlayers") + " + " + rank + " X "
					+ rules.getAddition("rank");
			
			log.debug(f);
			
			return (int) (rules.getBase() + numPlayers
					* rules.getAddition("numPlayers") + rank
					* rules.getAddition("rank"));
		}
		
	}
}
