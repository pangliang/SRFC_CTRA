package ctra.responsListener;

import java.io.IOException;

import net.sf.jinsim.Client;
import net.sf.jinsim.request.ButtonFunctionRequest;
import net.sf.jinsim.request.ButtonRequest;
import net.sf.jinsim.request.ButtonSubtype;
import net.sf.jinsim.response.ButtonClickedResponse;
import net.sf.jinsim.response.InSimResponse;
import ctra.types.ButtonClickId;
import ctra.types.ServerManager;

public class ButtonClickedResponseListener implements ResponseListener
{
	
	@Override
	public void service(InSimResponse response, ServerManager serverManager,
			Client client)
	{
		// TODO Auto-generated method stub
		ButtonClickedResponse rsp = (ButtonClickedResponse) response;
		if (rsp.getClickId() == ButtonClickId.CLOSE_RESULT_BUTTON_ID)
		{
			ButtonFunctionRequest bfq = new ButtonFunctionRequest();
			bfq.setConnectionId(rsp.getConnectionId());
			bfq.setSubtype(ButtonSubtype.DELETE_BUTTON);
			try
			{
				for (ButtonRequest b : serverManager.getRaceResultButton()
						.getButtons())
				{
					bfq.setClickId(b.getClickId());
					client.send(bfq);
				}
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
