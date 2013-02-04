
import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;

import net.sf.jinsim.Client;
import net.sf.jinsim.Tiny;
import net.sf.jinsim.request.MessageRequest;
import net.sf.jinsim.request.TinyRequest;
import net.sf.jinsim.response.ButtonClickedResponse;
import net.sf.jinsim.response.CarResetResponse;
import net.sf.jinsim.response.ConnectionLeaveResponse;
import net.sf.jinsim.response.FinishedRaceResponse;
import net.sf.jinsim.response.FlagResponse;
import net.sf.jinsim.response.InSimResponse;
import net.sf.jinsim.response.LapTimeResponse;
import net.sf.jinsim.response.MessageResponse;
import net.sf.jinsim.response.NewConnectionResponse;
import net.sf.jinsim.response.NewPlayerResponse;
import net.sf.jinsim.response.PenaltyResponse;
import net.sf.jinsim.response.PlayerLeavingResponse;
import net.sf.jinsim.response.RaceStartResponse;
import net.sf.jinsim.response.ResultResponse;
import net.sf.jinsim.response.ServerClosedResponse;
import net.sf.jinsim.response.SmallResponse;
import net.sf.jinsim.response.SplitTimeResponse;
import net.sf.jinsim.response.StateResponse;
import net.sf.jinsim.response.TinyResponse;

import org.apache.log4j.Logger;
import org.jdom.JDOMException;

import ctra.responsListener.ButtonClickedResponseListener;
import ctra.responsListener.CarResetResponseListener;
import ctra.responsListener.ConnectionLeaveResponseListener;
import ctra.responsListener.FinishedRaceResponseListener;
import ctra.responsListener.FlagResponseListener;
import ctra.responsListener.LapTimeResponseListener;
import ctra.responsListener.MessageResponseListener;
import ctra.responsListener.NewConnectionResponseListener;
import ctra.responsListener.NewPlayerResponseListener;
import ctra.responsListener.PenaltyResponseListener;
import ctra.responsListener.PlayerLeavingResponseListener;
import ctra.responsListener.RaceStartResponseListener;
import ctra.responsListener.ResponseListener;
import ctra.responsListener.ResultResponseListener;
import ctra.responsListener.ServerClosedResponseListener;
import ctra.responsListener.SmallResponseListener;
import ctra.responsListener.SplitTimeResponseListener;
import ctra.responsListener.StateResponseListener;
import ctra.responsListener.TinyResponseListener;
import ctra.types.CarRestrictionList;
import ctra.types.IntegralAdder;
import ctra.types.ServerManager;
import ctra.types.TrackConfig;
import ctra.types.TrackList;

public class SRFC_CTRA extends Client{
	Logger log = Logger.getLogger(SRFC_CTRA.class);
	
	private int serverNum;
	private String serverIp;
	private int port;
	private String adminPassword;
	private int canJionRank;
	ServerManager serverManager;

