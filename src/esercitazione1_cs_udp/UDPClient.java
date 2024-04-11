/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package esercitazione1_cs_udp;

import java.io.*;
import java.net.*;


/**
 *
 * @author Alessio Betti
 */
public class UDPClient {

    
    public static void main(String[] args) {
        int porta = 2020; //numero della porta
        InetAddress serverAddress; //indirizzo del server
        DatagramSocket dSocket; //socket del client UDP
        DatagramPacket outPacket; //pacchetto da inviare al server
        DatagramPacket inPacket; //pacchetto ricevuto in input dal server
        byte[] buffer; //array di byte per i dati da inviare
        
        String messaggio = "richiesta di data e ora";
        String risposta;
        
        try{
            //si ricava l'indirizzo del server attraverso il metodo getLocalHost()
            serverAddress = InetAddress.getLocalHost();
            System.out.println("Indirizzo del server trovato!");
            
            dSocket = new DatagramSocket();
            
            /*  si prepara il pacchetto da inviare al server,
                specificando il messaggio, la lunghezza e la socket del server (indirizzo e porta) */
            outPacket = new DatagramPacket(messaggio.getBytes(), messaggio.length(), serverAddress, porta);
            
            
            System.out.println(outPacket);
            //si invia il pacchetto al server
            dSocket.send(outPacket);
            
            //si prepara il buffer per ricevere la risposta
            buffer = new byte[256];
            
            //si prepara il pacchetto per ricevere i dati nel buffer
            inPacket = new DatagramPacket(buffer, buffer.length);
            
            //si accetta il datagramma di risposta
            dSocket.receive(inPacket);
            
            //si estrae il messaggio
            risposta = new String(inPacket.getData(), 0, inPacket.getLength());
            System.out.println(risposta);
            
            System.out.println("il server ha inviato una risposta");
            System.out.println("Data e ora del server: " + risposta);
            
            //chiusura della comunicazione
            dSocket.close();
            System.out.println("Comunicazione in chiusura");
         
        } catch(UnknownHostException e){
            System.err.println("Errore - DNS");
            e.getStackTrace();
        } catch(SocketException e){
            System.err.println("Errore - socket");
            e.getStackTrace();
        }catch(IOException e){
            System.err.println("Errore - I/O");
            e.getStackTrace();
        }
        
    }
    
}
