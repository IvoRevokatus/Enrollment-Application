package com.example.wmpfinal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    private List<Subject> subjectList;

    public SubjectAdapter(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    @Override
    public SubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubjectViewHolder holder, int position) {
        Subject subject = subjectList.get(position);

        holder.subjectNameTextView.setText(subject.getName());
        holder.creditsTextView.setText("Credits: " + subject.getCredits());

        // Set the checkbox status based on the subject's selected state
        holder.subjectCheckBox.setChecked(subject.isSelected());

        holder.subjectCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> subject.setSelected(isChecked));
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public static class SubjectViewHolder extends RecyclerView.ViewHolder {
        TextView subjectNameTextView, creditsTextView;
        CheckBox subjectCheckBox;

        public SubjectViewHolder(View itemView) {
            super(itemView);
            subjectNameTextView = itemView.findViewById(R.id.subjectNameTextView);
            creditsTextView = itemView.findViewById(R.id.creditsTextView);
            subjectCheckBox = itemView.findViewById(R.id.subjectCheckBox);
        }
    }
}
