package cs496.prj_2;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import com.koushikdutta.ion.Ion;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.ListCallback;
import com.strongloop.android.loopback.callbacks.ObjectCallback;
import com.strongloop.android.loopback.callbacks.VoidCallback;

import java.util.HashMap;
import java.util.Map;

public class SingleContacts extends Activity {
    String nameEdit, numberEdit, emailEdit;
    private RestAdapter mRestAdapter;
    private AddressRepository mAddressRepo;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mRestAdapter = new RestAdapter(getApplicationContext(), "http://52.78.69.111:3000/api");

        mAddressRepo = mRestAdapter.createRepository(AddressRepository.class);
        setContentView(R.layout.single_contact);
        Intent in = getIntent();

        String name = in.getStringExtra("name");
        String number = in.getStringExtra("phoneNum");
        String email = in.getStringExtra("email");
        String img = in.getStringExtra("image");
        final int position = in.getIntExtra("position", 0);
        final String id = in.getStringExtra("id");
        final String picture = in.getStringExtra("pic");


        final EditText txtName = (EditText) findViewById(R.id.txtname);
        final EditText txtphoneNum = (EditText) findViewById(R.id.txtnumber);
        txtphoneNum.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        final EditText txtemail = (EditText) findViewById(R.id.txtemail);
        final ImageView profile = (ImageView) findViewById(R.id.imgprofile);


        txtName.setText(name, TextView.BufferType.EDITABLE);
        txtphoneNum.setText(number, TextView.BufferType.EDITABLE);
        txtemail.setText(email, TextView.BufferType.EDITABLE);
        Ion.with(profile).placeholder(R.drawable.placeholder).fitCenter().resize(100, 100)
                .load(img);
//        new ImageDownloaderTask(profile).execute(img);

        Button buttonEdit = (Button) findViewById(R.id.buttonEdit);

        buttonEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                nameEdit = txtName.getText().toString();
                numberEdit = txtphoneNum.getText().toString();
                emailEdit = txtemail.getText().toString();

                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("id", id);
                extra.putString("name", nameEdit);
                extra.putString("email", emailEdit);
                extra.putString("phone_num", numberEdit);
                extra.putString("picture", picture);
                extra.putInt("position", position);

                intent.putExtras(extra);
                setResult(1, intent);
                finish();


//                Map<String, Object> param = new HashMap<String, Object>();
//                param.put("id", id);
//                param.put("name", nameEdit);
//                param.put("email", emailEdit);
//                param.put("phone_num", numberEdit);
//                param.put("picture", picture);
//                param.put("owner", AccessToken.getCurrentAccessToken().getUserId());
//
//
//                mAddressRepo.put((HashMap<String, Object>) param, new ObjectCallback<Address>() {
//                    @Override
//                    public void onSuccess(Address object) {
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//
//                    }
            }
        });
    }
}
