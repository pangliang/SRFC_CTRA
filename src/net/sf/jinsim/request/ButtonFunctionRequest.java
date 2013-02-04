package net.sf.jinsim.request;

import java.nio.ByteBuffer;

import net.sf.jinsim.PacketType;



public class ButtonFunctionRequest extends InSimRequest {

	private ButtonSubtype subtype = ButtonSubtype.DELETE_BUTTON;
	private byte connectionId;
	private byte clickId;
	
	public ButtonFunctionRequest() {
		super(PacketType.BUTTON_FUNCTION, 8);
	}

	
	public void assemble(ByteBuffer buffer) {
        super.assemble(buffer);
        buffer.put(subtype.getId());
        buffer.put(connectionId);
        buffer.put(clickId);
        buffer.position(buffer.position()+2);
    }


	public void setClickId(byte clickId) {
		this.clickId = clickId;
	}


	public void setConnectionId(byte connectionId) {
		this.connectionId = connectionId;
	}


	public void setSubtype(ButtonSubtype subtype) {
		this.subtype = subtype;
	}

	
}
