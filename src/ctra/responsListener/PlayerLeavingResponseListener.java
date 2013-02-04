package ctra.responsListener;

import net.sf.jinsim.Client;
import net.sf.jinsim.request.MessageRequest;
import net.sf.jinsim.response.InSimResponse;
import net.sf.jinsim.response.PlayerLeavingResponse;

import org.apache.log4j.Logger;

import ctra.types.Player;
import ctra.types.RestartThread;
import ctra.types.ServerManager;

public class PlayerLeavingResponseListener implements ResponseListener
{
	Logger	log	= Logger.getLogger(this.getClass());
	
	@Override
	public void service(InSimResponse response, ServerManager serverManager,
			Client client)
	{
		// TODO Auto-generated method stub
		
		PlayerLeavingResponse rsp = (PlayerLeavingResponse) response;
		Player player = serverManager.getRacingPlayer(rsp.getPlayerId());
		if (player == null)
			return;
		
		serverManager.removeRacingPlayer(player);
		serverManager.addDNFPlayer(player);
		
		try
		{
			if (serverManager.getRacingPlayerList().size() == 0)
			{
				serverManager.endRace();
				
				if(serverManager.getTrackList().getNextDelayMin()>0)
				{
					RestartThread.delayStart(client, 30);
				}else{
					// 结束比赛；tiny包中换地图
					MessageRequest msg = new MessageRequest();
					msg.setMessage("/end");
					log.debug("/end");
					client.send(msg);
				}
			}
		} catch (Exception e)
		{
			log.debug("forServer", e);
		}
		
		log.debug(player.getLfsName() + ","
				+ serverManager.getAllPlayerRaceState());
	}
	
}
