package net.sf.jinsim.response;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import net.sf.jinsim.PacketType;



public class AutocrossLayoutResponse extends InSimResponse {

	private byte start;
	private byte checkpoints;
	private byte objects;
	private String name;
	
	@Override
	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		buffer.position(buffer.position()+1);
		start = buffer.get();
		checkpoints = buffer.get();
		objects = buffer.get();
		name = getString(buffer, 32);
	}

	public byte getStart() {
		return start;
	}

	public byte getCheckpoints() {
		return checkpoints;
	}

	public byte getObjects() {
		return objects;
	}

	public String getName() {
		return name;
	}

	public AutocrossLayoutResponse() {
		super(PacketType.AUTOCROSS_LAYOUT);
	}

}
