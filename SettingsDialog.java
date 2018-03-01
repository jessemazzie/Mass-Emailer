import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import javax.swing.*;

public class SettingsDialog extends JFrame implements ActionListener {
	Container cp;
	MailService mailService;
	JTextField serverDomainBox;
	JTextField portNumberBox;
	JTextField senderUsername;
	JPasswordField senderPassword;
	JTextField sentFrom;
	JTextField sentDate;
	JTextField subject;
	JTextArea messageContents;
	Vector<String> recipients;
	
	
	SettingsDialog() {
		recipients = new Vector<String>();
		getRecipients();
		this.setLayout(new BorderLayout());
		JPanel inputPanel = new JPanel(new GridLayout(8, 2));
		JPanel buttonPanel = new JPanel(new FlowLayout());
		
		//Instantiate our textfields/passwordfields
		serverDomainBox = new JTextField(15);
		serverDomainBox.setText("smtp.gmx.com");
		portNumberBox = new JTextField(5);
		portNumberBox.setText("456");
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
		senderUsername.setText("testing1232@gmx.com");
		senderPassword = new JPasswordField(15);
		senderPassword.setText("Testing1!");
		sentFrom = new JTextField(15);
		sentDate = new JTextField(15);
		subject = new JTextField(15);
		messageContents = new JTextArea(2, 20);
		
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
		inputPanel.add(new JLabel("Message body: "));
		inputPanel.add(messageContents);
		
		buttonPanel.add(newButton("Submit", "SUBMIT"));
		//configure the dialog
		cp = getContentPane();
		setTitle("Mass Emailer");
		setVisible(true);
		setSize(500, 400);
		
		mailService = new MailService(serverDomainBox.getText(), portNumberBox.getText(), String.valueOf(senderPassword.getPassword()), senderUsername.getText());

		cp.add(inputPanel, BorderLayout.NORTH);
		cp.add(buttonPanel, BorderLayout.CENTER);
		this.pack();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	JButton newButton(String label, String actionCommand) {
		JButton tempButton = new JButton(label);
		tempButton.setActionCommand(actionCommand);
		tempButton.addActionListener(this);
		
		return tempButton;
	}
	
	void getRecipients() {
		String line; 
		try {
			FileInputStream fis = new FileInputStream("emails.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			
			while((line = reader.readLine()) != null) 
				recipients.add(line);
			
			System.out.println(recipients);
		} catch (IOException e) {
			//SHOW ERROR MESSAGE HERE.
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand().equals("SUBMIT")) {
			mailService.sendMail(senderUsername.getText(), recipients, messageContents.getText());
		}
	}
}
