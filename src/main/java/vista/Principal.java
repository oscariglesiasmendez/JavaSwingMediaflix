package vista;

import codigo.AllOrdersTableModel;
import codigo.BookTableModel;
import codigo.ClientTableModel;
import entities.Employee;
import codigo.EmployeeTableModel;
import codigo.GameTableModel;
import codigo.MovieTableModel;
import codigo.OrderDetailTableModel;
import codigo.OrderTableModel;
import codigo.ProductStockModel;
import entities.Product;
import codigo.ProductTableModel;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import entities.Book;
import entities.Client;
import entities.Game;
import entities.Movie;
import entities.Order;
import entities.OrderDetail;
import entities.OrderStatus;
import static entities.ProductType.BOOK;
import static entities.ProductType.GAME;
import static entities.ProductType.MOVIE;
import exceptions.TableWithOutDataException;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import service.BookApiCall;
import service.ClientApiCall;
import service.EmployeeApiCall;
import service.GameApiCall;
import service.MovieApiCall;
import service.OrderApiCall;
import service.ProductApiCall;
import utils.EmailConfig;
import utils.PdfUtilities;
import static vista.ModeloAplicado.LIBRO;
import static vista.ModeloAplicado.PELICULA;

public class Principal extends javax.swing.JDialog {

    DefaultComboBoxModel<String> modeloCombo;

    ProductTableModel productModel;
    public ProductApiCall productApiCall;
    List<Product> products;
    List<Product> productsByTitle;

    BookTableModel bookModel;
    public BookApiCall bookApiCall;
    List<Book> books;
    List<Book> booksByTitle;

    MovieTableModel movieModel;
    public MovieApiCall movieApiCall;
    List<Movie> movies;
    List<Movie> moviesByTitle;

    GameTableModel gameModel;
    public GameApiCall gameApiCall;
    List<Game> games;
    List<Game> gamesByTitle;

    ModeloAplicado tableModel;

    ClientTableModel clientModel;
    public ClientApiCall clientApiCall;
    List<Client> clients;
    List<Client> clientsByName;

    EmployeeTableModel employeeModel;
    public EmployeeApiCall employeeApiCall;
    List<Employee> employees;
    List<Employee> employeesByName;

    OrderDetailTableModel orderDetailModel;
    OrderApiCall orderApiCall;

    //Empleado que tiene la sesion iniciada
    Employee currentEmployee;

    List<OrderDetail> orderDetails;

    OrderTableModel orderModel;

    AllOrdersTableModel allOrdersModel;
    List<Order> allOrders;
    List<Order> ordersByEmail;

    ProductStockModel productNoStockModel;
    ProductStockModel productLowStockModel;

    DefaultComboBoxModel<String> modeloComboStock;

    PdfUtilities pdfUtilities;

    //Constructor para el administrador
    public Principal(Login login, boolean modal, boolean isAdmin) {
        super(login, modal);
        initComponents();

        inicializarDialogAdministrador();

    }

    //Constructor para los empleados
    public Principal(Login login, boolean modal, Employee e) {
        super(login, modal);
        initComponents();

        this.currentEmployee = e;

        inicializarDialogEmpleado();
        lblNombreEmpleado.setText("Empleado:  " + e.getFirstName());

    }

    private void inicializarDialogEmpleado() {
        // Establece el tamaño del dialogo para que ocupe toda la pantalla
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = ge.getMaximumWindowBounds();
        this.setSize(bounds.getSize());

        pnlTabla.setSize(bounds.getSize());
        pnlBotonesProductos.setSize(bounds.getSize());
        pnlPrincipal.setSize(bounds.getSize());

        pnlPrincipal.setVisible(true);

        btnEmpleados.setVisible(false);
        lblEmpleados.setVisible(false);

        modeloCombo = new DefaultComboBoxModel<>();

        cmbFiltroTabla.setModel(modeloCombo);

        productApiCall = new ProductApiCall();
        productModel = new ProductTableModel();
        products = new ArrayList<>();
        productsByTitle = new ArrayList<>();

        bookApiCall = new BookApiCall();
        books = new ArrayList<>();
        booksByTitle = new ArrayList<>();
        bookModel = new BookTableModel();

        movieApiCall = new MovieApiCall();
        movies = new ArrayList<>();
        moviesByTitle = new ArrayList<>();
        movieModel = new MovieTableModel();

        gameApiCall = new GameApiCall();
        games = new ArrayList<>();
        gamesByTitle = new ArrayList<>();
        gameModel = new GameTableModel();

        clientApiCall = new ClientApiCall();
        clients = new ArrayList<>();
        clientsByName = new ArrayList<>();
        clientModel = new ClientTableModel();

        employeeApiCall = new EmployeeApiCall();
        employees = new ArrayList<>();
        employeesByName = new ArrayList<>();
        employeeModel = new EmployeeTableModel();

        orderApiCall = new OrderApiCall();
        orderDetailModel = new OrderDetailTableModel();

        tblOrderDetail.setModel(orderDetailModel);
        orderDetails = new ArrayList<>();

        orderModel = new OrderTableModel();

        allOrdersModel = new AllOrdersTableModel();
        allOrders = new ArrayList<>();
        ordersByEmail = new ArrayList<>();

        tblUltimasVentas.setModel(orderModel);

        productNoStockModel = new ProductStockModel();
        productLowStockModel = new ProductStockModel();

        modeloComboStock = new DefaultComboBoxModel<>();

        lblProductosPocoStock.setText("Últimos clientes dados de alta");
        lblProductosSinStock.setText("Últimas ventas");

        pnlArticulosVenta.setVisible(true);
        tblOrderDetail.setModel(orderDetailModel);
        pnlTablaDetalleVenta.setVisible(false);
        txtTitulo.setText("");

        tblProductosStock.setModel(clientModel);

        clientModel.removeClients();
        clientModel.addClients(clientApiCall.getLatest20Clients());

        cmbFiltroStock.setVisible(false);

        pnlTablaDetalleVenta.setVisible(false);

        tblTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblTable.getTableHeader().setOpaque(false);
        tblTable.getTableHeader().setBackground(new Color(35, 7, 59));
        tblTable.getTableHeader().setForeground(new Color(255, 255, 255));

        tblOrderDetail.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblOrderDetail.getTableHeader().setOpaque(false);
        tblOrderDetail.getTableHeader().setBackground(new Color(35, 7, 59));
        tblOrderDetail.getTableHeader().setForeground(new Color(255, 255, 255));

        tblProductosStock.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblProductosStock.getTableHeader().setOpaque(false);
        tblProductosStock.getTableHeader().setBackground(new Color(35, 7, 59));
        tblProductosStock.getTableHeader().setForeground(new Color(255, 255, 255));

        tblUltimasVentas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblUltimasVentas.getTableHeader().setOpaque(false);
        tblUltimasVentas.getTableHeader().setBackground(new Color(35, 7, 59));
        tblUltimasVentas.getTableHeader().setForeground(new Color(255, 255, 255));

        pdfUtilities = new PdfUtilities();

        try {
            orderModel.removeOrders();
            orderModel.addOrders(orderApiCall.findLatestOrders());
        } catch (Exception ex) {
            System.out.println("Error " + ex.getMessage());
        }

        btnActualizarTablas.setVisible(false);

        ImageIcon image = new ImageIcon(getClass().getResource("/images/logoMediaflix.png"));
        lblLogo.setIcon(new ImageIcon(image.getImage().getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_DEFAULT)));

        image = new ImageIcon(getClass().getResource("/images/X_logo.png"));
        btnExit.setIcon(new ImageIcon(image.getImage().getScaledInstance(btnExit.getWidth(), btnExit.getHeight(), Image.SCALE_DEFAULT)));

        image = new ImageIcon(getClass().getResource("/images/reload.png"));
        lblActualizarTablas.setIcon(new ImageIcon(image.getImage().getScaledInstance(lblActualizarTablas.getWidth(), lblActualizarTablas.getHeight(), Image.SCALE_SMOOTH)));

        tableModel = ModeloAplicado.NINGUNO;

