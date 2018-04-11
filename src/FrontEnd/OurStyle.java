package FrontEnd;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public interface OurStyle {
	static final Color FOREGROUND = new Color(18, 165, 244);		// sky blue
	static final Color BACKGROUND = new Color(210, 227, 233);		// white-blue
	
	static final Color BUTTONTEXT = new Color(255,255,255);			// white
	static final Color LABEL = new Color(55, 55, 55);	 			// dark grey

	static final Font BIGFONT = new Font("Freight Sans", Font.BOLD, 20);
	static final Font SMALLFONT = new Font("Neueu Helvetica", Font.BOLD, 14);
	static final Font BUTTONFONT = new Font("Neue Helvetica", Font.BOLD, 12);
	
	static final Border BORDER = BorderFactory.createLineBorder(
							new Color(100, 100, 100), 2);
}
