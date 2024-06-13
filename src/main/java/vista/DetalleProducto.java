package vista;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import entities.Book;
import entities.Game;
import entities.Movie;
import entities.Product;
import entities.ProductType;
import static entities.ProductType.BOOK;
import static entities.ProductType.GAME;
import static entities.ProductType.MOVIE;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.sql.Date;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import service.CloudinaryService;
import service.ImageUtils;
import static vista.EstadoNavegacion.EDITAR;

public class DetalleProducto extends javax.swing.JDialog {

    DefaultComboBoxModel<ProductType> modeloTipoProducto;
    EstadoNavegacion estado;
    Principal principal;

    CloudinaryService cloudinaryService;

    Product p;

    // Crear un objeto SimpleDateFormat para el formato de la API
    SimpleDateFormat sdfApi = new SimpleDateFormat("yyyy-MM-dd");

    SimpleDateFormat sdfTxt = new SimpleDateFormat("dd/MM/yyyy");

    //Constructor vacío para crear un Producto (Libro, Película, Juego)
    public DetalleProducto(Principal principal, boolean modal) {
        super(principal, modal);
        initComponents();
        this.principal = principal;

        estado = EstadoNavegacion.NUEVO;

        inicializarDialog();

        lblBarCode.setVisible(false);

        pnlBotonesOperaciones.setVisible(false);

        pnlCamposIndividuales.setVisible(false);
        //inicializarComponentesProducto(p);

        actualizarComponentes(estado);
        btnEditar.setVisible(false);

        //Para que no se pueda modificar por error la url
        txtUrl.setEditable(false);

    }

    public DetalleProducto(Principal principal, boolean modal, Product p) {
        super(principal, modal);
        initComponents();
        this.principal = principal;
        this.p = p;

        estado = EstadoNavegacion.VISUALIZACION;

        inicializarDialog();

        pnlBotonesOperaciones.setVisible(true);

        inicializarComponentesProducto(p);

        actualizarComponentes(estado);

        generateBarCode(String.valueOf(p.getProductId()));

    }

    private void inicializarDialog() {
        // Establece el tamaño del dialogo para que ocupe toda la pantalla
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = ge.getMaximumWindowBounds();
        this.setSize(bounds.getSize());

        this.setLocationRelativeTo(null);

        cloudinaryService = new CloudinaryService();

        modeloTipoProducto = new DefaultComboBoxModel<>();

        //spinnerModel = new CustomDateSpinnerModel(value, ABORT, WIDTH, PROPERTIES);
        for (ProductType t : ProductType.values()) {
            modeloTipoProducto.addElement(t);
        }

        cmbTipo.setModel(modeloTipoProducto);
        
        lblUrl.setVisible(false);
        txtUrl.setVisible(false);
        scrollUrl.setVisible(false);

        ImageIcon image = new ImageIcon(getClass().getResource("/images/X_logo.png"));
        btnExit.setIcon(new ImageIcon(image.getImage().getScaledInstance(btnExit.getWidth(), btnExit.getHeight(), Image.SCALE_DEFAULT)));
    }

    private void inicializarComponentesProducto(Product p) {

        txtTitulo.setText(p.getTitle());
        txtDescripcion.setText(p.getDescription());
        spnStock.setValue(p.getStock());
        txtIdioma.setText(p.getLanguage());
        cmbTipo.setSelectedItem(p.getProductType());
        spnPrice.setValue(p.getPrice());
        spnRating.setValue(p.getRating());
        txtUrl.setText(p.getUrl());

        Image imagen = creaImagenDesdeURL(p.getUrl());

        lblImagen.setIcon(new ImageIcon(imagen.getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_SMOOTH)));

        txtGenero.setText(p.getGenre());

        txtReleaseDate.setDate(p.getReleaseDate());

        cmbDisponible.setSelectedItem(p.getAvailable() ? "Si" : "No");

        ProductType productType = p.getProductType();

