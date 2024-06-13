package utils;

import codigo.AllOrdersTableModel;
import codigo.BookTableModel;
import codigo.ClientTableModel;
import codigo.EmployeeTableModel;
import codigo.GameTableModel;
import codigo.MovieTableModel;
import codigo.ProductTableModel;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import entities.Order;
import entities.OrderDetail;
import entities.Product;
import entities.ProductType;
import exceptions.TableWithOutDataException;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import service.ProductApiCall;
import vista.ModeloAplicado;
import static vista.ModeloAplicado.CLIENTE;

public class PdfUtilities {

    private ProductApiCall productApiCall = new ProductApiCall();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy         HH:mm:ss");
    private ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/logoMediaflix.png"));

    public void createPdfHeader(Document document, String title, String entityTitle, String selectedFilter) throws MalformedURLException, IOException {

        float col = 280f;
        float columnWidth[] = {col / 2, col, col};

        Table table = new Table(columnWidth);
        table.setWidth(UnitValue.createPercentValue(100));
        table.setBackgroundColor(new DeviceRgb(61, 27, 99));
        table.setHorizontalAlignment(HorizontalAlignment.RIGHT);

        Image logo = new Image(ImageDataFactory.create(imageIcon.getImage(), null));

        logo.scaleToFit(125, 125);
        logo.setHorizontalAlignment(HorizontalAlignment.CENTER);

        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(logo));

