package com.salesianostriana.edu.MiarmaProject.error.exception;

import java.io.IOException;

public class StorageException extends RuntimeException {
    public StorageException(String mensaje, IOException ex) {
        super(mensaje,ex);
    }
    public StorageException(String mensaje){
        super(mensaje);
    }
}
