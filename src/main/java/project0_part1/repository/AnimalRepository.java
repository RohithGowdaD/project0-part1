package project0_part1.repository;

import project0_part1.model.AnimalData;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class AnimalRepository {

    private static final String DATABASE_NAME = "db.txt";
    private static final String NEWLINE = System.lineSeparator();

    private static void appendToFile(Path path, String content) throws IOException {
        Files.write(
                path,
                content.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
    }

    public boolean add(AnimalData animalData) throws IOException {

        Path path = Paths.get(DATABASE_NAME);

        String data = animalData.getName() + "," +
                animalData.getPicture() + "," +
                animalData.getLocation();

        appendToFile(path, data + NEWLINE);

        return true;
    }
    public List<AnimalData> findAll() throws IOException {

        List<AnimalData> result = new ArrayList<>();

        Path path = Paths.get(DATABASE_NAME);

        if (!Files.exists(path)) {
            return result;
        }

        List<String> lines = Files.readAllLines(path);

        for (String line : lines) {

            String[] parts = line.split(",");

            AnimalData animal = new AnimalData();
            animal.setName(parts[0]);
            animal.setPicture(parts[1]);
            animal.setLocation(parts[2]);

            result.add(animal);
        }

        return result;
    }
    public List<AnimalData> find(String name, String picture, String location) throws IOException {

        List<AnimalData> allAnimals = findAll();
        List<AnimalData> filtered = new ArrayList<>();

        for (AnimalData animal : allAnimals) {

            boolean matches = true;

            if (name != null && !name.isBlank()) {
                if (!animal.getName().equals(name)) {
                    matches = false;
                }
            }

            if (picture != null && !picture.isBlank()) {
                if (!animal.getPicture().equals(picture)) {
                    matches = false;
                }
            }

            if (location != null && !location.isBlank()) {
                if (!animal.getLocation().equals(location)) {
                    matches = false;
                }
            }

            if (matches) {
                filtered.add(animal);
            }
        }

        return filtered;
    }
}

