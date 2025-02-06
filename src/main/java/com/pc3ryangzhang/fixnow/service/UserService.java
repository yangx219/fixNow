package com.pc3ryangzhang.fixnow.service;

import com.pc3ryangzhang.fixnow.entity.User;
import com.pc3ryangzhang.fixnow.entity.ValueObject.MessageModel;
import com.pc3ryangzhang.fixnow.mapper.UserMapper;
import com.pc3ryangzhang.fixnow.util.GetSqlSession;
import com.pc3ryangzhang.fixnow.util.StringUtil;
import org.apache.ibatis.session.SqlSession;

public class UserService {
    public MessageModel userConnection(String email, String pwd) {
        MessageModel messageModel = new MessageModel();
        System.out.println(email);
        System.out.println(pwd);


        User u = new User();
        u.setEmail(email);
        u.setPassword(pwd);
        messageModel.setObject(u);
        //1. check if the empty of email and password
        if(StringUtil.isEmpty(email)||StringUtil.isEmpty(pwd)) {
            messageModel.setCode(0);
            messageModel.setMsg("email ou password peut pas être vide!");
            return messageModel;
        }

        //2. find Data from the DataBase by email
        SqlSession session = GetSqlSession.createSqlSession();
        assert session != null;
        UserMapper userMapper = session.getMapper(UserMapper.class);
        User user = userMapper.getUserByEmail(email);

        //3. verify the user exist
        if(user == null) {
            messageModel.setCode(0);
            messageModel.setMsg("L’utilisateur n’existe pas!");
            System.out.println("user == null");
            return messageModel;
        }

        //4. compare the input password with the password of database
        if(!pwd.equals(user.getPassword())) {
            messageModel.setCode(0);
            messageModel.setMsg("Mot de passe incorrect!") ;
            System.out.println("pwd incorrect");
            return messageModel;
        }

        //5. success
        messageModel.setCode(1);
        messageModel.setObject(user);
        System.out.println("user connection ok");
        return messageModel;

    }

    public MessageModel userInscription(User user) {
        MessageModel messageModel = new MessageModel();

        // Check for null or empty fields in the user object (simple validation)
        if (StringUtil.isEmpty(user.getEmail()) || StringUtil.isEmpty(user.getPassword())
                || StringUtil.isEmpty(user.getUserName()) || StringUtil.isEmpty(user.getUserType())) {
            messageModel.setCode(0);
            messageModel.setMsg("Tous les champs doivent être remplis!");
            System.out.println("user == null");
            return messageModel;
        }

        SqlSession session = null;
        try {
            session = GetSqlSession.createSqlSession();
            UserMapper userMapper = session.getMapper(UserMapper.class);

            // Check if the user already exists
            User existingUser = userMapper.getUserByEmail(user.getEmail());
            if (existingUser != null) {
                System.out.println("user == existing user");
                messageModel.setCode(0);
                messageModel.setMsg("Un utilisateur avec cet email existe déjà!");
                return messageModel;
            }

            // Insert the new user
            int result = userMapper.insertUser(user);
            if (result == 0) {
                messageModel.setCode(0);
                messageModel.setMsg("Inscription échouée, veuillez réessayer!");
                return messageModel;
            }

            session.commit();
            messageModel.setCode(1);
            messageModel.setObject(user);
            messageModel.setMsg("Inscription réussie!");
        } catch (Exception e) {
            if (session != null) {
                session.rollback();
            }
            messageModel.setCode(0);
            messageModel.setMsg("Erreur du serveur, veuillez réessayer!");
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return messageModel;
    }


    public boolean updateUserAvatar(int userId, String avatarPath) {
        SqlSession session = null;
        try {
            session = GetSqlSession.createSqlSession();
            UserMapper userMapper = session.getMapper(UserMapper.class);

            User userToUpdate = new User();
            userToUpdate.setUserId(userId); // 设置用户ID以指定要更新头像的用户
            userToUpdate.setAvatar(avatarPath); // 设置新头像路径

            int result = userMapper.updateUserAvatar(userToUpdate);
            session.commit(); // 提交更新

            return result > 0;
        } catch (Exception e) {
            if (session != null) {
                session.rollback(); // 发生异常时回滚事务
            }
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


    // 添加一个方法来获取用户的头像
    public String getUserAvatar(int userId) {
        SqlSession session = null;
        try {
            session = GetSqlSession.createSqlSession();
            UserMapper userMapper = session.getMapper(UserMapper.class);
            User user = userMapper.getAvatar(userId);

            return user != null ? user.getAvatar() : null; // 返回头像路径，如果用户不存在则返回null
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }


}
