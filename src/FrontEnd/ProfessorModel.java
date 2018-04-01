package FrontEnd;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ProfessorModel extends MainModel {
	
	public ProfessorModel(ObjectOutputStream sendObject, ObjectInputStream readObject)
	{
		super(readObject, sendObject);
	}
}
