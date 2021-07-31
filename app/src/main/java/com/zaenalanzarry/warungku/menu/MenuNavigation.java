package com.zaenalanzarry.warungku.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zaenalanzarry.warungku.R;
import com.zaenalanzarry.warungku.fragment.Data;
import com.zaenalanzarry.warungku.fragment.Home;
import com.zaenalanzarry.warungku.fragment.Laporan;
import com.zaenalanzarry.warungku.fragment.Profile;

public class MenuNavigation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_navigation);

        //Menampilkan halaman Fragment yang pertama kali muncul
        getFragmentPage(new Home());

        //inisialisasi bottomnav
        BottomNavigationView btnnav = findViewById(R.id.bottomNavigationView);

        btnnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;

                //Menantukan halaman Fragment yang akan tampil
                switch (item.getItemId()) {
                    case R.id.home:
                        fragment = new Home();
                        break;

                    case R.id.data:
                        fragment = new Data();
                        break;

                    case R.id.laporan:
                        fragment = new Laporan();
                        break;

                    case R.id.profile:
                        fragment = new Profile();
                        break;
                }
                return getFragmentPage(fragment);
            }
        });
    }

    //Menampilkan halaman Fragment
    private boolean getFragmentPage(Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.page_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}