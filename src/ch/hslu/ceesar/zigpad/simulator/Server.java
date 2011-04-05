package ch.hslu.ceesar.zigpad.simulator;

import java.net.*;
import java.io.*;

public class Server
{
	DatagramSocket socket = new DatagramSocket(1234);

	Server() throws IOException 
	{ 
		System.out.println("server started..");
		while (true)
		{
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
			
			byte[] answBuf = "ACK".getBytes();
			DatagramPacket answPacket = new DatagramPacket(answBuf, answBuf.length, address.getAddress(), port);
			socket.send(answPacket);
			System.out.println("Antwort gesendet");



		}
	}

	public static void main (String[] args) 
	{
		try
		{
			Server server = new Server();
		} catch (IOException e) { 
			System.out.print(e);
		}
	}
}
