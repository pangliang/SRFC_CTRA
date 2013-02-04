package net.sf.jinsim;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import net.sf.jinsim.request.InSimRequest;
import net.sf.jinsim.request.TinyRequest;
import net.sf.jinsim.response.InSimResponse;
import net.sf.jinsim.response.ResponseFactory;
import net.sf.jinsim.response.ServerClosedResponse;
import net.sf.jinsim.response.UnhandledPacketTypeException;

import org.apache.log4j.Logger;

public abstract class AbstractChannel implements Channel
{
	Logger						log			= Logger
													.getLogger(AbstractChannel.class);
	
	protected static final int	BUFFER_SIZE	= 2048;
	private static final long	TIMEOUT		= 30000;
	protected Client			client;
	protected boolean			running;
	protected ByteBuffer		sendBuffer;
	
	public AbstractChannel()
	{
		sendBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
		sendBuffer.order(ByteOrder.LITTLE_ENDIAN);
	}
	
	/**
	 * Close down the receiver. This means you don't want to receive any more
	 * responses from the LFS server.
	 */
	synchronized public void close() throws IOException
	{
		this.running = false;
	}
	
	public void run()
	{
		running = true;
		
		ResponseFactory packetFactory = ResponseFactory.getInstance();
		ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		
		ByteBuffer sizeBuffer = ByteBuffer.allocate(1);
		
		long currentTime = System.currentTimeMillis();
		int size = 0;
		InSimResponse packetData = null;
		while (running)
		{
			try
			{
				
				// read the packet size first
				if (size == 0)
				{
					sizeBuffer.clear();
					int numberRead = -1;
					try
					{
						numberRead = receive(sizeBuffer);
					} catch (IOException ex)
					{
						// do nothing chase numberRead is already -1
						log.debug("receiver something wrong !!", ex);
					}
					if (numberRead == -1)
					{
						close();
						log.debug("AbstractChannel run() return ");
						return;
					}
					
					if (numberRead > 0)
					{
						sizeBuffer.flip();
						size = (sizeBuffer.get() & 0xff) - 1;
						if (size < 0)
						{
							size = 0;
						} else
						{
							buffer.limit(size);
						}
					}
				}
				
				if (size != 0)
				{
					// now we know the packet size, so fill the buffer with all
					// packet data
					
					int numberRead = receive(buffer);
					
					if (numberRead == -1)
					{
						close();
						log.debug("AbstractChannel run() return ");
						return;
					}
				}
				
				if (size > 0 && buffer.position() == size)
				{
					// the buffer contains all data of the packet so lets
					// process the packet
					buffer.flip();
					
					// if (log.isDebugEnabled()) {
					// byte[] bytes = new byte[buffer.limit()];
					// buffer.get(bytes);
					// buffer.rewind();
					// String message = ("RECEIVE: bytes in buffer[" + (size+1)
					// + "]: [" );
					// for (int i=0; i<buffer.limit(); i++) {
					// message += (bytes[i] + ", ");
					// }
					// log.debug(message + "]");
					//	                	
					// }
					
					try
					{
						packetData = packetFactory.getPacketData(buffer);
						client.notifyListeners(packetData);
					} catch (Exception ex)
					{
						log.error("Unknown packet: " + ex.getMessage());
					}finally{
						
						//must do
						buffer.flip();
						size = 0;
					}
					
					
				}
				
				if (currentTime + TIMEOUT < System.currentTimeMillis())
				{
					client.send(new TinyRequest(Tiny.PING));
					currentTime = System.currentTimeMillis();
				}
				
				Thread.sleep(100);
			} catch (Exception e)
			{
				log.error("Something went wrong!", e);
			}
		}
		
		log.debug("AbstractChannel run() return ");
	}
	
	public synchronized void send(InSimRequest packet) throws IOException
	{
		packet.assemble(sendBuffer);
		sendBuffer.flip();
		
		int size = packet.getSize();
		if (log.isDebugEnabled())
		{
			byte[] bytes = new byte[size];
			sendBuffer.get(bytes);
			sendBuffer.flip();
			
//			 String message = ("SEND: bytes in " + packet.getType() +
//			 " buffer: [" );
//			 for (int i=0; i<size; i++) {
//			 message += (bytes[i] + ", ");
//			 }
//			 log.debug(message + "]");
			
		}
		send(sendBuffer);
	}
	
	protected abstract void send(ByteBuffer sendBuffer) throws IOException;
	
	public void setClient(Client client)
	{
		this.client = client;
		
	}
	
	protected abstract int receive(ByteBuffer buffer) throws IOException;
	
	public boolean isConnected()
	{
		return running;
	}
	
	public void finalize()
	{
		log.debug("AbstractChannel finalize()");
	}
	
}
