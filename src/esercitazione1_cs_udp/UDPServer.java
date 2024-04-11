/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
import java.io.*;
import java.net.*;
import java.util.Date;

/**
 *
 * @author Alessio Betti
 */
public class UDPServer {

    public static void main(String[] args) {

        int porta = 13; //si può scegliere una porta maggiore di 1024 oppure la porta standard del protocollo Daytime
        DatagramSocket dSocket; //oggetto socket UDP
        DatagramPacket inPacket; //datagramma di ricezione dei dati dal client
        DatagramPacket outPacket; //datagramma per inviare la risposta al client

        byte[] bufferIn; //buffer per la ricezione dei dati
        byte[] bufferOut;//buffer per l'invio di dati

        String messaggioIn; //testo in input dal client
        String messaggioOut; //testo in output verso il client

        Date d;

        try {
            
            // creiamo il socket, associandolo alla porta specificata
            dSocket = new DatagramSocket(porta);
            System.out.println("Apertura della porta in corso...");
            
            while(true){
                System.out.println("Server in ascolto sulla porta " + porta + "\n");
                
                //prepariamo il buffer su cui ricevere la richiesta del client
                bufferIn = new byte[256];
                
                //si crea un datagramma in cui trasportare il buffer
                inPacket = new DatagramPacket(bufferIn, bufferIn.length);
                
                //ricezione dei pacchetti dal client
                dSocket.receive(inPacket);
                
                System.out.println(inPacket);
                
                //recupero dell'IP e della porta del client
                InetAddress clientAddress = inPacket.getAddress();
                int clientPort = inPacket.getPort();
                
                //stsmpa del messaggio ricevuto dal client
                messaggioIn = new String(inPacket.getData(), 0, inPacket.getLength());
                System.out.println("Indirizzo client > " + clientAddress);
                System.out.println("Porta client > " + clientPort);
                System.out.println("Messaggio > " + messaggioIn);
                
                //si crea un oggetto di tipo Date
                d = new Date();
                
                //si crea il messaggio da inviare in uscita, associandolo alla comunicazione con il client
                messaggioOut = d.toString();
                bufferOut = messaggioOut.getBytes();
                
                //si crea un datagramma UDP in cui trasportare il messaggio
                outPacket = new DatagramPacket(bufferOut, bufferOut.length, clientAddress, clientPort);
                
                //si invia la risposta al client
                dSocket.send(outPacket);
                System.out.println("Risposta inviata!");
                
                
            }
           

        } catch (BindException e) {
            System.err.println("Errore - porta gia' in uso");
            e.getStackTrace();
        } catch (SocketException e){
            System.err.println("Errore - socket");
            e.getStackTrace();
        }catch (IOException e){
            System.err.println("Errore - I/O");
            e.getStackTrace();
        }
    }

}
