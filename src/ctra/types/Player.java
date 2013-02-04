package ctra.types;

import net.sf.jinsim.response.NewPlayerResponse;
import net.sf.jinsim.types.Tyres;

import org.apache.log4j.Logger;

public class Player
{
	
	Logger					log						= Logger
															.getLogger(Player.class);
	
	public static final int	RACE_STATE_CONNECT		= 0;
	public static final int	RACE_STATE_RACING		= 1;
	public static final int	RACE_STATE_FINISH		= 2;
	public static final int	RACE_STATE_DNF			= 3;
	
	private byte			playerId;
	private byte			connectionId;
	private byte			playerType;
	private int				playerFlags;
	private String			playerName;
	private String			numberPlate;
	private String			car;
	private String			skinName;
	private Tyres			tyres;
	private byte			addedMass;
	private byte			intakeRestriction;
	private byte			passengers;
	private byte			numberInRace;
	private byte			model;
	
	private boolean			needPenaltyAlart		= true;
	
	private String			lfsName;
	private boolean			isAdmin;
	private int				rank					= 0;
	private int				integral				= 0;
	
	private int				decIntegralForMistake	= 0;
	private int				mistakesTimes			= 0;
	private int				winIntegral				= 0;
	private int				payForJion				= 0;
	
	private long			lastYellowFlagTime		= 0;
	
	public Player(byte connectionId, String lfsName, boolean isAdmin)
	{
		
		this.connectionId = connectionId;
		this.lfsName = lfsName;
		this.isAdmin = isAdmin;
		
	}
	
	public void jionNewRacing(int serverNum,int raceId,NewPlayerResponse rsp, int payForJion)
	{
		this.setPlayerId(rsp.getPlayerId());
		this.setConnectionId(rsp.getConnectionId());
		this.setPlayerType(rsp.getPlayerType());
		this.setPlayerFlags(rsp.getPlayerFlags());
		this.setPlayerName(rsp.getPlayerName());
		this.setNumberPlate(rsp.getNumberPlate());
		this.setCar(rsp.getCar().getLongname());
		this.setSkinName(rsp.getSkinName());
		this.setTyres(rsp.getTyres());
		this.setAddedMass(rsp.getAddedMass());
		this.setIntakeRestriction(rsp.getIntakeRestriction());
		this.setPassengers(rsp.getPassengers());
		this.setNumberInRace(rsp.getNumberInRace());
		this.setModel(rsp.getModel());
		
		this.decIntegralForMistake = 0;
		this.winIntegral = 0;
		this.mistakesTimes = 0;
		this.integral += payForJion;
		this.payForJion = payForJion;
		
		DAO.addIntegralLog(serverNum,raceId,this, "JION_RACE", payForJion);
		
	}
	
	public void makeMistakes(int serverNum,int raceId,String reason, int decIntegral)
	{
		this.integral += decIntegral;
		this.decIntegralForMistake += decIntegral;
		mistakesTimes++;
		DAO.addIntegralLog(serverNum,raceId,this, reason, decIntegral);
	}
	
	public void finishRace(String reason,int serverNum,int raceId,int addIntegral)
	{
		this.integral += addIntegral;
		this.winIntegral += addIntegral;
		DAO.addIntegralLog(serverNum,raceId,this, reason, addIntegral);
	}
	
	public void closePenaltyAlart()
	{
		this.needPenaltyAlart = false;
	}
	
	public void openPenaltyAlart()
	{
		this.needPenaltyAlart = true;
	}
	
	public boolean needPenaltyAlart()
	{
		return this.needPenaltyAlart;
	}
	
	public byte getPlayerId()
	{
		return playerId;
	}
	
	public byte getAddedMass()
	{
		return addedMass;
	}
	
	public String getCar()
	{
		return car;
	}
	
	public byte getIntakeRestriction()
	{
		return intakeRestriction;
	}
	
	public byte getNumberInRace()
	{
		return numberInRace;
	}
	
	public String getNumberPlate()
	{
		return numberPlate;
	}
	
	public byte getPassengers()
	{
		return passengers;
	}
	
	public int getPlayerFlags()
	{
		return playerFlags;
	}
	
	public byte getPlayerType()
	{
		return playerType;
	}
	
	public String getSkinName()
	{
		return skinName;
	}
	
	public Tyres getTyres()
	{
		return tyres;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + ", playerId:+" + playerId + ", uniqueId: "
				+ connectionId + ",  lfsName=" + lfsName + ",  integral="
				+ integral + ",  isAdmin=" + isAdmin + ", playerType: "
				+ playerType + ", playerFlags: " + playerFlags
				+ ", playerName: " + playerName + ", numberPlate: "
				+ numberPlate + ", car: " + car + ", skinName: " + skinName
				+ ", tyres: " + tyres + ", addedMass: " + addedMass
				+ ", intakeRestriction: " + intakeRestriction + ", model: "
				+ model + ", passengers: " + passengers + ", numberInRace: "
				+ numberInRace;
	}
	
	public byte getConnectionId()
	{
		return connectionId;
	}
	
	public void setConnectionId(byte uniqueId)
	{
		this.connectionId = uniqueId;
	}
	
	public byte getModel()
	{
		return model;
	}
	
	public String getLfsName()
	{
		return lfsName;
	}
	
	public boolean isAdmin()
	{
		return isAdmin;
	}
	
	public int getIntegral()
	{
		return integral;
	}
	
	public String getPlayerName()
	{
		return playerName;
	}
	
	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}
	
	public void setPlayerId(byte playerId)
	{
		this.playerId = playerId;
	}
	
	public void setPlayerType(byte playerType)
	{
		this.playerType = playerType;
	}
	
	public void setPlayerFlags(int playerFlags)
	{
		this.playerFlags = playerFlags;
	}
	
	public void setNumberPlate(String numberPlate)
	{
		this.numberPlate = numberPlate;
	}
	
	public void setCar(String car)
	{
		this.car = car;
	}
	
	public void setSkinName(String skinName)
	{
		this.skinName = skinName;
	}
	
	public void setTyres(Tyres tyres)
	{
		this.tyres = tyres;
	}
	
	public void setAddedMass(byte addedMass)
	{
		this.addedMass = addedMass;
	}
	
	public void setIntakeRestriction(byte intakeRestriction)
	{
		this.intakeRestriction = intakeRestriction;
	}
	
	public void setPassengers(byte passengers)
	{
		this.passengers = passengers;
	}
	
	public void setNumberInRace(byte numberInRace)
	{
		this.numberInRace = numberInRace;
	}
	
	public void setModel(byte model)
	{
		this.model = model;
	}
	
	public void setIntegral(int integral)
	{
		this.integral = integral;
	}
	
	public int getDecIntegralForMistake()
	{
		return decIntegralForMistake;
	}
	
	public int getRank()
	{
		return Rank.getRank(integral);
	}
	
	public long getLastYellowFlagTime()
	{
		return lastYellowFlagTime;
	}
	
	public void setLastYellowFlagTime(long lastYellowFlagTime)
	{
		this.lastYellowFlagTime = lastYellowFlagTime;
	}
	
	public int getMistakesTimes()
	{
		return mistakesTimes;
	}
	
	public int getWinIntegral()
	{
		return winIntegral;
	}
	
	public int getPayForJion()
	{
		return payForJion;
	}
	
	public int getIncome()
	{
		return winIntegral + payForJion + decIntegralForMistake;
	}
	
}
