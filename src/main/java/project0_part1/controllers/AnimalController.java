package project0_part1.controllers;

import project0_part1.model.AnimalData;
import project0_part1.repository.AnimalRepository;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/animals")
public class AnimalController {

    private final AnimalRepository animalRepository;

    public AnimalController(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @PostMapping
    public boolean add(@RequestBody AnimalData data) {
        try {
            return animalRepository.add(data);
        } catch (IOException e) {
            return false;
        }
    }
    @GetMapping
    public List<AnimalData> getAll() {
        try {
            return animalRepository.findAll();
        } catch (IOException e) {
            return null;
        }
    }

    @GetMapping("/search")
    public List<AnimalData> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String picture,
            @RequestParam(required = false) String location) {

        try {
            return animalRepository.find(name,picture, location);
        } catch (IOException e) {
            return null;
        }
    }
}

