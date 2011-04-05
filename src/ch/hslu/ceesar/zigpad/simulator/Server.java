package ch.hslu.ceesar.zigpad.simulator;

import java.net.*;
import java.io.*;

public class Server implements Runnable
{
	public String answer = "ACK";
	public int port = 1234;
	

	Server() 
	{ 

	}
	
	public void startServer() throws IOException
	{
		System.out.println("server started..");
		while (true)
		{
			DatagramSocket socket = new DatagramSocket(port);
			byte[] buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			socket.receive(packet);
			System.out.println("empfangen: ");
			String s = new String(buf);
			System.out.println(s.trim());
			
			//antwort
			InetSocketAddress address = (InetSocketAddress)packet.getSocketAddress();
			int port = address.getPort();
			System.out.println("von: "+address);
			
			byte[] answBuf = answer.getBytes();
			DatagramPacket answPacket = new DatagramPacket(answBuf, answBuf.length, address.getAddress(), port);
			socket.send(answPacket);
			System.out.println("Antwort gesendet");
			socket.close();
		}		
	}

	public static void main (String[] args) 
	{
		new JConsole("Server");
		Server server = new Server();
		try {
			server.startServer();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void run(){
		try {
			startServer();
		} catch (IOException e) {
			System.out.print(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}

	}
}
