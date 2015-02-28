package com.apollo.util;

import java.sql.DriverManager;

/**
 * Created by mrugen on 2/28/15.
 */
public class DBUtil {

    Connection connection;
    static final String JDBC_DRIVER = "com.mysql.jdbc.driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/YAHOO_HAC";
    static final username ="";
    static final password ="";

    public static getConnection()
    {
          Class.forName(JDBC_DRIVER);
          connection = DriverManager.getConnection(DB_URL,username, password);
         return connection;
    }


}
