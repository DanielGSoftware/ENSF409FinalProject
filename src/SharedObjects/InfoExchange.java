package SharedObjects;

import java.io.Serializable;

/**
 * This class allows for information exchange between front end and back end
 * @author Huzaifa Amar and Daniel Guieb
 *
 */
public class InfoExchange implements Serializable {
	/**
	 * opcode to specify operation
	 */
	private String opcode;
	/**
	 * string array with info for exchanges
	 */
	private String[] info;
	/**
	 * serializable id of class
	 */
	private static final long serialVersionUID = 4;

	/**
	 * sets opcode
	 * @param opcode command code specifing operation
	 */
	public InfoExchange(String opcode)
	{
		this.opcode=opcode;
		info=null;
	}
	
	/**
	 * sets info 
	 * @param info array of strings
	 */
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
