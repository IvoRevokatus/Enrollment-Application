package com.example.wmpfinal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubjectSelectionActivity extends AppCompatActivity {
    private RecyclerView subjectRecyclerView;
    private Button saveButton;

    private List<Subject> subjectList = new ArrayList<>();
    private SubjectAdapter adapter = new SubjectAdapter(subjectList);

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private static final int MAX_CREDITS = 24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_selection);

        subjectRecyclerView = findViewById(R.id.subjectsRecyclerView);
        saveButton = findViewById(R.id.saveSelectionButton);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Populate the subject list
        subjectList.add(new Subject("Deep Learning", 4, false));
        subjectList.add(new Subject("Network Security", 4, false));
        subjectList.add(new Subject("Ethical Hacking", 4, false));
        subjectList.add(new Subject("Numerical Method", 4, false));
        subjectList.add(new Subject("3D Modelling", 4, false));
        subjectList.add(new Subject("Game Development", 4, false));
        subjectList.add(new Subject("Software Engineering", 4, false));
        subjectList.add(new Subject("Art", 2, false));

        fetchSelectedSubjects(); // Load data from Firestore

        saveButton.setOnClickListener(v -> saveEnrollment());
    }

    private void fetchSelectedSubjects() {
        String userId = auth.getCurrentUser().getUid();
        db.collection("Enrollment").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<String> selectedSubjects = (List<String>) documentSnapshot.get("selectedSubjects");

                        if (selectedSubjects != null) {
                            for (Subject subject : subjectList) {
                                if (selectedSubjects.contains(subject.getName())) {
                                    subject.setSelected(true); // Set checkbox to checked
                                }
                            }
                        }
                    }
                    // Initialize RecyclerView adapter after fetching data
                    adapter = new SubjectAdapter(subjectList);
                    subjectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                    subjectRecyclerView.setAdapter(adapter);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to load subjects: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void saveEnrollment() {
        List<String> selectedSubjects = new ArrayList<>();
        List<Integer> selectedCredits = new ArrayList<>();
        int totalCredits = 0;

        for (Subject subject : subjectList) {
            if (subject.isSelected()) {
                totalCredits += subject.getCredits();
                selectedSubjects.add(subject.getName());
                selectedCredits.add(subject.getCredits());
            }
        }

        if (totalCredits > MAX_CREDITS) {
            Toast.makeText(this, "Cannot exceed " + MAX_CREDITS + " credits", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = auth.getCurrentUser().getUid();
        Map<String, Object> enrollment = new HashMap<>();
        enrollment.put("selectedSubjects", selectedSubjects);
        enrollment.put("selectedCredits", selectedCredits);
        enrollment.put("totalCredits", totalCredits);

        db.collection("Enrollment").document(userId).set(enrollment)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Enrollment saved", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to save enrollment", Toast.LENGTH_SHORT).show());
    }
}
