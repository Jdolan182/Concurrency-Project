package Assignment;

public interface Coordinator extends java.rmi.Remote{

	public void request(Client client, String clientID) throws java.rmi.RemoteException;

	public boolean release() throws java.rmi.RemoteException;

}
