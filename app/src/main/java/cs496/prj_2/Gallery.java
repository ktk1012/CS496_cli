package cs496.prj_2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class Gallery extends Fragment{
    private ImageAdapter adapter;
    private Bitmap mPlaceHolderBitmap;

    @Override
    public void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        adapter = new ImageAdapter(getActivity());
        mPlaceHolderBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.waiting);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.gallery, container, false);

        GridView gridView = (GridView) view.findViewById(R.id.gridView);
        gridView.setAdapter(adapter);
//        gridView.setOnItemClickListener(new AdapterView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent in = new Intent(getActivity(), ImageActivity.class);
                in.putExtra("selected",position);

                startActivity(in);

            }
        });
        return view;
    }
    private class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c){
            mContext = c;
        }
        public Object getItem(int position){
            return null;
        }
        public long getItemId(int position){
            return 0;
        }
        public View getView(int position, View convertView, ViewGroup parent){
            ImageView imageView;

            if(convertView == null){
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(BitmapHelper.dpToPx(100), BitmapHelper.dpToPx(100)));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(2, 2, 2, 2);
            }
            else{
                imageView = (ImageView) convertView;
            }

            BitmapHelper.loadBitmap(mThumbIds[position],imageView,BitmapHelper.dpToPx(100),BitmapHelper.dpToPx(100),mPlaceHolderBitmap,getResources());

            return imageView;
        }
    }

}
