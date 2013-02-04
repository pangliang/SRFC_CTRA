package ctra.responsListener;

import net.sf.jinsim.Client;
import net.sf.jinsim.response.InSimResponse;
import ctra.types.ServerManager;

public interface ResponseListener {
	
	void service(InSimResponse response, ServerManager serverManager, Client client);
}
