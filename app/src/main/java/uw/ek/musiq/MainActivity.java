package uw.ek.musiq;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uw.ek.musiq.fragments.DietFragment;
import uw.ek.musiq.fragments.MusicFragment;
import uw.ek.musiq.fragments.WorkoutFragment;

import static uw.ek.musiq.AdminActivity.MY_PREFS_NAME;


public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.workouticon);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.workouticon);
        tabLayout.getTabAt(1).setIcon(R.drawable.musicicon);
        tabLayout.getTabAt(2).setIcon(R.drawable.dieticon);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new WorkoutFragment(), "Workout");
        adapter.addFragment(new MusicFragment(), "Music");
        adapter.addFragment(new DietFragment(), "Diet");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.download:
                startActivity(new Intent(this, Webview.class));
                break;
            case R.id.admin:
                adminDialog(this);


        }
        return super.onOptionsItemSelected(item);

    }

private void adminDialog(final Context c){
    final EditText taskEditText = new EditText(c);
    taskEditText.setHint("password");
    taskEditText.setPadding(5,5,5,5);
    AlertDialog dialog = new AlertDialog.Builder(c)
            .setTitle("Admin")
            .setMessage("Please enter password")
            .setView(taskEditText)
            .setPositiveButton("Go", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String input = String.valueOf(taskEditText.getText());
                    String restoredText = prefs.getString("password", null);

                    if (input.equals("workoutpassword")){
                        startActivity(new Intent(c, AdminActivity.class));
                    }else if (restoredText != null) {
                        String pass = prefs.getString("password", "No pass defined");
                        if (input.equals(pass)){
                            startActivity(new Intent(c, AdminActivity.class));

                        }

                    }



                }
            })
            .setNegativeButton("Cancel", null)
            .create();
    dialog.show();


    }


}
