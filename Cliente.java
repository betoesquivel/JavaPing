import java.net.*;
import java.io.*;
import java.util.*;

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
          + " 56(84) bytes of data.";
      String templateAnswer = "64 bytes from " + addr.getHostName() + "(" + addr.getHostAddress()
          + "): icmp_req=";
      String templateStatistics = "--- " + addr.getHostName() + " ping statistics ---";
      int transmitted = 0, received = 0;
      double packetLoss = 0, min = 10000, avg = 0, max = 0;

      InetAddress server= InetAddress.getByName(hostname);
      BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
      DatagramSocket socket = new DatagramSocket();
      System.out.println(firstLine);
      int cont = 0;
      long time = 0;
      double dTime = 0;
      while (cont < 4) {
        byte[] dataOut = new byte[templateAnswer.length()];
        dataOut = templateAnswer.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(dataOut, dataOut.length, server, 8888);
        time = System.currentTimeMillis();
        socket.send(sendPacket);
        transmitted++;
        byte[] dataIn = new byte[256];
        DatagramPacket receivePacket = new DatagramPacket(dataIn, 256);
        socket.receive (receivePacket);
        if (receivePacket != null){
            received++;
            time = System.currentTimeMillis() - time;
            dTime = time * .01;
            min = (dTime < min) ? dTime : min;
            max = (dTime > max) ? dTime : max;
            avg += dTime;
        }
        String s = new String(receivePacket.getData(), 0, receivePacket.getLength());
        System.out.format(s + "%d time=%.2f ms%n", cont, time*.01);
        cont ++;
      }  // end while
         System.out.println(templateStatistics);
         System.out.format("%d transmitted, %d received, %d packet loss%n", transmitted, received, ( int ) (packetLoss * 100));
         System.out.format("min/avg/max = %.3f/%.3f/%.3f ms%n", min, avg / received, max);
     }  // end try
     catch (UnknownHostException e) {System.err.println(e);}
     catch (SocketException se) {System.err.println(se);}
     catch (IOException e) {System.err.println(e);}
  }  // end main
}
