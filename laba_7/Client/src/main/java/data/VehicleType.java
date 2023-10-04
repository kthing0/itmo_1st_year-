package data;


import java.io.Serializable;
import java.util.Scanner;

public enum VehicleType implements Serializable {
    DRONE,
    SUBMARINE,
    CHOPPER;



    public static VehicleType addType() {
        int c = 1;
        Scanner sc = new Scanner(System.in);
        System.out.println("Выберите тип транспорта: ");
        for (VehicleType type : VehicleType.values()) {
            System.out.println(type + "(" + c++ + ")");
        }
        String vehicleType = sc.nextLine();
        try {
            if (vehicleType.equals("1")) {
                return DRONE;
            } else if (vehicleType.equals("2")) {
                return SUBMARINE;
            } else if (vehicleType.equals("3")) {
                return CHOPPER;
            } else {
                throw new IllegalArgumentException("Введенный вид транспорта не найден, попробуйте снова");
            }
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return addType();
        }

    }

}

