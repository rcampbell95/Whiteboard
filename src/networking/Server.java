package networking;


import java.net.DatagramSocket;

public class Server {
	private UDPSocket connection;
	
   public Server(int port) {
      try {
         connection = new UDPSocket(port);
      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }
   
   //protected String response(String msg) {
   	//TODO -- Implement response
   	
  // }
}
