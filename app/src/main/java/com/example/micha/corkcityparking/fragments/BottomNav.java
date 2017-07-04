package com.example.micha.corkcityparking.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.micha.corkcityparking.R;
import com.example.micha.corkcityparking.models.Record;

//public class BottomNav extends AppCompatActivity  {
public class BottomNav extends AppCompatActivity  implements SettingFragment.MyDialogListener, CarparkListFrag.CarParkListDialogListener {
    private static final String TAG = BottomNav.class.getSimpleName();
    private TextView mTextMessage;
    private Toolbar toolbar;
    private Fragment fragment;
    private DialogFragment dialogFragment;
    private FragmentManager fragmentManager;
    SettingFragment editNameDialogFragment;
    ParkCarDialog parkCarDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);
/*        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackground(new ColorDrawable(getResources().getColor(R.color.grey_900)));
        toolbar.setTitle("Cork City Parking");*/

/*        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("mytest");
        actionBar.setTitle("vogella.com");*/
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);


        fragmentManager = getSupportFragmentManager();
        fragment = new HomeFrag();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new HomeFrag();
                    //toolbar.setTitle("Cork City Parking");
                    break;
                case R.id.navigation_dashboard:
                    fragment = new MapFrag();
                    //toolbar.setTitle("Map");
                    break;
                case R.id.navigation_info:
                    fragment = new InfoFrag();
                    break;
            }
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content, fragment).commit();
            return true;
        }

    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings) {
            FragmentTransaction fm =  getSupportFragmentManager().beginTransaction();
            //ProfileViewFragment editNameDialogFragment = new ProfileViewFragment(listUserAttending.get(position).getUserProfileUrl(), listUserAttending.get(position).getUserId());
            editNameDialogFragment = new SettingFragment(this,this);
            editNameDialogFragment.show(fm, "test");
        }
        if (id == R.id.parkCar){
            FragmentTransaction fm =  getSupportFragmentManager().beginTransaction();
            //ProfileViewFragment editNameDialogFragment = new ProfileViewFragment(listUserAttending.get(position).getUserProfileUrl(), listUserAttending.get(position).getUserId());
            parkCarDialog = new ParkCarDialog(this,this);
            parkCarDialog.show(fm, "test");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnCloseDialog() {
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, fragment).commit();
        if(fragment instanceof MapFrag){
            ((MapFrag) fragment).updateMarkers();
        }
    }

    @Override
    public void dialogListener(Record selectedCarPark) {
       // Toast.makeText(this, "Selected park: " + selectedCarPark.getName(), Toast.LENGTH_SHORT).show();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, fragment).commit();
        if(fragment instanceof InfoFrag){
            ((InfoFrag) fragment).updateView(selectedCarPark);
        }
        else {
            final FragmentTransaction dTransation = fragmentManager.beginTransaction();
            dTransation.replace(R.id.content, parkCarDialog).commit();
            if (parkCarDialog instanceof ParkCarDialog) {

                ((ParkCarDialog) parkCarDialog).updateView(selectedCarPark);
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                //ProfileViewFragment editNameDialogFragment = new ProfileViewFragment(listUserAttending.get(position).getUserProfileUrl(), listUserAttending.get(position).getUserId());
                parkCarDialog = new ParkCarDialog(this, this);
                //parkCarDialog.updateView(selectedCarPark);
                parkCarDialog.show(fm, "test");
                //parkCarDialog.updateView(selectedCarPark);
            }
        }
    }
}
