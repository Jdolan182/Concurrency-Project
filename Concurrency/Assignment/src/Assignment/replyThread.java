package Assignment;

import java.rmi.RemoteException;

public class replyThread extends Thread {

	private Client clientRef;

	public replyThread(Client ref) {

		clientRef = ref;
	}

	public void run() {
		
		synchronized(clientRef){
		
		try {
				clientRef.reply();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
}
