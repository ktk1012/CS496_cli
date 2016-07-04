package cs496.prj_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cs496.prj_2.R;

public class MainActivity extends AppCompatActivity {

    EditText email, password;
    Button login;
    String emailtxt, passtxt;
    List<NameValuePair> params;
//    List<NameValuePair> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);

        login = (Button)findViewById(R.id.buttonLogin);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                emailtxt = email.getText().toString();
                passtxt = password.getText().toString();
//                params = new ArrayList<NameValuePair>();
//                params.add(new BasicNameValuePair("email", emailtxt));
//                params.add(new BasicNameValuePair("password",passtxt));
//                ServerRequest sr = new ServerRequest();
//                JSONObject json = sr.getJSON("https://52.78.73.214:3000/login",params);
//                if(json!=null){
//                    try{
//                        String jsonstr = json.getString("response");
//                        if(json.getBoolean("res")){
//
//                        }
//                    }catch(JSONException e){
//                        e.printStackTrace();
//                    }
//                }

                Intent loginactivity = new Intent(MainActivity.this, MainActivity2.class);
                loginactivity.putExtra("email",emailtxt);
                loginactivity.putExtra("password",passtxt);
                startActivity(loginactivity);
                finish();
            }
        });
    }
}
