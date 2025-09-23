// 代码生成时间: 2025-09-23 19:17:40
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DatabaseMigrationTool {

    // Main application method
    public static void main(String[] args) {
        SpringApplication.run(DatabaseMigrationTool.class, args);
    }

    // Bean for DataSource configuration
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/targetDatabase");
        dataSource.setUsername("root");
        dataSource.setPassword("password");
        return dataSource;
    }

    // CommandLineRunner to execute migration on application start
    @Bean
    CommandLineRunner runMigration(DataSource dataSource) {
        return args -> {
            try (Connection connection = dataSource.getConnection();
                 Statement statement = connection.createStatement()) {

                ResultSet tables = statement.executeQuery("SHOW TABLES");
                List<String> tableNames = new ArrayList<>();
                while (tables.next()) {
                    tableNames.add(tables.getString(1));
                }

                // Assuming another dataSource bean for source database is available
                DataSource sourceDataSource = sourceDataSource();
                try (Connection sourceConnection = sourceDataSource.getConnection();
                     Statement sourceStatement = sourceConnection.createStatement();
                     ResultSet sourceTables = sourceStatement.executeQuery("SHOW TABLES")) {

                    while (sourceTables.next()) {
                        String sourceTableName = sourceTables.getString(1);
                        if (!tableNames.contains(sourceTableName)) {
                            // Assuming a method to copy data from source to target
                            copyTableData(sourceDataSource, dataSource, sourceTableName);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };
    }

    // Method to copy table data from source to target database
    private void copyTableData(DataSource sourceDataSource, DataSource targetDataSource, String tableName) throws SQLException {
        // Example of copying data using SQL queries
        try (Connection sourceConnection = sourceDataSource.getConnection();
             Connection targetConnection = targetDataSource.getConnection();
             Statement sourceStatement = sourceConnection.createStatement();
             Statement targetStatement = targetConnection.createStatement()) {

            String sourceSelectQuery = "SELECT * FROM " + tableName;
            String targetInsertQuery = "INSERT INTO " + tableName + " VALUES (?)";

            try (ResultSet resultSet = sourceStatement.executeQuery(sourceSelectQuery)) {
                DatabaseMetaData metadata = resultSet.getMetaData();
                int columnCount = metadata.getColumnCount();

                while (resultSet.next()) {
                    String insertQuery = "INSERT INTO " + tableName + " (";
                    for (int i = 1; i <= columnCount; i++) {
                        insertQuery += metadata.getColumnName(i);
                        if (i < columnCount) insertQuery += ", ";
                    }
                    insertQuery += ") VALUES (";
                    for (int i = 1; i <= columnCount; i++) {
                        insertQuery += "?";
                        if (i < columnCount) insertQuery += ", ";
                    }
                    insertQuery += ")";

                    targetStatement.executeUpdate(insertQuery);
                }
            }
        }
    }

    // Bean for source DataSource configuration
    @Bean
    public DataSource sourceDataSource() {
        DriverManagerDataSource sourceDataSource = new DriverManagerDataSource();
        sourceDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        sourceDataSource.setUrl("jdbc:mysql://localhost:3306/sourceDatabase");
        sourceDataSource.setUsername("root");
        sourceDataSource.setPassword("password");
        return sourceDataSource;
    }
}
