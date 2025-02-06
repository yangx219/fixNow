package com.pc3ryangzhang.fixnow.util;

import java.io.IOException;
import java.io.InputStream;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;



public class GetSqlSession {
    private static SqlSessionFactory sqlSessionFactory = buildSessionFactory();

    private static SqlSessionFactory buildSessionFactory() {
        try (InputStream input = Resources.getResourceAsStream("mybatis-config.xml")) {
            return new SqlSessionFactoryBuilder().build(input);
        } catch (IOException e) {

            throw new ExceptionInInitializerError("init SqlSessionFactory failed：" + e.getMessage());
        }
    }

    public static SqlSession createSqlSession() {
        return sqlSessionFactory.openSession();
    }

    public static void main(String[] args) {
        try (SqlSession session = createSqlSession()) {
            System.out.println(session);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
