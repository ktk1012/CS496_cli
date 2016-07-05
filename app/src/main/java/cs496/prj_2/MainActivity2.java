package cs496.prj_2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.strongloop.android.loopback.RestAdapter;

import cs496.prj_2.R;

/**
 * Created by q on 2016-07-04.
 */
public class MainActivity2 extends ActionBarActivity implements android.support.v7.app.ActionBar.TabListener{

    private ViewPager tabsviewPager;
    private Tabsadapter mTabsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanState) {
        super.onCreate(savedInstanState);
        setContentView(R.layout.activity_main2);
        Intent in = getIntent();
        String email = in.getStringExtra("email");
        String password = in.getStringExtra("password");

        tabsviewPager = (ViewPager) findViewById(R.id.tabspager);

        mTabsAdapter = new Tabsadapter(getSupportFragmentManager());

        tabsviewPager.setAdapter(mTabsAdapter);

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Tab Contactstab = getSupportActionBar().newTab().setText("Contacts").setTabListener(this);
        Tab Imagestab = getSupportActionBar().newTab().setText("Images").setTabListener(this);
        Tab Randomtab = getSupportActionBar().newTab().setText("Random").setTabListener(this);

        getSupportActionBar().addTab(Contactstab);
        getSupportActionBar().addTab(Imagestab);
        getSupportActionBar().addTab(Randomtab);


        //This helps in providing swiping effect for v7 compat library
        tabsviewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                getSupportActionBar().setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });

    }

    @Override
    public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTabSelected(Tab selectedtab, FragmentTransaction arg1) {
        // TODO Auto-generated method stub
        tabsviewPager.setCurrentItem(selectedtab.getPosition()); //update tab position on tap
    }

    @Override
    public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
        // TODO Auto-generated method stub

    }
}
