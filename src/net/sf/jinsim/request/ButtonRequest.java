package net.sf.jinsim.request;

import java.nio.ByteBuffer;



import net.sf.jinsim.PacketType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ButtonRequest extends InSimRequest {

	private static Log log = LogFactory.getLog(ButtonRequest.class);

	public static final int TEXT_LENGTH = 240;
	public static final int MAX_WIDTH = 200;
	public static byte BUTTON_STYLE_LIGHT_GREY = 0;
	public static byte BUTTON_STYLE_YELLOW = 1;
	public static byte BUTTON_STYLE_BLACK = 2;
	public static byte BUTTON_STYLE_WHITE = 3;
	public static byte BUTTON_STYLE_GREEN = 4;
	public static byte BUTTON_STYLE_RED = 5;
	public static byte BUTTON_STYLE_BLUE = 6;
	public static byte BUTTON_STYLE_GREY = 7;

	public static byte BUTTON_STYLE_CLICK = 8;
	public static byte BUTTON_STYLE_LIGHT = 16;
	public static byte BUTTON_STYLE_DARK = 32;
	public static byte BUTTON_STYLE_LEFT = 64;
	public static byte BUTTON_STYLE_RIGHT = -128;

	private byte connectionId;
	private byte clickId;
	private byte inst;
	private int buttonStyle;
	private byte typeIn;
	private byte left;
	private byte top;
	private byte width;
	private byte height;
	private String text;

	public ButtonRequest() {
		super(PacketType.BUTTON, TEXT_LENGTH + 12);
	}

	public void assemble(ByteBuffer buffer) {
		// set size to multiple of 4
		int textLength = (((int) text.getBytes().length / 4) + 1) * 4;
		if (textLength > TEXT_LENGTH) {
			textLength = TEXT_LENGTH;
		}
		size = 12 + textLength;
//		if (log.isDebugEnabled()) {
//			log.debug("Button buffer size: " + size);
//		}

		super.assemble(buffer);
		
		buffer.put(connectionId);
		buffer.put(clickId);
		buffer.put(inst);
		buffer.put((byte) buttonStyle);
		buffer.put(typeIn);
		buffer.put(left);
		buffer.put(top);
		buffer.put(width);
		buffer.put(height);

		assembleString(buffer, text, textLength);
	}

	public void setButtonStyle(int buttonStyle) {
		this.buttonStyle = buttonStyle;
	}

	public void setClickId(byte clickId) {
		this.clickId = clickId;
	}

	public void setConnectionId(byte connectionId) {
		this.connectionId = connectionId;
	}

	public void setHeight(byte height) {
		this.height = height;
	}

	public void setInst(byte inst) {
		this.inst = inst;
	}

	public void setLeft(byte left) {
		this.left = left;
	}

	public void setTop(byte top) {
		this.top = top;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public void setText(int text) {
		this.text = String.valueOf(text);
	}

	public void setTypeIn(byte typeIn) {
		this.typeIn = typeIn;
	}

	public void setWidth(byte width) {
		this.width = width;
	}
	
	public static byte getButtonStyle(String style) throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException
	{
		return (Byte)ButtonRequest.class.getField(style).get(null);
	}
	
	public String toString()
	{
		return super.toString()+",connectionId:"+connectionId
		+",clickId     :"+clickId     
		+",inst        :"+inst        
		+",buttonStyle :"+buttonStyle 
		+",typeIn      :"+typeIn      
		+",left        :"+left        
		+",top         :"+top         
		+",width       :"+width       
		+",height      :"+height      
		+",text        :"+text;
	}

	public byte getLeft()
	{
		return left;
	}

	public byte getTop()
	{
		return top;
	}

	public byte getWidth()
	{
		return width;
	}

	public byte getHeight()
	{
		return height;
	}

	public String getText()
	{
		return text;
	}

	public byte getClickId()
	{
		return clickId;
	}

}
