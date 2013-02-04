package net.sf.jinsim.response;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;


import net.sf.jinsim.PacketType;
import net.sf.jinsim.request.ButtonSubtype;


public class ButtonFunctionResponse extends InSimResponse {

	private ButtonSubtype subtype;
	private byte connectionId;
	private byte clickId;
	
	
	@Override
	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		subtype = ButtonSubtype.getButtonSubtype(buffer.get());
		connectionId= buffer.get();
        clickId = buffer.get();
        buffer.position(buffer.position()+2);
	}


	public byte getClickId() {
		return clickId;
	}


	public byte getConnectionId() {
		return connectionId;
	}


	public ButtonSubtype getSubtype() {
		return subtype;
	}


	public ButtonFunctionResponse() {
		super(PacketType.BUTTON_FUNCTION);
	}

}
