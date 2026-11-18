package it.monitoraggioinsetti.monitoraggio_insetti.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.UUID;
import org.slf4j.Logger;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.web.multipart.MultipartFile;

import it.monitoraggioinsetti.monitoraggio_insetti.model.Photo;
import it.monitoraggioinsetti.monitoraggio_insetti.model.Report;
import it.monitoraggioinsetti.monitoraggio_insetti.repository.PhotoRepository;

@Service
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final Path photosDirectory;
    private static final Logger logger = LoggerFactory.getLogger(PhotoService.class);

    public PhotoService(PhotoRepository photoRepository, @Value("${app.photos.directory}") String photosDirectory) {
        this.photoRepository = photoRepository;
        this.photosDirectory = Path.of(photosDirectory).toAbsolutePath().normalize();

        createPhotosDirectory();
    }

    private void registerRollbackCleanup(Path destination) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
                if (status == STATUS_ROLLED_BACK) {
                    try {
                        Files.deleteIfExists(destination);
                    } catch (IOException e) {
                        logger.error(
                                "Impossibile eliminare il file dopo il rollback: {}", destination, e);
                    }
                }
            }
        });
    }

    private void createPhotosDirectory() {
        try {
            Files.createDirectories(this.photosDirectory);
        } catch (IOException e) {
            throw new IllegalStateException("Impossibile creare la cartella delle foto", e);
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

    private void writeFile(MultipartFile file, Path destination) {
        boolean fileCreated = false;

        try (InputStream input = file.getInputStream()) {
            OutputStream output = Files.newOutputStream(destination, StandardOpenOption.CREATE_NEW,
                    StandardOpenOption.WRITE);

            fileCreated = true;

            try (output) {
                input.transferTo(output);
            }
        } catch (IOException e) {
            if (fileCreated) {
                try {
                    Files.deleteIfExists(destination);
                } catch (IOException cleanupException) {
                    e.addSuppressed(cleanupException);
                }

            }
            throw new IllegalStateException("Impossibile salvare le foto", e);
        }

    }

    @Transactional
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

        String fileName = UUID.randomUUID() + "." + extension;
        Path destination = this.photosDirectory.resolve(fileName);

        writeFile(file, destination);
        registerRollbackCleanup(destination);

        Photo photo = new Photo(fileName, report);
        report.addPhoto(photo);
        return this.photoRepository.saveAndFlush(photo);

    }

}
