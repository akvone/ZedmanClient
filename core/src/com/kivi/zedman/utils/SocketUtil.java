package com.kivi.zedman.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.kivi.zedman.Bot;
import com.kivi.zedman.Bullet;
import com.kivi.zedman.ZWorld;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * ZedmanClient interaction
 * Класс для создания соединения с сервером и обработки поступивших ответов от него
 */
public class SocketUtil {

    private ZWorld zWorld;
    private String socketID;    //ID соединения, полученный от сервера. Каждому игроку соответсвует один ID
    private Socket socket;

    public SocketUtil(ZWorld world){
        zWorld = world;
    }

    public void connectSocket(){
        try	{
            socket = IO.socket("http://localhost:1488");
            socket.connect();
        } catch (URISyntaxException e){
            e.printStackTrace();
        }
    }
    public void configureSocketEvent(){
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() { //Вешает Listener на событые
            @Override                                            //соединения
            public void call(Object... args) {
                Gdx.app.log("SocketIO", "Connected successfully");
            }
        });
        socket.on("SocketID", new Emitter.Listener() {      //Сервер возвращает ID соединения.
            @Override                                       //Этот ID заодно ID клиента с точки
            public void call(Object... args) {              //зрения сервера
                JSONObject data = (JSONObject) args[0];
                try {
                    String id = data.getString("id");
                    socketID = id;
                    Gdx.app.log("SocketIO", id + "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        socket.on("playerLeft", new Emitter.Listener() {    //Пользователь покинул игру
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                String id = "";
                try {
                    id = data.getString("id");
                    ZWorld.bots.remove(id);
                    //TODO: удалить тело покинувшего игрока
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Gdx.app.log("SocketIO", "Player with id of " + id + " is going to leave; my id is " + socketID);
                if (id.equals(socketID)){
                    Gdx.app.log("SocketIO", "You left");
                } else {
                    Gdx.app.log("SocketIO", "Player " + socketID + " left the game");
                }
            }
        });
        socket.on("newPlayer", new Emitter.Listener() { //Новый пользователь. Присылается его ID
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                String id;
                try {
                    id = data.getString("id");
                    Bot bot = new Bot(zWorld);
                    ZWorld.bots.put(id, bot);
                    Gdx.app.log("New player with ", id + " id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        socket.on("getPlayers", new Emitter.Listener() {    //При соединении сервер высылает ID и
            @Override                                       //координатывсех текущих пользователей
            public void call(Object... args) {
                JSONArray data = (JSONArray) args[0];
                try {
                    for (int i = 0; i < data.length(); i++){
                        String  id = data.getJSONObject(i).getString("id");
                        Vector2 pos = new Vector2();
                        pos.x = ((Double) data.getJSONObject(i).getDouble("x")).floatValue();
                        pos.y = ((Double) data.getJSONObject(i).getDouble("y")).floatValue();
                        if (!id.equals(socketID)) {
                            ZWorld.bots.put(data.getJSONObject(i).getString("id"), new Bot(zWorld, pos));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        socket.on("playerMoved", new Emitter.Listener() {   //Событие на перемещение другого игрока
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String id = data.getString("id");
                    Vector2 pos = new Vector2();
                    pos.x = ((Double) data.getDouble("x")).floatValue();
                    pos.y = ((Double) data.getDouble("y")).floatValue();
                    Bot bot = ZWorld.bots.get(id);
//                    bot.getBody().setTransform(pos, 0); <- В этом случае возможен рантайм в рандомный момент
                    bot.positionFromServer = pos;
                    bot.updated = false;
                    Gdx.app.log("SocketIO", "Updated position successfully: "+ pos.x + " : " + pos.y);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        socket.on("bulletCreated", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String id = data.getString("id");
                    Vector2 position = new Vector2();
                    Vector2 direction = new Vector2();
                    position.x = ((Double) data.getDouble("x")).floatValue();
                    position.y = ((Double) data.getDouble("y")).floatValue();
                    direction.x = ((Double) data.getDouble("vx")).floatValue();
                    direction.y = ((Double) data.getDouble("vy")).floatValue();

                    zWorld.bulletToCreate = new Bullet(position, direction);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Socket getSocket() {
        return socket;
    }
}
