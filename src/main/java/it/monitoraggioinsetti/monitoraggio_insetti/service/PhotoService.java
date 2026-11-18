package it.monitoraggioinsetti.monitoraggio_insetti.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.monitoraggioinsetti.monitoraggio_insetti.model.Photo;
import it.monitoraggioinsetti.monitoraggio_insetti.model.Report;
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
            Files.createDirectories(this.photosDirectory);
        } catch (IOException e) {
            throw new IllegalStateException("Impossibile cercare la cartella delle foto", e);
        }
    }

    private String validateImage(MultipartFile file) {
        try (InputStream input = file.getInputStream();
                ImageInputStream imageInput = ImageIO.createImageInputStream(input)) {

            if (imageInput == null)
                throw new IllegalArgumentException("Impossibile leggere l'immagine");

            Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInput);

            if (!readers.hasNext())
                throw new IllegalArgumentException("Il file non è un'immagine riconosciuta");

            ImageReader reader = readers.next();

            try {
                reader.setInput(imageInput);

                String format = reader.getFormatName();

                if (!"JPEG".equalsIgnoreCase(format) && !"PNG".equalsIgnoreCase(format))
                    throw new IllegalArgumentException("Sono ammesse solo immagini JPEG e PNG");

                int width = reader.getWidth(0);
                int height = reader.getHeight(0);

                if (width <= 0 || height <= 0 || (long) width * height > 20_000_000)
                    throw new IllegalArgumentException("L'immagine supera il limite di 20 megapixel");

                reader.read(0);

                return "JPEG".equalsIgnoreCase(format) ? "jpg" : "png";

            } finally {
                reader.dispose();
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Immagine danneggiata o illegibile", e);
        }

    }

    public Photo save(MultipartFile file, Report report) {
        if (file == null || file.isEmpty())
            throw new IllegalArgumentException("La foto è obbligatoria");
        if (report == null)
            throw new IllegalArgumentException("La segnalazione è obbligatoria");
        if (file.getSize() > 5L * 1024 * 1024)
            throw new IllegalArgumentException("La foto non può superare 5miB");

        String contentType = file.getContentType();

        if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType))
            throw new IllegalArgumentException("Sono ammesse solo immagini JPEG e PNG");

        String extension = validateImage(file);

    }

}
