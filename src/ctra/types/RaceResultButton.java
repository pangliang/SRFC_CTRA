package ctra.types;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

import net.sf.jinsim.Client;
import net.sf.jinsim.request.ButtonRequest;

import org.apache.log4j.Logger;

public class RaceResultButton
{
	Logger log = Logger.getLogger(this.getClass());
	private static final ResourceBundle rsb = ResourceBundle.getBundle("raceResultButtons");
	HashMap<String, Player> playerList=new LinkedHashMap<String, Player>();
	ArrayList<ButtonRequest> buttons=new ArrayList<ButtonRequest>();
	
	private int startId;
	private byte top;
	private byte left;
	private int leftBlank;
	private int topBlank;
	private byte buttonHeight;
	
	public RaceResultButton(ServerManager serverManager)
	{
		
		
		for(Byte pos:serverManager.getFinishPlayerList().keySet())
		{
			this.playerList.put(pos+"", serverManager.getFinishPlayerList().get(pos));
			
		}
		
		for(Player player:serverManager.getDNFPlayerList().values())
		{
			this.playerList.put("DNF"+( this.playerList.size()+1), player);
		}

		this.top=Byte.valueOf(rsb.getString("TOP"));
		this.left=Byte.valueOf(rsb.getString("LEFT"));
		this.leftBlank=Byte.valueOf(rsb.getString("LEFT_BLANK"));
		this.topBlank=Byte.valueOf(rsb.getString("TOP_BLANK"));
		this.buttonHeight=Byte.valueOf(rsb.getString("BUTTON_HEIGHT"));
		this.startId=Integer.valueOf(rsb.getString("START_ID"));
		
		createTitileButton();
		createValueButton();
	}
	
