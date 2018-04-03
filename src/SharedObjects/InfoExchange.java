package SharedObjects;

import java.io.Serializable;

public abstract class InfoExchange implements Serializable {
	protected String opcode;

	public InfoExchange(String opcode)
	{
		this.opcode=opcode;
	}
	
}
