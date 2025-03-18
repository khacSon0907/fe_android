package com.example.myapplication.model;


public class ProfileOption {
    private final int iconResId;
    private final String title;
    private final String subtitle;

    public ProfileOption(int iconResId, String title, String subtitle) {
        this.iconResId = iconResId;
        this.title = title;
        this.subtitle = subtitle;
    }

    public int getIconResId() { // Đảm bảo phương thức này tồn tại
        return iconResId;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }
}
