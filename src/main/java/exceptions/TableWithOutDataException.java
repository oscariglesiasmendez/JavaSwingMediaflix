package exceptions;

public class TableWithOutDataException extends Exception {

    public TableWithOutDataException() {
        super();
    }

    public TableWithOutDataException(String msg) {
        super(msg);
    }
}
