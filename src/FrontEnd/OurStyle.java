package FrontEnd;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * An interface for constants that the GUIs use as a common style
 * @authors Daniel Guieb, Huzaifa Amar
 * @version 1.0
 * @since April 12, 2018
 *
 */
public interface OurStyle {
	
	/**
	 * The foreground for components and background for buttons
	 */
	static final Color FOREGROUND = new Color(18, 165, 244);		// sky blue
	
	/**
	 * The background for components
	 */
	static final Color BACKGROUND = new Color(210, 227, 233);		// white-blue
	
	/**
	 * The font color for buttons
	 */
	static final Color BUTTONTEXT = new Color(255,255,255);			// white
	
	/**
	 * The font color for labels
	 */
	static final Color LABEL = new Color(55, 55, 55);	 			// dark grey

	/**
	 * The font style for large fonts
	 */
	static final Font BIGFONT = new Font("Freight Sans", Font.BOLD, 20);
	
	/**
	 * The font style for small fonts
	 */
	static final Font SMALLFONT = new Font("Neueu Helvetica", Font.BOLD, 14);
	
	/**
	 * The font style for buttons
	 */
	static final Font BUTTONFONT = new Font("Neue Helvetica", Font.BOLD, 12);
	
	/**
	 * A line border for components which need borders
	 */
	static final Border BORDER = BorderFactory.createLineBorder(
							new Color(100, 100, 100), 2);
}
