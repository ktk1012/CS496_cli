package cs496.prj_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by q on 2016-07-06.
 */
public class AddressAdapter extends BaseAdapter {
    private ArrayList<Address> data;
    private LayoutInflater layoutInflater;

    public AddressAdapter(Context context, ArrayList<Address> d) {
        this.data = d;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.contacts_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Address addr_item = data.get(position);
        holder.nameView.setText(addr_item.getName());
        holder.emailView.setText(addr_item.getEmail());
        holder.numView.setText(addr_item.getPhone_num());
        if (holder.imgView != null) {
            Ion.with(holder.imgView).placeholder(R.drawable.placeholder).fitCenter().resize(100, 100)
                    .load(addr_item.getPicture());
        }
//        new ImageDownloaderTask(holder.imgView).execute(addr_item.getPicture());
        return convertView;
    }

    public void updateAddressAdapter(ArrayList<Address> newList) {
        data.clear();
        data.addAll(newList);
        this.notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView nameView;
        TextView numView;
        TextView emailView;
        ImageView imgView;

        public ViewHolder(View view) {
            nameView = (TextView) view.findViewById(R.id.name);
            numView = (TextView) view.findViewById(R.id.number);
            emailView = (TextView) view.findViewById(R.id.email);
            imgView = (ImageView) view.findViewById(R.id.img);
        }
    }


}
