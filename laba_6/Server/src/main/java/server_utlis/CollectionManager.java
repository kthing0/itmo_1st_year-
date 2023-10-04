package server_utlis;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.Vehicle;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CollectionManager implements Serializable {


    private LinkedHashMap <String, Vehicle> collection = new LinkedHashMap<>();
    private String fileName;
    private LocalDateTime localDateTime;
    public int currentId = 1;
    private ObjectMapper mapper = new ObjectMapper();
    public LocalDateTime getLocalDate() {
        return LocalDateTime.now();
    }

    public CollectionManager(String fileName) {
        this.fileName = fileName;
        localDateTime = LocalDateTime.now();
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFileName() {
        return fileName;
    }


    public CollectionManager() {
        collection = new LinkedHashMap<>();
    }

    public LinkedHashMap<String, Vehicle> getCollection() {
        return collection;
    }


    public void parseJson() throws IOException {
        mapper.findAndRegisterModules();
        System.out.println("Укажите путь к файлу:");
        Scanner sc = new Scanner(System.in);
        String file = sc.nextLine();
        collection = mapper.readValue(new File(file), Vehicle.class);
        for (String key : collection.keySet()) {
            collection.get(key).setId(currentId);
            collection.get(key).setCreationDate(getLocalDate());
            currentId++;
        }
        setFileName(file);
    }

    public int getId() {
        return currentId++;
    }
    public void setId(int id){
        currentId = id;
    }

    public String info() {
        return "Тип: " + collection.getClass().getGenericSuperclass().toString() +
        "\nДата инициализации: " + getLocalDate() +
        "\nКоличество элементов: " + collection.size() +
        "\nИмя и путь к файлу: " + fileName;
    }

    public void clear() {
        collection.clear();
    }


    public String print_field_descending_type () {
        Map<String, Vehicle> sortedCollection = collection.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> -e.getValue().getId()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> { throw new AssertionError(); },
                        LinkedHashMap::new
                ));
        StringBuilder res = new StringBuilder();
        for(String key : sortedCollection.keySet()){
            res.append(sortedCollection.get(key).getId()).append(")").append(sortedCollection.get(key).getType()).append("\n");
        }
        return String.valueOf(res);
    }

    public String count_less_than_fuel_type(String fuelType) {
        long count = collection.values()
                .stream()
                .filter(vehicle -> vehicle.getFuelType().toString().length() < fuelType.length())
                .count();

        return "Количество элементов, меньших, чем [" + fuelType + "]: " + count;
    }

    public void save(String fileName) {
        try {
            File file = new File(fileName);

            String jsonContent = collection.entrySet()
                    .stream()
                    .map(entry -> "\"" + entry.getKey() + "\": " + entry.getValue())
                    .collect(Collectors.joining(",\n", "{\n", "\n}"));

            try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file))) {
                osw.write(jsonContent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean insert(String key, Vehicle vehicle) {
        if (collection.containsKey(key)) {
            System.out.println("Элемент с таким ключом уже существует.");
            return false;
        }

        collection.put(key, vehicle);
        System.out.println("Элемент успешно добавлен в коллекцию");
        return true;
    }

    public boolean update(int id, Vehicle vehicle) {
        for (String key : collection.keySet()) {
            if (collection.get(key).getId() == id) {
                vehicle.setId(id);
                collection.put(key, vehicle);
                currentId--;
                System.out.println("Элемент с id [" + id + "] был успешно обновлен.");
                return true;
            }
        }
        return false;
    }

}
