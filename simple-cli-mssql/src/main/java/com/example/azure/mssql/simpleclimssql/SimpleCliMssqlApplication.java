package com.example.azure.mssql.simpleclimssql;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.StringUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimpleCliMssqlApplication implements CommandLineRunner {

    @Value("${app.serverName:your-sql-server.database.windows.net}")
    private String serverName;

    @Value("${app.databaseName:demo}")
    private String databaseName;

    @Value("${app.authentication:ActiveDirectoryMSI}")
    private String authentication;

    @Value("${app.msiClientId}")
    private String msiClientId;

    public static void main(String[] args) {
        SpringApplication.run(SimpleCliMssqlApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Inside SimpleCliMssqlApplication run");
		System.out.println("serverName=>"+serverName);
		System.out.println("databaseName=>"+databaseName);
		System.out.println("authentication=>"+authentication);
		System.out.println("msiClientId=>"+msiClientId);
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setServerName(serverName); // Replace with your server name
        ds.setDatabaseName(databaseName); // Replace with your database name
        ds.setAuthentication(authentication);
        // Optional
        if (StringUtils.isEmpty(msiClientId)) {
            ds.setMSIClientId(msiClientId); // Replace with Client ID of User-Assigned Managed Identity to be used
        }

        try (Connection connection = ds.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT SUSER_SNAME()")) {
            if (rs.next()) {
                System.out.println("You have successfully logged on as: " + rs.getString(1));
            }
        }
    }
}
