package ctra.types;
import java.util.ResourceBundle;

import net.sf.jinsim.request.ButtonRequest;


public class WelcomeButton extends ButtonRequest{
	
	private static final ResourceBundle rsb = ResourceBundle.getBundle("buttons");
	private float textSize;
	public WelcomeButton()
	{
		super();
		this.setButtonStyle(BUTTON_STYLE_DARK);
		this.setHeight(Byte.valueOf(rsb.getString("INFO_BUTTON_HEIGHT")));
		this.setWidth(Byte.valueOf(rsb.getString("INFO_BUTTON_WIDTH")));
		this.setLeft(Byte.valueOf(rsb.getString("INFO_BUTTON_LEFT")));
		this.setTop(Byte.valueOf(rsb.getString("INFO_BUTTON_TOP")));
		this.textSize=Float.valueOf(rsb.getString("INFO_BUTTON_TEXTSIZE"));
		
		this.setClickId(ButtonClickId.WELCOME_BUTTON_ID);
		//this.setInst((byte)0);
		this.setRequestInfo((byte)170);
		this.setInst((byte)-128); //总是可见

	}
	
	public void setText(String text)
	{
		super.setText(text);
		int textLength=text.getBytes().length-1;
		if(textLength>TEXT_LENGTH) textLength=TEXT_LENGTH;
		
		int buttonWidth=(int)(((float)textLength/TEXT_LENGTH)*MAX_WIDTH)+1;
		buttonWidth*=textSize;
		int left=(MAX_WIDTH-buttonWidth)/2;
		this.setLeft((byte)left);
		
		
		this.setWidth((byte)buttonWidth);
	}

}
