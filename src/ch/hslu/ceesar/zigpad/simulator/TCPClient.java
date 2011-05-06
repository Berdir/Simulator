package ch.hslu.ceesar.zigpad.simulator;
import java.net.*;
import java.io.*;

public class TCPClient
{
	TCPClient() 
	{

	}
	
	public void send(String message, String ip, int destPort, int srcPort)
	{
		System.out.println("client startet..");		
		
		Socket server = null;
		BufferedInputStream input = null; 
		BufferedOutputStream output = null;
		
		try {
			InetAddress adress = Inet4Address.getByName(ip);
			InetAddress localAddr = InetAddress.getLocalHost();
			if (srcPort > 0)
				server = new Socket(adress, destPort, localAddr, srcPort);
			else
				server = new Socket(adress, destPort);
			server.setSoTimeout(2000);
		
			input = new BufferedInputStream(server.getInputStream());
			output = new BufferedOutputStream(server.getOutputStream());

			output.write(message.getBytes());
			output.flush();
			System.out.println("gesendet: "+message);
		
			//jetzt lesen..
			byte[] buf = new byte[256];
			input.read(buf);			
			System.out.println("empfangen: ");
			String s = new String(buf);
			System.out.println(s.trim());
		
			input.close();
			output.close();
			server.close();
		}catch (IOException e) {
			
			System.out.println(e.getMessage());

			try {
				if (input !=null )input.close();
				if (output !=null )output.close();
				if (server !=null )server.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void main (String[] args) 
	{
			new JConsole("Client");
			TCPClient client = new TCPClient();
			client.send("MARKUS", "127.0.0.1", 1234, 0);
	}
}