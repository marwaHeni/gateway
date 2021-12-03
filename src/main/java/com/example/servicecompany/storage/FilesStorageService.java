package com.example.servicecompany.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface FilesStorageService {

    public void init() throws IOException;

    public void save(MultipartFile file);

    public Resource load(String filename);

    public Resource loadImageTiers(String filename);

    public void deleteAll();

    public Stream<Path> loadAll();

    public Stream<Path> loadAllTiers();


    public void deleteImage(String filename);
}
