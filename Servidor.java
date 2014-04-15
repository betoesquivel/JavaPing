import java.net.*;
import java.io.*;
import java.util.Random;
public class Servidor
{
   static DatagramSocket socket;

   public static void main(String args[]){

	byte[] buffer = new byte[4096];
    int porcentajePerdida = Integer.parseInt(args[0]);

      try{
           socket = new DatagramSocket(8888);
      } catch (Exception e){
	    System.err.println ("Unable to bind port");
	}
	for (;;){
	   try{
		DatagramPacket packet = new DatagramPacket(buffer, 4096);
		socket.receive(packet);
        Random randno = new Random();
        Thread.sleep(randno.nextInt(10) + 10);
        Random randResponse = new Random();
        if (randResponse.nextInt(5) != 0){
            socket.send(packet);
        }
	   }catch (IOException ioe){
		System.err.println ("Error : " + ioe);
	   }catch (InterruptedException e){
        System.err.println ("Error : " + e);
       }
	}
   }
}
