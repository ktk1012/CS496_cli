package cs496.prj_2;

import android.app.Application;

import com.github.nkzawa.engineio.client.Socket;
import com.github.nkzawa.socketio.client.IO;

import java.net.URISyntaxException;

/**
 * Created by q on 2016-07-07.
 */
public class ChatApp extends Application{
    private PubSub mPubSub;
    private com.github.nkzawa.socketio.client.Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://52.78.69.111:3000/");
            mPubSub = new PubSub(mSocket);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public com.github.nkzawa.socketio.client.Socket getSocket() {
        return mSocket;
    }

    public PubSub getPubSub() {
        return mPubSub;
    }
}
