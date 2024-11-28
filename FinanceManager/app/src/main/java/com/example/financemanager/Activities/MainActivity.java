package com.example.financemanager.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.financemanager.R;
import com.example.financemanager.Utils.DBConfigUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    String email;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            email = currentUser.getEmail();
            finish();
            Intent intent = new Intent(MainActivity.this, NavigationViewActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadDatabase();
        addControls();
        addEvents();
    }

    private void loadDatabase() {
        DBConfigUtil.deleteDatabase(this);
        DBConfigUtil.copyDatabaseFromAssets(this);
    }

    private void addEvents() {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        textView = findViewById(R.id.tvStart);
        mAuth = FirebaseAuth.getInstance();
        //test();
    }

    private void test(){
        Intent intent = new Intent(MainActivity.this, NavigationViewActivity.class);
        intent.putExtra("email", "abc");
        startActivity(intent);
    }

}