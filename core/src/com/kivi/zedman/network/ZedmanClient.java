package com.kivi.zedman.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;
import com.kivi.zedman.utils.Constants;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Kirill on 28.04.2016.
 */
public class ZedmanClient {
    public Client client;
    public static Scanner scanner;

    private float updateTime = 1/Constants.STANDARD_PACKETS_PER_SECOND;

    public ZedmanClient() {
        scanner = new Scanner(System.in);
        client = new Client();
        register();

        NetworkListener nl = new NetworkListener();
        nl.init(client);
        client.addListener(nl);

        client.start();
        try {
            client.connect(50000, "127.0.0.1", 54555, 54777);

        } catch (IOException e) {
            e.printStackTrace();
            client.stop();
        }
    }

    private void register() {
        Kryo kryo = client.getKryo();
        kryo.register(Packet.SomeMessage.class);
        kryo.register(Packet.ConnectRequest.class);
        kryo.register(Packet.ClientControls.class);
    }

    public void update(){
        Packet.ClientControls cc = new Packet.ClientControls();
        client.sendUDP(cc);
    }
}
