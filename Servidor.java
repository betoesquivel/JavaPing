import java.net.*;
import java.io.*;
import java.util.Random;
public class Servidor
{
   static DatagramSocket socket;

   public static void main(String args[]){

	byte[] buffer = new byte[4096];
    int porcentajePerdida = 0;
    if (args[0]!=null){
        porcentajePerdida = Integer.parseInt(args[0]);
        if (porcentajePerdida < 0){
            porcentajePerdida = 0;
            System.out.println("Mínimo permitido de perdida es 0.");
        } else if (porcentajePerdida > 20){
            porcentajePerdida = 20;
            System.out.println("Máximo permitido de perdida es 20.");
        }
    }
    System.out.println("Inicializando servidor simulando " + porcentajePerdida
            + "% de pérdida de paquetes...");

    int[] randomLossPool = new int[100];
    //Fill array with 1s
    for (int i = 0; i<100; i++){
        randomLossPool[i] = 1;
    }

    //randomlly fill with 0 n places in array, n being the
    //desired loss percentage
    int randomIndex = 0;
    Random randomFill = new Random();
    while(porcentajePerdida > 0){
        randomIndex = randomFill.nextInt(99);
        if(randomLossPool[randomIndex] != 0){
            randomLossPool[randomIndex] = 0;
            porcentajePerdida--;
        }
    }

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

        if (randomLossPool[randResponse.nextInt(99)] == 1 ){
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
