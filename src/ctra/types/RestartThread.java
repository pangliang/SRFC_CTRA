package ctra.types;

import java.io.IOException;

import net.sf.jinsim.Client;
import net.sf.jinsim.request.MessageRequest;

public class RestartThread extends Thread
{
	private static RestartThread	instance	= null;
	private Client					client		= null;
	private int						delaySecond;
	private boolean					running		= false;
	
	private RestartThread(Client client, int delaySecond)
	{
		this.client = client;
		this.delaySecond = delaySecond;
	}
	
	public static synchronized void delayStart(Client client, int delaySecond)
	{
		stopDelay();
		instance = new RestartThread(client, delaySecond);
		instance.start();
	}
	
	public static synchronized void stopDelay()
	{
		if (instance != null)
			instance.running = false;
	}
	
	public void run()
	{
		running = true;
		MessageRequest msg = new MessageRequest();
		Text t = Text.FINISH_RACE;
		int delay = -1;
		while (running && delay < delaySecond)
		{
			try
			{
				delay++;
				if ((delay % 30 == 0 && delaySecond - delay > 30)
						|| (delay % 10 == 0 && delaySecond - delay <= 30 && delaySecond
								- delay > 10) || delaySecond - delay <=10)
				{
					t.setParameter(1, delaySecond - delay);
					msg.setMessage(t.getText());
					client.send(msg);
				}
				Thread.sleep(1000);
			} catch (Exception e)
			{
				
			}
		}
		if(running)
		{
			msg.setMessage("/restart");
			try
			{
				client.send(msg);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
