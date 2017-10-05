package smitek.scheduled;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbServer {

  public static void main(String[] args) {


    Server retVal = null;

    try {
      final String userDir = System.getProperty("user.dir");
      // System.setProperty("h2.baseDir", userDir + "/data/jumpstart");

      retVal = Server.createTcpServer("-baseDir", userDir + "/data/jumpstart");
      retVal.start();

      Connection conn = null;
      try {
        Class.forName("org.h2.Driver");
        conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/jumpstart", "sa", "sa");
      } finally {
        if (conn != null)
          conn.close();
      }
    } catch (final Exception ex) {

    }

  }
}

