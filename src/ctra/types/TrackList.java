package ctra.types;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class TrackList
{
	Logger									log			= Logger.getLogger(this
																.getClass());
	private HashMap<Integer, TrackConfig>	trackList	= new HashMap<Integer, TrackConfig>();
	private int								currentIndex		= 0;
	private long lastChanged;
	private int		delay;
	
	@SuppressWarnings("unchecked")
	public TrackList(String trackListFile) throws JDOMException
	{
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(trackListFile);
		Element root = doc.getRootElement();
		delay=Integer.parseInt(root.getChildTextTrim("delay"));
		
		List<Element> trackConfigList = root.getChildren("config");
		int index = 0;
		for (Element aTrack : trackConfigList)
		{
			String track = aTrack.getChildTextTrim("track");
			String cars = aTrack.getChildTextTrim("cars");
			int laps = Integer.parseInt(aTrack.getChildTextTrim("laps"));
			int wind = Integer.parseInt(aTrack.getChildTextTrim("wind"));
			trackList.put(index, new TrackConfig(track, cars, laps, wind));
			index++;
		}
		
		lastChanged=System.currentTimeMillis();
		
	}
	
	public void nextAtOnce()
	{
		long now=System.currentTimeMillis();
		currentIndex++;
		lastChanged=now;
		
		log.debug("nextAtOnce lastChanged:"+lastChanged);
		if (currentIndex >= trackList.size())
		{
			currentIndex = 0;
			
		}
	}
	
	public TrackConfig next()
	{
		long changTime=lastChanged+delay*60*1000;
		long now=System.currentTimeMillis();
		log.debug("now:"+now +" changTime:"+changTime);
		if(now>changTime)
		{
			currentIndex++;
			lastChanged=now;
		}
		
		if (currentIndex >= trackList.size())
		{
			currentIndex = 0;
			return trackList.get(0);
		} else
		{
			return trackList.get(currentIndex);
		}
	}
	
	
	public String getNextTrackName()
	{
		
		if (currentIndex+1 >= trackList.size())
		{
			
			return trackList.get(0).getTrack();
		} else
		{
			return trackList.get(currentIndex+1).getTrack();
		}
	}
	
	public String getNextTrackCars()
	{
		
		if (currentIndex+1 >= trackList.size())
		{
			
			return trackList.get(0).getCars();
		} else
		{
			return trackList.get(currentIndex+1).getCars();
		}
	}
	
	public long getNextDelayMin()
	{
		long changTime=lastChanged+delay*60*1000;
		long now=System.currentTimeMillis();
		return (changTime-now)/(60*1000);
	}
	
	
}
