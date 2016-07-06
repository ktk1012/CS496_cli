package cs496.prj_2;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.provider.ContactsContract.CommonDataKinds.Phone;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.google.common.collect.ImmutableMap;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.ListCallback;
import com.strongloop.android.loopback.callbacks.VoidCallback;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;
import java.util.Map;

public class Contacts extends Fragment {

//    HttpClient httpClient = new DefaultHttpClient();
//    HttpContext httpContext = new BasicHttpContext();
//    HttpPost httpPost = new HttpPost(/*put url her*/);
//    String serverResponse;
    private Context mContext;
    private Cursor cur;
    private RestAdapter mRestAdapter;
    private AddressRepository mAddressRepo;
    private AddressAdapter mAdapter;

    ArrayList<Address> localContacts = new ArrayList<>();
    ArrayList<Address> Contacts = new ArrayList<Address>();
    ListView listView;

    String JsonArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.contacts_list_view, container, false);

        listView = (ListView) view.findViewById(android.R.id.list);

        mRestAdapter = new RestAdapter(getContext(), "http://52.78.69.111:3000/api");

        mAddressRepo = mRestAdapter.createRepository(AddressRepository.class);

        Log.d("OnCreateView", String.valueOf(Contacts.size()));


        mAdapter = new AddressAdapter(getContext(), Contacts);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Address addr = (Address) mAdapter.getItem(position);

                Intent in = new Intent(getActivity().getApplicationContext(),
                        SingleContacts.class);
                in.putExtra("name", addr.getName());
                in.putExtra("phoneNum", addr.getPhone_num());
                in.putExtra("email",addr.getEmail());
                in.putExtra("image", addr.getPicture());
                in.putExtra("id", addr.getId().toString());
                in.putExtra("pic", addr.getPicture());
                startActivity(in);
            }
        });

        String owner = AccessToken.getCurrentAccessToken().getUserId();
        mAddressRepo.get(owner, new ListCallback<Address>() {
            @Override
            public void onSuccess(List<Address> objects) {
                Contacts = (ArrayList<Address>) objects;
                mAdapter.updateAddressAdapter(Contacts);
                for (int i = 0; i < Contacts.size(); i++) {
                    Log.d("SUCCESS", "Contact" + Contacts.get(i).getEmail());
                }
                Log.d("COUNT", String.valueOf(mAdapter.getCount()));
            }

            @Override
            public void onError(Throwable t) {

            }
        });

        return view;

    }

    public void onCreate(Bundle savedInstanceState) {
        ContentResolver cr = getActivity().getApplicationContext().getContentResolver();
        super.onCreate(savedInstanceState);
        mContext = getContext();
        cur = mContext.getContentResolver().query(Phone.CONTENT_URI, null, null, null, null);





//        if(cur.moveToFirst() && cur.getCount()>0)
//        {
//            while (cur.moveToNext()) {
//                String name = cur.getString(cur.getColumnIndex(Phone.DISPLAY_NAME));
//                String number = cur.getString(cur.getColumnIndex(Phone.NUMBER));
//                Log.d("READ", name + number);
//                String owner = AccessToken.getCurrentAccessToken().getUserId();
//                Address localContact = repository.createObject(ImmutableMap.of("name", name,
//                        "number", number, "owner", owner));
////                localContact.setName(name);
////                localContact.setPhone_num(number);
////                localContact.setOwner(owner);
//                localContact.save(new VoidCallback() {
//                    @Override
//                    public void onSuccess() {
//                        Log.d("SAVE", "SUCCESS");
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        Log.d("SAVE", "FAIL");
//                    }
//                });
////                localContacts.add(localContact);
//            }
//        }
//        cur.close();


//        JsonArray = listmap_to_json_string(localContacts);

//        try{
//            StringEntity se = new StringEntity(JsonArray);
//            httpPost.setEntity(se);
//            httpPost.setHeader("Accept", "application/json");
//            httpPost.setHeader("Content-type", "application/json");
//
//            HttpResponse response = httpClient.execute(httpPost,httpContext);
//            JSONArray JsonResponse = (JSONArray) response;
//            HttpEntity entity = response.getEntity();
//
//            serverResponse = EntityUtils.toString(entity);
//
//            for (int i=0; i<JsonResponse.length(); i++){
//                HashMap<String, String> map = new HashMap<>();
//                JSONObject c = (JSONObject)JsonResponse.get(i);
//                Iterator iter = c.keys();
//                while(iter.hasNext()){
//                    String currentkey = (String) iter.next();
//                    map.put(currentkey, c.getString(currentkey));
//                }
//                Contacts.add(map);
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
    }

    public String listmap_to_json_string(ArrayList<HashMap<String, String>> list){
        JSONArray jsonArray = new JSONArray();
        for (Map<String, String> map: list){
            JSONObject json_obj = new JSONObject();
            for (Map.Entry <String,String> entry : map.entrySet()){
                String key = entry.getKey();
                String value = entry.getValue();
                try{
                    json_obj.put(key,value);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
            jsonArray.put(json_obj);
        }
        return jsonArray.toString();
    }
}
