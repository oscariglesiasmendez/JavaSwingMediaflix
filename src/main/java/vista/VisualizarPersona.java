package vista;

import entities.Client;
import entities.Employee;
import java.awt.Color;
import java.awt.Image;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import utils.PasswordUtilities;
import static vista.EstadoNavegacion.EDITAR;
import static vista.EstadoNavegacion.NUEVO;
import static vista.EstadoNavegacion.VISUALIZACION;
import static vista.EstadoNavegacion.ACTIVAR_DESACTIVAR;

public class VisualizarPersona extends javax.swing.JDialog {

    Principal principal;
    Client client;
    Employee employee;

    EstadoNavegacion estado;

    SimpleDateFormat sdfApi = new SimpleDateFormat("yyyy-MM-dd");

    SimpleDateFormat sdfTxt = new SimpleDateFormat("dd/MM/yyyy");

    //Constructor usado para crear un nuevo empleado, no hace falta crear uno para cliente porque se darán de alta por la app de android
    public VisualizarPersona(Principal principal, boolean modal, String opcion) {
        super(principal, modal);
        initComponents();

        this.principal = principal;

        inicializarDialog();

        lblPassword.setVisible(true);
        txtPassword.setVisible(true);

        lblDireccion.setVisible(false);
        txtDireccion.setVisible(false);

        this.estado = EstadoNavegacion.NUEVO;
        actualizarComponentes(estado);

        lblConsulta.setText("Creando Empleado");
    }

    public VisualizarPersona(Principal principal, boolean modal, Employee employee) {
        super(principal, modal);
        initComponents();

        this.principal = principal;

        this.employee = employee;

        inicializarDialog();

        lblConsulta.setText("Consultando Empleado");

        txtNombre.setText(employee.getFirstName());
        txtApellidos.setText(employee.getLastName());
        txtNif.setText(employee.getNif());
        txtEmail.setText(employee.getEmail());
        txtFecha.setDate(employee.getHiringDate());
        spnSalario.setValue(employee.getSalary());

        lblDireccion.setVisible(false);
        txtDireccion.setVisible(false);

        cmbDisponible.setSelectedItem(employee.getAvailable() ? "Si" : "No");
        lblVer.setVisible(false);
        lblOcultar.setVisible(false);
    }

    public VisualizarPersona(Principal principal, boolean modal, Client client) {
        super(principal, modal);
        initComponents();

        this.client = client;

        this.principal = principal;

        inicializarDialog();

        lblConsulta.setText("Consultando Cliente");

        lblFecha.setText("Fecha registro");
        lblSalario.setText("Dirección");

        lblSalario.setVisible(false);
        spnSalario.setVisible(false);

        txtNombre.setText(client.getFirstName());
        txtApellidos.setText(client.getLastName());
        txtEmail.setText(client.getEmail());
        txtFecha.setDate(client.getCreationDate());
        txtDireccion.setVisible(false);
        lblDireccion.setVisible(false);
        cmbDisponible.setSelectedItem(client.getAvailable() ? "Si" : "No");

        lblEditar.setVisible(false);
        btnEditar.setVisible(false);
        lblNuevo.setVisible(false);
        btnNuevo.setVisible(false);
        lblOcultar.setVisible(false);
        lblVer.setVisible(false);
        lblNif.setVisible(false);
        txtNif.setVisible(false);

    }

    private void inicializarDialog() {

        this.setLocationRelativeTo(null);

        this.estado = EstadoNavegacion.VISUALIZACION;
        actualizarComponentes(estado);

        lblPassword.setVisible(false);
        txtPassword.setVisible(false);

        pnlContenedor.setLocation(this.getWidth(), this.getHeight());

        ImageIcon image = new ImageIcon(getClass().getResource("/images/X_logo.png"));
        btnExit.setIcon(new ImageIcon(image.getImage().getScaledInstance(btnExit.getWidth(), btnExit.getHeight(), Image.SCALE_DEFAULT)));

        image = new ImageIcon(getClass().getResource("/images/mostrarPasswordWhite.png"));
        lblVer.setIcon(new ImageIcon(image.getImage().getScaledInstance(lblVer.getWidth(), lblVer.getHeight(), Image.SCALE_DEFAULT)));

        image = new ImageIcon(getClass().getResource("/images/ocultarPasswordWhite.png"));
        lblOcultar.setIcon(new ImageIcon(image.getImage().getScaledInstance(lblOcultar.getWidth(), lblOcultar.getHeight(), Image.SCALE_SMOOTH)));
        lblOcultar.setVisible(false);
    }

