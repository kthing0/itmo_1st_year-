package data;


import com.fasterxml.jackson.annotation.JsonProperty;
import utils.Client;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Vehicle extends LinkedHashMap<String, Vehicle>  implements Serializable {


    private static final long serialVersionUID = -5362517610926932290L;

    private int id;
    private String name;


    private Coordinates coordinates;

    private LocalDateTime creationDate;


    private Long enginePower;

    private VehicleType type;

    private FuelType fuelType;
    private Double x;
    private Float y;

    private Vehicle(
            int id,
            String name,
            Coordinates coordinates,
            Long enginePower,
            VehicleType type,
            FuelType fuelType)
    {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now();
        this.enginePower = enginePower;
        this.type = type;
        this.fuelType = fuelType;
    }

    public Vehicle(int id){
        this.id = id;

    }
    public Vehicle(String name){
        this.name = name;
    }
    public Vehicle(Double x, Float y){
        this.x = x;
        this.y = y;

    }

    public Vehicle(){}

    

    public static Vehicle getData(){
        int id = Client.getId();
        String name = addName();
        Coordinates coordinates = Coordinates.getData();
        Long enginePower = addEnginePower();
        VehicleType vehicleType = VehicleType.addType();
        FuelType fuelType = FuelType.getData();

        return new Vehicle(id, name, coordinates, enginePower, vehicleType, fuelType);
    }
    


    public static String addName() {
        try {
            System.out.println("Введите название: ");
            Scanner sc = new Scanner(System.in);
            String name = sc.nextLine();
            if (name.equals("")) {
                throw new Exception("Имя не может быть пустым");
            }
            return name;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return addName();
    }

    public static Long addEnginePower(){
        try {
            System.out.println("Введите мощность двигателя (целое число):");
            Scanner sc = new Scanner(System.in);
            Long enginePower = sc.nextLong();
            if(enginePower < 0){
                throw new IllegalArgumentException();
            }
            return enginePower;
        }catch (IllegalArgumentException e){
            System.out.println("Мощность не может быть отрицателньой");
        }catch (InputMismatchException e){
            System.out.println("Мощность должна быть целым числом");
        }
        return addEnginePower();
    }




    public void setId(int id) {
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Long getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(Long enginePower) {
        this.enginePower = enginePower;
    }


    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String toString(){
        return "{"+
                "\"id\": " + id +
                ", \"name\": \"" + name + "\"" +
                ", \"coordinates\": " + coordinates +
                ", \"creationDate\": \"" + creationDate +
                "\", \"enginePower\": " + enginePower +
                ", \"type\": \"" + type + "\"" +
                ", \"fuelType\": \"" + fuelType + "\"" +
                "}";
    }
    public String toHumanString(){
        return "ID: " + id +
                "\nИмя: " + name +
                "\nКоординаты: " + coordinates +
                "\nДата создания: " + creationDate +
                "\nМощность двигателя: " + enginePower +
                "\nТип транспорта: " + type +
                "\nТип топлива: " + fuelType;
    }
}
