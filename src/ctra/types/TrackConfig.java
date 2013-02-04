package ctra.types;




public class TrackConfig
{

	private String track;
	private String cars;
	private int laps;
	private int wind;
	
	public TrackConfig(String track,String cars,int laps,int wind)
	{
		this.track=track;
		this.cars=cars;
		this.laps=laps;
		this.wind=wind;
	}
	
	public String getTrack()
	{
		return track;
	}

	public String getCars()
	{
		return cars;
	}

	public int getLaps()
	{
		return laps;
	}

	public int getWind()
	{
		return wind;
	}
}
