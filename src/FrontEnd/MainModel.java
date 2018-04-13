package FrontEnd;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Abstract model class which has an object reader and writer
 * @authors Daniel Guieb, Huzaifa Amar
 * @version 1.0
 * @since April 13, 2018
 *
 */
abstract class MainModel  {
	/**
	 * The object writer
	 */
	protected ObjectOutputStream sendObject;
	
	/**
	 * The object reader
	 */
	protected ObjectInputStream readObject;
	
	/**
	 * A constructor which sets the object reader and writer
	 * @param readObject
	 * @param sendObject
	 */
	public MainModel(ObjectInputStream readObject, ObjectOutputStream sendObject) {
		this.readObject= readObject;
		this.sendObject=sendObject;
	}
	
}
