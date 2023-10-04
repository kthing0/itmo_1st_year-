package server_utlis;
import data.Coordinates;
import data.FuelType;
import data.Vehicle;
import data.VehicleType;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class DatabaseManager {
    Connection connection;
    private static DatabaseManager instance;


    private DatabaseManager() {
        try {
            Scanner auth = new Scanner(new FileReader("db_auth.txt"));
            String username = auth.nextLine().trim();
            String password = auth.nextLine().trim();
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/studs", username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS \"user_n\" (" +
                            "user_id SERIAL PRIMARY KEY," +
                            "login VARCHAR(20) NOT NULL," +
                            "password VARCHAR(200) NOT NULL" +
                            ");" +
                            "CREATE TABLE IF NOT EXISTS vehicles (" +
                            "key VARCHAR(20) NOT NULL UNIQUE," +
                            "vehicle_id SERIAL PRIMARY KEY," +
                            "name VARCHAR(20) NOT NULL," +
                            "coordinates_x DOUBLE PRECISION NOT NULL," +
                            "coordinates_y FLOAT NOT NULL," +
                            "creation_date TIMESTAMP NOT NULL," +
                            "engine_power BIGINT NOT NULL," +
                            "vehicle_type VARCHAR(20)," +
                            "fuel_type VARCHAR(11)," +
                            "owner_id INTEGER REFERENCES \"user_n\"(user_id) ON DELETE SET NULL" +
                            ");"
            );
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Произошла ошибка при инициализации БД");
            System.exit(777);
        } catch (FileNotFoundException e) {
            System.out.println("Не удалось найти файл аутентификации");
            System.exit(1);
        }
    }

    public static DatabaseManager getInstance(){
        if(instance == null) instance = new DatabaseManager();
        return instance;
    }

    public LinkedHashMap<String, Vehicle> loadData(){
        LinkedHashMap<String, Vehicle> collection = new LinkedHashMap<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM vehicle");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Vehicle vehicle = new Vehicle(
                            resultSet.getInt("vehicle_id"),
                            resultSet.getString("name"),
                            new Coordinates(resultSet.getDouble("coordinates_x"), resultSet.getFloat("coordinates_y")),
                            resultSet.getLong("engine_power"),
                            resultSet.getString("vehicle_type") == null ? null : VehicleType.valueOf(resultSet.getString("vehicle_type")),
                            resultSet.getString("fuel_type") == null ? null : FuelType.valueOf(resultSet.getString("fuel_type")
                        ));
                collection.put(resultSet.getString("key"), vehicle);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return collection;
    }

    public int insert(String key, Vehicle vehicle, String login){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO vehicles(key, name, coordinates_x, coordinates_y, "+
                            "creation_date, engine_power, vehicle_type, fuel_type, " +
                            "owner_id)" +
                            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, (SELECT user_id FROM \"user_n\" WHERE login = ?)) " +
                            "RETURNING vehicle_id");
            preparedStatement.setString(1, key);
            preparedStatement.setString(2, vehicle.getName());
            preparedStatement.setDouble(3, vehicle.getCoordinates().getX());
            preparedStatement.setFloat(4, vehicle.getCoordinates().getY());
            preparedStatement.setDate(5, Date.valueOf(vehicle.getCreationDate()));
            preparedStatement.setLong(6, vehicle.getEnginePower());
            if(vehicle.getType() != null) {
                preparedStatement.setString(7, vehicle.getType().name());
            }else
                preparedStatement.setNull(7, java.sql.Types.NULL);
            if(vehicle.getFuelType() != null){
                preparedStatement.setString(8, vehicle.getFuelType().name());
            }else
                preparedStatement.setNull(8, java.sql.Types.NULL);
            preparedStatement.setString(9, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("vehicle_id");
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public boolean clear() {
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("DELETE FROM vehicles");
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean remove_key(String key, String login) {
        try {
            if (!checkRights(key, login)) {
                return false;
            }
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM vehicles WHERE key = ?");
            preparedStatement.setString(1, key);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean replace_if_greater(String key, long power, String login){
        if(!checkRights(key, login)){
            return false;
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE vehicles SET engine_power = ? WHERE key = ? AND engine_power < ?");
            preparedStatement.setLong(1, power);
            preparedStatement.setString(2, key);
            preparedStatement.setLong(3, power);
            return preparedStatement.executeUpdate() > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean replace_if_lowe(String key, long power, String login){
        if(!checkRights(key, login)){
            return false;
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE vehicles SET engine_power = ? WHERE key = ? AND engine_power > ?");
            preparedStatement.setLong(1, power);
            preparedStatement.setString(2, key);
            preparedStatement.setLong(3, power);
            return preparedStatement.executeUpdate() > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean remove_lower(String fueltype, String login) {
        int fuel_len = fueltype.length();
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("DELETE FROM vehicles WHERE LENGTH(fuel_type) < ? AND owner_id IN" +
                    "(SELECT user_id FROM \"user_n\" WHERE login = ?)");
            prepareStatement.setInt(1, fuel_len);
            prepareStatement.setString(2, login);
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean update(int id, Vehicle vehicle, String login){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE vehicles SET name = ?, coordinates_x = ?, coordinates_y = ?," +
                            "creation_date = ?, engine_power = ?, vehicle_type = ?, fuel_type = ?" +
                            "WHERE vehicle_id = ? AND owner_id IN (SELECT user_id FROM \"user_n\" WHERE login = ?)"
            );
            preparedStatement.setString(1, vehicle.getName());
            preparedStatement.setDouble(2, vehicle.getCoordinates().getX());
            preparedStatement.setFloat(3, vehicle.getCoordinates().getY());
            preparedStatement.setDate(4, Date.valueOf(vehicle.getCreationDate()));
            preparedStatement.setLong(5, vehicle.getEnginePower());
            if (vehicle.getType() != null) {
                preparedStatement.setString(6, vehicle.getType().name());
            } else {
                preparedStatement.setNull(6, java.sql.Types.NULL);
            }
            if (vehicle.getFuelType() != null) {
                preparedStatement.setString(7, vehicle.getFuelType().name());
            } else{
                preparedStatement.setNull(7, java.sql.Types.NULL);
            }
            preparedStatement.setInt(8, id);
            preparedStatement.setString(9, login);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkRights(String key, String login) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM vehicles WHERE key = ? AND owner_id IN " +
                    "(SELECT user_id FROM \"user_n\" WHERE login = ?)");
            preparedStatement.setString(1, key);
            preparedStatement.setString(2, login);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean register(String login, String password){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM \"user_n\" WHERE login = ?");
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) return false;

            preparedStatement = connection.prepareStatement("INSERT INTO \"user_n\" (login, password) VALUES (?, ?)");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }


    public boolean existsUser(String login, String password) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM \"user_n\" WHERE login = ? AND password = ?");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

}
