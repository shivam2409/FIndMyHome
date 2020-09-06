package com.example.findmyhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        Fragment fragment = null;

        fragmentTransaction.add(R.id.fragment_layout,fragment);

        fragmentTransaction.commit();

        BottomNavigationView NavMenu = findViewById(R.id.Main_Nav);

        NavMenu.setOnNavigationItemSelectedListener(bottomNavListener);






    }


    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Fragment thisfragment = null;

            switch(menuItem.getItemId())
            {
                case R.id.Home:
                {
                    //thisfragment = new HomeFragment();
                    break;
                }
                case R.id.Fav:
                {
                    //thisfragment = new FavFragment();
                    break;
                }
                case R.id.Profile:
                {
                    //thisfragment = new ProfileFragment();
                    break;
                }
            }


            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,thisfragment);



            return true;
        }
    };

}
