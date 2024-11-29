package com.example.financemanager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.financemanager.DAO.UserDAO;
import com.example.financemanager.Model.User;
import com.example.financemanager.R;
import com.example.financemanager.Utils.FragmentUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationViewActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    String email;
    User user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_navigation_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void addEvents() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    FragmentUtils.replaceFragment(getSupportFragmentManager()
                            , R.id.frame_layout
                            , new HomeFragment()
                    );
                } else if (item.getItemId() == R.id.input) {
                    FragmentUtils.replaceFragment(getSupportFragmentManager()
                            , R.id.frame_layout
                            , new InputFragment()
                    );
                }
                else if (item.getItemId() == R.id.statement) {
                    FragmentUtils.replaceFragment(getSupportFragmentManager()
                            , R.id.frame_layout
                            , new StatementFragment()
                    );
                }
                else if (item.getItemId() == R.id.notification) {
                    FragmentUtils.replaceFragment(getSupportFragmentManager()
                            , R.id.frame_layout
                            , new NotificationFragment()
                    );
                }
                else if (item.getItemId() == R.id.setting) {
                    FragmentUtils.replaceFragment(getSupportFragmentManager()
                            , R.id.frame_layout
                            , new SettingFragment()
                    );
                }
                else{
                    FragmentUtils.replaceFragment(getSupportFragmentManager()
                            , R.id.frame_layout
                            , new HomeFragment()
                    );
                }
                return true;
            }
        });
    }

    private void addControls() {

//        if (currentUser == null) {
//            Intent intent = new Intent(NavigationViewActivity.this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//        } else {
//            email = currentUser.getEmail();
//        }
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.frame_layout , new HomeFragment());

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        UserDAO userDAO = new UserDAO(this);
        user = userDAO.getUserByEmail(email);
    }

    public int getUser_Id() {
        return user.getId();
    }

    public String getEmail() {
        return email;
    }

}