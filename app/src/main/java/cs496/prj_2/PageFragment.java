package cs496.prj_2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.koushikdutta.ion.Ion;

/**
 * Created by q on 2016-07-07.
 */
public class PageFragment extends Fragment{
    private String url;

    public static PageFragment create(Image img) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putString("url", img.getUrl());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString("url");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.single_image, container, false);
        ImageView imgview = (ImageView) rootView.findViewById(R.id.image);
        Ion.with(getContext()).load(url).withBitmap().resize(1000, 1000)
                .fitCenter().intoImageView(imgview);

        return rootView;
    }
}
