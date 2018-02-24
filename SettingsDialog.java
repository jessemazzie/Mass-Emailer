import java.awt.*;
import javax.swing.*;

public class SettingsDialog extends JDialog {
	Container cp;
	JTextField serverDomainBox;
	JTextField portNumberBox;
	JTextField senderUsername;
	JPasswordField senderPassword;
	JTextField sentFrom;
	JTextField sentDate;
	JTextField subject;
	
	
	SettingsDialog() {
		JPanel inputPanel = new JPanel(new GridLayout(7, 2));
		
		//Instantiate our textfields/passwordfields
		serverDomainBox = new JTextField(15);
		portNumberBox = new JTextField(5);
		portNumberBox.setInputVerifier(new InputVerifier() {
			@Override
			public boolean verify(JComponent component) {
				JTextField textField;
				int intValue;
				
				textField = (JTextField) component;
				intValue = Integer.parseInt(textField.getText());
				
				if(intValue < 0) {
					//JOptionPane.showMessageDialog(null,"Port number must be above 0.", JOptionPane.ERROR_MESSAGE);
					return false;
				}
				
				return true;
			}
		});
		senderUsername = new JTextField(15);
		senderPassword = new JPasswordField(15);
		sentFrom = new JTextField(15);
		sentDate = new JTextField(15);
		subject = new JTextField(15);
		
		//Add them to the JPanel
		inputPanel.add(new JLabel("Server domain: "));
		inputPanel.add(serverDomainBox);
		inputPanel.add(new JLabel("Port number: "));
		inputPanel.add(portNumberBox);
		inputPanel.add(new JLabel("Sender username: "));
		inputPanel.add(senderUsername);
		inputPanel.add(new JLabel("Sender password: "));
		inputPanel.add(senderPassword);
		inputPanel.add(new JLabel("Sent from: "));
		inputPanel.add(sentFrom);
		inputPanel.add(new JLabel("Sent date: "));
		inputPanel.add(sentDate);
		inputPanel.add(new JLabel("Subject: "));
		inputPanel.add(subject);
			
		//configure the dialog
		cp = getContentPane();
		setTitle("Mass Emailer");
		setVisible(true);
		setSize(500, 500);
		
		cp.add(inputPanel);
	}
}
