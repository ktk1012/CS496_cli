package cs496.prj_2;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.ListCallback;
import com.strongloop.android.loopback.callbacks.ObjectCallback;
import com.strongloop.android.loopback.callbacks.VoidCallback;

public class SingleContacts extends Activity {
    private Context mContext = getApplicationContext();
    String nameEdit, numberEdit, emailEdit;
    private RestAdapter mRestAdapter;
    private AddressRepository mAddressRepo;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mRestAdapter = new RestAdapter(mContext, "http://52.78.69.111:3000/api");

        mAddressRepo = mRestAdapter.createRepository(AddressRepository.class);
        setContentView(R.layout.single_contact);
        Intent in = getIntent();

        String name = in.getStringExtra("name");
        String number = in.getStringExtra("phoneNum");
        String email = in.getStringExtra("email");


        final EditText txtName = (EditText) findViewById(R.id.txtname);
        final EditText txtphoneNum = (EditText) findViewById(R.id.txtnumber);
        final EditText txtemail = (EditText) findViewById(R.id.txtemail);


        txtName.setText(name, TextView.BufferType.EDITABLE);
        txtphoneNum.setText(number, TextView.BufferType.EDITABLE);
        txtemail.setText(email, TextView.BufferType.EDITABLE);

        Button buttonEdit = (Button) findViewById(R.id.buttonEdit);

        buttonEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                nameEdit = txtName.getText().toString();
                numberEdit = txtphoneNum.getText().toString();
                emailEdit = txtemail.getText().toString();

                mAddressRepo.findById("???", new ObjectCallback<Address>() {
                    @Override
                    public void onSuccess(Address object) {
                        object.destroy(new VoidCallback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onError(Throwable t) {

                    }
                });
            }
        });
    }
}
