package net.sf.jinsim.request;

import java.nio.ByteBuffer;

import net.sf.jinsim.PacketType;



public class MessageToConnectionRequest extends InSimRequest {

	private int connectionId;
	private String message;

	public MessageToConnectionRequest() {
		super(PacketType.MESSAGE_TO_CONNECTION, 72);
	}
	
	public MessageToConnectionRequest(int connectionId, String message) {
		this();
		this.connectionId = connectionId;
		this.message = message;
	}
	
	public void assemble(ByteBuffer buffer) {
        super.assemble(buffer);
        buffer.put((byte)0);
        buffer.put((byte)connectionId);
        buffer.put((byte)0);
        buffer.put((byte)0);
        buffer.put((byte)0);
        assembleString(buffer, message, 64);
    }

	public void setConnectionId(int connectionId) {
		this.connectionId = connectionId;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
