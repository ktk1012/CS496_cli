package cs496.prj_2;

import com.strongloop.android.loopback.Model;

/**
 * Created by q on 2016-07-07.
 */
public class Message extends Model{
    private int type;
    private String author;
    private String content;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