        table.addCell(new Cell().setVerticalAlignment(VerticalAlignment.MIDDLE).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER).add(new Paragraph("Informe " + title).setFontColor(new DeviceRgb(Color.WHITE)).setFontSize(18f).setHorizontalAlignment(HorizontalAlignment.RIGHT)));

        if (entityTitle.isBlank() || entityTitle.isEmpty()) {
            table.addCell(new Cell().setVerticalAlignment(VerticalAlignment.MIDDLE).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER).add(new Paragraph("Filtro: " + selectedFilter).setFontColor(new DeviceRgb(Color.WHITE)).setFontSize(18f).setHorizontalAlignment(HorizontalAlignment.RIGHT)));
        } else {
            table.addCell(new Cell().setVerticalAlignment(VerticalAlignment.MIDDLE).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER).add(new Paragraph("Nombre: " + "\"" + entityTitle + "\"").setFontColor(new DeviceRgb(Color.WHITE)).setFontSize(18f).setHorizontalAlignment(HorizontalAlignment.RIGHT)).add(new Paragraph("Filtro: " + selectedFilter).setFontColor(new DeviceRgb(Color.WHITE)).setFontSize(18f).setHorizontalAlignment(HorizontalAlignment.RIGHT)));
        }

        document.add(table);

        // Salto de línea
        document.add(new Paragraph("\n"));
    }

    public void createPdfTable(Document document, JTable jTable, ModeloAplicado modeloTabla) throws TableWithOutDataException {
        // Verificar que la tabla tenga al menos una columna
        if (jTable.getColumnCount() == 0) {
            throw new TableWithOutDataException();
        }

        int[] columnasAMostrar;
        Table table = null;

        // Agregar filas de datos
        switch (modeloTabla) {
            case CLIENTE:
                ClientTableModel clientModel = (ClientTableModel) jTable.getModel();

                //Elijo que columnas del modelo quiero mostrar, ya que si muestro todo ocuparía demasiado, y campos como descripción son irrelevantes en el informe
                columnasAMostrar = new int[]{0, 1, 2, 3, 4};

                // Crear la tabla en el PDF
                table = new Table(columnasAMostrar.length);

                // Agregar encabezados de columna
                for (int columna : columnasAMostrar) {
                    table.addCell(jTable.getColumnName(columna));
                }

                addDataFromTableModel(clientModel, table, columnasAMostrar);
                break;
            case EMPLEADO:
                EmployeeTableModel employeeModel = (EmployeeTableModel) jTable.getModel();

                //Elijo que columnas del modelo quiero mostrar, ya que si muestro todo ocuparía demasiado, y campos como descripción son irrelevantes en el informe
                columnasAMostrar = new int[]{0, 1, 2, 3, 4, 5};

                // Crear la tabla en el PDF
                table = new Table(columnasAMostrar.length);

                // Agregar encabezados de columna
                for (int columna : columnasAMostrar) {
                    table.addCell(jTable.getColumnName(columna));
                }

                addDataFromTableModel(employeeModel, table, columnasAMostrar);
                break;
            case PRODUCTO:
                ProductTableModel productModel = (ProductTableModel) jTable.getModel();

                columnasAMostrar = new int[]{0, 1, 3, 4, 5, 6, 7, 9, 10};

                table = new Table(columnasAMostrar.length);

                for (int columna : columnasAMostrar) {
                    table.addCell(jTable.getColumnName(columna));
                }

                addDataFromTableModel(productModel, table, columnasAMostrar);
                break;
            case LIBRO:
                BookTableModel bookModel = (BookTableModel) jTable.getModel();

                columnasAMostrar = new int[]{0, 1, 3, 6, 9, 10, 11, 12, 13};

                table = new Table(columnasAMostrar.length);

                for (int columna : columnasAMostrar) {
                    table.addCell(jTable.getColumnName(columna));
                }

                addDataFromTableModel(bookModel, table, columnasAMostrar);
                break;
            case PELICULA:
                MovieTableModel movieModel = (MovieTableModel) jTable.getModel();

                columnasAMostrar = new int[]{0, 1, 3, 6, 9, 10, 11, 12, 13};

                table = new Table(columnasAMostrar.length);

                for (int columna : columnasAMostrar) {
                    table.addCell(jTable.getColumnName(columna));
                }

                addDataFromTableModel(movieModel, table, columnasAMostrar);
                break;
            case JUEGO:
                GameTableModel gameModel = (GameTableModel) jTable.getModel();

                columnasAMostrar = new int[]{0, 1, 3, 6, 9, 10, 11, 12, 13};

                table = new Table(columnasAMostrar.length);

                for (int columna : columnasAMostrar) {
                    table.addCell(jTable.getColumnName(columna));
                }

                addDataFromTableModel(gameModel, table, columnasAMostrar);
                break;
            case VENTAS:
                AllOrdersTableModel orderModel = (AllOrdersTableModel) jTable.getModel();

                columnasAMostrar = new int[]{0, 1, 2, 3, 4, 5};

                table = new Table(columnasAMostrar.length);

                for (int columna : columnasAMostrar) {
                    table.addCell(jTable.getColumnName(columna));
                }

                addDataFromTableModel(orderModel, table, columnasAMostrar);
                break;
            case NINGUNO:
                return;
            default:
                return;
        }
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        document.add(table);

    }

    // Función que añade datos al modelo, adaptada para mostrar solo las columnas especificadas
    private void addDataFromTableModel(TableModel model, Table table, int[] columnasAMostrar) {
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int columna : columnasAMostrar) {
                Object value = model.getValueAt(i, columna);
                if (value != null) {
                    table.addCell(value.toString());
                } else {
                    table.addCell("");
                }
            }
        }
    }

    public void generateTicket(Document document, Order order) throws FileNotFoundException, MalformedURLException, IOException {
        document.setMargins(10, 10, 10, 10);
        // Crear una tabla con una sola columna
        Table table = new Table(UnitValue.createPercentArray(new float[]{100}));
        table.setWidth(UnitValue.createPercentValue(100));
        table.setVerticalAlignment(VerticalAlignment.TOP);

        // Cargar el logo de la empresa
        Image logo = new Image(ImageDataFactory.create(imageIcon.getImage(), null));

        logo.scaleToFit(75, 75);
        logo.setHorizontalAlignment(HorizontalAlignment.CENTER);

        // Agregar la celda con el logo
        Cell logoCell = new Cell().setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.TOP).setBorder(Border.NO_BORDER);
        logoCell.add(logo);
        table.addCell(logoCell);

        // Agregar las celdas con la información de la empresa
        table.addCell(createCell("Mediaflix S.A."));
        table.addCell(createCell("Rúa Sinforiano López, 43"));
        table.addCell(createCell("CIF: B12345678"));
        table.addCell(createCell("\n"));

        document.add(table);

        // Crear la tabla de productos
        Table dataTable = new Table(UnitValue.createPercentArray(new float[]{50, 50}));
        //dataTable.setTextAlignment(TextAlignment.LEFT);
        //dataTable.setHorizontalAlignment(HorizontalAlignment.LEFT);
        dataTable.setWidth(UnitValue.createPercentValue(100));
        dataTable.addCell(createCell("Nº ticket: " + order.getOrderId()).setTextAlignment(TextAlignment.LEFT));

        Date now = new Date();

        dataTable.addCell(createCell(sdf.format(now)));

        document.add(dataTable);

        document.add(new Paragraph("\n"));

        // Crear la tabla de productos
        Table productsTable = new Table(UnitValue.createPercentArray(new float[]{20, 40, 20, 20}));
        productsTable.setWidth(UnitValue.createPercentValue(100));

        productsTable.addCell(createCell("Uds.").setBold());

        productsTable.addCell(createCell("Producto").setBold().setMaxWidth(80f));//Con esto corto el nombre si es mas largo)

        productsTable.addCell(createCell("Precio unit.").setBold());
        productsTable.addCell(createCell("Total").setBold());

        System.out.println(order.getDetails());

        double orderTotal = 0;

        double bookTotalPrice = 0;
        double otherProductsTotalPrice = 0;

        for (OrderDetail o : order.getDetails()) {

            Product p = productApiCall.getById(o.getProductId());

            int quantity = o.getQuantity();
            double unitPrice = p.getPrice();
            double productTotalPrice = quantity * unitPrice;

            orderTotal += productTotalPrice;

            if (p.getProductType().equals(ProductType.BOOK)) {
                bookTotalPrice += productTotalPrice;
            } else {
                otherProductsTotalPrice += productTotalPrice;
            }

            productsTable.addCell(createCell(String.valueOf(quantity)));
            productsTable.addCell(createCell(acortarNombreProducto(p.getTitle())));
            productsTable.addCell(createCell(String.format("%.2f", unitPrice)));
            productsTable.addCell(createCell(String.format("%.2f", productTotalPrice)));

        }

        //Espacio vacío para dejar una fila vacía
        productsTable.addCell(createCell("\n"));
        productsTable.addCell(createCell("\n"));
        productsTable.addCell(createCell("\n"));
        productsTable.addCell(createCell("\n"));

        // Aplicar borde solo alrededor de la tabla de productos
        productsTable.setBorder(Border.NO_BORDER);
        productsTable.setBorderTop(new SolidBorder(1));
        productsTable.setBorderBottom(new SolidBorder(1));
        productsTable.setBorderLeft(new SolidBorder(1));
        productsTable.setBorderRight(new SolidBorder(1));

        document.add(productsTable);

        //Tabla para añadir los tipos de IVA
        Table ivaTable = new Table(UnitValue.createPercentArray(new float[]{25, 25, 25, 25}));
        ivaTable.setWidth(UnitValue.createPercentValue(100));
        ivaTable.addCell(createCell("Tipo").setBold());
        ivaTable.addCell(createCell("Base").setBold());
        ivaTable.addCell(createCell("Cuota").setBold());
        ivaTable.addCell(createCell("Total").setBold());

        ivaTable.addCell(createCell("4%"));
        double base = calcularBase(bookTotalPrice, ProductType.BOOK);
        double cuota = calcularCuota(bookTotalPrice, ProductType.BOOK);
        double total = base + cuota;
        ivaTable.addCell(createCell(String.format("%.2f", base)));
        ivaTable.addCell(createCell(String.format("%.2f", cuota)));
        ivaTable.addCell(createCell(String.format("%.2f", total)));

        ivaTable.addCell(createCell("21%"));
        base = calcularBase(otherProductsTotalPrice, ProductType.GAME);
        cuota = calcularCuota(otherProductsTotalPrice, ProductType.GAME);
        total = base + cuota;
        ivaTable.addCell(createCell(String.format("%.2f", base)));
        ivaTable.addCell(createCell(String.format("%.2f", cuota)));
        ivaTable.addCell(createCell(String.format("%.2f", total)));

        ivaTable.setBorder(Border.NO_BORDER);
        ivaTable.setBorderBottom(new SolidBorder(1));
        ivaTable.setBorderLeft(new SolidBorder(1));
        ivaTable.setBorderRight(new SolidBorder(1));

        document.add(ivaTable);

        Table totalTable = new Table(UnitValue.createPercentArray(new float[]{60, 40}));
        totalTable.setWidth(UnitValue.createPercentValue(100));

        totalTable.setBorder(Border.NO_BORDER);
        totalTable.setBorderBottom(new SolidBorder(1));
        totalTable.setBorderLeft(new SolidBorder(1));
        totalTable.setBorderRight(new SolidBorder(1));

        totalTable.addCell(new Cell().add(new Paragraph("Total (Impuestos Incl.)").setBold().setFontSize(10f)).setVerticalAlignment(VerticalAlignment.MIDDLE));
        totalTable.addCell(new Cell().add(new Paragraph(String.format("%.2f", orderTotal) + " €").setBold().setFontSize(12f)).setTextAlignment(TextAlignment.RIGHT));

        document.add(totalTable);

        // Cerrar el documento
        document.close();
    }

    public static double calcularBase(double precio, ProductType type) {
        return precio / (1 + (type.equals(ProductType.BOOK) ? 0.04 : 0.21));
    }

    public static double calcularCuota(double precio, ProductType type) {
        return precio - calcularBase(precio, type);
    }

    //Si el nombre del producto es demasiado largo, lo corto para que asi en el ticket se ajuste a la perfección
    public static String acortarNombreProducto(String title) {
        int maxLength = 20;

        if (title.length() > maxLength) {
            return title.substring(0, maxLength - 3) + "...";
        } else {
            return title;
        }
    }

    // Método para crear una celda con un párrafo de texto
    private static Cell createCell(String text) {
        Paragraph paragraph = new Paragraph(text);
        paragraph.setHorizontalAlignment(HorizontalAlignment.CENTER);
        paragraph.setVerticalAlignment(VerticalAlignment.TOP);
        paragraph.setFontSize(8f);
        return new Cell().setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.TOP).setBorder(Border.NO_BORDER).add(paragraph);
    }

}
