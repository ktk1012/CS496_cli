package cs496.prj_2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cs496.prj_2.R;
/**
 * Created by q on 2016-07-04.
 */
public class Random extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.random, container, false);

        return view;
    }
}
