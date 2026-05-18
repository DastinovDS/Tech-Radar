package com.test.gitradar.dto;

public class UserProfileDTO {

    private String username;
    private String avatarUrl;
    private Long activityRate;
    private boolean trackedForLeaderboard;
    private boolean completedOnboarding;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Long getActivityRate() {
        return activityRate;
    }

    public void setActivityRate(Long activityRate) {
        this.activityRate = activityRate;
    }

    public boolean isTrackedForLeaderboard() {
        return trackedForLeaderboard;
    }

    public void setTrackedForLeaderboard(boolean trackedForLeaderboard) {
        this.trackedForLeaderboard = trackedForLeaderboard;
    }

    public boolean isCompletedOnboarding() {
        return completedOnboarding;
    }

    public void setCompletedOnboarding(boolean completedOnboarding) {
        this.completedOnboarding = completedOnboarding;
    }

    public UserProfileDTO() {
    }
}
