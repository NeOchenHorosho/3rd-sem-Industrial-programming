package hotel;

import java.io.*;
import java.util.Set;
import java.util.TreeSet;

public class HotelSet {
    private Set<Hotel> Hotels;

    public HotelSet() {
        this.Hotels = new TreeSet<>();
    }

    public void readFromFile(String filename) throws IOException {
        Hotels.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(";");
                    if (parts.length == 3) {
                        String cityName = parts[0];
                        String name = parts[1];
                        int stars = Integer.parseInt(parts[2]);
                        Hotels.add(new Hotel(cityName, name, stars));
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Ошибка парсинга строки: " + line);
                }
            }
        }
    }

    public void writeToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Hotel Hotel : Hotels) {
                writer.write(Hotel.toString());
                writer.newLine();
            }
        }
    }
    
    public void printByCity(String cityName) {
        System.out.println("Отели города" + cityName + ":");
        for(Hotel s : Hotels) {
            if(s.getCityName().equals(cityName)) 
                System.out.println(s);
        }
    }

    public void printByHotelName(String hotelName) {
        System.out.println("Отели с названием " + hotelName + ":");
        for(Hotel s : Hotels) {
            if(s.getName().equals(hotelName)) 
                System.out.println(s);
        }
    }

    public void print() {
        for(Hotel s : Hotels) {
            System.out.println(s);
        }
    }
}