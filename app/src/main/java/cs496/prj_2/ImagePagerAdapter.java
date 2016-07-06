package cs496.prj_2;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by q on 2016-07-06.
 */
public class ImagePagerAdapter extends PagerAdapter {
    private LayoutInflater mInflator;
    private ArrayList<Image> mImages;

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
