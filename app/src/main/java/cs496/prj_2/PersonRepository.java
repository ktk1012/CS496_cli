package cs496.prj_2;

import android.util.Log;

import com.google.common.collect.ImmutableMap;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.loopback.callbacks.VoidCallback;
import com.strongloop.android.remoting.adapters.Adapter;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

import java.util.Map;

/**
 * Created by q on 2016-07-05.
 */
public class PersonRepository extends ModelRepository<Person> {
    public PersonRepository() {
        super("person", Person.class);
    }

    @Override
    public RestContract createContract() {
        RestContract contract = super.createContract();

        RestContractItem fblogin = new RestContractItem("/" + getNameForRestUrl() + "/fblogin" ,"GET");
        contract.addItem(fblogin, getClassName() + ".fblogin");
        return contract;
    }

    public void fblogin(String token, String fb_uid, final VoidCallback callback) {
        Log.d("TokenSending", getNameForRestUrl());
        ImmutableMap params = ImmutableMap.of("tokenid", token, "fb_uid", fb_uid);
        invokeStaticMethod("fblogin", params, new Adapter.Callback() {

            @Override
            public void onSuccess(String response) {
                callback.onSuccess();
            }

            @Override
            public void onError(Throwable t) {
                callback.onError(t);
            }
        });
    }
}
