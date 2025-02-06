package com.pc3ryangzhang.fixnow.mapper;

import com.pc3ryangzhang.fixnow.entity.Profile;
import java.util.List;

public interface ProfileMapper {
    Profile getProfileById(Integer profileId);
    Profile getProfileByUserId(Integer userId);
    int insertProfile(Profile profile);
    int updateProfile(Profile profile);
    int deleteProfile(Integer profileId);
    List<Profile> getAllProfiles();
}
