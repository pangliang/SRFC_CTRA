package ctra.responsListener;

import java.io.IOException;

import net.sf.jinsim.Client;
import net.sf.jinsim.Tiny;
import net.sf.jinsim.request.MessageRequest;
import net.sf.jinsim.response.InSimResponse;
import net.sf.jinsim.response.TinyResponse;

import org.apache.log4j.Logger;

import ctra.types.RaceResultButton;
import ctra.types.ServerManager;
import ctra.types.Text;
import ctra.types.TrackConfig;

public class TinyResponseListener implements ResponseListener
{
	
	Logger	log	= Logger.getLogger(this.getClass());
	@Override
	public void service(InSimResponse response, ServerManager serverManager, Client client)
	{
		// TODO Auto-generated method stub
		
		TinyResponse rsp=(TinyResponse)response;
		if(rsp.getType().equals(Tiny.RACE_END))
		{
			

//			try
//			{
//				RaceResultButton rb=serverManager.newRaceResultButton();
//				rb.show(client,serverManager.getConnectedPlayerList());
//			} catch (IOException e)
//			{
//				// TODO Auto-generated catch block
//				log.debug("",e);
//			}
//			
			serverManager.endRace();
			log.debug(serverManager.getAllPlayerRaceState());
			
			try{
				
				MessageRequest msg=new MessageRequest();
				
				//换赛道
				TrackConfig trackConfig=serverManager.getTrackList().next();
				
				Text t=Text.CHANGE_MAP;
				t.setParameter(1, trackConfig.getTrack());
				t.setParameter(2, trackConfig.getLaps());
				t.setParameter(3, trackConfig.getCars());
				msg.setMessage(t.getText());
				client.send(msg);
				
				//命令换赛道
				msg.setMessage("/track="+trackConfig.getTrack());
				client.send(msg);
				
				//圈数
				msg.setMessage("/laps="+trackConfig.getLaps());
				client.send(msg);
				
				//风速
				msg.setMessage("/wind="+trackConfig.getWind());
				client.send(msg);
				
				//命令允许车辆
				msg.setMessage("/cars="+trackConfig.getCars());
				client.send(msg);
				
				//CLEAR
				msg.setMessage("/clear");
				client.send(msg);

				
			}catch(Exception e)
			{
				log.debug("",e);
			}
			
			
		}
		
	}
	
}
