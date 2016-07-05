package cs496.prj_2;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.provider.ContactsContract.CommonDataKinds.Phone;

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
import java.util.Iterator;
import java.util.Map;

public class Contacts extends Fragment {

    HttpClient httpClient = new DefaultHttpClient();
    HttpContext httpContext = new BasicHttpContext();
    HttpPost httpPost = new HttpPost(/*put url her*/);
    String serverResponse;
    private Context mContext;
    private Cursor cur;
    ArrayList<HashMap<String,String>> localContacts = new ArrayList<HashMap<String,String>>();
    ArrayList<HashMap<String,String>> Contacts = new ArrayList<HashMap<String,String>>();
    ListView listView;

    String JsonArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.contacts_list_view, container, false);
        listView = (ListView) view.findViewById(android.R.id.list);

        SimpleAdapter adapter = new SimpleAdapter(
                getActivity(), Contacts,
                R.layout.contacts_list_item,
                new String[]{"name", "number"},
                new int[] { R.id.name, R.id.number}
        );
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String name = ((TextView) view.findViewById(R.id.name))
                        .getText().toString();
//                String id1 = "Id: "+((TextView) view.findViewById(R.id.id))
//                        .getText().toString();
                String number = "Phone Number: " + ((TextView) view.findViewById(R.id.number))
                        .getText().toString();

                // Starting single contact activity
                Intent in = new Intent(getActivity().getApplicationContext(),
                        SingleContacts.class);
                in.putExtra("name", name);
                in.putExtra("phoneNum", number);
//                in.putExtra("id", id1);
                startActivity(in);

            }
        });
        return view;

    }

    public void onCreate(Bundle savedInstanceState) {
//        ContentResolver cr = getActivity().getApplicationContext().getContentResolver();
        mContext = getContext();
        cur = mContext.getContentResolver().query(Phone.CONTENT_URI, null, null, null, null);
        super.onCreate(savedInstanceState);

        if(cur.moveToFirst() && cur.getCount()>0)
        {
            while (cur.moveToNext()) {
                HashMap<String,String> localContact = new HashMap<>();
                String name = cur.getString(cur.getColumnIndex(Phone.DISPLAY_NAME));
                String number = cur.getString(cur.getColumnIndex(Phone.NUMBER));
                    localContact.put("name",name);
                    localContact.put("number", number);
                localContacts.add(localContact);
            }
        }
        cur.close();
        JsonArray = listmap_to_json_string(localContacts);

        try{
            StringEntity se = new StringEntity(JsonArray);
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse response = httpClient.execute(httpPost,httpContext);
            JSONArray JsonResponse = (JSONArray) response;
            HttpEntity entity = response.getEntity();

            serverResponse = EntityUtils.toString(entity);

            for (int i=0; i<JsonResponse.length(); i++){
                HashMap<String, String> map = new HashMap<>();
                JSONObject c = (JSONObject)JsonResponse.get(i);
                Iterator iter = c.keys();
                while(iter.hasNext()){
                    String currentkey = (String) iter.next();
                    map.put(currentkey, c.getString(currentkey));
                }
                Contacts.add(map);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
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
