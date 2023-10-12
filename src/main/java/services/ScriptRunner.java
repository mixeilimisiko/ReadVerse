package services;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ScriptRunner {

    public static void runScript(Connection connection, String scriptFileName) throws IOException, SQLException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                ScriptRunner.class.getResourceAsStream(scriptFileName)))) {
            String line;
            StringBuilder statementBuilder = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                // Ignore comments and empty lines
                if (!line.trim().startsWith("--") && !line.trim().isEmpty()) {
                    statementBuilder.append(line);

                    // Check if the line ends with a semicolon to indicate the end of a statement
                    if (line.trim().endsWith(";")) {
                        executeStatement(connection, statementBuilder.toString());
                        statementBuilder.setLength(0);  // Clear the statement builder
                    }
                }
            }
        }
    }

    private static void executeStatement(Connection connection, String sql) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }
}