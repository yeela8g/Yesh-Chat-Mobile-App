package com.example.myapplication.models;

public class UserWithoutPass {
    private String username;
    private String displayName;
    private String profilePic;

    public UserWithoutPass(String username, String displayName, String profilePic) {
        this.username = username;
        this.displayName = displayName;
        this.profilePic = profilePic;
    }

    // Getters and setters for the properties

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    @Override
    public String toString() {
        return "UserWithoutPass{" +
                "username='" + username + '\'' +
                ", displayName='" + displayName + '\'' +
                ", profilePic='" + profilePic + '\'' +
                '}';
    }
}
