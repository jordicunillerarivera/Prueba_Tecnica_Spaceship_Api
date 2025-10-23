package org.jordicunillerarivera.spaceship.config;

public class AppConstants {
    private AppConstants() {}

    // Kafka
    public static final String TOPIC = "spaceship-events";

    // Error
    public static final String ASPECT_NEGATIVE_ID = "[Aspect] Solicitud de nave con ID negativo: {} en {}";
    public static final String VALIDATION_FAILED = "Validation failed: ";
    public static final String UNEXPECTED_ERROR = "Unexpected error: ";
    public static final String SPACESHIP_NOT_FOUND_ID = "Spaceship not found with id ";

    // Error Code
    public static final String E400 = "400";
    public static final String E404 = "404";
    public static final String E200 = "200";
    public static final String E201 = "201";
    public static final String E204 = "204";


    // App Info
    public static final String TITLE = "Spaceship API";
    public static final String DESC = "CRUD de naves espaciales de series y peliculas";
    public static final String VER = "1.0.0";
    public static final String CONTACT_NAME = "Jordi Cunillera Rivera";
    public static final String CONTACT_EMAIL = "jordicunillera1@gmail.com";

    // Spaceships Info
    public static final String GET_ALL_SPACESHIPS = "Obtiene todas las naves";
    public static final String GET_ALL_SPACESHIPS_PAGINATED = "Devuelve todas las naves paginadas";
    public static final String GET_SPACESHIP_BY_ID = "Obtiene una nave por ID";
    public static final String SPACESHIP_FOUND = "Nave encontrada";
    public static final String SPACESHIP_NOT_FOUND = "Nave no encontrada";
    public static final String SEARCH_SPACESHIPS_BY_NAME = "Buscar naves por nombre";
    public static final String CREATE_SPACESHIP = "Crear una nueva nave";
    public static final String SPACESHIP_CREATED = "Nave creada correctamente";
    public static final String INVALID_DATA = "Datos inválidos";
    public static final String UPDATE_SPACESHIP = "Actualizar una nave existente";
    public static final String SPACESHIP_UPDATED = "Nave actualizada correctamente";
    public static final String DELETE_SPACESHIP = "Eliminar una nave";
    public static final String SPACESHIP_DELETED = "Nave eliminada";

}
