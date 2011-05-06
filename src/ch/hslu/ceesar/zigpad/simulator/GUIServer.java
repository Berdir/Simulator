package ch.hslu.ceesar.zigpad.simulator;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Checkbox;

public class GUIServer {

	private JFrame frame;
	private JTextField txtPort;
	private JTextField txtAnswer;
	private JRadioButton _TCP;
	private JRadioButton _UDP;
	private JButton btnStartServer;
	private JButton btnStopServer;
	private Server server;
	private Checkbox _activeAnswer;

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
		new JConsole("Server Console");
		
		frame = new JFrame("Server Control");
		frame.setBounds(100, 100, 225, 146);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(3, 2, 0, 0));
		
		JLabel lblPort = new JLabel("Port");
		panel.add(lblPort);
		
		txtPort = new JTextField();
		txtPort.setText("1234");
		panel.add(txtPort);
		txtPort.setColumns(10);
		
		_activeAnswer = new Checkbox("Reply with");
		_activeAnswer.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				txtAnswer.setEnabled(_activeAnswer.getState());
			}
		});
		_activeAnswer.setState(true);
		panel.add(_activeAnswer);
		
		txtAnswer = new JTextField();
		txtAnswer.setText("ACK");
		panel.add(txtAnswer);
		txtAnswer.setColumns(10);
		
		_TCP = new JRadioButton("TCP");
		_TCP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			  _UDP.setSelected(!_TCP.isSelected());
			}			   
		});
		panel.add(_TCP);
		
		_UDP = new JRadioButton("UDP");
		_UDP.setSelected(true);
		_UDP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			   _TCP.setSelected(!_UDP.isSelected());
			}
		});
		panel.add(_UDP);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(1, 2, 0, 0));
		
		btnStartServer = new JButton("Start Server");
		panel_1.add(btnStartServer);
		btnStartServer.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				deactivateFields(true);
				
				if (_UDP.isSelected())
					server = new UDPServer();					
				else
					server = new TCPServer();
				
				server.port = Integer.parseInt(txtPort.getText());
				server.answer = txtAnswer.getText();
				server.doAnswer = _activeAnswer.getState();
				server.startServer();					

			}
		});
		btnStartServer.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		btnStopServer = new JButton("Stop Server");
		btnStopServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deactivateFields(false);

				server.stopServer();
				
			}
		});
		btnStopServer.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(btnStopServer);
		
		deactivateFields(false);
	}
	
	private void deactivateFields(boolean b)
	{
		btnStartServer.setEnabled(!b);
		btnStopServer.setEnabled(b);
		txtPort.setEnabled(!b);
		txtAnswer.setEnabled(!b && _activeAnswer.getState());
		_activeAnswer.setEnabled(!b);
		_TCP.setEnabled(!b);
		_UDP.setEnabled(!b);
	}

}
