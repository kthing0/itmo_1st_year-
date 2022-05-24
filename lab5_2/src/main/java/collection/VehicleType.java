package collection;

import utils.Printer;

import java.util.Locale;
import java.util.Scanner;

public enum VehicleType {
    DRONE,
    SUBMARINE,
    CHOPPER;

    VehicleType() {
    }

    public static VehicleType addType() {
        int c = 1;
        Scanner sc = new Scanner(System.in);
        Printer.print("Выберите тип транспорта: ");
        for (VehicleType type : VehicleType.values()) {
            Printer.print(type + "(" + c++ + ")");
        }
        String vehicleType = sc.nextLine().toUpperCase(Locale.ROOT);
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
            Printer.printErr(e.getMessage());
            return addType();
        }

    }

}

