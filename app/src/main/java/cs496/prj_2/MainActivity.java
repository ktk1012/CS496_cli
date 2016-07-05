package cs496.prj_2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.strongloop.android.loopback.*;
import com.strongloop.android.loopback.callbacks.VoidCallback;

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
    private CallbackManager callbackManger;
    List<NameValuePair> params;
//    List<NameValuePair> params;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS,Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ_CONTACTS);
            Toast.makeText(getApplicationContext(), "permission requested", Toast.LENGTH_LONG);
        }
        int PERMISSIONS_REQUEST_INTERNET = 300;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.INTERNET}, PERMISSIONS_REQUEST_INTERNET);
            Toast.makeText(getApplicationContext(), "permission requested", Toast.LENGTH_LONG);
        }
        super.onCreate(savedInstanceState);
        /* Initialize facebook sdk */
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManger = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);

         email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);

        /* Initialize facebook login */
        final LoginButton loginButton = (LoginButton)findViewById(R.id.facebook_login);
        loginButton.setReadPermissions("public_profile", "user_friends");

        loginButton.registerCallback(callbackManger, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Token", loginResult.getAccessToken().getToken());
                Log.d("TokenId", loginResult.getAccessToken().getUserId());
                RestAdapter adapter = new RestAdapter(getApplicationContext(), "http://52.78.69.111:3000/api/");
                PersonRepository personRepository = adapter.createRepository(PersonRepository.class);
                String token = loginResult.getAccessToken().getToken();
                String uid = loginResult.getAccessToken().getUserId();
                personRepository.fblogin(token, uid, new VoidCallback() {
                    @Override
                    public void onSuccess() {
                        gotoMainActivity();
                    }

                    @Override
                    public void onError(Throwable t) {
                    }
                });
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        login = (Button)findViewById(R.id.buttonLogin);
        int success=1;
        //If login already
        if (success==1) {
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

                    gotoMainActivity();
                }
            });
        }else if(success==0) {

        }else {
            gotoMainActivity();
        }

    }

    private void gotoMainActivity() {
        Intent loginactivity = new Intent(MainActivity.this, MainActivity2.class);
        loginactivity.putExtra("email",emailtxt);
        loginactivity.putExtra("password",passtxt);
        startActivity(loginactivity);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManger.onActivityResult(requestCode, resultCode, data);
    }
}
