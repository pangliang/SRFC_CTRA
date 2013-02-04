package ctra.responsListener;

import net.sf.jinsim.Client;
import net.sf.jinsim.response.InSimResponse;
import net.sf.jinsim.response.NewConnectionResponse;

import org.apache.log4j.Logger;

import ctra.types.Player;
import ctra.types.DAO;
import ctra.types.ServerManager;
import ctra.types.Text;
import ctra.types.WelcomeButton;

public class NewConnectionResponseListener implements ResponseListener {
	Logger log = Logger.getLogger(this.getClass());
	@Override
	public void service(InSimResponse response, ServerManager serverManager, Client client) {
		// TODO Auto-generated method stub
		
		NewConnectionResponse rsp=(NewConnectionResponse)response;
		if(rsp.getId()==0)return;
		try
		{
			
			Player player=new Player(rsp.getId(),rsp.getUsername(),rsp.isAdmin());
			DAO.mount(player);
			serverManager.addConnectedPlayer(player);
		
			WelcomeButton wb=new WelcomeButton();
			Text t=Text.WELCOME_BUTTON;
			t.setParameter(1, serverManager.getRaceId());
			wb.setText(t.getText());
			wb.setConnectionId(player.getConnectionId());
			client.send(wb);
			
			log.debug(player.getLfsName()+","+serverManager.getAllPlayerRaceState());
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			log.debug("NewConnectionResponseListener",e);
		} 
		
		//test(serverManager, client);
		
	}
	
	
}
