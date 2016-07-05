package cs496.prj_2;

import com.strongloop.android.loopback.ModelRepository;

/**
 * Created by q on 2016-07-05.
 */
public class AddressRepository extends ModelRepository<Address> {
    public AddressRepository() {
        super("address", Address.class);
    }
}
