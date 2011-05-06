package ch.hslu.ceesar.zigpad.simulator;
import java.net.*;
import java.io.*;

public class TCPServer extends Server
{
	
	private ServerSocket server;
	private boolean serverRunning = true;

	TCPServer()  
	{ 

	}
	
	public void startServer()
	{
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("TCP server started..");
				Socket client = null;
				BufferedInputStream input = null; 
				BufferedOutputStream output = null;
				try {
						server = new ServerSocket(port);
						
						while (serverRunning)
						{				
							client = server.accept();
					
							input = new BufferedInputStream(client.getInputStream());
					
							byte[] buf = new byte[256];
							input.read(buf);			
							System.out.println("empfangen: ");
							String s = new String(buf);
							System.out.println(s.trim());
						
							InetSocketAddress address = (InetSocketAddress)client.getRemoteSocketAddress();
							System.out.println("von: "+address);
						
							//Antwort
							if (doAnswer)
							{
								byte[] answBuf = answer.getBytes();
								output = new BufferedOutputStream(client.getOutputStream());
								output.write(answBuf);
								output.flush();
								output.close();
								System.out.println("Antwort gesendet");
							}
						
							input.close();
							client.close();
						
						}
				} catch (IOException e) {
					System.out.println(e.getMessage());
					try {
						if (input !=null)input.close();
						if (output !=null)output.close();
						if (client != null && !client.isClosed())client.close();
						if (server != null && !server.isClosed())server.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
				}
				
				System.out.println("Server angehalten");
				
			}
		}).start();
		
		
	}
	
	public void stopServer()
	{
		serverRunning = false;
		try {server.close();} catch (Exception e) {e.printStackTrace();}
	}

	public static void main (String[] args) 
	{
		new JConsole("Server");
		TCPServer server = new TCPServer();
		server.answer = "ACK";
		server.port = 1234;
		server.doAnswer = true;
		server.startServer();
		//try {Thread.sleep(1000);}catch (Exception e) {}
		//server.stopServer();
	}


}