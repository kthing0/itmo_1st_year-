package collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.databind.ObjectMapper;

import utils.Printer;
import validation.Validator;

import java.io.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionManager {
    LinkedHashMap <String, Vehicle> collection = new LinkedHashMap<>();
   // private LinkedHashMap<String, Vehicle> collection;
    private String fileName;
    private LocalDateTime localDateTime;
    public int currentId = 1;
    ObjectMapper mapper = new ObjectMapper();
    public LocalDateTime getLocalDate() {
        return localDateTime.now();
    }

    public CollectionManager(String fileName) {
        this.fileName = fileName;
        localDateTime = LocalDateTime.now();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getId() {
        return currentId++;
    }

    public CollectionManager() {
        collection = new LinkedHashMap<>();
    }

    public LinkedHashMap<String, Vehicle> getCollection() {
        return collection;
    }


    public void parseJson() throws IOException {
        mapper.findAndRegisterModules();
        Printer.print("Укажите путь к файлу:");
        Scanner sc = new Scanner(System.in);
        String file = sc.nextLine();
        Validator.checkPermissions(file);
        collection = mapper.readValue(new File(file), Vehicle.class);
        Validator.validateFields((Vehicle) collection);
        for (String key : collection.keySet()) {
            collection.get(key).setId(currentId);
            collection.get(key).setCreationDate(getLocalDate());
            currentId++;
        }
        setFileName(file);
    }

    public void show() {
        if (collection.isEmpty()) {
            Printer.print("В коллекции нет элементов");
        } else {
            for (String key : collection.keySet()) {
                Printer.print("[" + key + "] \n" + collection.get(key).toHumanString() + "\n" );

            }
        }
    }

    public void info() {
        Printer.print("Тип: " + collection.getClass().getGenericSuperclass().toString());
        Printer.print("Дата инициализации: " + getLocalDate());
        Printer.print("Количество элементов: " + collection.size());
        Printer.print("Имя и путь к файлу: " + fileName);
    }

    public void clear() {
        collection.clear();
    }

    public void remove_key(String removingKey) {
        if (collection.containsKey(removingKey)) {
            collection.remove(removingKey);
            Printer.printSuccess("Элемент с ключом \"" + removingKey + "\" успешно удален из коллекции");
        } else {
            Printer.printErr("Элемент с таким ключом не найден");
        }
    }

    public void remove_lower(String element) {
        if (collection.containsKey(element)) {
            int l = 0;
            for (int i = 0; i < collection.size(); i++) {
                String key = (String) collection.keySet().toArray()[i];
                if(element.length() > key.length()){
                    collection.remove(key);
                    l++;
                }
            }
            if(l == 1 || l == 21){
                Printer.printSuccess(l + " элемент был успешно удален из коллекции");
            }if(1 < l && l < 5){
                Printer.printSuccess(l + " элемента были успешно удалены из коллекции");
            }if(l > 5){
                Printer.printSuccess(l + " элементов было успешно удалено из коллекции");
            }
        }
    }

    public void print_field_descending_type () {
        Map<String, Vehicle> sortedCollection = collection.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> -e.getValue().getId()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> { throw new AssertionError(); },
                        LinkedHashMap::new
                ));
        for(String key : sortedCollection.keySet()){
            Printer.print(sortedCollection.get(key).getId() + ")" + sortedCollection.get(key).getType());
        }
    }

    public void count_less_than_fuel_type(String fuelType){
        int c = 0;
        for (Map.Entry<String, Vehicle> entry : collection.entrySet()){
            if(entry.getValue().getFuelType().toString().length() < fuelType.length()){
                c++;
            }
        }
        Printer.print("Количество элементов, меньших, чем [" + fuelType + "]: " + c);
    }

    public void filter_starts_with_name(String name){
        int c = 0;
        for(String key : collection.keySet()){
            if(collection.get(key).getName().startsWith(name)){
                c++;
                Printer.print(collection.get(key));
            }
        }
        if(c==0){
            Printer.printSuccess("Элементов, содержащих подстроку [" + name + "] в поле \"name\", не найдено.");
        }
    }


    public void save(String fileName) {
        try {
            int c = collection.size();
            File file = new File(fileName);
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file));
            for (String key : collection.keySet()) {
                //Printer.print("{\"" + key + "\": " + collection.get(key) + ",");
                if(c==collection.size()){
                    osw.write("{\"" + key + "\": \n" + collection.get(key) + ",");
                }
                else if(c == 1){
                    osw.write("\n\"" + key + "\": \n" + collection.get(key) + "}");
                }else{
                    osw.write("\n\"" + key + "\": \n" + collection.get(key) + ",");
                }
                c--;
            }
            osw.close();
            }catch(IOException e){
            e.printStackTrace();
        }
    }

    public boolean insert(String key, Vehicle vehicle) {
        try {
            if (collection.containsKey(key)) {
                throw new Exception("Элемент с таким ключом уже существует.");
            }
            collection.put(key, vehicle);
            currentId++;
            Printer.printSuccess("Элемент успешно добавлен в коллекцию");
            return true;
        } catch (NullPointerException e) {
            Printer.printErr("Невозможно добавить элемент");

        } catch (Exception e) {
            Printer.printErr(e.getMessage());
            Printer.print("Хотите изменить ключ и добавить этот элемент в коллекцию? (y/n)");
            Scanner sc = new Scanner(System.in);
            String line = sc.nextLine();

            if (line.equals("y")) {
                Printer.print("Введите новый ключ для элемента");
                String newKey = sc.nextLine();
                collection.put(newKey, vehicle);
                Printer.printSuccess("Элемент успешно добавлен в коллекцию");
                return true;
            } else if (line.equals("n")) {
                return false;
            }
        }
        return false;
    }

    public boolean update(int id, Vehicle vehicle) {
        int c = 0;
        for (String key : collection.keySet()) {
            if (collection.get(key).getId() == id) {
                c++;
                collection.remove(key);
                vehicle.setId(id);
                collection.put(key, vehicle);
                return true;
            }
            if(c==0){
                Printer.print("ID элемента для обновления не найден. Добавить введенный элемент в коллекцию? (y/n)");
                Scanner sc = new Scanner(System.in);
                String line = sc.nextLine();

                if(line.equals("y")){
                    Printer.print("Введите новый ключ для элемента");
                    String newKey = sc.nextLine();
                    collection.put(newKey, vehicle);
                    Printer.printSuccess("Элемент успешно добавлен в коллекцию");
                    return true;
                }
                else if(line.equals("n")){
                    return false;
                }
            }
        }
        return false;
    }


    public void replace_if_greater(String key){
        try {
            Printer.print("Введите новую мощность двигателя: ");
            Scanner sc = new Scanner(System.in);
            Long power = sc.nextLong();
            if (collection.get(key).getEnginePower() <= power) {
                collection.get(key).setEnginePower(power);
                Printer.printSuccess("Значение успешно обновлено");
            } else {
                Printer.printErr("Не удалось заменить значение\nМощность двигателя у \"" + key + "\" больше на " + (collection.get(key).getEnginePower() - power));
            }
        }catch (NoSuchElementException e){
            Printer.printErr("Мощность должна быть числом");
        }catch (NullPointerException e){
            Printer.printErr("Элемент с ключом \""+key+"\" не найден");
        }
    }



    public void replace_if_lowe(String key){
        Printer.print("Введите новую мощность двигателя: ");
        Scanner sc = new Scanner(System.in);
        try {
            Long power = sc.nextLong();
            if (collection.get(key).getEnginePower() >= power) {
                collection.get(key).setEnginePower(power);
                Printer.printSuccess("Значение успешно обновлено");
            } else {
                Printer.printErr("Не удалось заменить значение\nМощность двигателя у \"" + key + "\" меньше на " + (power - collection.get(key).getEnginePower()));
            }
        }catch (NoSuchElementException e){
            Printer.printErr("Мощность должна быть числом");
        }catch (NullPointerException e){
            Printer.printErr("Элемент с ключом \""+key+"\" не найден");
        }
    }
}
