package cs496.prj_2;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.engineio.client.Socket;
import com.github.nkzawa.socketio.client.IO;
import com.google.common.collect.ImmutableMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by q on 2016-07-07.
 */
public class PubSub {
    ArrayList<String> container = new ArrayList<String>();
    com.github.nkzawa.socketio.client.Socket mSocket;

    public PubSub(com.github.nkzawa.socketio.client.Socket mSocket) {
        this.mSocket = mSocket;
    }

    public void Subscribe(String collectionName, String modelId, String method, String methodName, Emitter.Listener callback) {
        String name = "";
        if (method == "POST") {
            name = collectionName + "/" + methodName + "/" + method;
            mSocket.on(name, callback);
        } else {
            name = collectionName + "/" + modelId + "/" + methodName + "/" + method;
            mSocket.on(name, callback);
        }
        Log.d("SUBSCRIBE", name);
        container.add(name);
    }

    public void UnscribeAll() {
        for (int i = 0; i < container.size(); i++) {
            mSocket.off(container.get(i));
        }
        container.clear();
    }

}
