package droidlymobilegames.ca.thelegendofsteve.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import droidlymobilegames.ca.thelegendofsteve.GameviewActivity;

public class GameClient extends Thread {

    private InetAddress ipAddress;
    private DatagramSocket socket;
    private GameviewActivity game;

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

            try {
                //Idea send data to server telling it that I'm moving left/right/up/down server
                // responds back to player to move in direction which is handled locally
                game.player.entityDirection = new String(data).trim();
                //Now we need to figure out how to send data array that tells other players
                //what animation the player is currently at plus other data like x y valuesin the world
            }catch (Exception e){

            }
        }
    }

    public void sendData(byte[] data) {

        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1313);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public byte[] getData() {
        return (String.valueOf(game.player.posX)).getBytes();
    }
}
