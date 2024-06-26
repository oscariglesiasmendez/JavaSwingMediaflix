package vista;

import entities.Employee;
import java.awt.Color;
import java.awt.Image;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import service.EmployeeApiCall;
import utils.EmailConfig;
import utils.PasswordUtilities;

public class Login extends javax.swing.JFrame {

    int xMouse, yMouse;

    EmployeeApiCall employeeApiCall;
    List<Employee> employees;

    public Login() {
        initComponents();
        
        
        
        this.setLocationRelativeTo(null);

        employeeApiCall = new EmployeeApiCall();

        //llamadasApi.getAllEmployees();
        inicializarEstilo();

        try {
            employees = employeeApiCall.getAllAvailableEmployees();
        } catch (Exception e) {
            System.out.println("No es posible comunicarse con la API en estos momentos");
        }

    }

    private void inicializarEstilo() {
        ImageIcon image = new ImageIcon(getClass().getResource("/images/logoMediaflix.png"));
        lblLogo.setIcon(new ImageIcon(image.getImage().getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_DEFAULT)));

        image = new ImageIcon(getClass().getResource("/images/X_logo.png"));
        btnExit.setIcon(new ImageIcon(image.getImage().getScaledInstance(btnExit.getWidth(), btnExit.getHeight(), Image.SCALE_DEFAULT)));

        image = new ImageIcon(getClass().getResource("/images/mostrarPassword.png"));
        lblVer.setIcon(new ImageIcon(image.getImage().getScaledInstance(lblVer.getWidth(), lblVer.getHeight(), Image.SCALE_DEFAULT)));

