package ctra.responsListener;

import java.io.IOException;

import net.sf.jinsim.Client;
import net.sf.jinsim.PenaltyReason;
import net.sf.jinsim.request.MessageToConnectionRequest;
import net.sf.jinsim.response.InSimResponse;
import net.sf.jinsim.response.PenaltyResponse;

import org.apache.log4j.Logger;

import ctra.types.Player;
import ctra.types.ServerManager;
import ctra.types.Text;

public class PenaltyResponseListener implements ResponseListener
{
	Logger	log	= Logger.getLogger(this.getClass());
	
	@Override
	public void service(InSimResponse response, ServerManager serverManager, Client client)
	{
		// TODO Auto-generated method stub
		PenaltyResponse rsp = (PenaltyResponse) response;
		Player player = serverManager.getRacingPlayer(rsp.getPlayerId());
		if(player==null)return;
		if(serverManager.getAllPlayerList().size()<2)return;
		
		
		String reason=null;
		String reasonText=null;
		if (rsp.getReason().equals(PenaltyReason.WRONG_WAY))
		{
			reason = "WRONG_WAY";	
			reasonText ="ÄæÐÐ";
		}else if(rsp.getReason().equals(PenaltyReason.FALSE_START))
		{
			reason = "FALSE_START";
			reasonText ="ÇÀÅÜ";
		}else if(rsp.getReason().equals(PenaltyReason.SPEEDING))
		{
			reason = "SPEEDING";
			reasonText ="PITÀï³¬ËÙ";
		}
		
		if(reason!=null)
		{
			int addIntegral=serverManager.getIntegralAdder().sum(reason,serverManager.getState().getNumPlayers(), player.getRank());		
			player.makeMistakes(serverManager.getServerNum(),serverManager.getRaceId(),reason,addIntegral);
			
			log.debug(player.getLfsName()+":"+reason);
			
			
			if(player.needPenaltyAlart())
			{
				Text t=Text.PENALTY_ALART;
				t.setParameter(1, reasonText);
				t.setParameter(2, addIntegral);
				MessageToConnectionRequest msgRequest = new MessageToConnectionRequest();
				msgRequest.setConnectionId(player.getConnectionId());
				msgRequest.setMessage(t.getText());
				try
				{
					client.send(msgRequest);
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					log.debug(e);
				}
			}
			
			
			
		}
		
		
	}
	
	
	
}
