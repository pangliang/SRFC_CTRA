package ctra.responsListener;

import net.sf.jinsim.Client;
import net.sf.jinsim.response.InSimResponse;

import org.apache.log4j.Logger;

import ctra.types.ServerManager;

public class FlagResponseListener implements ResponseListener
{
	Logger	log	= Logger.getLogger(this.getClass());
	
	@Override
	public void service(InSimResponse response, ServerManager serverManager,
			Client client)
	{
// TODO Auto-generated method stub
//		FlagResponse rsp = (FlagResponse) response;
//		
//		if (rsp.getFlag() == FlagResponse.FLAG_YELLOW && rsp.getOn()
//				&& serverManager.getAllPlayerList().size() > 1)
//		{
//			Player player = serverManager.getRacingPlayer(rsp.getPlayerId());
//			if (player == null)
//				return;
//			
//			long now=System.currentTimeMillis();
//			if(player.getLastYellowFlagTime()+5*1000<now)
//			{
//				player.setLastYellowFlagTime(now);
//				
//				int addIntegral = serverManager.getIntegralAdder().sum(
//						"YELLOW_FLAG", serverManager.getState().getNumPlayers(),
//						player.getRank());
//				player.makeMistakes(serverManager.getRaceId(),"YELLOW_FLAG",  addIntegral);
//				
//				if (player.needPenaltyAlart())
//				{
//					Text t = Text.PENALTY_ALART;
//					t.setParameter(1, "µ¼ÖÂ»ÆÆì");
//					t.setParameter(2, addIntegral);
//					MessageToConnectionRequest msgRequest = new MessageToConnectionRequest();
//					msgRequest.setConnectionId(player.getConnectionId());
//					msgRequest.setMessage(t.getText());
//					try
//					{
//						client.send(msgRequest);
//					} catch (IOException e)
//					{
//						// TODO Auto-generated catch block
//						log.debug(e);
//					}
//				}
//			}
//			
//			
//		}
		
	}
	
}
