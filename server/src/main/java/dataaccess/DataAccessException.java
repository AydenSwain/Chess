package dataaccess;

/**
 * Indicates there was an error connecting to the database
 */
public class DataAccessException extends RuntimeException{
    public DataAccessException(String message) {
        super(message);
    }
}
