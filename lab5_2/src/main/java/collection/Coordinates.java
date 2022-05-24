package collection;

import com.fasterxml.jackson.annotation.JsonProperty;
import utils.Printer;

import java.util.NoSuchElementException;
import java.util.Scanner;


public class Coordinates {

    private Double x;


    private Float y;


    private Coordinates(Double x,Float y){
        this.x = x;
        this.y = y;
    }
    public Coordinates(){}

    public Double getX(){
        return x;
    }
    public Float getY(){
        return y;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Float y) {
        this.y = y;
    }


    public static Double addX(){
        try {
            Scanner sc = new Scanner(System.in);
            Printer.print("Введите координаты по x (целое число): ");
            Double x = sc.nextDouble();
            return x;
        }
        catch (NoSuchElementException e){
            Printer.printErr("X должен быть целым числом");
            return addX();
        }
    }
    public static Float addY(){
        try {
            Scanner sc = new Scanner(System.in);
            Printer.print("Введите координаты по y (целое число): ");
            Float y = sc.nextFloat();
            if(y<-61){
                Printer.printErr("Y должен быть больше -61");
                return addY();
            }
            return y;
        }catch (NoSuchElementException e){
            Printer.printErr("Y должен быть целым числом");
            return addY();
        }
    }

    public static Coordinates addCoordinates(){
        double x = addX();
        float y = addY();
        return new Coordinates(x, y);
    }

    public String toString(){
        return "{"+
                "\"x\": " + x +
                ", \"y\": " + y +
                "}";
    }
}
