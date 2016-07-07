package cs496.prj_2;

import com.strongloop.android.loopback.Model;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by q on 2016-07-07.
 */
public class Channel extends Model {
    private String Id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    private String name;
    private ArrayList<User> users;
    private ArrayList<Message> messages;

}
