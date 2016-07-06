package cs496.prj_2;

import android.provider.Telephony;

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

import java.util.HashMap;

/**
 * Created by q on 2016-07-05.
 */
public class AddressRepository extends ModelRepository<Address> {
    public AddressRepository() {
        super("address", Address.class);
    }

    @Override
    public RestContract createContract() {
        RestContract contract = super.createContract();

        contract.addItem(new RestContractItem("/" + getNameForRestUrl(), "GET"),
                getClassName() + ".get");

        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/:id", "PUT"),
                getClassName() + ".put");

        contract.addItem(new RestContractItem("/" + getNameForRestUrl(), "PUT"),
                getClassName() + ".upsert");

        return contract;
    }

    public void get(String owner, final ListCallback<Address> callback) {
        invokeStaticMethod("get", ImmutableMap.of("filter",
                ImmutableMap.of("where",
                        ImmutableMap.of("owner", owner))), new JsonArrayParser<Address>(this, callback));
    }

    public void put(HashMap<String, Object> params, final ObjectCallback<Address> callback) {
        invokeStaticMethod("put", params, new JsonObjectParser<Address>(this, callback));
    }

    public void upsert(HashMap<String, Object> params, final ObjectCallback<Address> callback) {
        invokeStaticMethod("upsert", params, new JsonObjectParser<Address>(this, callback));
    }
}
