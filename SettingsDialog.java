import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SettingsDialog extends JDialog implements ActionListener {
	Container cp;
	JTextField serverDomainBox;
	JTextField portNumberBox;
	JTextField senderUsername;
	JPasswordField senderPassword;
	JTextField sentFrom;
	JTextField sentDate;
	JTextField subject;
	
	
	SettingsDialog() {
		this.setLayout(new BorderLayout());
		JPanel inputPanel = new JPanel(new GridLayout(7, 2));
		JPanel buttonPanel = new JPanel(new FlowLayout());
		
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
				
				if(intValue < 0 || intValue > 65535) {
					JOptionPane.showMessageDialog(null, null, "Port number must be between 0 and 65535.", JOptionPane.ERROR_MESSAGE);
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
		
		buttonPanel.add(new JButton("Submit"));
			
		//configure the dialog
		cp = getContentPane();
		setTitle("Mass Emailer");
		setVisible(true);
		setSize(500, 250);
		
		cp.add(inputPanel, BorderLayout.NORTH);
		cp.add(buttonPanel, BorderLayout.CENTER);
	}
	
	JButton newButton(String label, String actionCommand) {
		JButton tempButton = new JButton(label);
		tempButton.setActionCommand(actionCommand);
		tempButton.addActionListener(this);
		
		return tempButton;
	}


	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		
	}
}
