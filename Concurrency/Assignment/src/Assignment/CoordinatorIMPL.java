package Assignment;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Vector;

public class CoordinatorIMPL extends java.rmi.server.UnicastRemoteObject implements Coordinator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4718525118934253588L;

	protected CoordinatorIMPL() throws RemoteException {
		super();
		queue = new Vector<Client>();
	}

	public static void main(String args[]) {

		System.setSecurityManager(new SecurityManager());
		try {
			Coordinator server = new CoordinatorIMPL();
			Naming.rebind("//127.0.0.1/MessageServer", server);
			System.out.println("Server Bound");
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public synchronized void request(Client client, String clientID) throws RemoteException {

		queue.addElement(client);
		System.out.println("Request added: " + clientID + " size = " + queue.size());

		if (queue.size() == 1) {

			tryRunRequest();
		}

	}

	private void tryRunRequest() throws RemoteException {

		Client client = queue.firstElement();

		if (count != 7) {
			for (int i = 0; i < queue.size(); i++) {

				client = queue.firstElement();

				if (client.getPriority().equals("C")) {
					queue.addElement(client);
					queue.removeElementAt(0);
					// System.out.println("noob Client at front");
				}
			}
		} else {
			count = 0;
		}

		runRequest(client);

	}

	private void runRequest(Client client) throws RemoteException {

		client = queue.firstElement();
		rp = new replyThread(client);
		rp.start();
		count++;
	}

	public synchronized boolean release() throws RemoteException {

		if (queue.size() == 0)
			return false;
		else {

			queue.removeElementAt(0);
			System.out.println("Release, removed: size = " + queue.size());
			if (queue.size() > 0) {
				// Client client = queue.firstElement();
				tryRunRequest();
			}
			return true;
		}
	}

	private replyThread rp;
	private Vector<Client> queue;
	private ServerSocket s;
	private Socket client;
	private int count = 0;

}