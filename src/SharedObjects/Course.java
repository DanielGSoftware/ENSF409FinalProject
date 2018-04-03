package SharedObjects;

public class Course extends InfoExchange {
	private int proffid;
	private String name;
	private int active;
	private static final long serialVersionUID = 1L;

	public Course(int proffid, String name, int active, String opcode)
	{
		super(opcode);
		this.proffid=proffid;
		this.name=name;
		this.active=active;
	}
	
	public String[] browseCourses()
	{
		
	}
}
