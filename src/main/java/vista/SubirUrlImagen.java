package vista;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import service.CloudinaryService;
import service.ImageUtils;

public class SubirUrlImagen extends javax.swing.JDialog {

    DetalleProducto detalle;
    CloudinaryService cloudinaryService;
    String urlCloudinary;
    
    public SubirUrlImagen(DetalleProducto detalle, boolean modal, CloudinaryService cloudinaryService) {
        super(detalle, modal);
        initComponents();
        
        this.cloudinaryService = cloudinaryService;

        inicializarDialog();

        this.detalle = detalle;
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
    
    public String getUrlCloudinary(){
        return this.urlCloudinary;
    }
    

    private void inicializarDialog() {

        this.setLocationRelativeTo(null);

        ImageIcon image = new ImageIcon(getClass().getResource("/images/upload.png"));
        lblUpload.setIcon(new ImageIcon(image.getImage().getScaledInstance(lblUpload.getWidth(), lblUpload.getHeight(), Image.SCALE_DEFAULT)));

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
        jScrollPane1 = new javax.swing.JScrollPane();
        txtUrl = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JPanel();
        lblAceptar = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JPanel();
        lblCancelar = new javax.swing.JLabel();
        btnUpload = new javax.swing.JPanel();
        lblUpload = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 173, 228));

        txtUrl.setColumns(20);
        txtUrl.setRows(5);
        jScrollPane1.setViewportView(txtUrl);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Ingrese la Url de la foto que desea subir a Cloudinary");

        btnAceptar.setBackground(new java.awt.Color(61, 27, 99));

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
            .addComponent(lblAceptar, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
        );
        btnAceptarLayout.setVerticalGroup(
            btnAceptarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAceptar, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
        );

        btnCancelar.setBackground(new java.awt.Color(61, 27, 99));

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
            .addComponent(lblCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
        );
        btnCancelarLayout.setVerticalGroup(
            btnCancelarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
        );

        btnUpload.setBackground(new java.awt.Color(61, 27, 99));

        lblUpload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUploadMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblUploadMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblUploadMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnUploadLayout = new javax.swing.GroupLayout(btnUpload);
        btnUpload.setLayout(btnUploadLayout);
        btnUploadLayout.setHorizontalGroup(
            btnUploadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblUpload, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
        );
        btnUploadLayout.setVerticalGroup(
            btnUploadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblUpload, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(btnUpload, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpload, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(61, 61, 61)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(12, Short.MAX_VALUE))
        );

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

    private void lblAceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAceptarMouseClicked

        try {

            ImageUtils imageUtils = new ImageUtils();
            String imageToBase64 = imageUtils.imageURLToBase64(txtUrl.getText());

            urlCloudinary = cloudinaryService.upload(imageToBase64);

            txtUrl.setText(urlCloudinary);

            Image imagenLibro = this.creaImagenDesdeURL(urlCloudinary);

            lblImagen.setIcon(new ImageIcon(imagenLibro.getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_SMOOTH)));

            
            
            this.dispose();
            
        } catch (IOException ex) {
            System.out.println("Exception url");
        }

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

    private void lblUploadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUploadMouseClicked

        Image imagenLibro = this.creaImagenDesdeURL(txtUrl.getText().trim());

        lblImagen.setIcon(new ImageIcon(imagenLibro.getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_SMOOTH)));

    }//GEN-LAST:event_lblUploadMouseClicked

    private void lblUploadMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUploadMouseEntered
        btnUpload.setBackground(new Color(96, 57, 138));
    }//GEN-LAST:event_lblUploadMouseEntered

    private void lblUploadMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUploadMouseExited
        btnUpload.setBackground(new Color(61, 27, 99));
    }//GEN-LAST:event_lblUploadMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnAceptar;
    private javax.swing.JPanel btnCancelar;
    private javax.swing.JPanel btnUpload;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAceptar;
    private javax.swing.JLabel lblCancelar;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel lblUpload;
    private javax.swing.JTextArea txtUrl;
    // End of variables declaration//GEN-END:variables
}
