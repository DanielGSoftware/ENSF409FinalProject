package FrontEnd;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public interface OurStyle {
//	static final Color BACKGROUND = new Color(255, 209, 175);		//my peach
	static final Color FONT = new Color(80, 86, 87);				// dark grey
	
//	static final Color FOREGROUND = new Color(209, 242, 165);		// melon green
//	static final Color BACKGROUND = new Color(239, 250, 180);		// honeydew
//	static final Color FONT = new Color(255,255,255);
	
//	static final Color FOREGROUND = new Color(59, 89, 182);			//fb
	
//	static final Color FOREGROUND = new Color(255, 196, 140);		// orange cream
	
	static final Color BACKGROUND = new Color(244, 234, 213);		// sand
	static final Color FOREGROUND = new Color(214, 129, 137);		// red bean

	static final Font BIGFONT = new Font("Freight Sans", Font.BOLD, 20);
	static final Font SMALLFONT = new Font("Neue Helvetica", Font.BOLD, 12);
	
	static final Border BLACKBORDER = BorderFactory.createLineBorder(
							Color.black, 2);
}
