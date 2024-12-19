package com.example.wmpfinal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button enrollButton, summaryButton, logoutButton;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       enrollButton = findViewById(R.id.enrollButton);
       summaryButton = findViewById(R.id.summaryButton);
       logoutButton = findViewById(R.id.logoutButton);

        auth = FirebaseAuth.getInstance();

        enrollButton.setOnClickListener(v -> startActivity(new Intent(this, SubjectSelectionActivity.class)));
        summaryButton.setOnClickListener(v -> startActivity(new Intent(this, SummaryActivity.class)));

        logoutButton.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
