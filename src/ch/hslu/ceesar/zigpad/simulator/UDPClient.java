package ch.hslu.ceesar.zigpad.simulator;

import java.net.*;
import java.io.*;

public class UDPClient
{
	UDPClient()
	{

	}
	
	public void send(String message, String ip, int destPort, int srcPort)
	{
		System.out.println("client startet..");
		DatagramSocket socket = null;
		
		try
		{
			InetAddress adress = Inet4Address.getByName(ip);
			socket = new DatagramSocket(srcPort);
		
			DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), adress, destPort);
			socket.send(packet);
		
			System.out.println("gesendet, warte auf antwort...");
		
			byte[] buf = new byte[256];
			DatagramPacket responsePacket = new DatagramPacket(buf, buf.length);
			socket.setSoTimeout(2000);
			socket.receive(responsePacket);
		
			String s = new String(buf);
			System.out.println("empfangen: ");
			System.out.println(s.trim());
		
			socket.close();
		
		}catch (IOException e) {
			if (socket !=null )socket.close();
			System.out.println(e.getMessage());
		}

		
	}

	public static void main (String[] args) 
	{
			new JConsole("Client");
			UDPClient client = new UDPClient();
			client.send("MARKUS", "127.0.0.1", 1234, 4321);
			
	}
}
