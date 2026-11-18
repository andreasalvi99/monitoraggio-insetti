package it.monitoraggioinsetti.monitoraggio_insetti.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.monitoraggioinsetti.monitoraggio_insetti.repository.PhotoRepository;

@Service
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final Path photosDirectory;

    public PhotoService(PhotoRepository photoRepository, @Value("${app.photos.directory}") String photosDirectory) {
        this.photoRepository = photoRepository;
        this.photosDirectory = Path.of(photosDirectory).toAbsolutePath().normalize();

        createPhotosDirectory();
    }

    private void createPhotosDirectory() {
        try {
            Files.createDirectories((this.photosDirectory));
        } catch (IOException e) {
            throw new IllegalArgumentException("Impossibile cercare la cartella delle foto", e);
        }
    }
}