	private HashMap<Class<? extends InSimResponse>,ResponseListener> listenerMap=new HashMap<Class<? extends InSimResponse>,ResponseListener>();
	
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	public SRFC_CTRA(String configFileName,String trackListFileName,String addIntegralRulesFileName,String carRestrictionListFileName) throws IOException, JDOMException {
		ResourceBundle rsb = ResourceBundle.getBundle(configFileName);
		serverNum=Integer.parseInt(rsb.getString("SERVER_NUM"));
		serverIp = rsb.getString("SERVER_IP");
		port = Integer.parseInt(rsb.getString("SERVER_PORT"));
		adminPassword = rsb.getString("SERVER_ADMIN_PASSWORD");
		canJionRank	=	Integer.parseInt(rsb.getString("CAN_JION_RANK"));
		
		TrackList trackList=new TrackList(ClassLoader.getSystemResource("").getPath()+trackListFileName);
		IntegralAdder integralAdder=new IntegralAdder(ClassLoader.getSystemResource("").getPath()+addIntegralRulesFileName);
		CarRestrictionList carRestrictionList=new CarRestrictionList(ClassLoader.getSystemResource("").getPath()+carRestrictionListFileName);
		serverManager=new ServerManager(serverNum,canJionRank,trackList,integralAdder,carRestrictionList);
		
		setLinsteners();
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			SRFC_CTRA prog = new SRFC_CTRA(args[0],args[1],args[2],args[3]);
			prog.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	public void start() throws JDOMException, IOException{
		
		connect(serverIp, port, adminPassword, "SRFC_CTRA",
				(short) 0, 32, 0,(char)0);

		TinyRequest lapInfoRequest = new TinyRequest(Tiny.SEND_STATE_INFO);
		send(lapInfoRequest);
		
		TinyRequest allConnectionsRequest = new TinyRequest(Tiny.ALL_CONNECTIONS);
		send(allConnectionsRequest);
		
		TinyRequest playersInfoRequest = new TinyRequest(Tiny.ALL_PLAYERS);
		send(playersInfoRequest);
		
		MessageRequest msg=new MessageRequest();
		
		msg.setMessage("/end");
		send(msg);
		
		//换赛道
		TrackConfig trackConfig=serverManager.getTrackList().next();
		
		//命令换赛道
		msg.setMessage("/track="+trackConfig.getTrack());
		send(msg);
		
		//圈数
		msg.setMessage("/laps="+trackConfig.getLaps());
		send(msg);
		
		//风速
		msg.setMessage("/wind="+trackConfig.getWind());
		send(msg);
		
		//命令允许车辆
		msg.setMessage("/cars="+trackConfig.getCars());
		send(msg);
		
		//CLEAR
		msg.setMessage("/clear");
		send(msg);

	}

	private void setLinsteners()
	{
		this.addListener(SplitTimeResponse.class, new SplitTimeResponseListener());
		this.addListener(LapTimeResponse.class, new LapTimeResponseListener());
		this.addListener(NewPlayerResponse.class, new NewPlayerResponseListener());
		this.addListener(PlayerLeavingResponse.class, new PlayerLeavingResponseListener());
		this.addListener(StateResponse.class, new StateResponseListener());
		this.addListener(NewConnectionResponse.class, new NewConnectionResponseListener());
		this.addListener(MessageResponse.class, new MessageResponseListener());
		this.addListener(ConnectionLeaveResponse.class, new ConnectionLeaveResponseListener());
		this.addListener(ServerClosedResponse.class, new ServerClosedResponseListener());
		this.addListener(FlagResponse.class, new FlagResponseListener());
		this.addListener(CarResetResponse.class, new CarResetResponseListener());
		this.addListener(PenaltyResponse.class, new PenaltyResponseListener());
		this.addListener(RaceStartResponse.class, new RaceStartResponseListener());
		this.addListener(ResultResponse.class, new ResultResponseListener());
		this.addListener(FinishedRaceResponse.class, new FinishedRaceResponseListener());
		this.addListener(TinyResponse.class, new TinyResponseListener());
		this.addListener(ButtonClickedResponse.class, new ButtonClickedResponseListener());
		this.addListener(SmallResponse.class, new SmallResponseListener());
	}
	
	

	public void notifyListeners(InSimResponse response) {
		// TODO Auto-generated method stub
		//log.debug(response);
		Class<? extends InSimResponse> key=response.getClass();
		ResponseListener listener=listenerMap.get(key);
		if(listener!=null)
		{
			try{
				listener.service(response, serverManager, this);
			}catch(Exception e)
			{
				log.debug("notifyListeners:",e);
			}
			
		}

	}
	

	private void addListener(Class<? extends InSimResponse> key,ResponseListener listener) {
		listenerMap.put(key, listener);
	}
	
	private void removeListener(Class<? extends InSimResponse> key) {
		listenerMap.remove(key);
	}
}
