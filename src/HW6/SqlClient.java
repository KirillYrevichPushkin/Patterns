package HW6;


import java.sql.*;

public class SqlClient {

    private static Connection connection;
    private static Statement statement;
    private CashPeople cashPeople;

    public SqlClient(CashPeople cashPeople) {
        this.cashPeople = cashPeople;
    }

    void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Patternbase.db");
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

     People findById(int id) {
        if(cashPeople.getPeopleFromCash(id)!=null){
            return cashPeople.getPeopleFromCash(id);
        }else {
        String query = String.format("select * from people where id='%s'", id);
        try (ResultSet set = statement.executeQuery(query)) {
            while (set.next()) {
                People people = new People();
                people.setId(set.getInt(1));
                people.setName(set.getString(2));
                people.setAge(set.getInt(3));
                return people;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;}

    }

    void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
