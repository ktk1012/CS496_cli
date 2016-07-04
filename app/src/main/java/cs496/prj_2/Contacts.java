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

import java.util.ArrayList;
import java.util.HashMap;

public class Contacts extends Fragment {

    private Context mContext;
    private Cursor cur;
    ArrayList<HashMap<String,String>> localContacts = new ArrayList<HashMap<String,String>>();
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.contacts_list_view, container, false);
//        super.onCreate(savedInstanceState);
//        mContext = getContext();
//        cur = mContext.getContentResolver().query(Phone.CONTENT_URI, null, null, null, null);
////        super.onCreate(savedInstanceState);
//
//        if(cur.getCount()>0)
//        {
//            while (cur.moveToNext()) {
//                HashMap<String,String> localContact = new HashMap<>();
////                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
//                String name = cur.getString(cur.getColumnIndex(Phone.DISPLAY_NAME));
//                String number = cur.getString(cur.getColumnIndex(Phone.NUMBER));
////                if (("1").equals(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)))) {
////                    Cursor pCur = cr.query(
////                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
////                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
////
////                    String phoneNum = pCur.getString(pCur.getColumnIndex(
////                                ContactsContract.CommonDataKinds.Phone.NUMBER));
////                    localContact.put("id",id);
//                localContact.put("name",name);
//                localContact.put("phoneNum", number);
////                }else{
////                    localContact.put("id",id);
////                    localContact.put("name",name);
////                    localContact.put("phoneNum",null);
////                }
//                localContacts.add(localContact);
//            }
//        }
//        cur.close();

        listView = (ListView) view.findViewById(android.R.id.list);

        SimpleAdapter adapter = new SimpleAdapter(
                getActivity(), localContacts,
                R.layout.contacts_list_item,
                new String[]{"name", "phoneNum"},
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
//                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(Phone.DISPLAY_NAME));
                String number = cur.getString(cur.getColumnIndex(Phone.NUMBER));
//                if (("1").equals(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)))) {
//                    Cursor pCur = cr.query(
//                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
//                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
//
//                    String phoneNum = pCur.getString(pCur.getColumnIndex(
//                                ContactsContract.CommonDataKinds.Phone.NUMBER));
//                    localContact.put("id",id);
                    localContact.put("name",name);
                    localContact.put("phoneNum", number);
//                }else{
//                    localContact.put("id",id);
//                    localContact.put("name",name);
//                    localContact.put("phoneNum",null);
//                }
                localContacts.add(localContact);
            }
        }
        cur.close();
    }

}
