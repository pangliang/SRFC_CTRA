package net.sf.jinsim;

import java.io.IOException;

import net.sf.jinsim.request.InSimRequest;

public interface Channel extends Runnable {

	public int getPort();
	
	public void close() throws IOException;
	
	public void send(InSimRequest packet) throws IOException;
	
	public void setClient(Client client);
	
	public boolean isConnected();
	
}
