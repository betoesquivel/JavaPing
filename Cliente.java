import java.net.*;
import java.io.*;

public class Cliente{
   public static void main(String args[]){
	if (args.length != 1){
		System.err.println ("Syntax - java Cliente hostname");
		return;
	}
            String hostname = args[0];
            InetAddress addr = null;
            try{
	   addr = InetAddress.getByName(hostname);
	}catch (UnknownHostException uhe){
	   System.err.println ("Unable to resolve host");
	   return;
	}
      try {
      String firstLine = "PING " + addr.getHostName() + "(" + addr.getHostAddress() + ")"
          + "56(84) bytes of data.";
      String theLine = "64 bytes from " + addr.getHostName() + "(" + addr.getHostAddress()
          + "): icmp_req=";
      InetAddress server= InetAddress.getByName(hostname);
      BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
      DatagramSocket socket = new DatagramSocket();
      System.out.println(firstLine);
      int cont = 0;
      double time = 0;
      while (cont < 4) {
        byte[] dataOut = new byte[theLine.length()];
        dataOut = theLine.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(dataOut, dataOut.length, server, 8888);
        time = 0;
        socket.send(sendPacket);
        byte[] dataIn = new byte[256];
        DatagramPacket receivePacket = new DatagramPacket(dataIn, 256);
        socket.receive (receivePacket);
        time = 0;
        String s = new String(receivePacket.getData(), 0, receivePacket.getLength());
        System.out.println(s + cont + " time=" + time +  " ms");
        cont ++;
      }  // end while
     }  // end try
     catch (UnknownHostException e) {System.err.println(e);}
     catch (SocketException se) {System.err.println(se);}
     catch (IOException e) {System.err.println(e);}
  }  // end main
}
