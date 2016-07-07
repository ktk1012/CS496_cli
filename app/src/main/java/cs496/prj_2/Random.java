package cs496.prj_2;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.koushikdutta.async.parser.JSONObjectParser;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.ListCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import cs496.prj_2.R;
/**
 * Created by q on 2016-07-04.
 */
public class Random extends Fragment{
    private static int REQ_ADD = 1234;
    private ArrayList<Channel> mChannelList = new ArrayList<Channel>();
    private RestAdapter mRestAdapter;
    private ChannelRepository mChannelRepo;
    private ChannelAdapter mAdapter;
    private ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRestAdapter = new RestAdapter(getContext(), "http://52.78.69.111:3000/api");
        mChannelRepo = mRestAdapter.createRepository(ChannelRepository.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.channel_list, container, false);

        mListView = (ListView) view.findViewById(R.id.channellist);

        mAdapter = new ChannelAdapter(getContext(), mChannelList);
        mListView.setAdapter(mAdapter);
        Refresh();

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.channelFab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                Intent in = new Intent(getActivity(), CreateChannelActivity.class);
                startActivityForResult(in, REQ_ADD);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Channel channel = (Channel) mAdapter.getItem(position);
                Intent in = new Intent(getActivity().getApplicationContext(),
                        ChannelJoin.class);
                in.putExtra("id", channel.getId().toString());
                startActivity(in);
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQ_ADD && resultCode == 1) {
            Refresh();
        }
    }

    public void Refresh() {
        mChannelRepo.findAll(new ListCallback<Channel>() {
            @Override
            public void onSuccess(List<Channel> objects) {
                mChannelList = (ArrayList<Channel>) objects;
                mAdapter.updateChannelAdapter(mChannelList);
            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }

}
