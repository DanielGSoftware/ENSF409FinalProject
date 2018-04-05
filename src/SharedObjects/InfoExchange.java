package SharedObjects;

import java.io.Serializable;

public class InfoExchange implements Serializable {
	private String opcode;
	private String[] info;
	private static final long serialVersionUID = 4;

	public InfoExchange(String opcode)
	{
		this.opcode=opcode;
		info=null;
	}
	
	public InfoExchange(String[] info)
	{
		this.info=info;
		opcode=null;
	}
	
	public String getOpcode()
	{
		return opcode;
	}
	
	public void setInfo(String[] a)
	{
		info=a;
	}
	
	public String[] getInfo()
	{
		return info;
	}
	
}
