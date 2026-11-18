package it.monitoraggioinsetti.monitoraggio_insetti.service;

import org.springframework.stereotype.Service;

import it.monitoraggioinsetti.monitoraggio_insetti.repository.PhotoRepository;

@Service
public class PhotoService {
    private final PhotoRepository photoRepository;

    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }
}
