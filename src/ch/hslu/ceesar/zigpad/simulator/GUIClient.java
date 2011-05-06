package ch.hslu.ceesar.zigpad.simulator;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Checkbox;
import javax.swing.JRadioButton;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class GUIClient {

	private JFrame frame;
	private JRadioButton _TCP;
	private JRadioButton _UDP;
	private Checkbox _randomPort;
	private JTextField _msg;
	private JTextField _ip;
	private JTextField _destPort;
	private JTextField _srcPort;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIClient window = new GUIClient();
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
	public GUIClient() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		new JConsole("Client Console");
		
		frame = new JFrame("Client Control");
		
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setBounds(100, 100, 214, 215);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JButton btnSend = new JButton("Send");
		btnSend.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSend.setBackground(Color.LIGHT_GRAY);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					int srcPort = Integer.parseInt(_srcPort.getText());
					if (!_srcPort.isEnabled()) srcPort = 0;
					int destPort = Integer.parseInt(_destPort.getText());

					if (_UDP.isSelected())
					{
						UDPClient client = new UDPClient();
						client.send(_msg.getText(), _ip.getText(), destPort, srcPort);
					}else
					{
						TCPClient client = new TCPClient();
						client.send(_msg.getText(), _ip.getText(), destPort, srcPort);
					}
			}
		});
		frame.getContentPane().add(btnSend, BorderLayout.CENTER);
		
		Panel panel = new Panel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(6, 2, 0, 0));
		
		_TCP = new JRadioButton("TCP");
		_TCP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				_UDP.setSelected(!_TCP.isSelected());
				_randomPort.setState(_TCP.isSelected());
				_srcPort.setEnabled(!_TCP.isSelected());
			}
		});
		panel.add(_TCP);
		
		_UDP = new JRadioButton("UDP");
		_UDP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				_TCP.setSelected(!_UDP.isSelected());
				_randomPort.setState(!_UDP.isSelected());
				_srcPort.setEnabled(_UDP.isSelected());
			}
		});
		
		_UDP.setSelected(true);
		panel.add(_UDP);
		
		JLabel lblIpaddress = new JLabel("IPAddress");
		panel.add(lblIpaddress);
		
		_ip = new JTextField();
		_ip.setText("127.0.0.1");
		panel.add(_ip);
		_ip.setColumns(10);
		
		JLabel lblDestPort = new JLabel("Dest Port");
		panel.add(lblDestPort);
		
		_destPort = new JTextField();
		_destPort.setText("1234");
		panel.add(_destPort);
		_destPort.setColumns(10);
		
		JLabel lblMessage = new JLabel("message");
		panel.add(lblMessage);
		
		_msg = new JTextField();
		_msg.setText("MARKUS");
		panel.add(_msg);
		_msg.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Source Port");
		panel.add(lblNewLabel);
		
		_srcPort = new JTextField();
		_srcPort.setText("4321");
		panel.add(_srcPort);
		_srcPort.setColumns(10);
		
		JLabel lblRandomPort = new JLabel("");
		panel.add(lblRandomPort);
		
		_randomPort = new Checkbox("Random Port");
		_randomPort.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				_srcPort.setEnabled(!_randomPort.getState());
			}
		});
		panel.add(_randomPort);
	}

}
