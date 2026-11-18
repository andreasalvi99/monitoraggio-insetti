package it.monitoraggioinsetti.monitoraggio_insetti.config;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final Path photosDirectory;

    public WebConfig(@Value("${app.photos.directory}") String photosDirectory) {
        this.photosDirectory = Path.of(photosDirectory).toAbsolutePath().normalize();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String location = this.photosDirectory.toUri().toString();

        if (!location.endsWith("/")) {
            location += "/";
        }

        registry.addResourceHandler("/media/photos/**").addResourceLocations(location);
    }

}
