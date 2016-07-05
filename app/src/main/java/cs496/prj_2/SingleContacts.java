package cs496.prj_2;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SingleContacts extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_contact);
        Intent in = getIntent();

        String name = in.getStringExtra("name");
        String number = in.getStringExtra("phoneNum");
        String email = in.getStringExtra("email");


        TextView txtName = (TextView) findViewById(R.id.txtname);
        TextView txtphoneNum = (TextView) findViewById(R.id.txtnumber);
        TextView txtemail = (TextView) findViewById(R.id.txtemail);


        txtName.setText(name);
        txtphoneNum.setText(number);
        txtemail.setText(email);
    }
}
