# MiarmaProject
## _Proyecto 2ª DAM (Acceso a datos / Programación de servicios y procesos / Diseño de interfaces / Programación multimedia y dispositivos móviles)_

Este proyecto ha sido desarrollado por Alejandro Martin Cuevas.
Programas empleados:

- API: IntelliJ idea
- Postman
- Angular: VisualStudio code

>## Funcionalidad
  Crear una aplicación de la gestión y visualización de los datos referentes a un conjunto de inmobiliarias y sus asociacines.
  Creando peticiones (usando API REST) desde las entidades, asociaciones y aplicando cambios en la forma de mostrar su estructura a través de Dtos para luego mostrar y modificar esos datos realizando a través de angular.
  
>## Instrucciones de uso
Para ejecutar esta aplicación tras clonar el repositorio, debes ejecutar en la consola de tu IDE, "spring-boot:run", con la configuración de Maven.

Para el uso de la colección de postman debemos quitar todos los archivos en el form-data si es que encontramos alguno al exportarlo, cuando vayamos a hacer una petición debemos comprobar si esta pide un @RequestPart y si asi es ir al apartado del postman previamente mencionado y adjuntarlo con el mismo nombre que nos encontremos en el controller. El token se debe de guardar automáticamente cuando hagamos el login del usuario. 


>## Estructura de paquetes
| Paquete | URL |
| ------ | ------ |
| Controllers | [MiarmaProject/Controllers](https://github.com/alejandrosalesianos/MiarmaProject/tree/master/MiarmaProject/src/main/java/com/salesianostriana/edu/MiarmaProject/controllers) |
| Error | [MiarmaProject/error](https://github.com/alejandrosalesianos/MiarmaProject/tree/master/MiarmaProject/src/main/java/com/salesianostriana/edu/MiarmaProject/error) |
| DTOs | [MiarmaProject/DTOs](https://github.com/alejandrosalesianos/MiarmaProject/tree/master/MiarmaProject/src/main/java/com/salesianostriana/edu/MiarmaProject/model/dto) |
| Models | [MiarmaProject/Models](https://github.com/alejandrosalesianos/MiarmaProject/tree/master/MiarmaProject/src/main/java/com/salesianostriana/edu/MiarmaProject/model) |
| Repositories | [MiarmaProject/Repositories](https://github.com/alejandrosalesianos/MiarmaProject/tree/master/MiarmaProject/src/main/java/com/salesianostriana/edu/MiarmaProject/repositories)
| Services | [MiarmaProject/Services](https://github.com/alejandrosalesianos/MiarmaProject/tree/master/MiarmaProject/src/main/java/com/salesianostriana/edu/MiarmaProject/services)
| Util | [MiarmaProject/Utils](https://github.com/alejandrosalesianos/MiarmaProject/tree/master/MiarmaProject/src/main/java/com/salesianostriana/edu/MiarmaProject/utils)
| Config | [MiarmaProject/Config](https://github.com/alejandrosalesianos/MiarmaProject/tree/master/MiarmaProject/src/main/java/com/salesianostriana/edu/MiarmaProject/config)
| Validation | [MiarmaProject/Validation](https://github.com/alejandrosalesianos/MiarmaProject/tree/master/MiarmaProject/src/main/java/com/salesianostriana/edu/MiarmaProject/validation) |
| Users | [MiarmaProject/Users](https://github.com/alejandrosalesianos/MiarmaProject/tree/master/MiarmaProject/src/main/java/com/salesianostriana/edu/MiarmaProject/users) |

>## Entidades
  Contamos con 3 entidades que son:
  - UserEntity
  - PeticionFollow
  - Post

  
>## Asociaciones
## ManytoMany Unidirrecional ( UserEntity -> Follower )

#### UserEntity
```sh
@ManyToMany
    private List<UserEntity> followers = new ArrayList<>();
```
## ManyToOne bidireccional (Post -> UserEntity)
#### Post
```sh
 @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( foreignKey = @ForeignKey(name = "FK_POST_USER"))
    private UserEntity user;   
```
#### UserEntity
```sh
@OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Post> posts = new ArrayList<>();
```

## ManyToOne bidireccional (PeticionFollow -> UserEntity)
#### PeticionFollow
```sh
    @ManyToOne
    @JoinColumn(name = "emisor_id")
    private UserEntity emisor;
```
#### UserEntity
```sh
    @OneToMany(mappedBy = "emisor")
    @Builder.Default
    private List<PeticionFollow> peticiones = new ArrayList<>();
```
