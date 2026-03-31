import java.sql.*;

public class HighScoreDB {
    private static final String URL = "jdbc:sqlite:dungeon_saves.db";

    public void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS HighScores (id INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Points INTEGER);";

        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement()) {
             statement.execute(createTableSQL);

        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }

    public void saveStats(String playerName, int finalPoints) {
        String insertSQL = "INSERT INTO HighScores(Name, Points) VALUES(?, ?)";;

        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, playerName);
            preparedStatement.setInt(2, finalPoints);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error saving run: " + e.getMessage());
        }
    }

    public void printHighScores(){
        String selectSQL = "SELECT * FROM HighScores ORDER BY Points DESC LIMIT 3";

        try(Connection connection = DriverManager.getConnection(URL);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectSQL)){

            System.out.println("-=High Scores=-");
            int i = 1;
            while(resultSet.next()){
                String name = resultSet.getString("Name");
                int points = resultSet.getInt("Points");
                System.out.println(i + ": " + name + " - " + points);
                i++;
            }
        }catch (SQLException e) {
            System.out.println("Error retrieving scores: " + e.getMessage());
        }
    }
}