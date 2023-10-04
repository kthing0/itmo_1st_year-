package server_utlis;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.Vehicle;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class CollectionManager implements Serializable {


    private Map<String, Vehicle> collection = Collections.synchronizedMap(new LinkedHashMap<>());

    public LocalDateTime getLocalDate() {
        return LocalDateTime.now();
    }




    public CollectionManager() {
        collection = new LinkedHashMap<>();
    }

    public Map<String, Vehicle> getCollection() {
        return collection;
    }

    DatabaseManager databaseManager = DatabaseManager.getInstance();
    public void parseJson() throws IOException {
        collection = databaseManager.loadData();
    }


    public String info() {
        return "Тип: " + collection.getClass().getGenericSuperclass().toString() +
        "\nДата инициализации: " + getLocalDate() +
        "\nКоличество элементов: " + collection.size();
    //    "\nИмя и путь к файлу: " + fileName;
    }

    public void clear() {
        synchronized (collection){
            if(databaseManager.clear()) collection.clear();
        }
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



    public boolean insert(String key, Vehicle vehicle, String login) {
        synchronized (collection) {
            try{
                if (collection.containsKey(key)) {
                    return false;
                }

                int id = databaseManager.insert(key, vehicle, login);
                if(id > -1){
                    vehicle.setId(id);
                    collection.put(key, vehicle);
                    return true;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return false;
        }
    }


    public boolean update(int id, Vehicle vehicle, String login) {
        synchronized (collection) {
            try {
                if(!databaseManager.update(id, vehicle, login)) return false;
                for (String key : collection.keySet()) {
                    if (collection.get(key).getId() == id) {
                        vehicle.setId(id);
                        collection.put(key, vehicle);
                        System.out.println("Элемент с id [" + id + "] был успешно обновлен.");
                        return true;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return false;
        }
    }

    public boolean removeKey(String key, String login){
        synchronized (collection){
            try {
                if (!databaseManager.remove_key(key, login)) return false;
                collection.remove(key);
                return true;
            } catch (Exception e){
                e.printStackTrace();
            }
            return false;
        }
    }

    public boolean removeLower(String fueltype, String login){
        synchronized (collection){
            try{
                if(!databaseManager.remove_lower(fueltype, login)){
                    return false;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return true;
        }
    }

    public boolean replaceIfGreater(String key, long power, String login){
        synchronized (collection){
            try{
                if(!databaseManager.replace_if_greater(key, power, login)){
                    return false;
                }
                if(collection.get(key).getEnginePower() <= power){
                        collection.get(key).setEnginePower(power);
                    return true;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean replaceIfLowe(String key, long power, String login){
        synchronized (collection){
            try{
                if(!databaseManager.replace_if_lowe(key, power, login)){
                    return false;
                }
                if(collection.get(key).getEnginePower() >= power){
                    collection.get(key).setEnginePower((long) power);
                    return true;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }
}
