package data;


import java.io.Serializable;
import java.util.Scanner;

public enum FuelType implements Serializable {
    ELECTRICITY,
    DIESEL,
    ALCOHOL,
    NUCLEAR,
    PLASMA;


    public static FuelType getData() {
        int c = 1;
        Scanner sc = new Scanner(System.in);
        System.out.println("Выберите тип транспорта или оставьте поле пустым: ");
        for (FuelType type : FuelType.values()) {
            System.out.println(type + "(" + c++ + ")");
        }
        // FuelType fuelType = FuelType.valueOf(sc.nextLine().toUpperCase(Locale.ROOT));
        String fuelType = sc.nextLine();
        try {
            if (fuelType.equals("1")) {
                return ELECTRICITY;
            } else if (fuelType.equals("2")) {
                return DIESEL;
            } else if (fuelType.equals("3")) {
                return ALCOHOL;
            } else if (fuelType.equals("4")) {
                return NUCLEAR;
            } else if (fuelType.equals("5")) {
                return PLASMA;
            } else if (fuelType.equals("")) {
                return null;
            } else {
                throw new IllegalArgumentException("Введенный вид транспорта не найден, попробуйте снова");
            }
        }catch (IllegalArgumentException e) {
                System.out.println("Данный тип топлива не найден");
                return getData();
            }
        }
}