        pnlTabla.setVisible(false);

    }

    private void inicializarDialogAdministrador() {
        inicializarDialogEmpleado();
        employeeModel = new EmployeeTableModel();
        employeeApiCall = new EmployeeApiCall();

        btnEmpleados.setVisible(true);
        lblEmpleados.setVisible(true);

        btnConfiguracion.setVisible(false);
        lblConfiguracion.setVisible(false);
        lblNombreEmpleado.setText("Empleado:  Admin");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlFondo = new javax.swing.JPanel();
        pnlBotones = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnPrincipal = new javax.swing.JPanel();
        lblPrincipal = new javax.swing.JLabel();
        btnProductos = new javax.swing.JPanel();
        lblProductos = new javax.swing.JLabel();
        btnEmpleados = new javax.swing.JPanel();
        lblEmpleados = new javax.swing.JLabel();
        btnClientes = new javax.swing.JPanel();
        lblClientes = new javax.swing.JLabel();
        btnVentas = new javax.swing.JPanel();
        lblVentas = new javax.swing.JLabel();
        btnCerrarSesion = new javax.swing.JPanel();
        lblCerrarSesion = new javax.swing.JLabel();
        btnConfiguracion = new javax.swing.JPanel();
        lblConfiguracion = new javax.swing.JLabel();
        btnAprovisionamiento = new javax.swing.JPanel();
        lblAprovisionamiento = new javax.swing.JLabel();
        pnlHeader = new javax.swing.JPanel();
        btnExit = new javax.swing.JLabel();
        lblNombreEmpleado = new javax.swing.JLabel();
        pnlTabla = new javax.swing.JPanel();
        pnlBotonesProductos = new javax.swing.JPanel();
        btnLibros = new javax.swing.JPanel();
        lblLibros = new javax.swing.JLabel();
        btnPeliculas = new javax.swing.JPanel();
        lblPeliculas = new javax.swing.JLabel();
        btnJuegos = new javax.swing.JPanel();
        lblJuegos = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTable = new javax.swing.JTable();
        cmbFiltroTabla = new javax.swing.JComboBox<>();
        lblSelecFiltro = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JPanel();
        lblBuscar = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnCrearNuevo = new javax.swing.JPanel();
        lblCrearNuevo = new javax.swing.JLabel();
        btnGenerarInforme = new javax.swing.JPanel();
        lblGenerarInforme = new javax.swing.JLabel();
        pnlPrincipal = new javax.swing.JPanel();
        pnlArticulosVenta = new javax.swing.JPanel();
        btnAñadirProducto = new javax.swing.JPanel();
        lblAñadirProducto = new javax.swing.JLabel();
        txtIdProducto = new javax.swing.JTextField();
        pnlTablaDetalleVenta = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblOrderDetail = new javax.swing.JTable();
        btnVender = new javax.swing.JPanel();
        lblVender = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pnlActualizaciones = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblUltimasVentas = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblProductosStock = new javax.swing.JTable();
        btnActualizarTablas = new javax.swing.JPanel();
        lblActualizarTablas = new javax.swing.JLabel();
        lblProductosPocoStock = new javax.swing.JLabel();
        lblProductosSinStock = new javax.swing.JLabel();
        cmbFiltroStock = new javax.swing.JComboBox<>();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        pnlFondo.setBackground(new java.awt.Color(204, 173, 228));

        pnlBotones.setBackground(new java.awt.Color(61, 27, 99));

        lblLogo.setBackground(new java.awt.Color(255, 255, 255));
        lblLogo.setPreferredSize(new java.awt.Dimension(200, 200));

        jSeparator1.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator1.setOpaque(true);

        btnPrincipal.setBackground(new java.awt.Color(61, 27, 99));

        lblPrincipal.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblPrincipal.setForeground(new java.awt.Color(255, 255, 255));
        lblPrincipal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPrincipal.setText("Principal");
        lblPrincipal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblPrincipal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPrincipalMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblPrincipalMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblPrincipalMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnPrincipalLayout = new javax.swing.GroupLayout(btnPrincipal);
        btnPrincipal.setLayout(btnPrincipalLayout);
        btnPrincipalLayout.setHorizontalGroup(
            btnPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        btnPrincipalLayout.setVerticalGroup(
            btnPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        btnProductos.setBackground(new java.awt.Color(61, 27, 99));

        lblProductos.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblProductos.setForeground(new java.awt.Color(255, 255, 255));
        lblProductos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProductos.setText("Productos");
        lblProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblProductosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblProductosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblProductosMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnProductosLayout = new javax.swing.GroupLayout(btnProductos);
        btnProductos.setLayout(btnProductosLayout);
        btnProductosLayout.setHorizontalGroup(
            btnProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        btnProductosLayout.setVerticalGroup(
            btnProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblProductos, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        btnEmpleados.setBackground(new java.awt.Color(61, 27, 99));

        lblEmpleados.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblEmpleados.setForeground(new java.awt.Color(255, 255, 255));
        lblEmpleados.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEmpleados.setText("Empleados");
        lblEmpleados.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEmpleadosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblEmpleadosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblEmpleadosMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnEmpleadosLayout = new javax.swing.GroupLayout(btnEmpleados);
        btnEmpleados.setLayout(btnEmpleadosLayout);
        btnEmpleadosLayout.setHorizontalGroup(
            btnEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        btnEmpleadosLayout.setVerticalGroup(
            btnEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        btnClientes.setBackground(new java.awt.Color(61, 27, 99));

        lblClientes.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblClientes.setForeground(new java.awt.Color(255, 255, 255));
        lblClientes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblClientes.setText("Clientes");
        lblClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblClientesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblClientesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblClientesMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnClientesLayout = new javax.swing.GroupLayout(btnClientes);
        btnClientes.setLayout(btnClientesLayout);
        btnClientesLayout.setHorizontalGroup(
            btnClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        btnClientesLayout.setVerticalGroup(
            btnClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        btnVentas.setBackground(new java.awt.Color(61, 27, 99));

        lblVentas.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblVentas.setForeground(new java.awt.Color(255, 255, 255));
        lblVentas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblVentas.setText("Ventas");
        lblVentas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblVentasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblVentasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblVentasMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnVentasLayout = new javax.swing.GroupLayout(btnVentas);
        btnVentas.setLayout(btnVentasLayout);
        btnVentasLayout.setHorizontalGroup(
            btnVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblVentas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        btnVentasLayout.setVerticalGroup(
            btnVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblVentas, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        btnCerrarSesion.setBackground(new java.awt.Color(61, 27, 99));

        lblCerrarSesion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));
        lblCerrarSesion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCerrarSesion.setText("Cerrar Sesión");
        lblCerrarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCerrarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCerrarSesionMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCerrarSesionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCerrarSesionMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnCerrarSesionLayout = new javax.swing.GroupLayout(btnCerrarSesion);
        btnCerrarSesion.setLayout(btnCerrarSesionLayout);
        btnCerrarSesionLayout.setHorizontalGroup(
            btnCerrarSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblCerrarSesion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        btnCerrarSesionLayout.setVerticalGroup(
            btnCerrarSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblCerrarSesion, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        btnConfiguracion.setBackground(new java.awt.Color(61, 27, 99));

        lblConfiguracion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblConfiguracion.setForeground(new java.awt.Color(255, 255, 255));
        lblConfiguracion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblConfiguracion.setText("Configuración");
        lblConfiguracion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblConfiguracion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblConfiguracionMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblConfiguracionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblConfiguracionMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnConfiguracionLayout = new javax.swing.GroupLayout(btnConfiguracion);
        btnConfiguracion.setLayout(btnConfiguracionLayout);
        btnConfiguracionLayout.setHorizontalGroup(
            btnConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblConfiguracion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        btnConfiguracionLayout.setVerticalGroup(
            btnConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblConfiguracion, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        btnAprovisionamiento.setBackground(new java.awt.Color(61, 27, 99));

        lblAprovisionamiento.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblAprovisionamiento.setForeground(new java.awt.Color(255, 255, 255));
        lblAprovisionamiento.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAprovisionamiento.setText("Aprovisionamiento");
        lblAprovisionamiento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAprovisionamiento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAprovisionamientoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblAprovisionamientoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblAprovisionamientoMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnAprovisionamientoLayout = new javax.swing.GroupLayout(btnAprovisionamiento);
        btnAprovisionamiento.setLayout(btnAprovisionamientoLayout);
        btnAprovisionamientoLayout.setHorizontalGroup(
            btnAprovisionamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAprovisionamiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        btnAprovisionamientoLayout.setVerticalGroup(
            btnAprovisionamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAprovisionamiento, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlBotonesLayout = new javax.swing.GroupLayout(pnlBotones);
        pnlBotones.setLayout(pnlBotonesLayout);
        pnlBotonesLayout.setHorizontalGroup(
            pnlBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnPrincipal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnProductos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBotonesLayout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(pnlBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
            .addComponent(btnVentas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnCerrarSesion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnConfiguracion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnAprovisionamiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlBotonesLayout.setVerticalGroup(
            pnlBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBotonesLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(btnPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAprovisionamiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnConfiguracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        pnlBotonesLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnEmpleados, btnProductos});

        pnlHeader.setBackground(new java.awt.Color(35, 7, 59));

        btnExit.setBackground(new java.awt.Color(255, 255, 255));
        btnExit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExit.setOpaque(true);
        btnExit.setPreferredSize(new java.awt.Dimension(48, 48));
        btnExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExitMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExitMouseExited(evt);
            }
        });

        lblNombreEmpleado.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblNombreEmpleado.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHeaderLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblNombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlHeaderLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlHeaderLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(lblNombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlTabla.setBackground(new java.awt.Color(204, 173, 228));

        pnlBotonesProductos.setBackground(new java.awt.Color(204, 173, 228));

        btnLibros.setBackground(new java.awt.Color(61, 27, 99));

        lblLibros.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblLibros.setForeground(new java.awt.Color(255, 255, 255));
        lblLibros.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLibros.setText("Libros");
        lblLibros.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLibros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLibrosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblLibrosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblLibrosMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnLibrosLayout = new javax.swing.GroupLayout(btnLibros);
        btnLibros.setLayout(btnLibrosLayout);
        btnLibrosLayout.setHorizontalGroup(
            btnLibrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLibros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        btnLibrosLayout.setVerticalGroup(
            btnLibrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLibros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnPeliculas.setBackground(new java.awt.Color(61, 27, 99));

        lblPeliculas.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblPeliculas.setForeground(new java.awt.Color(255, 255, 255));
        lblPeliculas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPeliculas.setText("Peliculas");
        lblPeliculas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblPeliculas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPeliculasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblPeliculasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblPeliculasMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnPeliculasLayout = new javax.swing.GroupLayout(btnPeliculas);
        btnPeliculas.setLayout(btnPeliculasLayout);
        btnPeliculasLayout.setHorizontalGroup(
            btnPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblPeliculas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        btnPeliculasLayout.setVerticalGroup(
            btnPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblPeliculas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnJuegos.setBackground(new java.awt.Color(61, 27, 99));

        lblJuegos.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblJuegos.setForeground(new java.awt.Color(255, 255, 255));
        lblJuegos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblJuegos.setText("Videojuegos");
        lblJuegos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblJuegos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblJuegosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblJuegosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblJuegosMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnJuegosLayout = new javax.swing.GroupLayout(btnJuegos);
        btnJuegos.setLayout(btnJuegosLayout);
        btnJuegosLayout.setHorizontalGroup(
            btnJuegosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblJuegos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        btnJuegosLayout.setVerticalGroup(
            btnJuegosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblJuegos, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlBotonesProductosLayout = new javax.swing.GroupLayout(pnlBotonesProductos);
        pnlBotonesProductos.setLayout(pnlBotonesProductosLayout);
        pnlBotonesProductosLayout.setHorizontalGroup(
            pnlBotonesProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBotonesProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnLibros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(298, 298, 298)
                .addComponent(btnPeliculas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(250, 250, 250)
                .addComponent(btnJuegos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlBotonesProductosLayout.setVerticalGroup(
            pnlBotonesProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBotonesProductosLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(pnlBotonesProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnJuegos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPeliculas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLibros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26))
        );

        tblTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblTable.setSelectionBackground(new java.awt.Color(61, 27, 99));
        tblTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblTable.getTableHeader().setReorderingAllowed(false);
        tblTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblTable);

        cmbFiltroTabla.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cmbFiltroTabla.setForeground(new java.awt.Color(61, 27, 99));
        cmbFiltroTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFiltroTablaActionPerformed(evt);
            }
        });

        lblSelecFiltro.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblSelecFiltro.setForeground(new java.awt.Color(61, 27, 99));
        lblSelecFiltro.setText("Seleccione Filtro:");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(61, 27, 99));
        lblTitulo.setText("Título :");

        txtTitulo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        btnBuscar.setBackground(new java.awt.Color(61, 27, 99));

        lblBuscar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblBuscar.setForeground(new java.awt.Color(255, 255, 255));
        lblBuscar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBuscar.setText("Buscar");
        lblBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBuscarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblBuscarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblBuscarMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnBuscarLayout = new javax.swing.GroupLayout(btnBuscar);
        btnBuscar.setLayout(btnBuscarLayout);
        btnBuscarLayout.setHorizontalGroup(
            btnBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
        );
        btnBuscarLayout.setVerticalGroup(
            btnBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(204, 173, 228));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(61, 27, 99));
        jLabel3.setText("Viendo:");

        lblEstado.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblEstado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEstado.setText("jLabel4");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(35, 35, 35)
                .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblEstado))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(204, 173, 228));

        btnCrearNuevo.setBackground(new java.awt.Color(61, 27, 99));

        lblCrearNuevo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblCrearNuevo.setForeground(new java.awt.Color(255, 255, 255));
        lblCrearNuevo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCrearNuevo.setText("Crear Nuevo");
        lblCrearNuevo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCrearNuevo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCrearNuevoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCrearNuevoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCrearNuevoMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnCrearNuevoLayout = new javax.swing.GroupLayout(btnCrearNuevo);
        btnCrearNuevo.setLayout(btnCrearNuevoLayout);
        btnCrearNuevoLayout.setHorizontalGroup(
            btnCrearNuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblCrearNuevo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
        );
        btnCrearNuevoLayout.setVerticalGroup(
            btnCrearNuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblCrearNuevo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        btnGenerarInforme.setBackground(new java.awt.Color(61, 27, 99));

        lblGenerarInforme.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblGenerarInforme.setForeground(new java.awt.Color(255, 255, 255));
        lblGenerarInforme.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGenerarInforme.setText("Generar Informe");
        lblGenerarInforme.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblGenerarInforme.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblGenerarInformeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblGenerarInformeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblGenerarInformeMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnGenerarInformeLayout = new javax.swing.GroupLayout(btnGenerarInforme);
        btnGenerarInforme.setLayout(btnGenerarInformeLayout);
        btnGenerarInformeLayout.setHorizontalGroup(
            btnGenerarInformeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblGenerarInforme, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
        );
        btnGenerarInformeLayout.setVerticalGroup(
            btnGenerarInformeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblGenerarInforme, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(btnCrearNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addComponent(btnGenerarInforme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCrearNuevo, btnGenerarInforme});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 10, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnGenerarInforme, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCrearNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnCrearNuevo, btnGenerarInforme});

        javax.swing.GroupLayout pnlTablaLayout = new javax.swing.GroupLayout(pnlTabla);
        pnlTabla.setLayout(pnlTablaLayout);
        pnlTablaLayout.setHorizontalGroup(
            pnlTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTablaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlBotonesProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTablaLayout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addGap(18, 18, 18)
                        .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1382, Short.MAX_VALUE)
                        .addComponent(lblSelecFiltro)
                        .addGap(18, 18, 18)
                        .addGroup(pnlTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbFiltroTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTablaLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlTablaLayout.setVerticalGroup(
            pnlTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTablaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlBotonesProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblSelecFiltro)
                        .addComponent(cmbFiltroTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTitulo)
                        .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pnlPrincipal.setBackground(new java.awt.Color(204, 173, 228));
        pnlPrincipal.setPreferredSize(new java.awt.Dimension(1370, 638));

        pnlArticulosVenta.setBackground(new java.awt.Color(204, 173, 228));

        btnAñadirProducto.setBackground(new java.awt.Color(61, 27, 99));

        lblAñadirProducto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblAñadirProducto.setForeground(new java.awt.Color(255, 255, 255));
        lblAñadirProducto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAñadirProducto.setText("Añadir");
        lblAñadirProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAñadirProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAñadirProductoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblAñadirProductoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblAñadirProductoMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnAñadirProductoLayout = new javax.swing.GroupLayout(btnAñadirProducto);
        btnAñadirProducto.setLayout(btnAñadirProductoLayout);
        btnAñadirProductoLayout.setHorizontalGroup(
            btnAñadirProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAñadirProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
        );
        btnAñadirProductoLayout.setVerticalGroup(
            btnAñadirProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAñadirProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        txtIdProducto.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtIdProducto.setForeground(new java.awt.Color(153, 153, 153));
        txtIdProducto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtIdProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtIdProductoMouseClicked(evt);
            }
        });
        txtIdProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIdProductoKeyPressed(evt);
            }
        });

        pnlTablaDetalleVenta.setBackground(new java.awt.Color(204, 173, 228));

        tblOrderDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblOrderDetail.setSelectionBackground(new java.awt.Color(204, 173, 228));
        tblOrderDetail.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblOrderDetail.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tblOrderDetail);

        btnVender.setBackground(new java.awt.Color(61, 27, 99));

        lblVender.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblVender.setForeground(new java.awt.Color(255, 255, 255));
        lblVender.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblVender.setText("Vender");
        lblVender.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblVender.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblVenderMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblVenderMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblVenderMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnVenderLayout = new javax.swing.GroupLayout(btnVender);
        btnVender.setLayout(btnVenderLayout);
        btnVenderLayout.setHorizontalGroup(
            btnVenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblVender, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        btnVenderLayout.setVerticalGroup(
            btnVenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblVender, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout pnlTablaDetalleVentaLayout = new javax.swing.GroupLayout(pnlTablaDetalleVenta);
        pnlTablaDetalleVenta.setLayout(pnlTablaDetalleVentaLayout);
        pnlTablaDetalleVentaLayout.setHorizontalGroup(
            pnlTablaDetalleVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTablaDetalleVentaLayout.createSequentialGroup()
                .addGroup(pnlTablaDetalleVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnVender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 44, Short.MAX_VALUE))
        );
        pnlTablaDetalleVentaLayout.setVerticalGroup(
            pnlTablaDetalleVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTablaDetalleVentaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnVender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        jLabel2.setBackground(new java.awt.Color(61, 27, 99));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(61, 27, 99));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Realizar venta");

        javax.swing.GroupLayout pnlArticulosVentaLayout = new javax.swing.GroupLayout(pnlArticulosVenta);
        pnlArticulosVenta.setLayout(pnlArticulosVentaLayout);
        pnlArticulosVentaLayout.setHorizontalGroup(
            pnlArticulosVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlArticulosVentaLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlArticulosVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlTablaDetalleVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlArticulosVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlArticulosVentaLayout.createSequentialGroup()
                            .addComponent(txtIdProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(28, 28, 28)
                            .addComponent(btnAñadirProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlArticulosVentaLayout.setVerticalGroup(
            pnlArticulosVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlArticulosVentaLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(pnlArticulosVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAñadirProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtIdProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69)
                .addComponent(pnlTablaDetalleVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlActualizaciones.setBackground(new java.awt.Color(204, 173, 228));

        tblUltimasVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblUltimasVentas.setSelectionBackground(new java.awt.Color(61, 27, 99));
        tblUltimasVentas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblUltimasVentas.getTableHeader().setReorderingAllowed(false);
        tblUltimasVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUltimasVentasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblUltimasVentas);

        tblProductosStock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblProductosStock.setSelectionBackground(new java.awt.Color(61, 27, 99));
        tblProductosStock.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblProductosStock.getTableHeader().setReorderingAllowed(false);
        tblProductosStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductosStockMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblProductosStock);

        btnActualizarTablas.setBackground(new java.awt.Color(61, 27, 99));

        lblActualizarTablas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblActualizarTablas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblActualizarTablasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblActualizarTablasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblActualizarTablasMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnActualizarTablasLayout = new javax.swing.GroupLayout(btnActualizarTablas);
        btnActualizarTablas.setLayout(btnActualizarTablasLayout);
        btnActualizarTablasLayout.setHorizontalGroup(
            btnActualizarTablasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblActualizarTablas, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
        );
        btnActualizarTablasLayout.setVerticalGroup(
            btnActualizarTablasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblActualizarTablas, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
        );

        lblProductosPocoStock.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblProductosPocoStock.setForeground(new java.awt.Color(61, 27, 99));
        lblProductosPocoStock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProductosPocoStock.setText("Productos con pocas unidades");

        lblProductosSinStock.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblProductosSinStock.setForeground(new java.awt.Color(61, 27, 99));
        lblProductosSinStock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProductosSinStock.setText("Productos sin stock");

        cmbFiltroStock.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cmbFiltroStock.setForeground(new java.awt.Color(61, 27, 99));
        cmbFiltroStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFiltroStockActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlActualizacionesLayout = new javax.swing.GroupLayout(pnlActualizaciones);
        pnlActualizaciones.setLayout(pnlActualizacionesLayout);
        pnlActualizacionesLayout.setHorizontalGroup(
            pnlActualizacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlActualizacionesLayout.createSequentialGroup()
                .addGroup(pnlActualizacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblProductosSinStock, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlActualizacionesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlActualizacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addComponent(jScrollPane2)
                            .addGroup(pnlActualizacionesLayout.createSequentialGroup()
                                .addComponent(btnActualizarTablas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(pnlActualizacionesLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblProductosPocoStock, javax.swing.GroupLayout.PREFERRED_SIZE, 958, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cmbFiltroStock, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        pnlActualizacionesLayout.setVerticalGroup(
            pnlActualizacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlActualizacionesLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblProductosSinStock, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlActualizacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlActualizacionesLayout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(cmbFiltroStock, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlActualizacionesLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlActualizacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblProductosPocoStock, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnActualizarTablas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPrincipalLayout.createSequentialGroup()
                .addComponent(pnlActualizaciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(pnlArticulosVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlPrincipalLayout.setVerticalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlArticulosVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlActualizaciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout pnlFondoLayout = new javax.swing.GroupLayout(pnlFondo);
        pnlFondo.setLayout(pnlFondoLayout);
        pnlFondoLayout.setHorizontalGroup(
            pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFondoLayout.createSequentialGroup()
                .addComponent(pnlBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlFondoLayout.createSequentialGroup()
                        .addComponent(pnlTabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
            .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFondoLayout.createSequentialGroup()
                    .addGap(253, 253, 253)
                    .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 2345, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        pnlFondoLayout.setVerticalGroup(
            pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFondoLayout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlTabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(pnlBotones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlFondoLayout.createSequentialGroup()
                    .addGap(71, 71, 71)
                    .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 811, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblGenerarInformeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGenerarInformeMouseExited
        btnGenerarInforme.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblGenerarInformeMouseExited

    private void lblGenerarInformeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGenerarInformeMouseEntered
        btnGenerarInforme.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblGenerarInformeMouseEntered

    private void lblGenerarInformeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGenerarInformeMouseClicked

        String title = txtTitulo.getText().trim();
        String selectedFilter = (String) cmbFiltroTabla.getSelectedItem();

        if (selectedFilter == null) {
            selectedFilter = "Ninguno";
        }

        System.out.println("tblTable.getRowCount()" + tblTable.getRowCount());
        if (tblTable.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "La tabla de la que intenta generar un informe no contiene datos.");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar archivo PDF");

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo PDF (*.pdf)", "pdf");
        fileChooser.setFileFilter(filter);

        // Configurar el filtro de archivos para mostrar solo carpetas
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // Mostrar el diálogo de guardar
        int result = fileChooser.showSaveDialog(null);

        // Verificar si el usuario ha seleccionado un archivo
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            String filename = selectedFile.getName();
            if (!filename.toLowerCase().endsWith(".pdf")) {
                filename += ".pdf";
                selectedFile = new File(selectedFile.getParent(), filename);
            }

            String tipoInforme = null;

            switch (tableModel) {
                case CLIENTE:
                    tipoInforme = "Clientes";
                    break;
                case EMPLEADO:
                    tipoInforme = "Empleados";
                    break;
                case JUEGO:
                    tipoInforme = "Juegos";
                    break;
                case LIBRO:
                    tipoInforme = "Libros";
                    break;
                case PRODUCTO:
                    tipoInforme = "Productos";
                    break;
                case PELICULA:
                    tipoInforme = "Peliculas";
                    break;
                case VENTAS:
                    tipoInforme = "Ventas";
                    break;
            }

            try (PdfWriter writer = new PdfWriter(selectedFile); PdfDocument pdf = new PdfDocument(writer); Document document = new Document(pdf, PageSize.A4.rotate())) {

                pdfUtilities.createPdfHeader(document, tipoInforme, title, selectedFilter);
                pdfUtilities.createPdfTable(document, tblTable, tableModel);

                JOptionPane.showMessageDialog(this, "¡PDF creado éxito!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "La tabla con la que quiere generar el informe está vacía. Por favor, intentelo con una tabla con datos");
            } catch (TableWithOutDataException ex) {
                JOptionPane.showMessageDialog(this, "La tabla con la que quiere generar el informe está vacía. Por favor, intentelo con una tabla con datos");
            }
        } else if (result == JFileChooser.CANCEL_OPTION) {
            JOptionPane.showMessageDialog(this, "Ha cancelado la exportación a PDF");
        }

    }//GEN-LAST:event_lblGenerarInformeMouseClicked

    private void lblCrearNuevoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCrearNuevoMouseExited
        btnCrearNuevo.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblCrearNuevoMouseExited

    private void lblCrearNuevoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCrearNuevoMouseEntered
        btnCrearNuevo.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblCrearNuevoMouseEntered

    private void lblCrearNuevoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCrearNuevoMouseClicked

        if (tableModel.equals(ModeloAplicado.EMPLEADO)) {
            new VisualizarPersona(this, true, "").setVisible(true);
            employeeModel.removeEmployees();
            employees = employeeApiCall.getAllEmployees();
            employeeModel.addEmployees(employees);
        } else if (tableModel.equals(ModeloAplicado.PRODUCTO) || tableModel.equals(ModeloAplicado.LIBRO) || tableModel.equals(ModeloAplicado.PELICULA) || tableModel.equals(ModeloAplicado.JUEGO)) {
            new DetalleProducto(this, true).setVisible(true);
            productModel.removeProducts();
            products = productApiCall.getAllProducts();
            productModel.addProducts(products);
        }
    }//GEN-LAST:event_lblCrearNuevoMouseClicked

    private void lblBuscarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBuscarMouseExited
        btnBuscar.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblBuscarMouseExited

    private void lblBuscarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBuscarMouseEntered
        btnBuscar.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblBuscarMouseEntered

    private void lblBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBuscarMouseClicked
        pnlPrincipal.setVisible(false);

        String titulo = txtTitulo.getText();
        this.revalidate();
        this.repaint();

        switch (tableModel) {
            case PRODUCTO:
                productsByTitle.clear();
                productsByTitle = productApiCall.getProductsByTitle(titulo);
                productModel.removeProducts();
                productModel.addProducts(productsByTitle);
                break;
            case LIBRO:
                booksByTitle.clear();
                booksByTitle = bookApiCall.getAllBooksByTitle(titulo);
                bookModel.removeBooks();
                bookModel.addBooks(booksByTitle);
                break;
            case PELICULA:
                moviesByTitle.clear();
                moviesByTitle = movieApiCall.getAllMoviesByTitle(titulo);
                movieModel.removeMovies();
                movieModel.addMovies(moviesByTitle);
                break;
            case JUEGO:
                gamesByTitle.clear();
                gamesByTitle = gameApiCall.getAllGamesByTitle(titulo);
                gameModel.removeGames();
                gameModel.addGames(gamesByTitle);
                break;
            case CLIENTE:
                clientsByName.clear();
                clientsByName = clientApiCall.getClientsByName(titulo);
                clientModel.removeClients();
                clientModel.addClients(clientsByName);
                break;
            case EMPLEADO:
                employeesByName.clear();
                employeesByName = employeeApiCall.getEmployeesByName(titulo);
                employeeModel.removeEmployees();
                employeeModel.addEmployees(employeesByName);
                break;
            case VENTAS:
                ordersByEmail.clear();

                try {
                    ordersByEmail = orderApiCall.findOrdersByClientEmail(titulo);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "No existe ningún cliente con ese email");
                    return;
                }

                if (ordersByEmail.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No existe ningún cliente con ese email");
                    return;
                }

                allOrdersModel.removeOrders();
                allOrdersModel.addOrders(ordersByEmail);
                break;

            default:
                break;
        }

        cmbFiltroTabla.setSelectedIndex(-1);
    }//GEN-LAST:event_lblBuscarMouseClicked

    private void cmbFiltroTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFiltroTablaActionPerformed
        pnlPrincipal.setVisible(false);
        pnlTabla.setVisible(true);
        pnlPrincipal.repaint();

        String filtro = (String) cmbFiltroTabla.getSelectedItem();

        if (tableModel.equals(ModeloAplicado.PRODUCTO)) {
            if (productsByTitle.isEmpty()) {
                productModel.removeProducts();
                products = productApiCall.getAllProducts();
                productModel.addProducts(products);
            } else {
                productModel.removeProducts();
                productModel.addProducts(productsByTitle);
            }

            productModel.mostrarTablaPor(filtro);
        }

        if (tableModel.equals(ModeloAplicado.LIBRO)) {
            if (booksByTitle.isEmpty()) {
                bookModel.removeBooks();
                books = bookApiCall.getAllBooks();
                bookModel.addBooks(books);
            } else {
                bookModel.removeBooks();
                bookModel.addBooks(booksByTitle);
            }

            bookModel.mostrarTablaPor(filtro);
        }

        if (tableModel.equals(ModeloAplicado.PELICULA)) {
            if (moviesByTitle.isEmpty()) {
                movieModel.removeMovies();
                movies = movieApiCall.getAllMovies();
                movieModel.addMovies(movies);
            } else {
                movieModel.removeMovies();
                movieModel.addMovies(moviesByTitle);
            }

            movieModel.mostrarTablaPor(filtro);
        }

        if (tableModel.equals(ModeloAplicado.JUEGO)) {
            if (gamesByTitle.isEmpty()) {
                gameModel.removeGames();
                games = gameApiCall.getAllGames();
                gameModel.addGames(games);
            } else {
                gameModel.removeGames();
                gameModel.addGames(gamesByTitle);
            }

            gameModel.mostrarTablaPor(filtro);
        }

        if (tableModel.equals(ModeloAplicado.EMPLEADO)) {
            if (employeesByName.isEmpty()) {
                employeeModel.removeEmployees();
                employees = employeeApiCall.getAllEmployees();
                employeeModel.addEmployees(employees);
            } else {
                employeeModel.removeEmployees();
                employeeModel.addEmployees(employees);
            }

            employeeModel.mostrarTablaPor(filtro);
        }

        if (tableModel.equals(ModeloAplicado.CLIENTE)) {
            if (clientsByName.isEmpty()) {
                clientModel.removeClients();
                clients = clientApiCall.getAllClients();
                clientModel.addClients(clients);
            } else {
                clientModel.removeClients();
                clientModel.addClients(clientsByName);
            }

            clientModel.mostrarTablaPor(filtro);
        }

    }//GEN-LAST:event_cmbFiltroTablaActionPerformed

    private void tblTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTableMouseClicked
        txtTitulo.setText("");
        pnlPrincipal.setVisible(false);
        pnlTabla.setVisible(true);

        int row = tblTable.getSelectedRow();

        if (row >= 0 && row < tblTable.getRowCount()) {
            switch (tableModel) {
                case PRODUCTO:
                    Product product = productModel.getProduct(row);
                    Long idProducto = product.getProductId();

                    switch (product.getProductType()) {
                        case BOOK:
                            Book book = bookApiCall.getBookById(idProducto);
                            product = book;
                            break;
                        case MOVIE:
                            Movie movie = movieApiCall.getMovieById(idProducto);
                            product = movie;
                            break;
                        case GAME:
                            Game game = gameApiCall.getGameById(idProducto);
                            product = game;
                            break;
                        default:
                            break;
                    }
                    new DetalleProducto(this, true, product).setVisible(true);

                    //Refresco la tabla para mostrar los cambios
                    productModel.removeProducts();
                    products = productApiCall.getAllProducts();
                    productModel.addProducts(products);

                    break;

                case EMPLEADO:
                    Employee employee = employeeModel.getEmployee(row);
                    new VisualizarPersona(this, true, employee).setVisible(true);

                    //Refresco la tabla para mostrar los cambios
                    employeeModel.removeEmployees();
                    employees = employeeApiCall.getAllEmployees();
                    employeeModel.addEmployees(employees);

                    break;

                case CLIENTE:
                    Client client = clientModel.getClient(row);
                    new VisualizarPersona(this, true, client).setVisible(true);

                    //Refresco la tabla para mostrar los cambios
                    clientModel.removeClients();
                    clients = clientApiCall.getAllClients();
                    clientModel.addClients(clients);

                    break;

                case LIBRO:
                    Book book = bookModel.getBook(row);
                    new DetalleProducto(this, true, book).setVisible(true);

                    //Refresco la tabla para mostrar los cambios
                    bookModel.removeBooks();
                    books = bookApiCall.getAllBooks();
                    bookModel.addBooks(books);

                    break;

                case PELICULA:
                    Movie movie = movieModel.getMovie(row);
                    new DetalleProducto(this, true, movie).setVisible(true);

                    //Refresco la tabla para mostrar los cambios que han ocurrido
                    movieModel.removeMovies();
                    movies = movieApiCall.getAllMovies();
                    movieModel.addMovies(movies);

                    break;
                case JUEGO:
                    Game game = gameModel.getGame(row);
                    new DetalleProducto(this, true, game).setVisible(true);

                    //Refresco la tabla para mostrar los cambios
                    gameModel.removeGames();
                    games = gameApiCall.getAllGames();
                    gameModel.addGames(games);

                    break;
                default:
                    break;
            }
        }

        cmbFiltroTabla.setSelectedIndex(-1);
    }//GEN-LAST:event_tblTableMouseClicked

    private void lblJuegosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblJuegosMouseExited
        btnJuegos.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblJuegosMouseExited

    private void lblJuegosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblJuegosMouseEntered
        btnJuegos.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblJuegosMouseEntered

    private void lblJuegosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblJuegosMouseClicked
        lblTitulo.setText("Título:");
        txtTitulo.setText("");
        pnlPrincipal.setVisible(false);
        pnlTabla.setVisible(true);
        this.revalidate();
        this.repaint();

        String itemAt = cmbFiltroTabla.getItemAt(0);

        if (itemAt != null || !tableModel.equals(ModeloAplicado.JUEGO)) {
            modeloCombo.removeAllElements();
        }

        modeloCombo.addElement("Todos");
        modeloCombo.addElement("Disponibles");
        modeloCombo.addElement("No Disponibles");
        modeloCombo.addElement("Stock Ascendente");
        modeloCombo.addElement("Stock Descendente");
        modeloCombo.addElement("Precio Ascendente");
        modeloCombo.addElement("Precio Descendente");
        modeloCombo.addElement("Título Ascendente");
        modeloCombo.addElement("Título Descendente");
        modeloCombo.addElement("Desarrollador Ascendente");
        modeloCombo.addElement("Desarrollador Descendente");
        modeloCombo.addElement("Duración Ascendente");
        modeloCombo.addElement("Duración Descendente");

        tblTable.setModel(gameModel);

        tableModel = ModeloAplicado.JUEGO;

        lblEstado.setText("Juegos");

        pnlTabla.setVisible(true);

        gameModel.removeGames();
        games = gameApiCall.getAllGames();
        gameModel.addGames(games);

        cmbFiltroTabla.setSelectedIndex(-1);
    }//GEN-LAST:event_lblJuegosMouseClicked

    private void lblPeliculasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPeliculasMouseExited
        btnPeliculas.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblPeliculasMouseExited

    private void lblPeliculasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPeliculasMouseEntered
        btnPeliculas.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblPeliculasMouseEntered

    private void lblPeliculasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPeliculasMouseClicked
        lblTitulo.setText("Título:");
        txtTitulo.setText("");
        pnlPrincipal.setVisible(false);
        pnlTabla.setVisible(true);
        this.revalidate();
        this.repaint();

        String itemAt = cmbFiltroTabla.getItemAt(0);

        if (itemAt != null || !tableModel.equals(ModeloAplicado.PELICULA)) {
            modeloCombo.removeAllElements();
        }

        modeloCombo.addElement("Todos");
        modeloCombo.addElement("Disponibles");
        modeloCombo.addElement("No Disponibles");
        modeloCombo.addElement("Stock Ascendente");
        modeloCombo.addElement("Stock Descendente");
        modeloCombo.addElement("Precio Ascendente");
        modeloCombo.addElement("Precio Descendente");
        modeloCombo.addElement("Título Ascendente");
        modeloCombo.addElement("Título Descendente");
        modeloCombo.addElement("Director Ascendente");
        modeloCombo.addElement("Director Descendente");
        modeloCombo.addElement("Duración Ascendente");
        modeloCombo.addElement("Duración Descendente");

        tblTable.setModel(movieModel);

        tableModel = ModeloAplicado.PELICULA;

        lblEstado.setText("Peliculas");

        pnlTabla.setVisible(true);

        movieModel.removeMovies();
        movies = movieApiCall.getAllMovies();
        movieModel.addMovies(movies);

        cmbFiltroTabla.setSelectedIndex(-1);
    }//GEN-LAST:event_lblPeliculasMouseClicked

    private void lblLibrosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLibrosMouseExited
        btnLibros.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblLibrosMouseExited

    private void lblLibrosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLibrosMouseEntered
        btnLibros.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblLibrosMouseEntered

    private void lblLibrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLibrosMouseClicked
        lblTitulo.setText("Título:");
        txtTitulo.setText("");
        pnlPrincipal.setVisible(false);
        pnlTabla.setVisible(true);
        this.revalidate();
        this.repaint();

        String itemAt = cmbFiltroTabla.getItemAt(0);

        if (itemAt != null || !tableModel.equals(ModeloAplicado.LIBRO)) {
            modeloCombo.removeAllElements();
        }

        modeloCombo.addElement("Todos");
        modeloCombo.addElement("Disponibles");
        modeloCombo.addElement("No Disponibles");
        modeloCombo.addElement("Stock Ascendente");
        modeloCombo.addElement("Stock Descendente");
        modeloCombo.addElement("Precio Ascendente");
        modeloCombo.addElement("Precio Descendente");
        modeloCombo.addElement("Título Ascendente");
        modeloCombo.addElement("Título Descendente");
        modeloCombo.addElement("Autor Ascendente");
        modeloCombo.addElement("Autor Descendente");
        modeloCombo.addElement("Número Páginas Ascendente");
        modeloCombo.addElement("Número Páginas Descendente");

        tblTable.setModel(bookModel);

        tableModel = ModeloAplicado.LIBRO;

        lblEstado.setText("Libros");

        pnlTabla.setVisible(true);

        bookModel.removeBooks();
        books = bookApiCall.getAllBooks();
        bookModel.addBooks(books);

        cmbFiltroTabla.setSelectedIndex(-1);
    }//GEN-LAST:event_lblLibrosMouseClicked

    private void btnExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseExited
        btnExit.setBackground(Color.white);
    }//GEN-LAST:event_btnExitMouseExited

    private void btnExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseEntered
        btnExit.setBackground(new Color(204, 173, 228));
    }//GEN-LAST:event_btnExitMouseEntered

    private void btnExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_btnExitMouseClicked

    private void lblAprovisionamientoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAprovisionamientoMouseExited
        btnAprovisionamiento.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblAprovisionamientoMouseExited

    private void lblAprovisionamientoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAprovisionamientoMouseEntered
        btnAprovisionamiento.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblAprovisionamientoMouseEntered

    private void lblAprovisionamientoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAprovisionamientoMouseClicked

        tableModel = ModeloAplicado.APROVISIONAMIENTO;

        lblCrearNuevo.setVisible(true);
        btnCrearNuevo.setVisible(true);

        txtTitulo.setText("");
        lblProductosPocoStock.setText("Productos con pocas unidades");
        lblProductosSinStock.setText("Productos sin stock");

        pnlTabla.setVisible(false);
        pnlPrincipal.setVisible(true);
        pnlArticulosVenta.setVisible(false);
        cmbFiltroStock.setVisible(true);

        modeloComboStock.removeAllElements();
        cmbFiltroStock.setModel(modeloComboStock);

        modeloComboStock.addElement("Stock Ascendente");
        modeloComboStock.addElement("Stock Descendente");
        modeloComboStock.addElement("Alfabéticamente Ascendente");
        modeloComboStock.addElement("Alfabéticamente Descendente");

        productNoStockModel.removeProducts();
        tblUltimasVentas.setModel(productNoStockModel);
        productNoStockModel.addProducts(productApiCall.findAllProductsZeroStock());

        productLowStockModel.removeProducts();
        tblProductosStock.setModel(productLowStockModel);
        productLowStockModel.addProducts(productApiCall.findAllProductsStockBetween1And5());

        lblEstado.setText("Aprovisionamiento");

        btnActualizarTablas.setVisible(false);

        this.revalidate();
        this.repaint();

    }//GEN-LAST:event_lblAprovisionamientoMouseClicked

    private void lblConfiguracionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblConfiguracionMouseExited
        btnConfiguracion.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblConfiguracionMouseExited

    private void lblConfiguracionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblConfiguracionMouseEntered
        btnConfiguracion.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblConfiguracionMouseEntered

    private void lblConfiguracionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblConfiguracionMouseClicked
        try {
            new ConfiguracionUsuario(this, true, currentEmployee).setVisible(true);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_lblConfiguracionMouseClicked

    private void lblCerrarSesionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCerrarSesionMouseExited
        btnCerrarSesion.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblCerrarSesionMouseExited

    private void lblCerrarSesionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCerrarSesionMouseEntered
        btnCerrarSesion.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblCerrarSesionMouseEntered

    private void lblCerrarSesionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCerrarSesionMouseClicked
        this.dispose();
        //new Login().setVisible(true);
    }//GEN-LAST:event_lblCerrarSesionMouseClicked

    private void lblVentasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblVentasMouseExited
        btnVentas.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblVentasMouseExited

    private void lblVentasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblVentasMouseEntered
        btnVentas.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblVentasMouseEntered

    private void lblVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblVentasMouseClicked
//        lblTitulo.setVisible(false);
//        txtTitulo.setVisible(false);
//        lblBuscar.setVisible(false);
//        btnBuscar.setVisible(false);

        lblCrearNuevo.setVisible(false);
        btnCrearNuevo.setVisible(false);
        lblSelecFiltro.setVisible(false);
        cmbFiltroTabla.setVisible(false);

        lblEstado.setText("Ventas");
        lblTitulo.setText("Email de cliente");
        txtTitulo.setText("");
        pnlPrincipal.setVisible(false);
        pnlTabla.setVisible(true);
        tblTable.setModel(allOrdersModel);

        tableModel = ModeloAplicado.VENTAS;

        try {
            allOrdersModel.removeOrders();
            allOrders = orderApiCall.findAll();
            allOrdersModel.addOrders(allOrders);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        this.revalidate();
        this.repaint();
    }//GEN-LAST:event_lblVentasMouseClicked

    private void lblClientesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblClientesMouseExited
        btnClientes.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblClientesMouseExited

    private void lblClientesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblClientesMouseEntered
        btnClientes.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblClientesMouseEntered

    private void lblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblClientesMouseClicked
        lblCrearNuevo.setVisible(false);
        btnCrearNuevo.setVisible(false);

        lblTitulo.setVisible(true);
        txtTitulo.setVisible(true);
        lblBuscar.setVisible(true);
        btnBuscar.setVisible(true);
        lblSelecFiltro.setVisible(true);
        cmbFiltroTabla.setVisible(true);

        txtTitulo.setText("");
        pnlPrincipal.setVisible(false);
        pnlTabla.setVisible(true);
        this.revalidate();
        this.repaint();
        lblTitulo.setText("Nombre:");

        String itemAt = cmbFiltroTabla.getItemAt(0);

        if (itemAt != null || !tableModel.equals(ModeloAplicado.CLIENTE)) {
            modeloCombo.removeAllElements();
        }
        modeloCombo.addElement("Todos");
        modeloCombo.addElement("Disponibles");
        modeloCombo.addElement("No Disponibles");
        modeloCombo.addElement("Fecha Ascendente");
        modeloCombo.addElement("Fecha Descendente");
        modeloCombo.addElement("Alfabéticamente Ascendente");
        modeloCombo.addElement("Alfabéticamente Descendente");

        tblTable.setModel(clientModel);

        tableModel = ModeloAplicado.CLIENTE;

        lblEstado.setText("Clientes");

        pnlTabla.setVisible(true);

        clientModel.removeClients();
        clients = clientApiCall.getAllClients();
        clientModel.addClients(clients);
    }//GEN-LAST:event_lblClientesMouseClicked

    private void lblEmpleadosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEmpleadosMouseExited
        btnEmpleados.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblEmpleadosMouseExited

    private void lblEmpleadosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEmpleadosMouseEntered
        btnEmpleados.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblEmpleadosMouseEntered

    private void lblEmpleadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEmpleadosMouseClicked
        lblTitulo.setVisible(true);
        txtTitulo.setVisible(true);
        lblBuscar.setVisible(true);
        btnBuscar.setVisible(true);
        lblSelecFiltro.setVisible(true);
        cmbFiltroTabla.setVisible(true);

        lblCrearNuevo.setVisible(true);
        btnCrearNuevo.setVisible(true);
        txtTitulo.setText("");
        pnlPrincipal.setVisible(false);
        pnlTabla.setVisible(true);
        this.revalidate();
        this.repaint();
        lblTitulo.setText("Nombre:");

        modeloCombo.removeAllElements();
        modeloCombo.addElement("Todos");
        modeloCombo.addElement("Empleados actuales");
        modeloCombo.addElement("Antiguos empleados");
        modeloCombo.addElement("Fecha contratación ascendente");
        modeloCombo.addElement("Fecha contratación descendente");
        modeloCombo.addElement("Salario ascendente");
        modeloCombo.addElement("Salario descendente");
        modeloCombo.addElement("Alfabéticamente Ascendente");
        modeloCombo.addElement("Alfabéticamente Descendente");

        tblTable.setModel(employeeModel);

        tableModel = ModeloAplicado.EMPLEADO;

        lblEstado.setText("Empleados");

        pnlTabla.setVisible(true);

        employeeModel.removeEmployees();
        employees = employeeApiCall.getAllEmployees();
        employeeModel.addEmployees(employees);
    }//GEN-LAST:event_lblEmpleadosMouseClicked

    private void lblProductosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProductosMouseExited
        btnProductos.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblProductosMouseExited

    private void lblProductosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProductosMouseEntered
        btnProductos.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblProductosMouseEntered

    private void lblProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProductosMouseClicked
        lblTitulo.setVisible(true);
        txtTitulo.setVisible(true);
        lblBuscar.setVisible(true);
        btnBuscar.setVisible(true);
        lblSelecFiltro.setVisible(true);
        cmbFiltroTabla.setVisible(true);

        lblCrearNuevo.setVisible(true);
        btnCrearNuevo.setVisible(true);
        txtTitulo.setText("");
        pnlPrincipal.setVisible(false);
        pnlTabla.setVisible(true);
        this.revalidate();
        this.repaint();
        lblTitulo.setText("Título:");

        String itemAt = cmbFiltroTabla.getItemAt(0);

        if (itemAt != null || !tableModel.equals(ModeloAplicado.PRODUCTO)) {
            modeloCombo.removeAllElements();
        }
        modeloCombo.addElement("Todos");
        modeloCombo.addElement("Disponibles");
        modeloCombo.addElement("No Disponibles");
        modeloCombo.addElement("Stock Ascendente");
        modeloCombo.addElement("Stock Descendente");
        modeloCombo.addElement("Precio Ascendente");
        modeloCombo.addElement("Precio Descendente");
        modeloCombo.addElement("Alfabéticamente Ascendente");
        modeloCombo.addElement("Alfabéticamente Descendente");

        tblTable.setModel(productModel);

        tableModel = ModeloAplicado.PRODUCTO;

        lblEstado.setText("Productos");

        pnlTabla.setVisible(true);

        productModel.removeProducts();
        products = productApiCall.getAllProducts();
        productModel.addProducts(products);
    }//GEN-LAST:event_lblProductosMouseClicked

    private void lblPrincipalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPrincipalMouseExited
        btnPrincipal.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblPrincipalMouseExited

    private void lblPrincipalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPrincipalMouseEntered
        btnPrincipal.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblPrincipalMouseEntered

    private void lblPrincipalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPrincipalMouseClicked
        lblProductosPocoStock.setText("Últimos clientes dados de alta");
        lblProductosSinStock.setText("Últimas ventas");

        lblCrearNuevo.setVisible(false);
        btnCrearNuevo.setVisible(false);

        btnActualizarTablas.setVisible(false);
        pnlArticulosVenta.setVisible(true);
        tblOrderDetail.setModel(orderDetailModel);
        pnlTablaDetalleVenta.setVisible(false);
        txtTitulo.setText("");
        tableModel = ModeloAplicado.NINGUNO;
        pnlTabla.setVisible(false);
        pnlPrincipal.setVisible(true);

        tblProductosStock.setModel(clientModel);
        clientModel.removeClients();
        clientModel.addClients(clientApiCall.getLatest20Clients());

        tblUltimasVentas.setModel(orderModel);
        orderModel.removeOrders();
        try {
            orderModel.addOrders(orderApiCall.findLatestOrders());
        } catch (Exception ex) {
            System.out.println("Error al hacer el findLatestOrders");
        }

        cmbFiltroStock.setVisible(false);

        this.revalidate();
        this.repaint();
    }//GEN-LAST:event_lblPrincipalMouseClicked

    private void lblVenderMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblVenderMouseExited
        btnVender.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblVenderMouseExited

    private void lblVenderMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblVenderMouseEntered
        btnVender.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblVenderMouseEntered

    private void lblVenderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblVenderMouseClicked

        if (orderDetails.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay ningún producto seleccionado");
            return;
        }

        FormaPago paymentForm = new FormaPago(this, true);
        paymentForm.setVisible(true);

        String paymentMethod = paymentForm.getPaymentMethod();

        if (paymentMethod == null) {
            return;
        }

        LocalDateTime currentDateTime = LocalDateTime.now();

        Order order = new Order(currentDateTime, 0D, paymentMethod, OrderStatus.COMPLETED, orderDetails);

        Order createOrder = null;

        // GetClientByEmail(contacto.mediaflix@gmail.com) el cliente de caja
        Client client = null;

        try {
            client = clientApiCall.searchClientByEmail("contacto.mediaflix@gmail.com");
            System.out.println("Existe el cliente");
        } catch (Exception e) {
            System.out.println("No existe el cliente");
            client = new Client(0L, "Cliente de Caja", "", "contacto.mediaflix@gmail.com", new Date(), true);
            client = clientApiCall.createClient(client);
        }

        try {
            createOrder = orderApiCall.createOrder(client.getClientId(), order);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this, "En este momento no ha sido posible crear el pedido, por favor inténtelo de nuevo.");
            return;
        }

        // Carpeta de destino para los pdfs de tickets
        String folderPath = System.getProperty("user.dir") + File.separator + "tickets";

        // Crea la carpeta si no existe ya
        File folder = new File(folderPath);
        if (!folder.exists()) {
            boolean created = folder.mkdirs();
            if (!created) {
                System.err.println("Error: No se pudo crear la carpeta 'tickets'");
                return;
            }
        }

        // Genera el nombre del archivo de ticket de manera secuencial
        int ticketNumber = createOrder.getOrderId().intValue(); //TODO mirarlo
        String filename = "ticket_" + ticketNumber + ".pdf";
        File outputFile = new File(folderPath, filename);

        // Tamaño del ticket
        float width = 80 * 2.83465f;
        float height = 150 * 2.83465f;

        // Crear el tamaño de página personalizado
        PageSize ticketPageSize = new PageSize(width, height);

        try (PdfWriter writer = new PdfWriter(outputFile); PdfDocument pdf = new PdfDocument(writer); Document document = new Document(pdf, ticketPageSize)) {

            pdfUtilities.generateTicket(document, createOrder);

            txtIdProducto.setText("");
            orderDetailModel.removeOrderDetails();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        String email = JOptionPane.showInputDialog("Inserte el correo electrónico al que desea enviar el ticket").trim();

        if (email == null || email.isBlank() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El email es incorrecto");
            return;
        }

        EmailConfig.createEmailWithAttachment(email, "Pedido Nº " + createOrder.getOrderId(), outputFile);
        EmailConfig.sendEmail("Ticket enviado con éxito");

        JOptionPane.showMessageDialog(this, "Venta registrada");
        pnlTablaDetalleVenta.setVisible(false);
    }//GEN-LAST:event_lblVenderMouseClicked

    private void lblAñadirProductoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAñadirProductoMouseExited
        btnAñadirProducto.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblAñadirProductoMouseExited

    private void lblAñadirProductoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAñadirProductoMouseEntered
        btnAñadirProducto.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblAñadirProductoMouseEntered

    private void lblAñadirProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAñadirProductoMouseClicked

        String str_idProducto = txtIdProducto.getText().trim();

        añadirProductoAPedido(str_idProducto);

    }//GEN-LAST:event_lblAñadirProductoMouseClicked

    private void añadirProductoAPedido(String str_idProducto) {
        if (str_idProducto.isEmpty() || str_idProducto.isBlank()) {
            JOptionPane.showMessageDialog(this, "No ha insertado un id de producto válido. Por favor vuelva a intentarlo");
            txtIdProducto.setText("");
            return;
        }

        long idProducto = Long.parseLong(str_idProducto);

        for (OrderDetail o : orderDetails) {
            if (o.getProductId() == idProducto) {
                JOptionPane.showMessageDialog(this, "El pedido ya contiene ese producto");
                txtIdProducto.setText("");
                return;
            }
        }

        Product product = productApiCall.getById(idProducto);

        if (product != null) {

            pnlTablaDetalleVenta.setVisible(true);

            int quantity = 0;
            try {
                quantity = Integer.parseInt(JOptionPane.showInputDialog("Inserte la cantidad del artículo").trim());
            } catch (Exception e) {
                System.out.println("Error parseo cantidad");
            }

            if (quantity > product.getStock()) {
                JOptionPane.showMessageDialog(this, "Ha insertado una cantidad mayor al stock disponible. El stock para el producto " + product.getProductId() + " es " + product.getStock());
                return;
            } else if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Ha insertado una cantidad menor o igual que 0. Por favor vuelva a intentarlo");
                return;
            }

            OrderDetail detail = new OrderDetail();

            detail.setProductId(idProducto);
            detail.setQuantity(quantity);

            orderDetails.add(detail);
            orderDetailModel.addAnOrderDetail(detail);
            txtIdProducto.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "No existe ningún producto con ese ID");
            txtIdProducto.setText("");
        }
    }


    private void lblActualizarTablasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblActualizarTablasMouseExited
        btnActualizarTablas.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblActualizarTablasMouseExited

    private void lblActualizarTablasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblActualizarTablasMouseEntered
        btnActualizarTablas.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblActualizarTablasMouseEntered

    private void lblActualizarTablasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblActualizarTablasMouseClicked
        try {
            orderModel.removeOrders();
            orderModel.addOrders(orderApiCall.findLatestOrders());
        } catch (Exception ex) {
            System.out.println("Error " + ex.getMessage());
        }
    }//GEN-LAST:event_lblActualizarTablasMouseClicked

    private void txtIdProductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdProductoKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            añadirProductoAPedido(txtIdProducto.getText());
        }

    }//GEN-LAST:event_txtIdProductoKeyPressed

    private void cmbFiltroStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFiltroStockActionPerformed
        String filtro = (String) cmbFiltroStock.getSelectedItem();

        productNoStockModel.mostrarTablaPor(filtro);
        productLowStockModel.mostrarTablaPor(filtro);

    }//GEN-LAST:event_cmbFiltroStockActionPerformed

    private void tblUltimasVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUltimasVentasMouseClicked
        if (tableModel.equals(ModeloAplicado.APROVISIONAMIENTO)) {
            int selectedRow = tblUltimasVentas.getSelectedRow();
            Product p = productNoStockModel.getProduct(selectedRow);

            String str_quantity = JOptionPane.showInputDialog("Indique la cantidad que quiere recibir del producto (" + p.getProductId() + ") " + p.getTitle());

            if (str_quantity == null) {
                return;
            }

            if (str_quantity.isBlank()) {
                JOptionPane.showMessageDialog(this, "Ha insertado una cantidad menor que cero. Por favor vuelva a intentarlo");
                return;
            }

            try {
                int quantity = Integer.parseInt(str_quantity);

                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(this, "Ha insertado una cantidad menor que cero. Por favor vuelva a intentarlo");
                    return;
                }

                switch (p.getProductType()) {
                    case BOOK:
                        Book b = bookApiCall.getBookById(p.getProductId());
                        b.setStock(b.getStock() + quantity);
                        bookApiCall.updateBook(b);
                        break;
                    case GAME:
                        Game g = gameApiCall.getGameById(p.getProductId());
                        g.setStock(g.getStock() + quantity);
                        gameApiCall.updateGame(g);
                        break;
                    case MOVIE:
                        Movie m = movieApiCall.getMovieById(p.getProductId());
                        m.setStock(m.getStock() + quantity);
                        movieApiCall.updateMovie(m);
                        break;
                }

                // Lo pongo a vigente para que se pueda realizar la compra
                productApiCall.activateProduct(p.getProductId());

                JOptionPane.showMessageDialog(this, "¡Ha recibido " + quantity + " unidades del artículo seleccionado!");

            } catch (NumberFormatException numberFormatException) {
                JOptionPane.showMessageDialog(this, "Ha insertado una cantidad no válida. Por favor vuelva a intentarlo");
                return;
            }

            productNoStockModel.removeProducts();
            tblUltimasVentas.setModel(productNoStockModel);
            productNoStockModel.addProducts(productApiCall.findAllProductsZeroStock());

            productLowStockModel.removeProducts();
            tblProductosStock.setModel(productLowStockModel);
            productLowStockModel.addProducts(productApiCall.findAllProductsStockBetween1And5());

        }
    }//GEN-LAST:event_tblUltimasVentasMouseClicked

    private void tblProductosStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductosStockMouseClicked
        if (tableModel.equals(ModeloAplicado.APROVISIONAMIENTO)) {
            int selectedRow = tblProductosStock.getSelectedRow();
            Product p = productLowStockModel.getProduct(selectedRow);

            String str_quantity = JOptionPane.showInputDialog("Indique la cantidad que quiere recibir del producto (" + p.getProductId() + ") " + p.getTitle());

            if (str_quantity == null) {
                return;
            }

            if (str_quantity.isBlank()) {
                JOptionPane.showMessageDialog(this, "Ha insertado una cantidad menor que cero. Por favor vuelva a intentarlo");
                return;
            }

            try {
                int quantity = Integer.parseInt(str_quantity);

                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(this, "Ha insertado una cantidad menor que cero. Por favor vuelva a intentarlo");
                    return;
                }

                switch (p.getProductType()) {
                    case BOOK:
                        Book b = bookApiCall.getBookById(p.getProductId());
                        b.setStock(b.getStock() + quantity);
                        bookApiCall.updateBook(b);
                        break;
                    case GAME:
                        Game g = gameApiCall.getGameById(p.getProductId());
                        g.setStock(g.getStock() + quantity);
                        gameApiCall.updateGame(g);
                        break;
                    case MOVIE:
                        Movie m = movieApiCall.getMovieById(p.getProductId());
                        m.setStock(m.getStock() + quantity);
                        movieApiCall.updateMovie(m);
                        break;
                }

                // Lo pongo a vigente para que se pueda realizar la compra
                productApiCall.activateProduct(p.getProductId());

                JOptionPane.showMessageDialog(this, "¡Ha recibido " + quantity + " unidades del artículo seleccionado!");

            } catch (NumberFormatException numberFormatException) {
                JOptionPane.showMessageDialog(this, "Ha insertado una cantidad no válida. Por favor vuelva a intentarlo");
                return;
            }

            productNoStockModel.removeProducts();
            tblUltimasVentas.setModel(productNoStockModel);
            productNoStockModel.addProducts(productApiCall.findAllProductsZeroStock());

            productLowStockModel.removeProducts();
            tblProductosStock.setModel(productLowStockModel);
            productLowStockModel.addProducts(productApiCall.findAllProductsStockBetween1And5());

        }
    }//GEN-LAST:event_tblProductosStockMouseClicked

    private void txtIdProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtIdProductoMouseClicked
        txtIdProducto.setText("");
    }//GEN-LAST:event_txtIdProductoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnActualizarTablas;
    private javax.swing.JPanel btnAprovisionamiento;
    private javax.swing.JPanel btnAñadirProducto;
    private javax.swing.JPanel btnBuscar;
    private javax.swing.JPanel btnCerrarSesion;
    private javax.swing.JPanel btnClientes;
    private javax.swing.JPanel btnConfiguracion;
    private javax.swing.JPanel btnCrearNuevo;
    private javax.swing.JPanel btnEmpleados;
    private javax.swing.JLabel btnExit;
    private javax.swing.JPanel btnGenerarInforme;
    private javax.swing.JPanel btnJuegos;
    private javax.swing.JPanel btnLibros;
    private javax.swing.JPanel btnPeliculas;
    private javax.swing.JPanel btnPrincipal;
    private javax.swing.JPanel btnProductos;
    private javax.swing.JPanel btnVender;
    private javax.swing.JPanel btnVentas;
    private javax.swing.JComboBox<String> cmbFiltroStock;
    private javax.swing.JComboBox<String> cmbFiltroTabla;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblActualizarTablas;
    private javax.swing.JLabel lblAprovisionamiento;
    private javax.swing.JLabel lblAñadirProducto;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblCerrarSesion;
    private javax.swing.JLabel lblClientes;
    private javax.swing.JLabel lblConfiguracion;
    private javax.swing.JLabel lblCrearNuevo;
    private javax.swing.JLabel lblEmpleados;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblGenerarInforme;
    private javax.swing.JLabel lblJuegos;
    private javax.swing.JLabel lblLibros;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblNombreEmpleado;
    private javax.swing.JLabel lblPeliculas;
    private javax.swing.JLabel lblPrincipal;
    private javax.swing.JLabel lblProductos;
    private javax.swing.JLabel lblProductosPocoStock;
    private javax.swing.JLabel lblProductosSinStock;
    private javax.swing.JLabel lblSelecFiltro;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblVender;
    private javax.swing.JLabel lblVentas;
    private javax.swing.JPanel pnlActualizaciones;
    private javax.swing.JPanel pnlArticulosVenta;
    private javax.swing.JPanel pnlBotones;
    private javax.swing.JPanel pnlBotonesProductos;
    private javax.swing.JPanel pnlFondo;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JPanel pnlTabla;
    private javax.swing.JPanel pnlTablaDetalleVenta;
    private javax.swing.JTable tblOrderDetail;
    private javax.swing.JTable tblProductosStock;
    private javax.swing.JTable tblTable;
    private javax.swing.JTable tblUltimasVentas;
    private javax.swing.JTextField txtIdProducto;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables
}
