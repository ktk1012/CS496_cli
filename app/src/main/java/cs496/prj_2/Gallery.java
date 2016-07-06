package cs496.prj_2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.strongloop.android.loopback.AccessToken;
import com.strongloop.android.loopback.Container;
import com.strongloop.android.loopback.ContainerRepository;
import com.strongloop.android.loopback.File;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.ListCallback;
import com.strongloop.android.loopback.callbacks.ObjectCallback;

import java.util.ArrayList;
import java.util.List;

public class Gallery extends Fragment{
    private ImageAdapter adapter;
    private Bitmap mPlaceHolderBitmap;
    private RestAdapter mRestAdapter;
    private ImageRepository mImageRepo;
    private ArrayList<Image> images = new ArrayList<Image>();

    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        mRestAdapter = new RestAdapter(getContext(), "http://52.78.69.111:3000/api");
        super.onCreate(savedInstanceBundle);
        mPlaceHolderBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.waiting);
        mRestAdapter = new RestAdapter(getContext(), "http://52.78.69.111:3000/api");
        mImageRepo = mRestAdapter.createRepository(ImageRepository.class);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.gallery, container, false);

        GridView gridView = (GridView) view.findViewById(R.id.gridView);

        adapter = new ImageAdapter(getContext());
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent in = new Intent(getActivity(), ImageActivity.class);
                Image img = images.get(position);
                in.putExtra("selected", img.getUrl());

                startActivity(in);

            }
        });
        String owner = com.facebook.AccessToken.getCurrentAccessToken().getUserId();
        mImageRepo.get(owner, new ListCallback<Image>() {
            @Override
            public void onSuccess(List<Image> objects) {
                Log.d("FILESUCCESS", String.valueOf(objects.size()));
                images = (ArrayList<Image>) objects;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable t) {

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
            return images.get(position);
        }
        public int getCount(){return images.size();}
        public long getItemId(int position){
            return position;
        }
        public View getView(int position, View convertView, ViewGroup parent){
            final ImageView imageView;

            if(convertView == null){
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(BitmapHelper.dpToPx(100), BitmapHelper.dpToPx(100)));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(2, 2, 2, 2);
            }
            else{
                imageView = (ImageView) convertView;
            }

            Image img = images.get(position);
            Ion.with(imageView).placeholder(R.drawable.placeholder).fitCenter().resize(100, 100)
                    .load(img.getUrl());
//            BitmapHelper.loadBitmap(mThumbIds[position],imageView,BitmapHelper.dpToPx(100),BitmapHelper.dpToPx(100),mPlaceHolderBitmap,getResources());

            return imageView;
        }

//        public void updateImageAdapter(ArrayList<Image> newList) {
//
//        }
    }

}
