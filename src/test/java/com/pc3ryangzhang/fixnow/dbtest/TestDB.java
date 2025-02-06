package com.pc3ryangzhang.fixnow.dbtest;

import com.pc3ryangzhang.fixnow.util.DBUtil;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class TestDB {
    @Test
    public void testDB() throws SQLException {
        System.out.println(DBUtil.getConnection());
    }
}
