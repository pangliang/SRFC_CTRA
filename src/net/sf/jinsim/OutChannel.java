package net.sf.jinsim;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;

import net.sf.jinsim.request.InSimRequest;
import net.sf.jinsim.response.OutGaugeResponse;
import net.sf.jinsim.response.OutSimResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OutChannel implements Channel {

	private static final int BUFFER_SIZE = 96;
	private static final int OUT_GAUGE_SIZE = 96;
	private static final int OUT_SIM_SIZE = 68;
	
	
	private Client client;
	private boolean running;
	
	static Log log = LogFactory.getLog(UDPChannel.class);
    
	protected DatagramChannel   datagramChannel;

	protected InetSocketAddress address;
	


    public OutChannel(int port) throws IOException {
    	this(new InetSocketAddress(port));
    }
    
	
	public OutChannel(InetSocketAddress inetSocketAddress) throws IOException {
		super();
        this.address = inetSocketAddress;
    	datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        datagramChannel.socket().bind(new InetSocketAddress(address.getPort()));
 	}

	public void close() throws IOException {
		running = false;
	}

	public int getPort() {
		return address.getPort();
	}

	public void send(InSimRequest packet) throws IOException {
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void run() {
	    running = true;
	
	    ByteBuffer buffer =  ByteBuffer.allocateDirect(BUFFER_SIZE);
	    buffer.order(ByteOrder.LITTLE_ENDIAN);
	    
	    while (running) {
	        try {
	        	
	        	datagramChannel.receive(buffer);
	            
	            if (buffer.position() == OUT_GAUGE_SIZE) {
	            	buffer.flip();
	            	/*
	            	if (log.isDebugEnabled()) {
	                	byte[] bytes = new byte[buffer.limit()];
	                	buffer.get(bytes);
	                	buffer.rewind();
                		String message = ("OUTGAUGE RECEIVE: bytes in buffer[" + (buffer.limit()) + "]: [" );
                		for (int i=0; i<buffer.limit(); i++) {
                			message += (bytes[i] + ", ");
                		}
                		log.debug(message + "]");
	                }
					*/
	            	
	            	OutGaugeResponse reponse = new OutGaugeResponse();
	            	reponse.construct(buffer);
	            	client.notifyListeners(reponse);
	                buffer.flip();
	            } else if (buffer.position() == OUT_SIM_SIZE || buffer.position() == OUT_SIM_SIZE - 4) {
	            	buffer.flip();
	            	
//	            	if (log.isDebugEnabled()) {
//	                	byte[] bytes = new byte[buffer.limit()];
//	                	buffer.get(bytes);
//	                	buffer.rewind();
//                		String message = ("OUTSIM RECEIVE: bytes in buffer[" + (buffer.limit()) + "]: [" );
//                		for (int i=0; i<buffer.limit(); i++) {
//                			message += (bytes[i] + ", ");
//                		}
//                		log.debug(message + "]");
//	                }
	            	OutSimResponse response = new OutSimResponse();
	            	response.construct(buffer);
	            	client.notifyListeners(response);
	                buffer.flip();
	            }
                Thread.sleep(10);
	        } catch (Exception e) {
	        	log.error("Something went wrong!", e);
	        }
	    }
	}


	public boolean isConnected() {
		return running;
	}

}
