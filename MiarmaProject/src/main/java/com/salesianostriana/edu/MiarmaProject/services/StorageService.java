package com.salesianostriana.edu.MiarmaProject.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    String store(MultipartFile file) throws IOException;

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename) throws FileNotFoundException;

    void deleteFile(String filename);

    void deleteAll();
}
