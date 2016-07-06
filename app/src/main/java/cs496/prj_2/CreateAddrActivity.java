package cs496.prj_2;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by q on 2016-07-06.
 */
public class CreateAddrActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_contact);
        Button bt1 = (Button) findViewById(R.id.buttonEdit);
        EditText ed1 = (EditText) findViewById(R.id.txtnumber);
        ed1.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        bt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String name = ((EditText)findViewById(R.id.txtname)).getText().toString();
                String num = ((EditText)findViewById(R.id.txtnumber)).getText().toString();
                String email = ((EditText)findViewById(R.id.txtemail)).getText().toString();

                if(name.length()==0 || num.length()==0 || email.length()==0){
                    Snackbar.make(v, "Please fill the name or phone number or email", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                    return;
                }
                Bundle extra = new Bundle();
                Intent in = new Intent();
                extra.putString("name",name);
                extra.putString("phone_num", num);
                extra.putString("email",email);
                extra.putString("picture","");
                in.putExtras(extra);
                setResult(1, in);
                finish();
            }
        });
    }
}
