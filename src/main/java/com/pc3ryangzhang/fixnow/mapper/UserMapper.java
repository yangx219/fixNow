package com.pc3ryangzhang.fixnow.mapper;

import com.pc3ryangzhang.fixnow.entity.User;


public interface UserMapper {
    public User getUserById(Integer UserId);
    public User getUserByName(String userName);

    User getUserByEmail(String email);

    int insertUser(User user);

    int updateUserAvatar(User user);

    // 添加一个新方法来获取用户头像
    User getAvatar(int userId);

}
