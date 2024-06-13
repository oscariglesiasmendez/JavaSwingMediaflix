package codigo;
import javax.swing.table.DefaultTableCellRenderer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCellRenderer extends DefaultTableCellRenderer {

    private final SimpleDateFormat dateFormat;

    public DateCellRenderer(String dateFormatPattern) {
        super();
        this.dateFormat = new SimpleDateFormat(dateFormatPattern);
    }

    @Override
    protected void setValue(Object value) {
        if (value instanceof Date) {
            value = dateFormat.format(value);
        }
        super.setValue(value);
    }
}
