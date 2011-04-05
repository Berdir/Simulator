package ch.hslu.ceesar.zigpad.simulator;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;

public class GUIServer {

	private JFrame frame;
	private Server server;
	private JTextField txtPort;
	private JTextField txtAnswer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIServer window = new GUIServer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUIServer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		new JConsole("Server");
		server = new Server();
		frame = new JFrame();
		frame.setBounds(100, 100, 155, 136);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JButton btnStartServer = new JButton("Start Server");
		btnStartServer.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				    server.port = Integer.parseInt(txtPort.getText());
				    server.answer = txtAnswer.getText();
					new Thread(server).start();
			}
		});
		btnStartServer.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(btnStartServer, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(2, 2, 0, 0));
		
		JLabel lblPort = new JLabel("Port");
		panel.add(lblPort);
		
		txtPort = new JTextField();
		txtPort.setText(""+server.port);
		panel.add(txtPort);
		txtPort.setColumns(10);
		
		JLabel lblAnswerstring = new JLabel("AnswerString");
		panel.add(lblAnswerstring);
		
		txtAnswer = new JTextField();
		txtAnswer.setText(""+server.answer);
		panel.add(txtAnswer);
		txtAnswer.setColumns(10);
	}

}
