package cs496.prj_2;

import com.strongloop.android.loopback.Model;

/**
 * Created by q on 2016-07-05.
 */
public class Address extends Model {
    private String name;
    private String picture;
    private String phone_num;
    private String email;
    private String owner;

    public void setName(String name) {this.name = name;}
    public String getName() {return name;}
    public void setPicture(String picture) {this.picture = picture;}
    public String getPicture() {return picture;}
    public void setPhone_num(String num) {this.phone_num = num;}
    public String getPhone_num() {return phone_num;}
    public void setEmail(String email) {this.email = email;}
    public String getEmail() {return email;}
    public void setOwner(String owner) {this.owner = owner;}
    public String getOwner() {return owner;}
}