	private void createValueButton()
	{
		byte top=(byte) (this.top+buttonHeight*1.5+topBlank);
		
		
		for(String key:playerList.keySet())
		{
			Player player=playerList.get(key);
			
			byte left=this.left;
			
			ButtonRequest button=new ButtonRequest();
			button.setButtonStyle(ButtonRequest.BUTTON_STYLE_LIGHT);
			button.setHeight(buttonHeight);
			button.setTop(top);
			button.setLeft(left);
			button.setWidth(Byte.valueOf(rsb.getString("POSITION_BUTTON_WIDTH")));
			button.setText(key);
			button.setClickId((byte) (startId+buttons.size()));
			button.setRequestInfo((byte) (startId+buttons.size()));
			buttons.add(button);
			
			left+=button.getWidth()+this.leftBlank;
			
			button=new ButtonRequest();
			button.setButtonStyle(ButtonRequest.BUTTON_STYLE_LIGHT);
			button.setHeight(buttonHeight);
			button.setTop(top);
			button.setLeft(left);
			button.setWidth(Byte.valueOf(rsb.getString("NAME_BUTTON_WIDTH")));
			button.setText(player.getLfsName());
			button.setClickId((byte) (startId+buttons.size()));
			button.setRequestInfo((byte) (startId+buttons.size()));
			buttons.add(button);
			
			left+=button.getWidth()+this.leftBlank;
			
			button=new ButtonRequest();
			button.setButtonStyle(ButtonRequest.BUTTON_STYLE_LIGHT);
			button.setHeight(buttonHeight);
			button.setTop(top);
			button.setLeft(left);
			button.setWidth(Byte.valueOf(rsb.getString("RANK_BUTTON_WIDTH")));
			button.setText(player.getRank());
			button.setClickId((byte) (startId+buttons.size()));
			button.setRequestInfo((byte) (startId+buttons.size()));
			buttons.add(button);
			
			left+=button.getWidth()+this.leftBlank;
			
			button=new ButtonRequest();
			button.setButtonStyle(ButtonRequest.BUTTON_STYLE_LIGHT);
			button.setHeight(buttonHeight);
			button.setTop(top);
			button.setLeft(left);
			button.setWidth(Byte.valueOf(rsb.getString("INTEGRAL_BUTTON_WIDTH")));
			button.setText(player.getIntegral());
			button.setClickId((byte) (startId+buttons.size()));
			button.setRequestInfo((byte) (startId+buttons.size()));
			buttons.add(button);
			
			left+=button.getWidth()+this.leftBlank;
			
			button=new ButtonRequest();
			button.setButtonStyle(ButtonRequest.BUTTON_STYLE_LIGHT);
			button.setHeight(buttonHeight);
			button.setTop(top);
			button.setLeft(left);
			button.setWidth(Byte.valueOf(rsb.getString("MISTAKE_TIMES_BUTTON_WIDTH")));
			button.setText(player.getMistakesTimes());
			button.setClickId((byte) (startId+buttons.size()));
			button.setRequestInfo((byte) (startId+buttons.size()));
			buttons.add(button);
			left+=button.getWidth()+this.leftBlank;
			
			button=new ButtonRequest();
			button.setButtonStyle(ButtonRequest.BUTTON_STYLE_LIGHT);
			button.setHeight(buttonHeight);
			button.setTop(top);
			button.setLeft(left);
			button.setWidth(Byte.valueOf(rsb.getString("DEC_INTEGRAL_FOR_MISTAKE_BUTTON_WIDTH")));
			button.setText(player.getDecIntegralForMistake());
			button.setClickId((byte) (startId+buttons.size()));
			button.setRequestInfo((byte) (startId+buttons.size()));
			buttons.add(button);
			left+=button.getWidth()+this.leftBlank;
			
			button=new ButtonRequest();
			button.setButtonStyle(ButtonRequest.BUTTON_STYLE_LIGHT);
			button.setHeight(buttonHeight);
			button.setTop(top);
			button.setLeft(left);
			button.setWidth(Byte.valueOf(rsb.getString("FOR_JION_BUTTON_WIDTH")));
			button.setText(player.getPayForJion());
			button.setClickId((byte) (startId+buttons.size()));
			button.setRequestInfo((byte) (startId+buttons.size()));
			buttons.add(button);
			left+=button.getWidth()+this.leftBlank;
			
			
			button=new ButtonRequest();
			button.setButtonStyle(ButtonRequest.BUTTON_STYLE_LIGHT);
			button.setHeight(buttonHeight);
			button.setTop(top);
			button.setLeft(left);
			button.setWidth(Byte.valueOf(rsb.getString("WIN_INTEGRAL_BUTTON_WIDTH")));
			button.setText(player.getWinIntegral());
			button.setClickId((byte) (startId+buttons.size()));
			button.setRequestInfo((byte) (startId+buttons.size()));
			buttons.add(button);
			left+=button.getWidth()+this.leftBlank;
			
			
			button=new ButtonRequest();
			button.setButtonStyle(ButtonRequest.BUTTON_STYLE_LIGHT);
			button.setHeight(buttonHeight);
			button.setTop(top);
			button.setLeft(left);
			button.setWidth(Byte.valueOf(rsb.getString("INCOME_BUTTON_WIDTH")));
			button.setText(player.getIncome());
			button.setClickId((byte) (startId+buttons.size()));
			button.setRequestInfo((byte) (startId+buttons.size()));
			buttons.add(button);
			left+=button.getWidth()+this.leftBlank;
			
			top+=buttonHeight+topBlank;
		}
		
		
	}
	
	
	private void createTitileButton()
	{
		byte top=this.top;
		byte left=this.left;
		byte buttonHeight=(byte) (this.buttonHeight*1.5);
		
		ButtonRequest button=new ButtonRequest();
		button.setButtonStyle(ButtonRequest.BUTTON_STYLE_LIGHT);
		button.setHeight(buttonHeight);
		button.setTop(top);
		button.setLeft(left);
		button.setWidth(Byte.valueOf(rsb.getString("POSITION_BUTTON_WIDTH")));
		button.setText(rsb.getString("POSITION_TITLE"));
		button.setClickId((byte) (startId+buttons.size()));
		button.setRequestInfo((byte) (startId+buttons.size()));
		buttons.add(button);
		
		left+=button.getWidth()+this.leftBlank;
		
		
		button=new ButtonRequest();
		button.setButtonStyle(ButtonRequest.BUTTON_STYLE_LIGHT);
		button.setHeight(buttonHeight);
		button.setTop(top);
		button.setLeft(left);
		button.setWidth(Byte.valueOf(rsb.getString("NAME_BUTTON_WIDTH")));
		button.setText(rsb.getString("NAME_TITLE"));
		button.setClickId((byte) (startId+buttons.size()));
		button.setRequestInfo((byte) (startId+buttons.size()));
		buttons.add(button);
		
		left+=button.getWidth()+this.leftBlank;
		
		button=new ButtonRequest();
		button.setButtonStyle(ButtonRequest.BUTTON_STYLE_LIGHT);
		button.setHeight(buttonHeight);
		button.setTop(top);
		button.setLeft(left);
		button.setWidth(Byte.valueOf(rsb.getString("RANK_BUTTON_WIDTH")));
		button.setText(rsb.getString("RANK_TITLE"));
		button.setClickId((byte) (startId+buttons.size()));
		button.setRequestInfo((byte) (startId+buttons.size()));
		buttons.add(button);
		
		left+=button.getWidth()+this.leftBlank;
		
		button=new ButtonRequest();
		button.setButtonStyle(ButtonRequest.BUTTON_STYLE_LIGHT);
		button.setHeight(buttonHeight);
		button.setTop(top);
		button.setLeft(left);
		button.setWidth(Byte.valueOf(rsb.getString("INTEGRAL_BUTTON_WIDTH")));
		button.setText(rsb.getString("INTEGRAL_TITLE"));
		button.setClickId((byte) (startId+buttons.size()));
		button.setRequestInfo((byte) (startId+buttons.size()));
		buttons.add(button);
		
		left+=button.getWidth()+this.leftBlank;
		
		button=new ButtonRequest();
		button.setButtonStyle(ButtonRequest.BUTTON_STYLE_LIGHT);
		button.setHeight(buttonHeight);
		button.setTop(top);
		button.setLeft(left);
		button.setWidth(Byte.valueOf(rsb.getString("MISTAKE_TIMES_BUTTON_WIDTH")));
		button.setText(rsb.getString("MISTAKE_TIMES_TITLE"));
		button.setClickId((byte) (startId+buttons.size()));
		button.setRequestInfo((byte) (startId+buttons.size()));
		buttons.add(button);
		left+=button.getWidth()+this.leftBlank;
		
		button=new ButtonRequest();
		button.setButtonStyle(ButtonRequest.BUTTON_STYLE_LIGHT);
		button.setHeight(buttonHeight);
		button.setTop(top);
		button.setLeft(left);
		button.setWidth(Byte.valueOf(rsb.getString("DEC_INTEGRAL_FOR_MISTAKE_BUTTON_WIDTH")));
		button.setText(rsb.getString("DEC_INTEGRAL_FOR_MISTAKE_TITLE"));
		button.setClickId((byte) (startId+buttons.size()));
		button.setRequestInfo((byte) (startId+buttons.size()));
		buttons.add(button);
		left+=button.getWidth()+this.leftBlank;
		
		button=new ButtonRequest();
		button.setButtonStyle(ButtonRequest.BUTTON_STYLE_LIGHT);
		button.setHeight(buttonHeight);
		button.setTop(top);
		button.setLeft(left);
		button.setWidth(Byte.valueOf(rsb.getString("FOR_JION_BUTTON_WIDTH")));
		button.setText(rsb.getString("FOR_JION_TITLE"));
		button.setClickId((byte) (startId+buttons.size()));
		button.setRequestInfo((byte) (startId+buttons.size()));
		buttons.add(button);
		left+=button.getWidth()+this.leftBlank;
		
		button=new ButtonRequest();
		button.setButtonStyle(ButtonRequest.BUTTON_STYLE_LIGHT);
		button.setHeight(buttonHeight);
		button.setTop(top);
		button.setLeft(left);
		button.setWidth(Byte.valueOf(rsb.getString("WIN_INTEGRAL_BUTTON_WIDTH")));
		button.setText(rsb.getString("WIN_INTEGRAL_TITLE"));
		button.setClickId((byte) (startId+buttons.size()));
		button.setRequestInfo((byte) (startId+buttons.size()));
		buttons.add(button);
		left+=button.getWidth()+this.leftBlank;
		
		button=new ButtonRequest();
		button.setButtonStyle(ButtonRequest.BUTTON_STYLE_LIGHT);
		button.setHeight(buttonHeight);
		button.setTop(top);
		button.setLeft(left);
		button.setWidth(Byte.valueOf(rsb.getString("INCOME_BUTTON_WIDTH")));
		button.setText(rsb.getString("INCOME_TITLE"));
		button.setClickId((byte) (startId+buttons.size()));
		button.setRequestInfo((byte) (startId+buttons.size()));
		buttons.add(button);
		left+=button.getWidth()+this.leftBlank;
		
		
		button=new ButtonRequest();
		button.setButtonStyle(ButtonRequest.BUTTON_STYLE_LIGHT|ButtonRequest.BUTTON_STYLE_CLICK);
		button.setHeight(buttonHeight);
		button.setTop(top);
		button.setLeft((byte) (left));
		button.setWidth((byte) 15);
		button.setText(rsb.getString("CLOSE_BUTTON_TEXT"));
		button.setClickId(ButtonClickId.CLOSE_RESULT_BUTTON_ID);
		button.setRequestInfo(ButtonClickId.CLOSE_RESULT_BUTTON_ID);
		buttons.add(button);
		
	}
	
	public void show(Client client,HashMap<Byte, Player> connectedList) throws IOException
	{
		for(Player player:connectedList.values())
		{
			for(ButtonRequest b:buttons)
			{
				
				b.setConnectionId(player.getConnectionId());
				client.send(b);
				//log.debug(b);
			}
		}
		
	}
	public ArrayList<ButtonRequest> getButtons()
	{
		return buttons;
	}
}
