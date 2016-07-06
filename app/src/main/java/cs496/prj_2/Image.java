package cs496.prj_2;

import com.strongloop.android.loopback.Model;

/**
 * Created by q on 2016-07-06.
 */
public class Image extends Model{
    private String Id;
    private String owner;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }
}
