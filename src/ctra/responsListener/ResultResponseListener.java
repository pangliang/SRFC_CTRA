package ctra.responsListener;

import net.sf.jinsim.Client;
import net.sf.jinsim.request.MessageRequest;
import net.sf.jinsim.request.MessageToConnectionRequest;
import net.sf.jinsim.response.InSimResponse;
import net.sf.jinsim.response.ResultResponse;

import org.apache.log4j.Logger;

import ctra.types.DAO;
import ctra.types.Player;
import ctra.types.RestartThread;
import ctra.types.ServerManager;
import ctra.types.Text;

public class ResultResponseListener implements ResponseListener
{
	Logger	log	= Logger.getLogger(this.getClass());
	
	@Override
	public void service(InSimResponse response, ServerManager serverManager,
			Client client)
	{
		// TODO Auto-generated method stub
		ResultResponse rsp = (ResultResponse) response;
		log.debug(rsp);
		Player player=serverManager.getRacingPlayer(rsp.getPlayerId());
		if(rsp.getPlayerId()!=0 && player!=null)
		{
			DAO.save(player,rsp,serverManager);
			
			forPlayer(player,rsp, serverManager, client);
			forServer(serverManager, client);
		}
		
		log.debug(serverManager.getAllPlayerRaceState());
		
		
	}
	
	
	private void forServer(ServerManager serverManager, Client client)
	{
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
		
	}
	
	private void forPlayer(Player player,ResultResponse rsp, ServerManager serverManager,
			Client client)
	{
		try
		{
			
			
			
			// ################# 算得分；
			
			// 比赛人数；
			int numPlayers = serverManager.getAllPlayerList().size();
			
			// 排位
			int position = rsp.getResultPosition() + 1; // rsp中第一名0，加1；
			
			// 玩家平均积分
			int sumRank = 0;
			for (Player p : serverManager.getAllPlayerList())
			{
				sumRank += p.getRank();
			}
			
			int averageRank = 0;
			if (numPlayers != 0)
				averageRank = sumRank / numPlayers;
			
			// 与平均分差距
			int averageRankGap = averageRank - player.getRank();
			
			// 得分
			
			
			
			String reason="";
			int addIntegral=0 ;
			if((rsp.getConfirmationFlags()&4) !=0)
			{
				reason="CONF_PENALTY_DT";
				addIntegral=0;
			}else if((rsp.getConfirmationFlags()&8) !=0)
			{
				reason="CONF_PENALTY_SG";
				addIntegral=0;
			}else if((rsp.getConfirmationFlags()&64) !=0)
			{
				reason="CONF_DID_NOT_PIT";
				addIntegral=0;
			}else{
				reason="FINISH_RACE";
				addIntegral = serverManager.getIntegralAdder().sumRaceWin(
						"FINISH_RACE", averageRankGap, numPlayers, position);
			}
			
			player.finishRace(reason,serverManager.getServerNum(),serverManager.getRaceId(),addIntegral);
			serverManager.removeRacingPlayer(player);
			serverManager.addFinishPlayer((byte) position, player);
			
			Text t = Text.GET_RESULT;
			t.setParameter(1, player.getMistakesTimes());
			t.setParameter(2, player.getDecIntegralForMistake());
			t.setParameter(3, player.getWinIntegral());
			t.setParameter(4, player.getIntegral());
			MessageToConnectionRequest msgRequest = new MessageToConnectionRequest();
			msgRequest.setConnectionId(player.getConnectionId());
			msgRequest.setMessage(t.getText());
			client.send(msgRequest);
			
			log.debug(t.getText());
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			log.debug("", e);
		}
	}
}
