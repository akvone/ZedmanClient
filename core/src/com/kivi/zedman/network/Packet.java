package com.kivi.zedman.network;

import com.kivi.zedman.controller.PlayerController;

import java.util.ArrayList;

/**
 * Created by Kirill on 28.04.2016.
 */
public class Packet {
    public static class SomeMessage {
        public String text;
    }

    public static class ConnectRequest{
    }

    public static class ClientControls{
        ArrayList<Boolean> arrayList;

        ClientControls(){
            arrayList = PlayerController.controls;
        }
    }
}
