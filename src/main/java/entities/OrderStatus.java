package entities;

public enum OrderStatus {
    PENDING, COMPLETED, CANCELED;

    @Override
    public String toString() {
        switch (this) {
            case PENDING:
                return "Pendiente de pago";
            case COMPLETED:
                return "Completado";
            case CANCELED:
                return "Cancelado";
            default:
                break;
        }
        return null;
    }

    public static OrderStatus toEnum(String tipo) {
        switch (tipo) {
            case "Pendiente de pago":
                return PENDING;
            case "Completado":
                return COMPLETED;
            case "Cancelado":
                return CANCELED;
            default:
                break;
        }
        return null;
    }
}
