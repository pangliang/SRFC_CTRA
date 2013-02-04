package net.sf.jinsim.response;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import net.sf.jinsim.Encoding;
import net.sf.jinsim.PacketType;



/**
 * 
 * @author Rene Pirringer (brilwing@liveforspeed.at)
 * 
 */
public class HiddenMessageResponse extends InSimResponse {

  private String message;
  private byte connectionId;
  private byte playerId;
	
	public byte getConnectionId() {
		return connectionId;
	}

	public String getMessage() {
		return message;
	}
	
	public byte getPlayerId() {
		return playerId;
	}

	public HiddenMessageResponse() {
		super(PacketType.HIDDEN_MESSAGE);
	}

	@Override
	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		buffer.position(buffer.position()+1);
		connectionId = buffer.get();
		playerId = buffer.get();
		message =  getString(buffer, 64);
	}

	@Override
	public String toString() {
		return super.toString() + ", connectionId: " + connectionId + ", playerId: " + playerId + ", message: " + message;
	}

}
