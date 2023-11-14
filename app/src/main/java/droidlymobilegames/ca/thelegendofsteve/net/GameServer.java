package droidlymobilegames.ca.thelegendofsteve.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import droidlymobilegames.ca.thelegendofsteve.GameviewActivity;

public class GameServer extends Thread{

    private GameviewActivity game;
    private int port = 1313;
    private DatagramSocket socket;
    public boolean running = false;

    String[] alldata = new String[5];

    public GameServer(GameviewActivity game){
        this.game = game;
        try {
            this.socket = new DatagramSocket(port);
        }catch (SocketException se){
            se.printStackTrace();
        }
    }
    @Override
    public void run() {
        super.run();
        while (running){
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data,data.length);
            try {
                socket.receive(packet);
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
            //Parse incoming data
            parsePacketData(packet.getData(),packet.getAddress(),packet.getPort());
        }
    }

    private void parsePacketData(byte[] data, InetAddress address, int port) {
        String _data = new String(data).trim();
        alldata[0] = _data.substring(0,1);
    }
    public void sendData(byte[] data, InetAddress ipAddress, int port) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
        try {
            this.socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServer(){
        running = true;
    }
    public void stopServer(){
        running = false;
    }
}
