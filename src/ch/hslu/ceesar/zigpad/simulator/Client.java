package ch.hslu.ceesar.zigpad.simulator;

import java.net.*;
import java.io.*;

public class Client
{
	Client()
	{

	}
	
	public void send(String message, String ip, int destPort, int srcPort) throws IOException
	{
		InetAddress adress = Inet4Address.getByName(ip);
		System.out.println("client startet..");
		
		DatagramSocket socket = new DatagramSocket(srcPort);
		DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), adress, destPort);
		socket.send(packet);
		
		System.out.println("gesendet, warte auf antwort...");
		
		byte[] buf = new byte[256];
		DatagramPacket responsePacket = new DatagramPacket(buf, buf.length);
		socket.setSoTimeout(2000);
		socket.receive(responsePacket);
		
		socket.close();
		
		String s = new String(buf);
		System.out.println("empfangen: ");
		System.out.println(s.trim());
		
	}

	public static void main (String[] args) 
	{
		try {
			new JConsole("Client");
			Client client = new Client();
			client.send("MARKUS", "127.0.0.1", 1234, 4321);
		} catch (IOException e) {
			System.out.print(e);
		}
	}
}
