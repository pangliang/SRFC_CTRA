package ctra.responsListener;

import net.sf.jinsim.Client;
import net.sf.jinsim.response.InSimResponse;
import net.sf.jinsim.response.SmallResponse;
import ctra.types.ServerManager;

public class SmallResponseListener implements ResponseListener
{

	@Override
	public void service(InSimResponse response, ServerManager serverManager,
			Client client)
	{
		// TODO Auto-generated method stub
		SmallResponse rsp=(SmallResponse)response;
		if(rsp.getValue()==1)
		{
			serverManager.getTrackList().nextAtOnce();
		}
	}
	
}
