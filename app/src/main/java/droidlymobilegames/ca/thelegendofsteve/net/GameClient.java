package droidlymobilegames.ca.thelegendofsteve.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import droidlymobilegames.ca.thelegendofsteve.GameviewActivity;

public class GameClient extends Thread {

    private InetAddress ipAddress;
    private DatagramSocket socket;
    private GameviewActivity game;
    public String stringData;
    public ArrayList<String> usernameList = new ArrayList<>();

    public GameClient(GameviewActivity game, String ipAddress) {
        this.game = game;
        try {
            this.socket = new DatagramSocket();
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            stringData = new String(packet.getData(), 0, packet.getLength());
            this.parsePacket(stringData, packet.getAddress(), packet.getPort());
        }
    }

    private void parsePacket(String data, InetAddress address, int port) {
        String[] dataArray = data.split(",");
        switch (dataArray[0]) {
            default:
            case "-1":
                break;
            case "01"://LOGIN
                game.addPlayer(dataArray[1],dataArray[2],dataArray[3],dataArray[4],address,port);
                break;
            case "02"://LOGOUT
                break;
            case "03"://MOVING
                game.movePlayer(dataArray[1],dataArray[2],dataArray[3],dataArray[4]);
                break;
        }
    }



    public void sendData(String databytes) {
        DatagramPacket packet = new DatagramPacket(databytes.getBytes(), databytes.length(),
                ipAddress, 1313);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}