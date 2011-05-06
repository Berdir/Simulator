package ch.hslu.ceesar.zigpad.simulator;

public abstract class Server {

	public String answer;
	public int port;
	public boolean doAnswer;
	
	abstract void startServer();
	abstract void stopServer();

}
