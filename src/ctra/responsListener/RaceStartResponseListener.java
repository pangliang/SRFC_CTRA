package ctra.responsListener;

import java.io.IOException;

import net.sf.jinsim.Client;
import net.sf.jinsim.Tiny;
import net.sf.jinsim.request.MessageRequest;
import net.sf.jinsim.request.TinyRequest;
import net.sf.jinsim.response.InSimResponse;

import org.apache.log4j.Logger;

import ctra.types.RestartThread;
import ctra.types.ServerManager;

public class RaceStartResponseListener implements ResponseListener
{
	Logger	log	= Logger.getLogger(this.getClass());
	
	@Override
	public void service(InSimResponse response, ServerManager serverManager,
			Client client)
	{
		// TODO Auto-generated method stub
		// RaceStartResponse rsp=(RaceStartResponse)response;
		
		try
		{
			RestartThread.stopDelay();
			serverManager.startRace();
			TinyRequest playersInfoRequest = new TinyRequest(Tiny.ALL_PLAYERS);
			client.send(playersInfoRequest);
			log.debug("RaceStart");
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			log.debug(e);
		}
		
	}
	
}
