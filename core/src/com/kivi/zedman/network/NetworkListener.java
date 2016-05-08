package com.kivi.zedman.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

/**
 * Created by Kirill on 28.04.2016.
 */
public class NetworkListener extends Listener{
    private Client client;

    public void init(Client client) {
        this.client=client;
    }

    @Override
    public void connected(Connection connection) {
        Log.info("[CLIENT] You have been connected");
        client.sendTCP(new Packet.ConnectRequest());
    }

    @Override
    public void disconnected(Connection connection) {
        Log.info("[CLIENT] You have been disconnected");
    }

    @Override
    public void received(Connection connection, Object o) {

    }
}