    private void actualizarComponentes(EstadoNavegacion estado) {

        switch (estado) {
            case VISUALIZACION:
                txtEditables(false);
                btnAceptar.setVisible(false);
                btnCancelar.setVisible(false);
                break;
            case EDITAR:
                txtEditables(true);
                btnAceptar.setVisible(true);
                btnCancelar.setVisible(true);
                btnNuevo.setVisible(false);
                btnEditar.setVisible(true);
                lblOcultar.setVisible(false);
                lblVer.setVisible(false);
                break;
            case NUEVO:
                txtEditables(true);
                limpiarTextos();
                btnAceptar.setVisible(true);
                btnCancelar.setVisible(true);
                btnNuevo.setVisible(true);
                btnEditar.setVisible(false);
                lblVer.setVisible(true);
                lblOcultar.setVisible(true);
                txtPassword.setVisible(true);
                lblPassword.setVisible(true);
                break;
        }
    }

    private void txtEditables(boolean activar) {
        txtNombre.setEditable(activar);
        txtApellidos.setEditable(activar);
        txtEmail.setEditable(activar);
        txtFecha.setEnabled(activar);
        txtNif.setEnabled(activar);
        spnSalario.setEnabled(activar);
        cmbDisponible.setEnabled(activar);
        txtDireccion.setEnabled(activar);
    }

    private void limpiarTextos() {
        txtNombre.setText("");
        txtApellidos.setText("");
        txtEmail.setText("");
        txtFecha.setDate(new java.util.Date());
        txtNif.setText("");
        spnSalario.setValue(0);
        txtDireccion.setText("");
        cmbDisponible.setSelectedIndex(-1);
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
        pnlHeader = new javax.swing.JPanel();
        btnExit = new javax.swing.JLabel();
        pnlContenedor = new javax.swing.JPanel();
        txtEmail = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        txtNif = new javax.swing.JTextField();
        lblNif = new javax.swing.JLabel();
        txtApellidos = new javax.swing.JTextField();
        lblApellidos = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblFecha = new javax.swing.JLabel();
        lblSalario = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JPanel();
        lblNuevo = new javax.swing.JLabel();
        btnEditar = new javax.swing.JPanel();
        lblEditar = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JPanel();
        lblAceptar = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JPanel();
        lblCancelar = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        cmbDisponible = new javax.swing.JComboBox<>();
        lblDisponible = new javax.swing.JLabel();
        spnSalario = new javax.swing.JSpinner();
        lblDireccion = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        txtFecha = new com.toedter.calendar.JDateChooser();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        lblOcultar = new javax.swing.JLabel();
        lblVer = new javax.swing.JLabel();
        lblConsulta = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(61, 27, 99));

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
                .addContainerGap(889, Short.MAX_VALUE)
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