        switch (productType) {
            case BOOK:
                Book book = (Book) p;

                lblAutor.setText("Autor");
                lblISBN.setText("ISBN");
                lblNumeroPaginas.setText("Número Páginas");
                lblEditorial.setText("Editorial");

                txtAutor.setText(book.getAuthor());
                txtISBN.setText(String.valueOf(book.getIsbn()));
                spnPageNumber.setValue(book.getPageNumber());
                txtEditorial.setText(book.getPublisher());
                lblMinutosHoras.setText("Páginas");
                break;
            case MOVIE:
                Movie movie = (Movie) p;

                lblAutor.setText("Director");
                lblISBN.setText("Estudio");
                lblNumeroPaginas.setText("Duración");
                lblEditorial.setText("URL Trailer");

                txtAutor.setText(movie.getDirector());
                txtISBN.setText(String.valueOf(movie.getStudio()));
                spnPageNumber.setValue(movie.getDuration());
                txtEditorial.setText(movie.getUrlTrailer());
                lblMinutosHoras.setText("Minutos");
                break;
            case GAME:
                Game game = (Game) p;

                lblAutor.setText("Desarrollador");
                lblISBN.setText("Plataforma");
                lblNumeroPaginas.setText("Duración");
                lblEditorial.setText("URL Trailer");

                txtAutor.setText(game.getDeveloper());
                txtISBN.setText(String.valueOf(game.getPlatform()));
                spnPageNumber.setValue(game.getDuration());
                txtEditorial.setText(game.getUrlTrailer());
                lblMinutosHoras.setText("Horas");
                break;
            default:
                break;
        }

    }

    private Image creaImagenDesdeURL(String url) {
        Image image = null;

        URL uri;
        try {
            uri = new URL(url);
            image = ImageIO.read(uri);
        } catch (MalformedURLException ex) {
            System.out.println("Url mal formada");
        } catch (IOException ex) {

        }
        return image;
    }

    private void actualizarComponentes(EstadoNavegacion estado) {

        switch (estado) {
            case VISUALIZACION:
                txtEditables(false);
                visibilidadBotones(false);
                lblBarCode.setVisible(true);
                lblEditar.setVisible(true);
                btnEditar.setVisible(true);
                btnNuevo.setVisible(true);
                lblNuevo.setVisible(true);
                break;
            case EDITAR:
                txtEditables(true);
                visibilidadBotones(false);
                lblBarCode.setVisible(true);
                btnNuevo.setVisible(false);
                lblNuevo.setVisible(false);
                break;
            case NUEVO:
                txtEditables(true);
                visibilidadBotones(true);
                limpiarTextos();
                lblBarCode.setVisible(false);
                lblEditar.setVisible(false);
                btnEditar.setVisible(false);
                break;
        }

    }

    private void txtEditables(boolean activar) {
        txtTitulo.setEditable(activar);
        txtDescripcion.setEditable(activar);
        spnStock.setEnabled(activar);
        txtIdioma.setEditable(activar);
        cmbTipo.setEnabled(activar);
        spnPrice.setEnabled(activar);
        spnRating.setEnabled(activar);
        txtUrl.setEditable(activar);
        txtGenero.setEditable(activar);
        cmbDisponible.setEnabled(activar);
        txtReleaseDate.setEnabled(activar);

        txtAutor.setEditable(activar);
        txtISBN.setEditable(activar);
        spnPageNumber.setEnabled(activar);
        txtEditorial.setEditable(activar);
    }

    private void limpiarTextos() {
        txtTitulo.setText("");
        txtDescripcion.setText("");
        spnStock.setValue(0);
        txtIdioma.setText("");
        cmbTipo.setSelectedIndex(-1);
        spnPrice.setValue(0);
        spnRating.setValue(0);
        txtUrl.setText("");
        txtGenero.setText("");
        cmbDisponible.setSelectedIndex(-1);
        txtReleaseDate.setDate(new java.util.Date());

        txtAutor.setText("");
        txtISBN.setText("");
        spnPageNumber.setValue(0);
        txtEditorial.setText("");

        lblImagen.setIcon(null);

    }

    private void visibilidadBotones(boolean visible) {
        btnCargarImagenLocal.setVisible(visible);
        btnCargarImagenUrl.setVisible(visible);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblImagen = new javax.swing.JLabel();
        cmbDisponible = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblUrl = new javax.swing.JLabel();
        spnRating = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        spnPrice = new javax.swing.JSpinner();
        cmbTipo = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtIdioma = new javax.swing.JTextField();
        spnStock = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        scrollUrl = new javax.swing.JScrollPane();
        txtUrl = new javax.swing.JTextArea();
        pnlHeader = new javax.swing.JPanel();
        btnExit = new javax.swing.JLabel();
        pnlCamposIndividuales = new javax.swing.JPanel();
        lblMinutosHoras = new javax.swing.JLabel();
        spnPageNumber = new javax.swing.JSpinner();
        lblNumeroPaginas = new javax.swing.JLabel();
        lblEditorial = new javax.swing.JLabel();
        lblISBN = new javax.swing.JLabel();
        lblAutor = new javax.swing.JLabel();
        txtAutor = new javax.swing.JTextField();
        txtISBN = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtEditorial = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JPanel();
        lblCancelar = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JPanel();
        lblAceptar = new javax.swing.JLabel();
        btnCargarImagenLocal = new javax.swing.JPanel();
        lblCargarImagenLocal = new javax.swing.JLabel();
        btnCargarImagenUrl = new javax.swing.JPanel();
        lblCargarImagenUrl = new javax.swing.JLabel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        pnlBotonesOperaciones = new javax.swing.JPanel();
        btnEditar = new javax.swing.JPanel();
        lblEditar = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JPanel();
        lblNuevo = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtGenero = new javax.swing.JTextArea();
        txtReleaseDate = new com.toedter.calendar.JDateChooser();
        lblBarCode = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(61, 27, 99));

        cmbDisponible.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbDisponible.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        cmbDisponible.setSelectedIndex(-1);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Disponible");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Fecha Salida");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Género");

        lblUrl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblUrl.setForeground(new java.awt.Color(255, 255, 255));
        lblUrl.setText("URL Imagen");

        spnRating.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        spnRating.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 5.0d, 0.10000000149011612d));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Rating");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Price");

        spnPrice.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        spnPrice.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));

        cmbTipo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Tipo");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Idioma");

        txtIdioma.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        spnStock.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        spnStock.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Stock");

        txtDescripcion.setColumns(20);
        txtDescripcion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setRows(5);
        txtDescripcion.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtDescripcion);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Descripción");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Titulo");

        txtTitulo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtUrl.setColumns(20);
        txtUrl.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtUrl.setRows(5);
        txtUrl.setAutoscrolls(false);
        scrollUrl.setViewportView(txtUrl);

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

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHeaderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlCamposIndividuales.setBackground(new java.awt.Color(61, 27, 99));

        lblMinutosHoras.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblMinutosHoras.setForeground(new java.awt.Color(255, 255, 255));
        lblMinutosHoras.setText("páginas");

        spnPageNumber.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        spnPageNumber.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 10));

        lblNumeroPaginas.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNumeroPaginas.setForeground(new java.awt.Color(255, 255, 255));
        lblNumeroPaginas.setText("Número Páginas");

        lblEditorial.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblEditorial.setForeground(new java.awt.Color(255, 255, 255));
        lblEditorial.setText("Editorial");

        lblISBN.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblISBN.setForeground(new java.awt.Color(255, 255, 255));
        lblISBN.setText("ISBN");

        lblAutor.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblAutor.setForeground(new java.awt.Color(255, 255, 255));
        lblAutor.setText("Autor");

        txtAutor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtISBN.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtEditorial.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jScrollPane2.setViewportView(txtEditorial);

        javax.swing.GroupLayout pnlCamposIndividualesLayout = new javax.swing.GroupLayout(pnlCamposIndividuales);
        pnlCamposIndividuales.setLayout(pnlCamposIndividualesLayout);
        pnlCamposIndividualesLayout.setHorizontalGroup(
            pnlCamposIndividualesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCamposIndividualesLayout.createSequentialGroup()
                .addGroup(pnlCamposIndividualesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCamposIndividualesLayout.createSequentialGroup()
                        .addGap(0, 12, Short.MAX_VALUE)
                        .addGroup(pnlCamposIndividualesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEditorial, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(pnlCamposIndividualesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblNumeroPaginas, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(pnlCamposIndividualesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCamposIndividualesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(pnlCamposIndividualesLayout.createSequentialGroup()
                            .addComponent(spnPageNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(lblMinutosHoras))
                        .addComponent(txtISBN, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                        .addComponent(txtAutor))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(107, 107, 107))
        );
        pnlCamposIndividualesLayout.setVerticalGroup(
            pnlCamposIndividualesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCamposIndividualesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCamposIndividualesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlCamposIndividualesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(pnlCamposIndividualesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnPageNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMinutosHoras, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNumeroPaginas, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlCamposIndividualesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCamposIndividualesLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(lblEditorial, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlCamposIndividualesLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnCancelar.setBackground(new java.awt.Color(61, 27, 99));
        btnCancelar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        lblCancelar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblCancelar.setForeground(new java.awt.Color(255, 255, 255));
        lblCancelar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCancelar.setText("Cancelar");
        lblCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCancelarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCancelarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCancelarMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnCancelarLayout = new javax.swing.GroupLayout(btnCancelar);
        btnCancelar.setLayout(btnCancelarLayout);
        btnCancelarLayout.setHorizontalGroup(
            btnCancelarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnCancelarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        btnCancelarLayout.setVerticalGroup(
            btnCancelarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        btnAceptar.setBackground(new java.awt.Color(61, 27, 99));
        btnAceptar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        lblAceptar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblAceptar.setForeground(new java.awt.Color(255, 255, 255));
        lblAceptar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAceptar.setText("Aceptar");
        lblAceptar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAceptar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAceptarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblAceptarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblAceptarMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnAceptarLayout = new javax.swing.GroupLayout(btnAceptar);
        btnAceptar.setLayout(btnAceptarLayout);
        btnAceptarLayout.setHorizontalGroup(
            btnAceptarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnAceptarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        btnAceptarLayout.setVerticalGroup(
            btnAceptarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAceptar, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        btnCargarImagenLocal.setBackground(new java.awt.Color(61, 27, 99));
        btnCargarImagenLocal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        lblCargarImagenLocal.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblCargarImagenLocal.setForeground(new java.awt.Color(255, 255, 255));
        lblCargarImagenLocal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCargarImagenLocal.setText("Cargar Imagen Local");
        lblCargarImagenLocal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCargarImagenLocal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCargarImagenLocalMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCargarImagenLocalMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCargarImagenLocalMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnCargarImagenLocalLayout = new javax.swing.GroupLayout(btnCargarImagenLocal);
        btnCargarImagenLocal.setLayout(btnCargarImagenLocalLayout);
        btnCargarImagenLocalLayout.setHorizontalGroup(
            btnCargarImagenLocalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblCargarImagenLocal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
        );
        btnCargarImagenLocalLayout.setVerticalGroup(
            btnCargarImagenLocalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblCargarImagenLocal, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        btnCargarImagenUrl.setBackground(new java.awt.Color(61, 27, 99));
        btnCargarImagenUrl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        lblCargarImagenUrl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblCargarImagenUrl.setForeground(new java.awt.Color(255, 255, 255));
        lblCargarImagenUrl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCargarImagenUrl.setText("Cargar Imagen desde URL");
        lblCargarImagenUrl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCargarImagenUrl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCargarImagenUrlMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCargarImagenUrlMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCargarImagenUrlMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnCargarImagenUrlLayout = new javax.swing.GroupLayout(btnCargarImagenUrl);
        btnCargarImagenUrl.setLayout(btnCargarImagenUrlLayout);
        btnCargarImagenUrlLayout.setHorizontalGroup(
            btnCargarImagenUrlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblCargarImagenUrl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        btnCargarImagenUrlLayout.setVerticalGroup(
            btnCargarImagenUrlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblCargarImagenUrl, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        pnlBotonesOperaciones.setBackground(new java.awt.Color(61, 27, 99));

        btnEditar.setBackground(new java.awt.Color(61, 27, 99));
        btnEditar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        lblEditar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblEditar.setForeground(new java.awt.Color(255, 255, 255));
        lblEditar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEditar.setText("Editar");
        lblEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEditarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblEditarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblEditarMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnEditarLayout = new javax.swing.GroupLayout(btnEditar);
        btnEditar.setLayout(btnEditarLayout);
        btnEditarLayout.setHorizontalGroup(
            btnEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnEditarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        btnEditarLayout.setVerticalGroup(
            btnEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblEditar, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        btnNuevo.setBackground(new java.awt.Color(61, 27, 99));
        btnNuevo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        lblNuevo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblNuevo.setForeground(new java.awt.Color(255, 255, 255));
        lblNuevo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNuevo.setText("Nuevo");
        lblNuevo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblNuevo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNuevoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblNuevoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblNuevoMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnNuevoLayout = new javax.swing.GroupLayout(btnNuevo);
        btnNuevo.setLayout(btnNuevoLayout);
        btnNuevoLayout.setHorizontalGroup(
            btnNuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnNuevoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        btnNuevoLayout.setVerticalGroup(
            btnNuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlBotonesOperacionesLayout = new javax.swing.GroupLayout(pnlBotonesOperaciones);
        pnlBotonesOperaciones.setLayout(pnlBotonesOperacionesLayout);
        pnlBotonesOperacionesLayout.setHorizontalGroup(
            pnlBotonesOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBotonesOperacionesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(231, 231, 231))
        );
        pnlBotonesOperacionesLayout.setVerticalGroup(
            pnlBotonesOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBotonesOperacionesLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pnlBotonesOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEditar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        txtGenero.setColumns(20);
        txtGenero.setRows(5);
        jScrollPane3.setViewportView(txtGenero);

        txtReleaseDate.setDateFormatString("dd/MM/yyyy");
        txtReleaseDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblBarCode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBarCodeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel9))
                                .addGap(79, 79, 79)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtReleaseDate, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(lblUrl)
                                    .addComponent(jLabel2))
                                .addGap(82, 82, 82)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(spnStock, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtTitulo)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                                        .addComponent(txtIdioma, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(spnPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(spnRating, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(scrollUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(204, 204, 204)
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(pnlCamposIndividuales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(281, 281, 281)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(pnlBotonesOperaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(88, 88, 88)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(filler3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnCargarImagenUrl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnCargarImagenLocal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblBarCode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addGap(38, 38, 38))
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmbTipo, spnPrice, spnRating, spnStock, txtIdioma});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(136, 136, 136)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnCargarImagenLocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(51, 51, 51)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(filler3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnCargarImagenUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(128, 128, 128)
                                .addComponent(lblBarCode, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 666, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(27, 27, 27)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(spnStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(txtIdioma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(spnPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(spnRating, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(scrollUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtReleaseDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbDisponible, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 82, Short.MAX_VALUE)
                                .addComponent(pnlBotonesOperaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(pnlCamposIndividuales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())))))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, txtTitulo});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblEditarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEditarMouseClicked

        estado = EstadoNavegacion.EDITAR;

        actualizarComponentes(estado);

        visibilidadBotones(true);

    }//GEN-LAST:event_lblEditarMouseClicked

    private void lblEditarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEditarMouseEntered
        btnEditar.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblEditarMouseEntered

    private void lblEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEditarMouseExited
        btnEditar.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblEditarMouseExited

    private void btnExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseClicked
        this.dispose();
    }//GEN-LAST:event_btnExitMouseClicked

    private void btnExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseEntered
        btnExit.setBackground(new Color(204, 173, 228));
    }//GEN-LAST:event_btnExitMouseEntered

    private void btnExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseExited
        btnExit.setBackground(Color.white);
    }//GEN-LAST:event_btnExitMouseExited

    private void lblNuevoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNuevoMouseClicked
        pnlCamposIndividuales.setVisible(false);
        estado = EstadoNavegacion.NUEVO;
        actualizarComponentes(estado);

    }//GEN-LAST:event_lblNuevoMouseClicked

    private void lblNuevoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNuevoMouseEntered
        btnNuevo.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblNuevoMouseEntered

    private void lblNuevoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNuevoMouseExited
        btnNuevo.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblNuevoMouseExited

    private void cmbTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoActionPerformed

        txtAutor.setText("");
        txtISBN.setText("");
        txtEditorial.setText("");
        spnPageNumber.setValue(0);

        ProductType productType = (ProductType) cmbTipo.getSelectedItem();

        muestraPanelCamposIndividuales(productType);

    }//GEN-LAST:event_cmbTipoActionPerformed

    private void lblCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCancelarMouseClicked
        this.dispose();
    }//GEN-LAST:event_lblCancelarMouseClicked

    private void lblCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCancelarMouseEntered
        btnCancelar.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblCancelarMouseEntered

    private void lblCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCancelarMouseExited
        btnCancelar.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblCancelarMouseExited

    private void lblAceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAceptarMouseClicked

        String titulo = null;
        String descripcion = null;
        Integer stock = null;
        String idioma = null;
        ProductType tipo = null;
        Double precio = null;
        Double rating = null;
        String url = null;
        String genero = null;
        java.util.Date utilDate = null;

        try {
            titulo = txtTitulo.getText();
            descripcion = txtDescripcion.getText();
            stock = (Integer) spnStock.getValue();
            idioma = txtIdioma.getText();
            tipo = (ProductType) cmbTipo.getSelectedItem();
            precio = ((Double) spnPrice.getValue());
            rating = ((Double) spnRating.getValue());
            url = txtUrl.getText();
            genero = txtGenero.getText();
            utilDate = txtReleaseDate.getDate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ha ocurrido un error, alguno de los campos no es válido. Por favor, vuelva a intentarlo. Gracias");
            return;
        }

        // Convertir java.util.Date a java.sql.Date
        Date fechaSalida = new Date(utilDate.getTime());

        String str_disponible = cmbDisponible.getSelectedItem().toString();
        Boolean disponible = str_disponible.equalsIgnoreCase("si") ? true : false;

        String autor = txtAutor.getText();
        Integer numeroPaginas = (Integer) spnPageNumber.getValue();
        String editorial = txtEditorial.getText();

        int statusCode;

        if (estado.equals(EstadoNavegacion.NUEVO)) {
            if (url == null || url.isEmpty() || url.isBlank()) {
                url = "https://res.cloudinary.com/dulpvbj0d/image/upload/v1712601008/mediaflix/kzpz2mwuotpppafdpx8c.png";
            }
            switch (tipo) {
                case BOOK:
                    Book book = new Book(titulo, descripcion, stock, idioma, tipo, precio, rating, url, genero, fechaSalida, disponible, Long.valueOf(txtISBN.getText()), autor, editorial, numeroPaginas);
                    try {
                        Book createBook = principal.bookApiCall.createBook(book);
                        inicializarComponentesProducto(createBook);
                        return;
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Ha ocurrido un error al crear el libro. Por favor, vuelva a intentarlo. Gracias");
                    }

                    JOptionPane.showMessageDialog(this, "¡El libro ha sido creado con éxito!");

                    p = book;
                    break;
                case MOVIE:
                    Movie movie = new Movie(titulo, descripcion, stock, idioma, tipo, precio, rating, url, genero, fechaSalida, disponible, autor, numeroPaginas, txtISBN.getText(), editorial);
                    
                    try {
                        Movie createMovie = principal.movieApiCall.createMovie(movie);
                        inicializarComponentesProducto(createMovie);
                        return;
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Ha ocurrido un error al crear la película. Por favor, vuelva a intentarlo. Gracias");
                    }

                    JOptionPane.showMessageDialog(this, "¡La película ha sido creado con éxito!");
                    p = movie;
                    break;
                case GAME:
                    Game game = new Game(titulo, descripcion, stock, idioma, tipo, precio, rating, url, genero, fechaSalida, disponible, autor, txtISBN.getText(), numeroPaginas, editorial);
                    
                    try {
                        Game createGame = principal.gameApiCall.createGame(game);
                        inicializarComponentesProducto(createGame);
                        return;
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Ha ocurrido un error al crear el juego. Por favor, vuelva a intentarlo. Gracias");
                    }

                    JOptionPane.showMessageDialog(this, "¡El juego ha sido creado con éxito!");
                    p = game;
                    break;
            }

        } else if (estado.equals(EstadoNavegacion.EDITAR)) {

            switch (tipo) {
                case BOOK:
                    Book book = new Book(p.getProductId(), titulo, descripcion, stock, idioma, tipo, precio, rating, url, genero, fechaSalida, disponible, Long.valueOf(txtISBN.getText()), autor, editorial, numeroPaginas);
                    statusCode = principal.bookApiCall.updateBook(book);

                    if (statusCode == 200) {
                        JOptionPane.showMessageDialog(this, "El libro ha sido actualizado con éxito");
                    } else if (statusCode == 400 || statusCode == 404) {
                        JOptionPane.showMessageDialog(this, "Ha ocurrido un problema al actualizar el libro, intentelo de nuevo. Gracias");
                    }
                    p = book;

                    break;

                case MOVIE:
                    Movie movie = new Movie(p.getProductId(), titulo, descripcion, stock, idioma, tipo, precio, rating, url, genero, fechaSalida, disponible, autor, numeroPaginas, txtISBN.getText(), editorial);
                    statusCode = principal.movieApiCall.updateMovie(movie);
                    if (statusCode == 200) {
                        JOptionPane.showMessageDialog(this, "La película ha sido actualizada con éxito");
                    } else if (statusCode == 400 || statusCode == 404) {
                        JOptionPane.showMessageDialog(this, "Ha ocurrido un problema al actualizar la película, intentelo de nuevo. Gracias");
                    }
                    p = movie;
                    break;
                case GAME:
                    Game game = new Game(p.getProductId(), titulo, descripcion, stock, idioma, tipo, precio, rating, url, genero, fechaSalida, disponible, autor, txtISBN.getText(), numeroPaginas, editorial);
                    statusCode = principal.gameApiCall.updateGame(game);
                    if (statusCode == 200) {
                        JOptionPane.showMessageDialog(this, "El juego ha sido actualizado con éxito");
                    } else if (statusCode == 400 || statusCode == 404) {
                        JOptionPane.showMessageDialog(this, "Ha ocurrido un problema al actualizar el juego, intentelo de nuevo. Gracias");
                    }
                    p = game;
                    break;
            }

        } else if (estado.equals(EstadoNavegacion.ACTIVAR_DESACTIVAR)) {

            if (p.getAvailable()) { // Está disponible
                int option = JOptionPane.showConfirmDialog(this, "¿Está seguro de que quiere desactivar el producto actual?", "Desactivar producto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (option == JOptionPane.OK_OPTION) { //Se seleccionó aceptar
                    principal.productApiCall.deactivateProduct(p.getProductId()); // Si está disponible lo desactivamos
                    JOptionPane.showMessageDialog(this, "Se ha cambiado el estado del producto a \"No Disponible\"");
                } else {
                    JOptionPane.showMessageDialog(this, "Se ha cancelado la operación");
                    return;
                }

            } else {
                int option = JOptionPane.showConfirmDialog(this, "¿Está seguro de que quiere activar el producto actual?", "Activar producto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (option == JOptionPane.OK_OPTION) { //Se seleccionó aceptar
                    principal.productApiCall.activateProduct(p.getProductId()); // Si está disponible lo desactivamos
                    JOptionPane.showMessageDialog(this, "Se ha cambiado el estado del producto a \"Disponible\"");
                } else {
                    JOptionPane.showMessageDialog(this, "Se ha cancelado la operación");
                    return;
                }
            }

        }

        this.dispose();
    }//GEN-LAST:event_lblAceptarMouseClicked

    private void lblAceptarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAceptarMouseEntered
        btnAceptar.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblAceptarMouseEntered

    private void lblAceptarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAceptarMouseExited
        btnAceptar.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblAceptarMouseExited

    private void lblCargarImagenLocalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCargarImagenLocalMouseClicked

        JFileChooser fc = new JFileChooser();

        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagenes", "jpg", "png");

        fc.addChoosableFileFilter(filtro);

        fc.setDialogType(JFileChooser.OPEN_DIALOG);
        fc.setDialogTitle("Selecione una imagen");

        int boton = fc.showOpenDialog(this);

        if (boton == JFileChooser.CANCEL_OPTION) {
            return;
        }

        File ficheroImagen = fc.getSelectedFile();
        try {

            ImageUtils imageUtils = new ImageUtils();
            String imageToBase64 = imageUtils.imageToBase64(ficheroImagen);

            String imageUrl = cloudinaryService.upload(imageToBase64);

            txtUrl.setText(imageUrl);
            txtUrl.setEditable(false);

            Image imagenLibro = this.creaImagenDesdeURL(imageUrl);

            lblImagen.setIcon(new ImageIcon(imagenLibro.getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_SMOOTH)));

        } catch (IOException ex) {
            System.out.println("Exception url");
        }

    }//GEN-LAST:event_lblCargarImagenLocalMouseClicked

    private void lblCargarImagenLocalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCargarImagenLocalMouseEntered
        btnCargarImagenLocal.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblCargarImagenLocalMouseEntered

    private void lblCargarImagenLocalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCargarImagenLocalMouseExited
        btnCargarImagenLocal.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblCargarImagenLocalMouseExited

    private void lblCargarImagenUrlMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCargarImagenUrlMouseClicked

        SubirUrlImagen subirImagen = new SubirUrlImagen(this, true, cloudinaryService);
        subirImagen.setVisible(true);

        String urlCloudinary = subirImagen.getUrlCloudinary();

        txtUrl.setText(urlCloudinary);
        txtUrl.setEditable(false);

        Image imagenLibro = this.creaImagenDesdeURL(urlCloudinary);

        lblImagen.setIcon(new ImageIcon(imagenLibro.getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_SMOOTH)));
    }//GEN-LAST:event_lblCargarImagenUrlMouseClicked

    private void lblCargarImagenUrlMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCargarImagenUrlMouseEntered
        btnCargarImagenUrl.setBackground(new Color(204, 173, 228));
    }//GEN-LAST:event_lblCargarImagenUrlMouseEntered

    private void lblCargarImagenUrlMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCargarImagenUrlMouseExited
        btnCargarImagenUrl.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblCargarImagenUrlMouseExited

    private void lblBarCodeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBarCodeMouseClicked


    }//GEN-LAST:event_lblBarCodeMouseClicked

    private void generateBarCode(String data) {
        String barcodeData = data;
        BarcodeFormat format = BarcodeFormat.CODE_128;

        BufferedImage barcodeImage = null;

        try {
            Writer writer = new MultiFormatWriter();

            BitMatrix matrix = writer.encode(barcodeData, format, 200, 100);

            int width = matrix.getWidth();
            int height = matrix.getHeight();
            barcodeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    barcodeImage.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }

        } catch (WriterException e) {
            e.printStackTrace();
        }

        Image swingImage = new ImageIcon(barcodeImage).getImage();
        lblBarCode.setIcon(new ImageIcon(swingImage));

//Este guarda la imagen del codigo de barras como png
//        String barcodeData = data;
//        BarcodeFormat format = BarcodeFormat.CODE_128;
//
//        BufferedImage barcodeImage = null;
//
//        try {
//            Writer writer = new MultiFormatWriter();
//            BitMatrix matrix = writer.encode(barcodeData, format, 200, 100); // Ajusta el ancho y alto según sea necesario
//
//            int width = matrix.getWidth();
//            int height = matrix.getHeight();
//            barcodeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//
//            for (int x = 0; x < width; x++) {
//                for (int y = 0; y < height; y++) {
//                    barcodeImage.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
//                }
//            }
//
//            // Save the image to a file
//            String filename = "barcode.png"; // Replace with desired filename and format
//            ImageIO.write(barcodeImage, "png", new File(filename));
//
//        } catch (WriterException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Error saving barcode image: " + e.getMessage());
//        }
//
//        // Update the label icon (optional)
//        if (lblBarCode != null) {
//            Image swingImage = new ImageIcon(barcodeImage).getImage();
//            lblBarCode.setIcon(new ImageIcon(swingImage));
//        }
    }

    private void muestraPanelCamposIndividuales(ProductType productType) {
        if (productType == null) {
            return;
        }

        switch (productType) {
            case BOOK:
                lblAutor.setText("Autor");
                lblISBN.setText("ISBN");
                lblNumeroPaginas.setText("Número Páginas");
                lblEditorial.setText("Editorial");
                lblMinutosHoras.setText("Páginas");
                break;
            case MOVIE:
                lblAutor.setText("Director");
                lblISBN.setText("Estudio");
                lblNumeroPaginas.setText("Duración");
                lblEditorial.setText("URL Trailer");
                lblMinutosHoras.setText("Minutos");
                break;
            case GAME:
                lblAutor.setText("Desarrollador");
                lblISBN.setText("Plataforma");
                lblNumeroPaginas.setText("Duración");
                lblEditorial.setText("URL Trailer");
                lblMinutosHoras.setText("Horas");
                break;
            default:
                break;
        }

        pnlCamposIndividuales.setVisible(true);

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnAceptar;
    private javax.swing.JPanel btnCancelar;
    private javax.swing.JPanel btnCargarImagenLocal;
    private javax.swing.JPanel btnCargarImagenUrl;
    private javax.swing.JPanel btnEditar;
    private javax.swing.JLabel btnExit;
    private javax.swing.JPanel btnNuevo;
    private javax.swing.JComboBox<String> cmbDisponible;
    private javax.swing.JComboBox<ProductType> cmbTipo;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblAceptar;
    private javax.swing.JLabel lblAutor;
    private javax.swing.JLabel lblBarCode;
    private javax.swing.JLabel lblCancelar;
    private javax.swing.JLabel lblCargarImagenLocal;
    private javax.swing.JLabel lblCargarImagenUrl;
    private javax.swing.JLabel lblEditar;
    private javax.swing.JLabel lblEditorial;
    private javax.swing.JLabel lblISBN;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel lblMinutosHoras;
    private javax.swing.JLabel lblNuevo;
    private javax.swing.JLabel lblNumeroPaginas;
    private javax.swing.JLabel lblUrl;
    private javax.swing.JPanel pnlBotonesOperaciones;
    private javax.swing.JPanel pnlCamposIndividuales;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JScrollPane scrollUrl;
    private javax.swing.JSpinner spnPageNumber;
    private javax.swing.JSpinner spnPrice;
    private javax.swing.JSpinner spnRating;
    private javax.swing.JSpinner spnStock;
    private javax.swing.JTextField txtAutor;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtEditorial;
    private javax.swing.JTextArea txtGenero;
    private javax.swing.JTextField txtISBN;
    private javax.swing.JTextField txtIdioma;
    private com.toedter.calendar.JDateChooser txtReleaseDate;
    private javax.swing.JTextField txtTitulo;
    private javax.swing.JTextArea txtUrl;
    // End of variables declaration//GEN-END:variables
}
