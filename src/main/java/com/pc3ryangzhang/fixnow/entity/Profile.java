package com.pc3ryangzhang.fixnow.entity;

public class Profile {
    private Integer profileId;
    private Integer userId;
    private String location;
    private String skills;
    private Integer experienceYears;
    private String bio;
    private String contactInfo;
    private String userName;  // 新增 userName 属性

    // Getters and Setters
    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void updateFrom(Profile other) {
        this.location = other.location;
        this.skills = other.skills;
        this.experienceYears = other.experienceYears;
        this.bio = other.bio;
        this.contactInfo = other.contactInfo;
        this.userName = other.userName;
    }
}
