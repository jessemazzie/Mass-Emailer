import java.awt.*;
import javax.swing.*;

public class SettingsDialog extends JDialog {
	Container cp;
	
	SettingsDialog() {
		
		
		//configure the dialog
		cp = getContentPane();
		setTitle("Mass Emailer");
		setVisible(true);
		setSize(500, 500);
	}
}
