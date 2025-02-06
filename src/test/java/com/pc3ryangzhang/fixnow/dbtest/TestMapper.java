package com.pc3ryangzhang.fixnow.dbtest;

import com.pc3ryangzhang.fixnow.entity.User;
import com.pc3ryangzhang.fixnow.mapper.*;
import com.pc3ryangzhang.fixnow.util.GetSqlSession;
import org.apache.ibatis.session.SqlSession;
public class TestMapper {
    public static void main(String[] args) {
        SqlSession session = GetSqlSession.createSqlSession();
        assert session != null;
        UserMapper userMapper = session.getMapper(UserMapper.class);


        User user = userMapper.getUserByName("YANG");
        System.out.printf(String.valueOf(user));
    }
}
