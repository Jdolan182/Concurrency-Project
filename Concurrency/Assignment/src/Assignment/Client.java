package Assignment;

public interface Client extends java.rmi.Remote {

	public boolean reply() throws java.rmi.RemoteException;
	public String getPriority() throws java.rmi.RemoteException;
}