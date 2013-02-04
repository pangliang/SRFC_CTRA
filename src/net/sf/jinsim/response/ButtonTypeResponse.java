package net.sf.jinsim.response;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import net.sf.jinsim.PacketType;



public class ButtonTypeResponse extends ButtonClickedResponse {

	private String text;
	
	public ButtonTypeResponse() {
		super(PacketType.BUTTON_TYPED);
	}

	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		text = getString(buffer, 96);
	}

	public String getText() {
		return text;
	}
}
