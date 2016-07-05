package cs496.prj_2;

import com.strongloop.android.loopback.*;

/**
 * Created by q on 2016-07-05.
 */
public class UserRepository<P> extends com.strongloop.android.loopback.UserRepository<User>{

    public interface LoginCallback extends com.strongloop.android.loopback.UserRepository.LoginCallback<User> {
    }

    public UserRepository() {
        super("Customer", null, User.class);
    }

    public UserRepository(String className, Class<User> userClass) {
        super(className, userClass);
    }

    public UserRepository(String className, String nameForRestUrl, Class<User> userClass) {
        super(className, nameForRestUrl, userClass);
    }
}
