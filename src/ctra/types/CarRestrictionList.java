package ctra.types;

import java.util.HashMap;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class CarRestrictionList
{
	private HashMap<String, CarRestriction>	carRestrictionMap=new HashMap<String, CarRestriction>();
	
	@SuppressWarnings("unchecked")
	public CarRestrictionList(String rulesFile) throws JDOMException
	{
		
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(rulesFile);
		Element root = doc.getRootElement();
		
		List<Element> trackConfigList = root.getChildren("restriction");
		for (Element aTrack : trackConfigList)
		{
			String carName = aTrack.getChildTextTrim("carName");
			int addedMass = Integer.parseInt(aTrack.getChildTextTrim("addedMass"));
			int intakeRestriction = Integer.parseInt(aTrack.getChildTextTrim("intakeRestriction"));
			carRestrictionMap.put(carName, new CarRestriction(carName, addedMass, intakeRestriction));
		}
		
	}
	
	public boolean check(String carName,int addedMass,int intakeRestriction)
	{
		CarRestriction r=carRestrictionMap.get(carName);
		if(r!=null && (addedMass<r.getAddedMass() || intakeRestriction <r.getIntakeRestriction()))
		{
			return false;
		}else{
			return true;
		}
	}
	
	public int getAddedMass(String carName)
	{
		CarRestriction r=carRestrictionMap.get(carName);
		if(r==null){
			return 0;
		}else{
			return r.getAddedMass();
		}
		
	}
	
	public int getIntakeRestriction(String carName)
	{
		CarRestriction r=carRestrictionMap.get(carName);
		if(r==null){
			return 0;
		}else{
			return r.getIntakeRestriction();
		}
		
	}
}
