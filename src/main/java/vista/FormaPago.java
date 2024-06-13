package vista;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;

public class FormaPago extends javax.swing.JDialog {

    Principal principal;
    String paymentMethod;

    public FormaPago(Principal principal, boolean modal) {
        super(principal, modal);
        initComponents();
        
        this.setLocationRelativeTo(null);

        this.principal = principal;
        inicializarDialog();

    }

    private void inicializarDialog() {
        ImageIcon image = new ImageIcon(getClass().getResource("/images/cash.png"));
        lblEfectivo.setIcon(new ImageIcon(image.getImage().getScaledInstance(lblEfectivo.getWidth(), lblEfectivo.getHeight(), Image.SCALE_DEFAULT)));

        image = new ImageIcon(getClass().getResource("/images/card.png"));
        lblTarjeta.setIcon(new ImageIcon(image.getImage().getScaledInstance(lblTarjeta.getWidth(), lblTarjeta.getHeight(), Image.SCALE_DEFAULT)));
        
        image = new ImageIcon(getClass().getResource("/images/X_logo.png"));
        btnExit.setIcon(new ImageIcon(image.getImage().getScaledInstance(btnExit.getWidth(), btnExit.getHeight(), Image.SCALE_DEFAULT)));
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnTarjeta = new javax.swing.JPanel();
        lblTarjeta = new javax.swing.JLabel();
        btnEfectivo = new javax.swing.JPanel();
        lblEfectivo = new javax.swing.JLabel();
        pnlHeader = new javax.swing.JPanel();
        btnExit = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(61, 27, 99));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Seleccione la forma de pago");

        btnTarjeta.setPreferredSize(new java.awt.Dimension(100, 100));

        lblTarjeta.setBackground(new java.awt.Color(204, 173, 228));
        lblTarjeta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblTarjeta.setOpaque(true);
        lblTarjeta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTarjetaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblTarjetaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblTarjetaMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnTarjetaLayout = new javax.swing.GroupLayout(btnTarjeta);
        btnTarjeta.setLayout(btnTarjetaLayout);
        btnTarjetaLayout.setHorizontalGroup(
            btnTarjetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTarjeta, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        btnTarjetaLayout.setVerticalGroup(
            btnTarjetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTarjeta, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        btnEfectivo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEfectivo.setPreferredSize(new java.awt.Dimension(100, 100));

        lblEfectivo.setBackground(new java.awt.Color(204, 173, 228));
        lblEfectivo.setOpaque(true);
        lblEfectivo.setPreferredSize(new java.awt.Dimension(100, 100));
        lblEfectivo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEfectivoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblEfectivoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblEfectivoMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnEfectivoLayout = new javax.swing.GroupLayout(btnEfectivo);
        btnEfectivo.setLayout(btnEfectivoLayout);
        btnEfectivoLayout.setHorizontalGroup(
            btnEfectivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblEfectivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        btnEfectivoLayout.setVerticalGroup(
            btnEfectivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblEfectivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(btnEfectivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                .addComponent(btnTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(56, 56, 56)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEfectivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(127, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblTarjetaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTarjetaMouseClicked
        paymentMethod = "Tarjeta";
        this.dispose();
    }//GEN-LAST:event_lblTarjetaMouseClicked

    private void lblTarjetaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTarjetaMouseEntered
        btnTarjeta.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblTarjetaMouseEntered

    private void lblTarjetaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTarjetaMouseExited
        btnTarjeta.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblTarjetaMouseExited

    private void lblEfectivoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEfectivoMouseClicked
        paymentMethod = "Efectivo";
        this.dispose();
    }//GEN-LAST:event_lblEfectivoMouseClicked

    private void lblEfectivoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEfectivoMouseEntered
        btnEfectivo.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblEfectivoMouseEntered

    private void lblEfectivoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEfectivoMouseExited
        btnEfectivo.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblEfectivoMouseExited

    private void btnExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseClicked
        this.dispose();
    }//GEN-LAST:event_btnExitMouseClicked

    private void btnExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseEntered
        btnExit.setBackground(new Color(204, 173, 228));
    }//GEN-LAST:event_btnExitMouseEntered

    private void btnExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseExited
        btnExit.setBackground(Color.white);
    }//GEN-LAST:event_btnExitMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnEfectivo;
    private javax.swing.JLabel btnExit;
    private javax.swing.JPanel btnTarjeta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblEfectivo;
    private javax.swing.JLabel lblTarjeta;
    private javax.swing.JPanel pnlHeader;
    // End of variables declaration//GEN-END:variables
}
