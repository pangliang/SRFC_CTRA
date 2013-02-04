package net.sf.jinsim;

public enum Track {

	BLACKWOOD_GP("000", "Blackwood GP", "BL1", 3 ),
	BLACKWOOD_GB_REV("001", "Blackwood GP Rev", "BL1R", 3),
	BLACKWOOD_RALLYX("010", "Blackwood Rallyx", "BL2", 2),
	BLACKWOOD_RALLYX_REV("011", "Blackwood Rallyx Rev", "BL2R", 2),
	BLACKWOOD_CAR_PARK("020", "Blackwood Car Park", "BL3", 0),
	SOUTH_CITY_CLASSIC("100", "South City Classic", "SO1", 2),
	SOUTH_CITY_CLASSIC_REV("101", "South City Classic Rev", "SO1R", 2),
	SOUTH_CITY_SPRINT_TRACK_1("110", "South City Sprint Track 1", "SO2", 2),
	SOUTH_CITY_SPRINT_TRACK_1_REV("111", "South City Sprint Track 1 Rev", "SO2R", 2),
	SOUTH_CITY_SPRINT_TRACK_2("120", "South City Sprint Track 2", "SO3", 2),
	SOUTH_CITY_SPRINT_TRACK_2_REV("121", "South City Sprint Track 2 Rev", "SO3R", 2),
	SOUTH_CITY_LONG("130", "South City Long", "SO4", 3),
	SOUTH_CITY_LONG_REV("131", "South City Long Rev", "SO4R",  3),
	SOUTH_CITY_TOWN_COURSE("140", "South City Town Course", "SO5", 3),
	SOUTH_CITY_TOWN_COURSE_REV("141", "South City Town Course Rev", "SO5R", 3),
	SOUTH_CITY_CHICANE_ROUTE("150", "South City Chicane Route ", "SO6", 2),
	SOUTH_CITY_CHICANE_ROUTE_REV("151", "South City Chicane Route Rev ", "SO6R", 2),
	FERN_BAY_CLUB("200", "Fern Bay Club", "FE1", 2),
	FERN_BAY_CLUB_REV("201", "Fern Bay Club Rev", "FE1R", 2),
	FERN_BAY_GREEN("210", "Fern Bay Green", "FE2", 3),
	FERN_BAY_GREEN_REV("211", "Fern Bay Green Rev", "FE2R", 3),
	FERN_BAY_GOLD("220", "Fern Bay Gold", "FE3", 3),
	FERN_BAY_GOLD_REV("221", "Fern Bay Gold Rev", "FE3R", 3),
	FERN_BAY_BLACK("230", "Fern Bay Black", "FE4", 4),
	FERN_BAY_BLACK_REV("231", "Fern Bay Black Rev", "FE4R", 4),
	FERN_BAY_RALLYCROSS("240", "Fern Bay Rallycross", "FE5", 2),
	FERN_BAY_RALLYCROSS_REV("241", "Fern Bay Rallycross Rev", "FE5R", 2),
	FERN_BAY_RALLYX_GREEN("250", "Fern Bay Rallyx Green", "FE6", 2),
	FERN_BAY_RALLYX_GREEN_REV("251", "Fern Bay Rallyx Green Rev", "FE6R", 2),
	AUTOCROSS("300","Autocross", "AU1", 0),
	SKIP_PAD("310", "Skid Pad", "AU2", 0),
	DRAG_STRIP("320", "Drag Strip", "AU3", 1),
	EIGHT_LANE_DRAG("330", "Eight Lane Drag", "AU4", 1),
	KYOTO_RING_OVAL("400", "Kyoto Ring Oval", "KY1", 2),
	KYOTO_RING_OVAL_REV("401", "Kyoto Ring Oval Rev", "KY1R", 2),
	KYOTO_RING_NATIONAL("410", "Kyoto Ring National", "KY2", 3),
	KYOTO_RING_NATIONAL_REV("411", "Kyoto Ring National Rev", "KY2R", 3),
	KYOTO_RING_GP_LONG("420", "Kyoto Ring GP Long", "KY3", 3),
	KYOTO_RING_GP_LONG_REV("421", "Kyoto Ring GP Long Rev", "KY3R", 3),
	WESTHILL_INTERNATIONAL("500", "Westhill International", "WE1", 3),
	WESTHILL_INTERNATIONAL_REV("501", "Westhill International Rev", "WE1R", 3),
	ASTON_CADET("600", "Aston Cadet", "AS1", 2),
	ASTON_CADET_REV("601", "Aston Cadet Rev", "AS1R", 2),
	ASTON_CLUB("610", "Aston Club", "AS2", 2),
	ASTON_CLUB_REV("611", "Aston Club Rev", "AS2R", 2),
	ASTON_NATIONAL("620", "Aston National", "AS3", 2),
	ASTON_NATIONAL_REV("621", "Aston National Rev", "AS3R", 2),
	ASTON_HISTORIC("630", "Aston Historic", "AS4", 3),
	ASTON_HISTORIC_REV("641", "Aston Historic Rev", "AS4R",  3),
	ASTON_GRAND_PRIX("650", "Aston Grand Prix", "AS5", 4),
	ASTON_GRAND_PRIX_REV("651", "Aston Grand Prix Rev", "AS5R", 4),
	ASTON_GRAND_TOURING("660", "Aston Grand Touring", "AS6", 4),
	ASTON_GRAND_TOURING_REV("661", "Aston Grand Touring Rev", "AS6R", 4),
	ASTON_NORTH("670", "Aston North", "AS7", 4),
	ASTON_NORTH_REV("671", "Aston North Rev", "AS7R", 4);
	
	
	private String number;
	private String name;
	private int splits;
	private String shortname;
	
	public String getShortname() {
		return shortname;
	}

	Track(String number, String name, String shortname, int splits) {
		this.number = number;
		this.name = name;
		this.shortname = shortname;
		this.splits = splits;
	}

	public String getNumber() {
		return number;
	}
	
	public static Track getTrackByNumber(String number) {
		for (Track track : Track.values()) {
			if (track.getNumber().equals(number)) {
				return track;
			}
		}
		throw new IllegalArgumentException("No track for given number found: " + number);
	}

	public String getName() {
		return name;
	}

	public int getSplits() {
		return splits;
	}

	public static Track getTrackByShortName(String trackname) {
		for (Track track : Track.values()) {
			if (track.getShortname().equals(trackname)) {
				return track;
			}
		}
		throw new IllegalArgumentException("No track for given number found: " + trackname);
	}
	
	
}
