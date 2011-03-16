
import java.net.*;
import java.io.*;

public class Client
{
	Client() throws IOException 
	{
		
		InetAddress adress = Inet4Address.getByName("127.0.0.1");
		System.out.println("client startet..");
		
		DatagramSocket socket = new DatagramSocket(4321);
		DatagramPacket packet = new DatagramPacket(new byte[]{65,66,67,68}, 4, adress, 1234);
		socket.send(packet);
		
		System.out.println("gesendet, warte auf antwort...");
		
		byte[] buf = new byte[256];
		DatagramPacket responsePacket = new DatagramPacket(buf, buf.length);
		socket.receive(responsePacket);
		
		socket.close();
		
		String s = new String(buf);
		System.out.println("empfangen: ");
		System.out.println(s.trim());
		
		
		
		/**
		InputStream input = server.getInputStream(); 
		OutputStream output = server.getOutputStream();
		output.write(5);
		output.write(10);
		output.flush();
		System.out.println(input.read());
		server.close();
		input.close();
		output.close();
		*/

	}

	public static void main (String[] args) 
	{
		try {
			Client client = new Client();
		} catch (IOException e) {
			System.out.print(e);
		}
	}
}
