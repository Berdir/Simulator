package ch.hslu.ceesar.zigpad.simulator;

import java.net.*;
import java.io.*;

public class UDPServer extends Server
{
	private boolean serverRunning = true;
	private DatagramSocket socket;
		

	UDPServer() 
	{ 

	}
	
	public void startServer()
	{
		new Thread(new Runnable() {
			
			@Override
			public void run(){
				System.out.println("UDP server started..");
				while (serverRunning)
				{
					try {
						socket = new DatagramSocket(port);
					
						byte[] buf = new byte[256];
						DatagramPacket packet = new DatagramPacket(buf, buf.length);
						socket.receive(packet);
						System.out.println("empfangen: ");
						String s = new String(buf);
						System.out.println(s.trim());
						
						InetSocketAddress address = (InetSocketAddress)packet.getSocketAddress();
						System.out.println("von: "+address);
					
						//antwort
						if (doAnswer)
						{
							int port = address.getPort();
							byte[] answBuf = answer.getBytes();
							DatagramPacket answPacket = new DatagramPacket(answBuf, answBuf.length, address.getAddress(), port);
							socket.send(answPacket);
							System.out.println("Antwort gesendet");
						}
						socket.close();
	
					} catch (IOException e) {System.out.println(e.getMessage());}			
				}
				System.out.println("Server angehalten");
				
			}
		}).start();

	}
	
	public void stopServer()
	{
		serverRunning = false;
		socket.close();
	}

	public static void main (String[] args) 
	{
		new JConsole("Server");
		UDPServer server = new UDPServer();
		server.answer = "ACK";
		server.port = 1234;
		server.doAnswer = true;
		server.startServer();
		//try {Thread.sleep(1000);}catch (Exception e) {}
		//server.stopServer();
		
	}   
	

}
