package com.salesianostriana.edu.MiarmaProject.services.impl;

import com.salesianostriana.edu.MiarmaProject.config.StorageProperties;
import com.salesianostriana.edu.MiarmaProject.exception.StorageException;
import com.salesianostriana.edu.MiarmaProject.services.StorageService;
import com.salesianostriana.edu.MiarmaProject.utils.MediaTypeUrlResource;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    public static BufferedImage imageResizer(BufferedImage bufferedImage,int anchoDeseado){
        return Scalr.resize(bufferedImage,anchoDeseado);
    }

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @PostConstruct
    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException ex){
            throw new StorageException("No se pudo inicializar la localización de almacenamiento",ex);
        }
    }

    @Override
    public String store(MultipartFile file) throws IOException {

        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        String extension = StringUtils.getFilenameExtension(filename);
        String name = filename.replace("."+extension,"");

        BufferedImage img = ImageIO.read(file.getInputStream());

        BufferedImage escaleImg = imageResizer(img,128);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(escaleImg,extension,baos);

        MultipartFile newImage = new MockMultipartFile(name,baos.toByteArray());

        try {
            //comprobar si el fichero esta vacio
            if (newImage.isEmpty())
                throw new StorageException("El fichero subido esta vacio");

            while (Files.exists(rootLocation.resolve(filename))){
                    String suffix = Long.toString(System.currentTimeMillis());
                    suffix = suffix.substring(suffix.length()-6);

                    filename = name + "_" + suffix + "." +extension;
                }
            try(InputStream inputStream = newImage.getInputStream()){
                Files.copy(inputStream,rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex){
            throw new StorageException("Error en el almacenamiento del fichero "+ filename,ex);
        }

        return filename;
    }

    @Override
    public Stream<Path> loadAll() {
        try{
            return Files.walk(this.rootLocation,1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }catch (IOException ex){
            throw new StorageException("Error al leer los ficheros almacenados",ex);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) throws FileNotFoundException {
        try {
            Path file = load(filename);
            MediaTypeUrlResource resource = new MediaTypeUrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()){
                return resource;
            }
            else {
                throw new FileNotFoundException("No se pudo leer el archivo: " +filename);
            }
        } catch (MalformedURLException ex){
            throw new FileNotFoundException("No se pudo leer el archivo: " +filename);
        }
    }

    @Override
    public void deleteFile(String filename) {
        String justFilename = StringUtils.getFilename(filename);
        try {
            Path file = load(justFilename);
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new StorageException("Error al eliminar un fichero", e);
        }

    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
