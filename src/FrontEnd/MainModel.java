package FrontEnd;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

abstract class MainModel  {
	protected ObjectOutputStream sendObject;
	protected ObjectInputStream readObject;
}
