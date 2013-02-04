package ctra.types;

import java.util.ArrayList;
import java.util.HashMap;

import net.sf.jinsim.response.StateResponse;

public class ServerManager
{
	private int 			serverNum;
	private int				canJionRank;
	private TrackList		trackList;
	private IntegralAdder	integralAdder;
	private CarRestrictionList carRestrictionList;
	
	HashMap<Byte, Player>	racingPlayerList	= new HashMap<Byte, Player>();
	HashMap<Byte, Player>	connectedPlayerList	= new HashMap<Byte, Player>();
	HashMap<Byte, Player>	finishPlayerList	= new HashMap<Byte, Player>();
	HashMap<String, Player>	DNFPlayerList		= new HashMap<String, Player>();
	
	private int				raceId				=0;
	boolean					isRacing			= false;
	StateResponse			state;
	
	RaceResultButton		raceResultButton=null;
	
	RestartThread restartThread=null;
	
	public ServerManager(int serverNum,int canJionRank, TrackList trackList,
			IntegralAdder integralAdder,CarRestrictionList carRestrictionList)
	{
		this.serverNum=serverNum;
		this.canJionRank = canJionRank;
		this.trackList = trackList;
		this.integralAdder = integralAdder;
		this.raceId=DAO.getNextRaceId(serverNum);
		this.carRestrictionList=carRestrictionList;
	}
	
	public RaceResultButton newRaceResultButton()
	{
		raceResultButton= new RaceResultButton(this);
		return raceResultButton;
	}
	
	public RaceResultButton getRaceResultButton()
	{
		return raceResultButton;
	}
	
	public ArrayList<Player> getAllPlayerList()
	{
		ArrayList<Player> list=new ArrayList<Player>();
		list.addAll(racingPlayerList.values());
		list.addAll(finishPlayerList.values());
		list.addAll(DNFPlayerList.values());
		
		return list;
	}
	
	public String getAllPlayerRaceState()
	{
		return "connect:" + connectedPlayerList.size() + ",racing:"
				+ racingPlayerList.size() + ",finish:"
				+ finishPlayerList.size() + ",dnf:" + DNFPlayerList.size();
	}
	
	
	
	public void endRace()
	{
		removeAllDNFPlayer();
		removeAllFinishPlayer();
		removeAllRacingPlayer();
		this.isRacing = false;
	}
	
	public void startRace()
	{
		removeAllDNFPlayer();
		removeAllFinishPlayer();
		removeAllRacingPlayer();
		this.isRacing = true;
		raceId=DAO.getNextRaceId(serverNum);
	}
	
	public void addDNFPlayer(Player player)
	{
		if (player == null)
			return;
		DNFPlayerList.put(player.getLfsName(), player);
	}
	
	public Player getDNFPlayer(String lfsName)
	{
		return DNFPlayerList.get(lfsName);
	}
	
	public void removeAllDNFPlayer()
	{
		DNFPlayerList.clear();
	}
	
	public void removeDNFPlayer(Player player)
	{
		if (player == null)
			return;
		
		DNFPlayerList.remove(player.getLfsName());
	}
	
	public void addFinishPlayer(byte resultPosition,Player player)
	{
		if (player == null)
			return;
		finishPlayerList.put(resultPosition, player);
	}
	
	public Player getFinishPlayer(String lfsName)
	{
		for (Player player : finishPlayerList.values())
		{
			if(player.getLfsName().equals(lfsName))
				return player;
		}
		return null;
	}
	
	public void removeAllFinishPlayer()
	{
		finishPlayerList.clear();
	}
	
	public void addConnectedPlayer(Player player)
	{
		if (player == null)
			return;
		connectedPlayerList.put(player.getConnectionId(), player);
	}
	
	public void removeConnectedPlayer(Player player)
	{
		if (player == null)
			return;
		connectedPlayerList.remove(player.getConnectionId());
	}
	
	public Player getConnectedPlayer(byte connectionId)
	{
		return connectedPlayerList.get(connectionId);
		
	}
	
	public void addRacingPlayer(Player player)
	{
		if (player == null)
			return;
		racingPlayerList.put(player.getPlayerId(), player);
	}
	
	public void removeRacingPlayer(Player player)
	{
		if (player == null)
			return;
		DAO.save(player);
		racingPlayerList.remove(player.getPlayerId());
	}
	
	public void removeAllRacingPlayer()
	{
		for (Player player : racingPlayerList.values())
		{
			DAO.save(player);
		}
		racingPlayerList.clear();
	}
	
	public Player getRacingPlayer(byte playerId)
	{
		return racingPlayerList.get(playerId);
		
	}
	
	

	public HashMap<Byte, Player> getRacingPlayerList()
	{
		return racingPlayerList;
	}
	
	public HashMap<Byte, Player> getConnectedPlayerList()
	{
		return connectedPlayerList;
	}

	public HashMap<Byte, Player> getFinishPlayerList()
	{
		return finishPlayerList;
	}
	
	public HashMap<String, Player> getDNFPlayerList()
	{
		return DNFPlayerList;
	}

	public StateResponse getState()
	{
		return state;
	}
	
	public void setState(StateResponse state)
	{
		this.state = state;
	}
	
	public boolean isRacing()
	{
		return isRacing;
	}
	
	public int getRaceId()
	{
		return raceId;
	}
	
	public int getCanJionRank()
	{
		return canJionRank;
	}
	
	public TrackList getTrackList()
	{
		return trackList;
	}
	
	public IntegralAdder getIntegralAdder()
	{
		return integralAdder;
	}
	
	public int getServerNum()
	{
		return serverNum;
	}

	public CarRestrictionList getCarRestrictionList()
	{
		return carRestrictionList;
	}

	
	
	
	
}
