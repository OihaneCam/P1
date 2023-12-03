package Practica_1;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;

import org.mindrot.jbcrypt.BCrypt;


public class Ventana extends JFrame{
	
	static Logger log;
	
	protected JTextField tfEmail;
	protected JPasswordField passwordField;
	
	public Ventana() {
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setBounds(500, 150, 400, 200);
		this.setTitle("Inicio");
		
		JPanel pnlSur = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel pnlCentro = new JPanel(new GridLayout(3,2));
		add(pnlSur, BorderLayout.SOUTH);
		add(pnlCentro,BorderLayout.CENTER);

		JPanel pnlEmail = new JPanel();
		JLabel lblEmail = new JLabel("Email");
		tfEmail = new JTextField(15);
		pnlEmail.add(lblEmail);
		pnlEmail.add(tfEmail);
		
		JPanel pnlContr = new JPanel();
		JLabel lblPassword = new JLabel("Password");
		passwordField = new JPasswordField(15);
        passwordField.setEchoChar((char) 0);
        pnlContr.add(lblPassword);
        pnlContr.add(passwordField);
		
		
		JButton btnInicioSesion = new JButton("Iniciar Sesion");
		JLabel lblCuenta = new JLabel("¿No tienes cuenta?");
		JButton btnRegistro = new JButton("Registrarse");
		
		pnlSur.add(lblCuenta);
		pnlSur.add(btnRegistro);
		pnlCentro.add(pnlEmail);
		pnlCentro.add(pnlContr);
		JPanel panel = new JPanel();
		panel.add(btnInicioSesion);
		pnlCentro.add(panel);
		
		btnInicioSesion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				char[] contraseniaChar = passwordField.getPassword();
		        String contrasenia = new String(contraseniaChar);
		        String correo = tfEmail.getText();
				if(autenticarUsuario(correo, contrasenia)) {
					JOptionPane.showMessageDialog(null, "Bienvenido");
					log.log(Level.INFO, "Inicio de sesión exitoso - Email: " + correo);
	                dispose();
				}else {
	                JOptionPane.showMessageDialog(null, "Correo o contraseña incorrectos");
					log.log(Level.WARNING, "Alerta - Inicio de sesión fallido para Email: " + correo);
	            }
			}
		});
		
		btnRegistro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				Ventana_RegistroUsuario v = new Ventana_RegistroUsuario();
				v.setVisible(true);
			}
		});		
	}
	
	public boolean autenticarUsuario(String correo, String contrasenia) {
		 if (Ventana_RegistroUsuario.usuariosRegistrados.containsKey(correo)) {
	        Usuario usuario = Ventana_RegistroUsuario.usuariosRegistrados.get(correo);
	        String hashAlmacenado = usuario.getContrasena();

	        if (BCrypt.checkpw(contrasenia, hashAlmacenado)) {
	            return true; // La contraseña es correcta
	        } else {
	            return false; // La contraseña es incorrecta
	        }
		 } else {
			 return false;// El correo no está registrado, la autenticación falla
		 }
    }
	
	
	public static void main(String[] args) {
		try {
			log = Logger.getLogger("logger");
		}catch(Exception e) {
			e.printStackTrace();
		}
		log.log(Level.INFO, "Inicio de aplicación" + (new Date()) );
		
		Ventana v = new Ventana();
		v.setVisible(true);
	}
}