package ctra.responsListener;

import net.sf.jinsim.Client;
import net.sf.jinsim.response.InSimResponse;
import net.sf.jinsim.response.StateResponse;

import org.apache.log4j.Logger;

import ctra.types.ServerManager;

public class StateResponseListener implements ResponseListener {
	Logger log = Logger.getLogger(this.getClass());
	@Override
	public void service(InSimResponse response, ServerManager serverManager, Client client) {
		// TODO Auto-generated method stub
		//log.debug("Is racing: " + serverManager.isRacing());
		StateResponse rsp=(StateResponse)response;
		serverManager.setState(rsp);
	}


	
}
