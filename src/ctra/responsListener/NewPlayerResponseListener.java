package ctra.responsListener;

import java.io.IOException;

import net.sf.jinsim.Client;
import net.sf.jinsim.request.MessageRequest;
import net.sf.jinsim.request.MessageToConnectionRequest;
import net.sf.jinsim.response.InSimResponse;
import net.sf.jinsim.response.NewPlayerResponse;

import org.apache.log4j.Logger;

import ctra.types.Player;
import ctra.types.ServerManager;
import ctra.types.Text;
import ctra.types.WelcomeButton;

public class NewPlayerResponseListener implements ResponseListener
{
	Logger	log	= Logger.getLogger(this.getClass());
	
	@Override
	public void service(InSimResponse response, ServerManager serverManager,
			Client client)
	{
		// TODO Auto-generated method stub
		NewPlayerResponse rsp = (NewPlayerResponse) response;
		Player player = serverManager.getRacingPlayer(rsp.getPlayerId());
		
		if(player!=null)
		{
			
		}
		else{
			player=serverManager.getConnectedPlayer(rsp.getConnectionId());
			
			if (serverManager.isRacing()
					&& serverManager.getFinishPlayer(player.getLfsName()) == null
					&& serverManager.getDNFPlayer(player.getLfsName()) == null)
			{
				//new player join
				int payForJion = 0;
				if (serverManager.getState().getNumPlayers() > 1)
				{
					payForJion = serverManager.getIntegralAdder().sum("JION_RACE",
							serverManager.getState().getNumPlayers(),
							player.getRank());
				}
				
				player.jionNewRacing(serverManager.getServerNum(), serverManager
						.getRaceId(), rsp, payForJion);
				serverManager.addRacingPlayer(player);
				
				log.debug(player.getLfsName() + " JION_RACE");
				
			}else if(serverManager.getDNFPlayer(player.getLfsName()) != null)
			{
				//DNF player come back
				serverManager.removeDNFPlayer(player);
				player.jionNewRacing(serverManager.getServerNum(), serverManager
						.getRaceId(), rsp, 0);
				serverManager.addRacingPlayer(player);
				
				log.debug(player.getLfsName() + "DNF COME BACK");
			}
		}
		
		if (player.getRank() < serverManager.getCanJionRank())
		
		{
			try
			{
				MessageRequest spec = new MessageRequest();
				spec.setMessage("/spec " + player.getLfsName());
				client.send(spec);
				
				Text t = Text.CANNOT_JION_WARNING;
				t.setParameter(1, serverManager.getCanJionRank());
				t.setParameter(2, player.getRank());
				MessageToConnectionRequest warning = new MessageToConnectionRequest();
				warning.setConnectionId(player.getConnectionId());
				warning.setMessage(t.getText());
				client.send(warning);
				
				log.debug("/spec " + player.getLfsName());
				
			} catch (Exception e)
			{
				log.debug("/spec ", e);
			}
			
		} 
		
		if (!serverManager.getCarRestrictionList().check(
				rsp.getCar().getLongname(), rsp.getAddedMass(),
				rsp.getIntakeRestriction()))
		{
			try
			{
				MessageRequest spec = new MessageRequest();
				spec.setMessage("/spec " + player.getLfsName());
				client.send(spec);
				
				Text t = Text.Car_Restriction;
				t.setParameter(1, rsp.getCar().getLongname());
				t.setParameter(2, serverManager.getCarRestrictionList()
						.getAddedMass(rsp.getCar().getLongname()));
				t.setParameter(3, serverManager.getCarRestrictionList()
						.getIntakeRestriction(rsp.getCar().getLongname()));
				
				MessageToConnectionRequest warning = new MessageToConnectionRequest();
				warning.setConnectionId(player.getConnectionId());
				warning.setMessage(t.getText());
				client.send(warning);
				
				log.debug("/spec " + player.getLfsName());
				
			} catch (Exception e)
			{
				log.debug("/spec ", e);
			}
			
		} 
		
		if ((rsp.getPlayerType() & 2) != 0)
		{
			try
			{
				MessageRequest spec = new MessageRequest();
				spec.setMessage("/kick " + player.getLfsName());
				client.send(spec);
				
				Text t = Text.CANNOT_AI;
				MessageToConnectionRequest warning = new MessageToConnectionRequest();
				warning.setConnectionId(player.getConnectionId());
				warning.setMessage(t.getText());
				client.send(warning);
				
				log.debug("/kick " + player.getLfsName());
				
			} catch (Exception e)
			{
				log.debug("/kick ", e);
			}
			
		} 
		
//		if (serverManager.getFinishPlayer(player.getLfsName()) != null
//				|| serverManager.getDNFPlayer(player.getLfsName()) != null)
//		{
//			try
//			{
//				MessageRequest spec = new MessageRequest();
//				spec.setMessage("/spec " + player.getLfsName());
//				client.send(spec);
//				
//				MessageToConnectionRequest warning = new MessageToConnectionRequest();
//				warning.setConnectionId(player.getConnectionId());
//				warning.setMessage(Text.DNF_WARNING.getText());
//				client.send(warning);
//				
//				log.debug("/spec " + player.getLfsName());
//				
//			} catch (Exception e)
//			{
//				log.debug("/spec ", e);
//			}
//			
//		}
		
		WelcomeButton wb = new WelcomeButton();
		Text t = Text.WELCOME_BUTTON;
		t.setParameter(1, serverManager.getRaceId());
		wb.setText(t.getText());
		wb.setConnectionId(player.getConnectionId());
		try
		{
			client.send(wb);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug(player.getLfsName() + ","
				+ serverManager.getAllPlayerRaceState());
	}
	
}
