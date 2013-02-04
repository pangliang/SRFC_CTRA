package ctra.responsListener;

import net.sf.jinsim.Client;
import net.sf.jinsim.request.MessageToConnectionRequest;
import net.sf.jinsim.response.InSimResponse;
import net.sf.jinsim.response.MessageResponse;

import org.apache.log4j.Logger;

import ctra.types.Player;
import ctra.types.ServerManager;
import ctra.types.Text;

public class MessageResponseListener implements ResponseListener
{
	Logger	log	= Logger.getLogger(this.getClass());
	
	@Override
	public void service(InSimResponse response, ServerManager serverManager,
			Client client)
	{
		// TODO Auto-generated method stub
		
		MessageResponse rsp = (MessageResponse) response;
		String msg = rsp.getMessage().toUpperCase();// ×ª´óÐ´Æ¥Åä£»
		
		if (msg.indexOf("LOCC RANK") > -1)
		{
			
			try
			{
				Player player = serverManager.getConnectedPlayer(rsp
						.getConnectionId());
				MessageToConnectionRequest msgRequest = new MessageToConnectionRequest();
				msgRequest.setConnectionId(player.getConnectionId());
				Text t = Text.SHOW_INTEGRAL;
				t.setParameter(1, player.getIntegral());
				t.setParameter(2, player.getRank());
				msgRequest.setMessage(t.getText());
				client.send(msgRequest);
				
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				log.debug(e);
			}
		} else if (msg.indexOf("PL OFF") > -1)
		{
			Player player = serverManager.getConnectedPlayer(rsp
					.getConnectionId());
			player.closePenaltyAlart();
		} else if (msg.indexOf("PL ON") > -1)
		{
			Player player = serverManager.getConnectedPlayer(rsp
					.getConnectionId());
			player.openPenaltyAlart();
		} else if (msg.indexOf("NEXT TRACK") > -1)
		{
			try
			{
				Player player = serverManager.getConnectedPlayer(rsp
						.getConnectionId());
				MessageToConnectionRequest msgRequest = new MessageToConnectionRequest();
				msgRequest.setConnectionId(player.getConnectionId());
				Text t = Text.MSG_NEXT_TRACK;
				t.setParameter(1, serverManager.getTrackList().getNextTrackName());
				t.setParameter(2, serverManager.getTrackList().getNextDelayMin()+"");
				t.setParameter(3, serverManager.getTrackList().getNextTrackCars());
				msgRequest.setMessage(t.getText());
				client.send(msgRequest);
				
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				log.debug(e);
			}
			
		}
		
	}
	
}
