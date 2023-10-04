package data;


import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Coordinates implements Serializable {

    private static final long serialVersionUID = -4142500661902291215L;
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
            System.out.println("Введите координаты по x (целое число): ");
            Double x = sc.nextDouble();
            return x;
        }
        catch (NoSuchElementException e){
            System.out.println("X должен быть целым числом");
            return addX();
        }
    }
    public static Float addY(){
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Введите координаты по y (целое число): ");
            Float y = sc.nextFloat();
            if(y<-61){
                System.out.println("Y должен быть больше -61");
                return addY();
            }
            return y;
        }catch (NoSuchElementException e){
            System.out.println("Y должен быть целым числом");
            return addY();
        }
    }

    public static Coordinates getData(){
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
