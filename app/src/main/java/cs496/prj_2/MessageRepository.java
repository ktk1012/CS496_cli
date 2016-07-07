package cs496.prj_2;

import com.strongloop.android.loopback.ModelRepository;

/**
 * Created by q on 2016-07-07.
 */
public class MessageRepository extends ModelRepository<Message> {
    public MessageRepository() {super("message", Message.class);}
}
