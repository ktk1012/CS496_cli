package cs496.prj_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by q on 2016-07-07.
 */
public class ChannelAdapter extends BaseAdapter {
    private ArrayList<Channel> data;
    private LayoutInflater layoutInflater;

    public ChannelAdapter(Context c, ArrayList<Channel> d) {
        this.data = d;
        this.layoutInflater = LayoutInflater.from(c);
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
            convertView = layoutInflater.inflate(R.layout.channel_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Channel channel_item = data.get(position);
        holder.nameView.setText(channel_item.getName());

        return convertView;
    }

    static class ViewHolder {
        TextView nameView;

        public ViewHolder(View v) {
            nameView = (TextView) v.findViewById(R.id.channelname);
        }
    }

    public void updateChannelAdapter(ArrayList<Channel> newList) {
        data.clear();
        data.addAll(newList);
        this.notifyDataSetChanged();
    }
}
