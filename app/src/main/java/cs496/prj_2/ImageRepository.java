package cs496.prj_2;

import com.google.common.collect.ImmutableMap;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.loopback.callbacks.JsonArrayParser;
import com.strongloop.android.loopback.callbacks.ListCallback;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

/**
 * Created by q on 2016-07-06.
 */
public class ImageRepository extends ModelRepository<Image> {

    public ImageRepository() {
        super("image", Image.class);
    }

    @Override
    public RestContract createContract() {
        RestContract contract = super.createContract();

        contract.addItem(new RestContractItem("/" + getNameForRestUrl(), "GET"),
                getClassName() + ".get");

        return contract;
    }

    public void get(String userid, final ListCallback<Image> callback) {
        invokeStaticMethod("get", ImmutableMap.of("filter",
                ImmutableMap.of("where",
                        ImmutableMap.of("owner", userid))), new JsonArrayParser<Image>(this, callback));
    }
}
