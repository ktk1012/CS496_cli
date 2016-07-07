package cs496.prj_2;

import com.google.common.collect.ImmutableMap;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.loopback.callbacks.JsonArrayParser;
import com.strongloop.android.loopback.callbacks.JsonObjectParser;
import com.strongloop.android.loopback.callbacks.ListCallback;
import com.strongloop.android.loopback.callbacks.ObjectCallback;
import com.strongloop.android.loopback.callbacks.VoidCallback;
import com.strongloop.android.remoting.adapters.Adapter;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by q on 2016-07-07.
 */
public class ChannelRepository extends ModelRepository<Channel> {
    public ChannelRepository() {super("channel", Channel.class);}

    @Override
    public RestContract createContract() {
        RestContract restContract = super.createContract();

        restContract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/join", "GET"),
                getClassName() + ".join");

        restContract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/message", "GET"),
                getClassName() + ".message");


        return restContract;
    }

    public void join(String token, String session, final ListCallback<Message> callback) {
        JSONObject obj = new JSONObject();
        invokeStaticMethod("join", ImmutableMap.of("tokenid", token, "session", session),
                new JsonArrayParser<Message>(new MessageRepository(), callback));


    }

    public void sendMessage(String token, String message, String session, final VoidCallback callback) {
        invokeStaticMethod("message", ImmutableMap.of("tokenid", token, "content", message, "session", session),
                new Adapter.Callback() {

                    @Override
                    public void onSuccess(String response) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }
                });
    }

}
