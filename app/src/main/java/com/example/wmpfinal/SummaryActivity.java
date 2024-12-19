package com.example.wmpfinal;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class SummaryActivity extends AppCompatActivity {

    private TextView selectedSubjectsTextView;
    private TextView totalCreditsTextView;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        selectedSubjectsTextView = findViewById(R.id.selectedSubjectsTextView);
        totalCreditsTextView = findViewById(R.id.totalCreditsTextView);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Fetch data from Firestore
        String userId = auth.getCurrentUser().getUid();
        db.collection("Enrollment").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<String> selectedSubjects = (List<String>) documentSnapshot.get("selectedSubjects");
                        List<Long> subjectCredits = (List<Long>) documentSnapshot.get("selectedCredits");
                        Long totalCredits = documentSnapshot.getLong("totalCredits");

                        // Ensure both lists are non-null and have matching sizes
                        if (selectedSubjects != null && subjectCredits != null && selectedSubjects.size() == subjectCredits.size()) {
                            StringBuilder subjectsSummary = new StringBuilder("Selected Subjects:\n");

                            for (int i = 0; i < selectedSubjects.size(); i++) {
                                String subjectName = selectedSubjects.get(i);
                                Long credit = subjectCredits.get(i);
                                subjectsSummary.append(subjectName).append(" - ").append(credit).append(" Credits\n");
                            }

                            selectedSubjectsTextView.setText(subjectsSummary.toString());
                        } else if (selectedSubjects == null || subjectCredits == null) {
                            selectedSubjectsTextView.setText("No subjects selected.");
                        }

                        // Display total credits
                        if (totalCredits != null) {
                            totalCreditsTextView.setText("Total Credits: " + totalCredits);
                        } else {
                            totalCreditsTextView.setText("Total Credits: 0");
                        }
                    } else {
                        Toast.makeText(SummaryActivity.this, "No enrollment data found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(SummaryActivity.this, "Failed to fetch enrollment data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }
}