        image = new ImageIcon(getClass().getResource("/images/ocultarPassword.png"));
        lblOcultar.setIcon(new ImageIcon(image.getImage().getScaledInstance(lblOcultar.getWidth(), lblOcultar.getHeight(), Image.SCALE_SMOOTH)));
        lblOcultar.setVisible(false);
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
        jPanel2 = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblIniciarSesion = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        lblUsuario = new javax.swing.JLabel();
        spUser = new javax.swing.JSeparator();
        lblPassword = new javax.swing.JLabel();
        spPassword = new javax.swing.JSeparator();
        txtPassword = new javax.swing.JPasswordField();
        pnlEntrar = new javax.swing.JPanel();
        lblEntrar = new javax.swing.JLabel();
        pnlHeader = new javax.swing.JPanel();
        btnExit = new javax.swing.JLabel();
        lblOcultar = new javax.swing.JLabel();
        lblVer = new javax.swing.JLabel();
        lblOlvidoContraseña = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(204, 173, 228));

        jPanel2.setBackground(new java.awt.Color(61, 27, 99));

        lblLogo.setPreferredSize(new java.awt.Dimension(220, 220));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(178, Short.MAX_VALUE)
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(189, 189, 189))
        );

        lblIniciarSesion.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblIniciarSesion.setText("INICIAR SESIÓN");

        txtUsuario.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        txtUsuario.setForeground(new java.awt.Color(204, 204, 204));
        txtUsuario.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtUsuario.setText("Ingrese su nombre de usuario");
        txtUsuario.setBorder(null);
        txtUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUsuarioFocusLost(evt);
            }
        });
        txtUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtUsuarioMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtUsuarioMousePressed(evt);
            }
        });
        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyPressed(evt);
            }
        });

        lblUsuario.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblUsuario.setText("USUARIO");

        spUser.setBackground(new java.awt.Color(0, 0, 0));
        spUser.setForeground(new java.awt.Color(0, 0, 0));
        spUser.setMinimumSize(new java.awt.Dimension(10, 10));
        spUser.setOpaque(true);
        spUser.setPreferredSize(new java.awt.Dimension(10, 10));

        lblPassword.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblPassword.setText("CONTRASEÑA");

        spPassword.setBackground(new java.awt.Color(0, 0, 0));
        spPassword.setForeground(new java.awt.Color(0, 0, 0));
        spPassword.setMinimumSize(new java.awt.Dimension(10, 10));
        spPassword.setOpaque(true);
        spPassword.setPreferredSize(new java.awt.Dimension(10, 10));

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

        pnlEntrar.setBackground(new java.awt.Color(61, 27, 99));
        pnlEntrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblEntrar.setBackground(new java.awt.Color(204, 173, 228));
        lblEntrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblEntrar.setForeground(new java.awt.Color(255, 255, 255));
        lblEntrar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEntrar.setText("ENTRAR");
        lblEntrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEntrarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblEntrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblEntrarMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnlEntrarLayout = new javax.swing.GroupLayout(pnlEntrar);
        pnlEntrar.setLayout(pnlEntrarLayout);
        pnlEntrarLayout.setHorizontalGroup(
            pnlEntrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEntrarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblEntrar, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlEntrarLayout.setVerticalGroup(
            pnlEntrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEntrarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblEntrar, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlHeader.setBackground(new java.awt.Color(35, 7, 59));
        pnlHeader.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlHeaderMouseDragged(evt);
            }
        });
        pnlHeader.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlHeaderMousePressed(evt);
            }
        });

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

        lblOlvidoContraseña.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblOlvidoContraseña.setText("¿Ha olvidado su contraseña?");
        lblOlvidoContraseña.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblOlvidoContraseña.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblOlvidoContraseñaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblOlvidoContraseñaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblOlvidoContraseñaMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUsuario)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spUser, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPassword)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblOcultar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblVer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblIniciarSesion)
                    .addComponent(pnlEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblOlvidoContraseña))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtPassword, txtUsuario});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(lblIniciarSesion)
                .addGap(82, 82, 82)
                .addComponent(lblUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spUser, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(lblPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtPassword)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblOcultar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblVer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblOlvidoContraseña)
                .addGap(29, 29, 29)
                .addComponent(pnlEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 70, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtPassword, txtUsuario});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pnlHeaderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlHeaderMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_pnlHeaderMousePressed

    private void pnlHeaderMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlHeaderMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_pnlHeaderMouseDragged

    private void btnExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_btnExitMouseClicked

    private void btnExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseEntered
        btnExit.setBackground(new Color(204, 173, 228));
    }//GEN-LAST:event_btnExitMouseEntered

    private void btnExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseExited
        btnExit.setBackground(Color.white);
    }//GEN-LAST:event_btnExitMouseExited

    private void lblEntrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEntrarMouseEntered
        pnlEntrar.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblEntrarMouseEntered

    private void lblEntrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEntrarMouseExited
        pnlEntrar.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblEntrarMouseExited

    private void txtUsuarioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUsuarioMousePressed
        if (txtUsuario.getText().equals("Ingrese su nombre de usuario")) {
            txtUsuario.setText("");
            txtUsuario.setForeground(Color.black);
        }
        if (String.valueOf(txtPassword.getPassword()).isEmpty()) {
            txtPassword.setText("●●●●●●●●");
            txtPassword.setForeground(Color.gray);
        }

    }//GEN-LAST:event_txtUsuarioMousePressed

    private void txtPasswordMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPasswordMousePressed
        if (String.valueOf(txtPassword.getPassword()).equals("********")) {
            txtPassword.setText("");
            txtPassword.setForeground(Color.black);
        }
        if (txtUsuario.getText().isEmpty()) {
            txtUsuario.setText("Ingrese su nombre de usuario");
            txtUsuario.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtPasswordMousePressed

    private void lblEntrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEntrarMouseClicked
        //JOptionPane.showMessageDialog(this, "Login con los datos: \nUsuario: " + txtUsuario.getText() + "\nContraseña: " + String.valueOf(txtPassword.getPassword()));
        String user = txtUsuario.getText();
        String password = String.valueOf(txtPassword.getPassword());

        String encryptPassword = null;

        try {
            encryptPassword = PasswordUtilities.encryptPassword(password);
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Error encriptando contraseña en login");
        }

        if (user.equals("Admin") && password.equals("1111")) {
            JOptionPane.showMessageDialog(this, "¡Usuario Admin logueado con éxito!");
            new Principal(this, true, true).setVisible(true);
        } else {

            for (Employee e : employees) {
                if (e.getNif().equals(user) && e.getPassword().equals(encryptPassword) && e.getAvailable()) {
                    JOptionPane.showMessageDialog(this, "¡Usuario " + e.getFirstName() + " logueado con éxito!");
                    new Principal(this, true, e).setVisible(true);
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos. Por favor vuelva a intentarlo");
        }

    }//GEN-LAST:event_lblEntrarMouseClicked

    private void txtUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUsuarioMouseClicked
        txtUsuario.setForeground(Color.black);
        if (txtUsuario.getText().equals("Ingrese su nombre de usuario")) {
            txtUsuario.setText("");
        }
    }//GEN-LAST:event_txtUsuarioMouseClicked

    private void txtUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyPressed
        txtUsuario.setForeground(Color.black);
        if (txtUsuario.getText().equals("Ingrese su nombre de usuario")) {
            txtUsuario.setText("");
        }
    }//GEN-LAST:event_txtUsuarioKeyPressed

    private void txtPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPasswordMouseClicked
        txtPassword.setForeground(Color.black);
        if (String.valueOf(txtPassword.getPassword()).trim().equals("●●●●●●●●")) {
            txtPassword.setText("");
        }
    }//GEN-LAST:event_txtPasswordMouseClicked

    private void txtPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyPressed
        txtPassword.setForeground(Color.black);
        if (String.valueOf(txtPassword.getPassword()).trim().equals("●●●●●●●●")) {
            txtPassword.setText("");
        }
    }//GEN-LAST:event_txtPasswordKeyPressed

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

    private void txtUsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsuarioFocusLost
        validaNIF();
    }//GEN-LAST:event_txtUsuarioFocusLost

    private void lblOlvidoContraseñaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblOlvidoContraseñaMouseClicked
        
        int option = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que quieres restablecer la contraseña?", "Confirmación", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            
            String email = JOptionPane.showInputDialog("Inserte el email del usuario del que desea restablecer la contraseña");
            
            for(Employee e : employees){
                if(e.getEmail().equals(email)){
                    //Genero una contraseña aleatoria para el empleado
                    String password = PasswordUtilities.generatePassword(10);
                    e.setPassword(password);
                    System.out.println(password);
                    employeeApiCall.updateEmployee(e);
                    
                    EmailConfig.createEmailRecoveryPassword(email, "Contraseña de recuperación", e.getFirstName() + " " + e.getLastName(), password); 
                    EmailConfig.sendEmail("Contraseña enviada con éxito");
                    return;
                }
            }
            
            JOptionPane.showMessageDialog(this, "No existe ningún empleado que tenga ese email asignado. Inténtelo de nuevo o póngase en contacto con el Administrador");
            
        }

    }//GEN-LAST:event_lblOlvidoContraseñaMouseClicked

    private void lblOlvidoContraseñaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblOlvidoContraseñaMouseEntered
        lblOlvidoContraseña.setForeground(Color.blue);
    }//GEN-LAST:event_lblOlvidoContraseñaMouseEntered

    private void lblOlvidoContraseñaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblOlvidoContraseñaMouseExited
        lblOlvidoContraseña.setForeground(Color.black);
    }//GEN-LAST:event_lblOlvidoContraseñaMouseExited

    //Este método a parte de validar el NIF, en caso de que el txtField pierda el focus y si tiene todos los números lo autocompleta con la letra correcta
    public void validaNIF() {

        String nif = txtUsuario.getText().trim();

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

                txtUsuario.setText(sbf.toString());

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
                    txtUsuario.setText(String.valueOf(intDNI));
                }
            } catch (NumberFormatException e) {
                System.out.println("Error dni");
            }

        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnExit;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblEntrar;
    private javax.swing.JLabel lblIniciarSesion;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblOcultar;
    private javax.swing.JLabel lblOlvidoContraseña;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lblVer;
    private javax.swing.JPanel pnlEntrar;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JSeparator spPassword;
    private javax.swing.JSeparator spUser;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
