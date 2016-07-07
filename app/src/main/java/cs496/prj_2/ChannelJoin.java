package cs496.prj_2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.engineio.client.Socket;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.ListCallback;
import com.strongloop.android.loopback.callbacks.ObjectCallback;
import com.strongloop.android.loopback.callbacks.VoidCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by q on 2016-07-07.
 */
public class ChannelJoin extends AppCompatActivity {
    private PubSub mSub;
    private String room_session;
    private ChannelRepository mChannelRepo;
    private RestAdapter mRestAdapter;
    private com.github.nkzawa.socketio.client.Socket mSocket;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_room);
        ChatApp app = (ChatApp) ChannelJoin.this.getApplication();
        mSub = app.getPubSub();
        mSocket = app.getSocket();
        Intent in = getIntent();
        room_session = in.getStringExtra("id");
        final String token = AccessToken.getCurrentAccessToken().getToken();
        mRestAdapter = new RestAdapter(getApplicationContext(), "http://52.78.69.111:3000/api");
        mChannelRepo = mRestAdapter.createRepository(ChannelRepository.class);

        mSub.Subscribe("channel", room_session, "GET", "join", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                ChannelJoin.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        String msg;
                        try {
                            msg = data.getString("message");
                        } catch (JSONException e) {
                            return;
                        }
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        mSub.Subscribe("channel", room_session, "GET", "message", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                ChannelJoin.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject)args[0];
                        String content;
                        String author;
                        int type;
                        try {
                            JSONObject temp = data.getJSONObject("newMsg");
                            content = temp.getString("content");
                            author = temp.getString("author");
                            Toast.makeText(getApplicationContext(), content + ":  " + author, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });

        mChannelRepo.join(token, room_session, new ListCallback<Message>() {
            @Override
            public void onSuccess(List<Message> objects) {
                Log.d("STRRRRR", String.valueOf(objects.size()));

            }

            @Override
            public void onError(Throwable t) {

            }
        });
        final EditText ed = (EditText) findViewById(R.id.messageedit);
        final Button btn = (Button) findViewById(R.id.sendbutton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = AccessToken.getCurrentAccessToken().getToken();
                String text = ((EditText) findViewById(R.id.messageedit)).getText().toString();
                mChannelRepo.sendMessage(token, text, room_session, new VoidCallback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }
                });
//                JsonObject jobj = new JsonObject();
//                jobj.addProperty("tokenid", token);
//                jobj.addProperty("message", message);
//                jobj.addProperty("session", room_session);
//                JsonObject req = new JsonObject();
//                req.addProperty("params", jobj.toString());
//                Ion.with(getApplicationContext()).load("http://52.78.69.111:3000/api/Channels/message")
//                        .setJsonObjectBody(req).asJsonObject()
//                        .setCallback(new FutureCallback<JsonObject>() {
//                            @Override
//                            public void onCompleted(Exception e, JsonObject result) {
//
//                            }
//                        });
            }
        });

        mSocket.connect();
    }

    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        mSub.UnscribeAll();
    }
}
