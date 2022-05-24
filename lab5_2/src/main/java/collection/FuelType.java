package collection;

import utils.Printer;

import java.util.Locale;
import java.util.Scanner;

public enum FuelType {
    ELECTRICITY,
    DIESEL,
    ALCOHOL,
    NUCLEAR,
    PLASMA;


    public static FuelType addFuelType() {
        Scanner sc = new Scanner(System.in);
        Printer.print("Выберите тип транспорта: ");
        try {
            for (FuelType type : FuelType.values()) {
                Printer.print(type);
            }
            FuelType fuelType = FuelType.valueOf(sc.nextLine().toUpperCase(Locale.ROOT));

            return fuelType;
        }catch (IllegalArgumentException e){
            Printer.printErr("Данный тип топлива не найден");
            return addFuelType();
        }

    }
}
