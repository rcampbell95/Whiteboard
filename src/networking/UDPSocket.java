package networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPSocket extends DatagramSocket {

	public UDPSocket(int port) throws SocketException {
		super(port);
		// TODO Auto-generated constructor stub
	}
	
   public UDPSocket() throws SocketException {
      this(39587);
   }
	
   public void send(InetAddress hostIP, int port, String msg) {
      try {
         byte[] buffer = msg.getBytes();
         DatagramPacket packet = 
            new DatagramPacket(buffer, buffer.length, hostIP, port);
         super.send(packet);
      } catch(IOException e) {
         System.err.println(e.getMessage());
      }
   }
   
   public void send(String host, int port, String msg) {
      try {
         InetAddress hostIP = InetAddress.getByName(host);
         send(hostIP, port, msg);
      } catch(IOException e) {
         System.err.println(e.getMessage());
      }
   }
   
   public void send(int port, String msg) { 
      send("localhost", port, msg); 
   }

}
