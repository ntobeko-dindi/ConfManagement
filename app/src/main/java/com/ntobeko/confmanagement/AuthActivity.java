package com.ntobeko.confmanagement;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.ntobeko.confmanagement.Enums.UserRoles;
import com.ntobeko.confmanagement.data.FireBaseHelper;
import com.ntobeko.confmanagement.databinding.ActivityAuthBinding;
import com.ntobeko.confmanagement.models.Utilities;

public class AuthActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.ntobeko.confmanagement.databinding.ActivityAuthBinding binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarAuth.toolbar);
        binding.appBarAuth.fab.setOnClickListener(view -> {
            new FireBaseHelper().logout(this,getApplicationContext(), new MainActivity());
            Utilities.clearCurrentUserRoleFromSharedPreferences(getApplicationContext());
        });

        String currentUserRole = Utilities.getCurrentUserRoleFromSharedPreferences(getApplicationContext());

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_gallery, R.id.nav_home, R.id.nav_slideshow, R.id.nav_addconf, R.id.nav_authnews, R.id.nav_conferences, R.id.nav_approved, R.id.nav_rejected)
                    .setOpenableLayout(drawer)
                    .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_auth);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Menu menu =navigationView.getMenu();

        if(currentUserRole.equalsIgnoreCase(UserRoles.attendee.name())){
            MenuItem createConf = menu.findItem(R.id.nav_addconf);
            MenuItem approve = menu.findItem(R.id.nav_home);
            MenuItem postNews = menu.findItem(R.id.nav_slideshow);

            createConf.setVisible(false);
            approve.setTitle("Pending Registrations");
            postNews.setVisible(false);
        }else{
            MenuItem register = menu.findItem(R.id.nav_gallery);
            register.setVisible(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.auth, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_auth);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}