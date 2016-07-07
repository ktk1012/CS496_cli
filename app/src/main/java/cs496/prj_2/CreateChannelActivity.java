package cs496.prj_2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.common.collect.ImmutableMap;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.VoidCallback;

import java.util.ArrayList;

/**
 * Created by q on 2016-07-07.
 */
public class CreateChannelActivity extends AppCompatActivity{
    private RestAdapter mRestAdapter;
    private ChannelRepository mChannelRepo;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.channel_create);
        Button bt = (Button) findViewById(R.id.channel_create_button);
        EditText ed = (EditText) findViewById(R.id.channel_create);

        mRestAdapter = new RestAdapter(getApplicationContext(), "http://52.78.69.111:3000/api");
        mChannelRepo = mRestAdapter.createRepository(ChannelRepository.class);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) findViewById(R.id.channel_create)).getText().toString();
                if(name.length() == 0) {
                    Snackbar.make(v, "Please fill the name of channel", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                    return;
                }
                Channel item = mChannelRepo.createObject(ImmutableMap.of("name", name, "users", new ArrayList<User>(), "messages", new ArrayList<Message>()));
                item.save(new VoidCallback() {
                    @Override
                    public void onSuccess() {
                        setResult(1);
                        finish();
                    }

                    @Override
                    public void onError(Throwable t) {

                    }
                });
            }
        });
    }
}