        pnlContenedor.setBackground(new java.awt.Color(61, 27, 99));

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblEmail.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(255, 255, 255));
        lblEmail.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblEmail.setText("Email");

        txtNif.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNif.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNifFocusLost(evt);
            }
        });

        lblNif.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNif.setForeground(new java.awt.Color(255, 255, 255));
        lblNif.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNif.setText("NIF");

        txtApellidos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblApellidos.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblApellidos.setForeground(new java.awt.Color(255, 255, 255));
        lblApellidos.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblApellidos.setText("Apellidos");

        lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(255, 255, 255));
        lblNombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNombre.setText("Nombre");

        txtNombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblFecha.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFecha.setForeground(new java.awt.Color(255, 255, 255));
        lblFecha.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFecha.setText("Fecha Contratacion");

        lblSalario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSalario.setForeground(new java.awt.Color(255, 255, 255));
        lblSalario.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSalario.setText("Salario");

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
            .addComponent(lblNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        btnNuevoLayout.setVerticalGroup(
            btnNuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

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
            .addComponent(lblEditar, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        btnEditarLayout.setVerticalGroup(
            btnEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblEditar, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
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
            .addComponent(lblAceptar, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        btnAceptarLayout.setVerticalGroup(
            btnAceptarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAceptar, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
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
            .addComponent(lblCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        btnCancelarLayout.setVerticalGroup(
            btnCancelarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(61, 27, 99));

        cmbDisponible.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbDisponible.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        cmbDisponible.setSelectedIndex(-1);

        lblDisponible.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDisponible.setForeground(new java.awt.Color(255, 255, 255));
        lblDisponible.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDisponible.setText("Disponible");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(lblDisponible)
                .addGap(18, 18, 18)
                .addComponent(cmbDisponible, 0, 194, Short.MAX_VALUE)
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        spnSalario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        spnSalario.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, null, 50.0d));

        lblDireccion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDireccion.setForeground(new java.awt.Color(255, 255, 255));
        lblDireccion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDireccion.setText("Dirección");

        txtDireccion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtFecha.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblPassword.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPassword.setForeground(new java.awt.Color(255, 255, 255));
        lblPassword.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPassword.setText("Contraseña");

        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtPassword.setForeground(new java.awt.Color(204, 204, 204));
        txtPassword.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtPassword.setText("●●●●●●●●");
        txtPassword.setBorder(null);
        txtPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPasswordMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtPasswordMousePressed(evt);
            }
        });
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPasswordKeyPressed(evt);
            }
        });

        lblOcultar.setPreferredSize(new java.awt.Dimension(30, 30));
        lblOcultar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblOcultarMouseClicked(evt);
            }
        });

        lblVer.setPreferredSize(new java.awt.Dimension(30, 30));
        lblVer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblVerMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlContenedorLayout = new javax.swing.GroupLayout(pnlContenedor);
        pnlContenedor.setLayout(pnlContenedorLayout);
        pnlContenedorLayout.setHorizontalGroup(
            pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContenedorLayout.createSequentialGroup()
                .addContainerGap(791, Short.MAX_VALUE)
                .addGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContenedorLayout.createSequentialGroup()
                .addGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlContenedorLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlContenedorLayout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlContenedorLayout.createSequentialGroup()
                                    .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlContenedorLayout.createSequentialGroup()
                                    .addGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtApellidos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(pnlContenedorLayout.createSequentialGroup()
                                .addComponent(lblSalario)
                                .addGap(18, 18, 18)
                                .addComponent(spnSalario, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                        .addGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlContenedorLayout.createSequentialGroup()
                                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContenedorLayout.createSequentialGroup()
                                    .addComponent(lblFecha)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContenedorLayout.createSequentialGroup()
                                    .addComponent(lblDireccion)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContenedorLayout.createSequentialGroup()
                                    .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContenedorLayout.createSequentialGroup()
                                    .addComponent(lblNif, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtNif, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOcultar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblVer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(71, 71, 71))
        );
        pnlContenedorLayout.setVerticalGroup(
            pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContenedorLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlContenedorLayout.createSequentialGroup()
                        .addGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNif, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblOcultar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblVer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlContenedorLayout.createSequentialGroup()
                        .addGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spnSalario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSalario, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45))
        );

        lblConsulta.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblConsulta.setForeground(new java.awt.Color(255, 255, 255));
        lblConsulta.setText("jLabel1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlContenedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(lblConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82)
                .addComponent(lblConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlContenedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

    private void btnExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseClicked
        this.dispose();
    }//GEN-LAST:event_btnExitMouseClicked

    private void btnExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseEntered
        btnExit.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_btnExitMouseEntered

    private void btnExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseExited
        btnExit.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnExitMouseExited

    private void lblNuevoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNuevoMouseEntered
        btnNuevo.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblNuevoMouseEntered

    private void lblNuevoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNuevoMouseExited
        btnNuevo.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblNuevoMouseExited

    private void lblEditarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEditarMouseEntered
        btnEditar.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblEditarMouseEntered

    private void lblEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEditarMouseExited
        btnEditar.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblEditarMouseExited

    private void lblEditarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEditarMouseClicked
        this.estado = EstadoNavegacion.EDITAR;
        actualizarComponentes(estado);


    }//GEN-LAST:event_lblEditarMouseClicked

    private void lblNuevoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNuevoMouseClicked
        this.estado = EstadoNavegacion.NUEVO;
        actualizarComponentes(estado);
    }//GEN-LAST:event_lblNuevoMouseClicked

    private void lblAceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAceptarMouseClicked

        String firstName = null;
        String lastName = null;
        String nif = null;
        String email = null;
        Double salary = null;
        String str_date = null;
        String address = null;

        try {
            firstName = txtNombre.getText().trim();
            lastName = txtApellidos.getText().trim();
            nif = txtNif.getText().trim();
            email = txtEmail.getText().trim();
            salary = (Double) spnSalario.getValue();
            address = txtDireccion.getText().trim();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ha ocurrido un error, alguno de los campos no es válido. Por favor, vuelva a intentarlo. Gracias");
            return;
        }

        java.util.Date utilDate = txtFecha.getDate();

        // Convertir java.util.Date a java.sql.Date
        Date date = new Date(utilDate.getTime());

        String password = String.valueOf(txtPassword.getPassword());

        String encryptedPassword = null;
        try {
            //Encriptamos la contraseña
            encryptedPassword = PasswordUtilities.encryptPassword(password);
        } catch (NoSuchAlgorithmException ex) {
            JOptionPane.showMessageDialog(this, "Ha ocurrido un error encriptando la contraseña. Por favor vuelva a intentarlo");
            return;
        }

        String str_disponible = (String) cmbDisponible.getSelectedItem();

        Boolean available = str_disponible.equalsIgnoreCase("si") ? true : false;

        if (lblConsulta.getText().equals("Consultando Empleado") || lblConsulta.getText().equals("Creando Empleado")) {

            Employee e = new Employee(firstName, lastName, nif, encryptedPassword, email, salary, date, available);

            switch (estado) {
                case ACTIVAR_DESACTIVAR:
                    //Hago un post y actualizo el campo available
                    e.setEmployeeId(employee.getEmployeeId());

                    try {
                        if (employee.getAvailable()) {
                            principal.employeeApiCall.deactivateEmployee(e.getEmployeeId());
                            JOptionPane.showMessageDialog(this, "El empleado ha sido desactivado con éxito");
                        } else {
                            principal.employeeApiCall.activateEmployee(e.getEmployeeId());
                            JOptionPane.showMessageDialog(this, "El empleado ha sido activado con éxito");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Ha ocurrido un problema al activar/desactivar el empleado, intentelo de nuevo. Gracias");
                    }

                    break;

                case EDITAR:
                    //Le ponemos el id que tiene
                    e.setEmployeeId(employee.getEmployeeId());

                    int statusCode = principal.employeeApiCall.updateEmployee(e);

                    if (statusCode == 200) {
                        JOptionPane.showMessageDialog(this, "El empleado ha sido actualizado con éxito");
                    } else if (statusCode == 400 || statusCode == 404) {
                        JOptionPane.showMessageDialog(this, "Ha ocurrido un problema al actualizar el empleado, intentelo de nuevo. Gracias");
                    }

                    break;

                case NUEVO:

                    try {
                    Employee createEmployee = principal.employeeApiCall.createEmployee(e);
                    JOptionPane.showMessageDialog(this, "¡Empleado creado con éxito!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Ha ocurrido un error creando el empleado. Por favor vuelva a intentarlo");
                }

                break;

                default:
                    break;
            }

        } else { //TODO Cliente

            Client c = new Client(client.getClientId(), firstName, lastName, email, date, available);

            switch (estado) {
                case ACTIVAR_DESACTIVAR:

                    break;

                case EDITAR:

                    break;
                case NUEVO:

                    break;
                default:
                    break;
            }

        }
        
        estado = EstadoNavegacion.VISUALIZACION;

    }//GEN-LAST:event_lblAceptarMouseClicked

    private void lblAceptarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAceptarMouseEntered
        btnAceptar.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblAceptarMouseEntered

    private void lblAceptarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAceptarMouseExited
        btnAceptar.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblAceptarMouseExited

    private void lblCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCancelarMouseClicked
        this.dispose();
    }//GEN-LAST:event_lblCancelarMouseClicked

    private void lblCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCancelarMouseEntered
        btnCancelar.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblCancelarMouseEntered

    private void lblCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCancelarMouseExited
        btnCancelar.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblCancelarMouseExited

    private void lblOcultarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblOcultarMouseClicked
        lblVer.setVisible(true);
        lblOcultar.setVisible(false);
        txtPassword.setEchoChar('●');
    }//GEN-LAST:event_lblOcultarMouseClicked

    private void lblVerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblVerMouseClicked
        lblVer.setVisible(false);
        lblOcultar.setVisible(true);
        txtPassword.setEchoChar((char) 0);
    }//GEN-LAST:event_lblVerMouseClicked

    private void txtPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPasswordMouseClicked
        txtPassword.setForeground(Color.black);
        if (String.valueOf(txtPassword.getPassword()).trim().equals("●●●●●●●●")) {
            txtPassword.setText("");
        }
    }//GEN-LAST:event_txtPasswordMouseClicked

    private void txtPasswordMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPasswordMousePressed
        if (String.valueOf(txtPassword.getPassword()).equals("********")) {
            txtPassword.setText("");
            txtPassword.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtPasswordMousePressed

    private void txtPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyPressed
        txtPassword.setForeground(Color.black);
        if (String.valueOf(txtPassword.getPassword()).trim().equals("●●●●●●●●")) {
            txtPassword.setText("");
        }
    }//GEN-LAST:event_txtPasswordKeyPressed

    private void txtNifFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNifFocusLost
        validaNIF();
    }//GEN-LAST:event_txtNifFocusLost

    //Este método a parte de validar el NIF, en caso de que el txtField pierda el focus y si tiene todos los números lo autocompleta con la letra correcta
    public void validaNIF() {

        String nif = txtNif.getText().trim();

        char[] letras = "TRWAGMYFPDXBNJZSQVHLCKE".toCharArray();
        int intDNI;
        char letraCalculada;

        String strDNI;
        char letra;

        if (nif.length() == 8) {

            try {

                //Se convierte la cadena DNI a int
                intDNI = Integer.parseInt(nif);
                letraCalculada = letras[intDNI % 23];

                StringBuffer sbf = new StringBuffer(nif);

                sbf.append(letraCalculada);

                txtNif.setText(sbf.toString());

            } catch (NumberFormatException e) {
                System.out.println(e);
            }

        } else if (nif.length() == 9) {

            try {
                //Extraigo la letra del nif
                letra = nif.charAt(8);

                //Extraigo el DNI como texto
                strDNI = nif.substring(0, 8);

                //Se convierte la cadena DNI a int
                intDNI = Integer.parseInt(strDNI);
                //Calculamos la letra
                letraCalculada = letras[intDNI % 23];
                //Devolvemos la letra
                if (letraCalculada == letra) {

                } else {
                    JOptionPane.showMessageDialog(this, "El NIF introducido no es válido");
                    txtNif.setText(String.valueOf(intDNI));
                }
            } catch (NumberFormatException e) {
                System.out.println("Error dni");
            }

        } else if ((nif.length() > 0 && nif.length() < 8) || (nif.length() > 9)) {
            JOptionPane.showMessageDialog(this, "El NIF introducido no es válido");
            txtNif.setText(String.valueOf(""));
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnAceptar;
    private javax.swing.JPanel btnCancelar;
    private javax.swing.JPanel btnEditar;
    private javax.swing.JLabel btnExit;
    private javax.swing.JPanel btnNuevo;
    private javax.swing.JComboBox<String> cmbDisponible;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblAceptar;
    private javax.swing.JLabel lblApellidos;
    private javax.swing.JLabel lblCancelar;
    private javax.swing.JLabel lblConsulta;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblDisponible;
    private javax.swing.JLabel lblEditar;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblNif;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNuevo;
    private javax.swing.JLabel lblOcultar;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblSalario;
    private javax.swing.JLabel lblVer;
    private javax.swing.JPanel pnlContenedor;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JSpinner spnSalario;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private com.toedter.calendar.JDateChooser txtFecha;
    private javax.swing.JTextField txtNif;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
