package com.example.wmpfinal;

public class Subject {
    private String name;
    private int credits;
    private boolean selected;

    // Default constructor for Firestore
    public Subject() {}

    // Constructor
    public Subject(String name, int credits, boolean selected) {
        this.name = name;
        this.credits = credits;
        this.selected = selected;
    }

    public Subject(String name, int credits) {
        this.name = name;
        this.credits = credits;
    }


    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
