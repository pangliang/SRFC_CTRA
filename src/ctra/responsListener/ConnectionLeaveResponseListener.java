package ctra.responsListener;

import net.sf.jinsim.Client;
import net.sf.jinsim.response.ConnectionLeaveResponse;
import net.sf.jinsim.response.InSimResponse;

import org.apache.log4j.Logger;

import ctra.types.Player;
import ctra.types.ServerManager;

public class ConnectionLeaveResponseListener implements ResponseListener {
	Logger log = Logger.getLogger(this.getClass());
	@Override
	public void service(InSimResponse response, ServerManager serverManager, Client client) {
		// TODO Auto-generated method stub
		
		ConnectionLeaveResponse rsp=(ConnectionLeaveResponse)response;
		Player player=serverManager.getConnectedPlayer(rsp.getId());
		serverManager.removeConnectedPlayer(player);
		
		log.debug(player.getLfsName() +","+serverManager.getAllPlayerRaceState());
	}

}
