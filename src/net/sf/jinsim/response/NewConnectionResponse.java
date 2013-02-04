package net.sf.jinsim.response;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import net.sf.jinsim.PacketType;



public class NewConnectionResponse extends RaceTrackingResponse {

	private String lfsname;
	private String playername;
	private boolean isAdmin;
	private byte numberOfConnections;
	
	
	public NewConnectionResponse() {
		super(PacketType.NEW_CONNECTION);
	}


	@Override
	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		lfsname = getString(buffer, 24);
		playername = getString(buffer, 24);
		isAdmin = (buffer.get() > 0);
		numberOfConnections = buffer.get();
		buffer.position(buffer.position()+2);
	}

	@Override
	public String toString() {
		return super.toString() + 
		", lfsname: " + lfsname +
		", playername: " + playername + 
		", isAdmin: " + isAdmin +
		", numberOfConnection: " + numberOfConnections;
	}


	public boolean isAdmin() {
		return isAdmin;
	}


	public byte getNumberOfConnections() {
		return numberOfConnections;
	}


	public String getPlayerName() {
		return playername;
	}


	public String getUsername() {
		return lfsname;
	}
	
	
	
	
	

}
