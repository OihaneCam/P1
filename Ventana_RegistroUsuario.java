package Practica_1;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.System.Logger.Level;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import org.mindrot.jbcrypt.BCrypt;

public class Ventana_RegistroUsuario extends JFrame{
	public static HashMap<String, Usuario> usuariosRegistrados = new HashMap<>();
	private JTextField tfUsername;
    private JTextField tfEmail;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public Ventana_RegistroUsuario() {
        super("Registro de Usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel lblUsername = new JLabel("Username:");
        tfUsername = new JTextField();
        JLabel lblEmail = new JLabel("Email:");
        tfEmail = new JTextField();
        JLabel lblPassword = new JLabel("Password:");
        passwordField = new JPasswordField();
        JLabel lblConfirmPassword = new JLabel("Confirm password:");
        confirmPasswordField = new JPasswordField();

        JButton btnRegister = new JButton("Register");
        btnRegister.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 Pattern patronContrasenia = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$");
		            char[] contrasenia = passwordField.getPassword();
		            char[] confirmada = confirmPasswordField.getPassword();
		            String nombre = tfUsername.getText();
		            String correo = tfEmail.getText();
		            if (nombre.equals("Nombre") || correo.equals("Correo") || contrasenia.length == 0 || confirmada.length == 0) {
		                JOptionPane.showMessageDialog(null, "Para registrarse, debe introducir datos en todas las casillas.");
		                return;
		            }
		            char[] confirmarContrasenia = confirmPasswordField.getPassword();
		            if (!Arrays.equals(contrasenia, confirmarContrasenia)) {
		                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
		                return;
		            }
		            String cont = new String(contrasenia);
		            Matcher matcher = patronContrasenia.matcher(cont);
		            if (!matcher.matches()) {
		                StringBuilder mensajeError = new StringBuilder("La contraseña no cumple con los requisitos:\n");
		                if (!matcher.matches()) {
		                    if (!contraseniaCumpleRequisito("[A-Z]", cont)) {
		                        mensajeError.append("- Debe contener al menos una letra mayúscula.\n");
		                    }
		                    if (!contraseniaCumpleRequisito("[a-z]", cont)) {
		                        mensajeError.append("- Debe contener al menos una letra minúscula.\n");
		                    }
		                    if (!contraseniaCumpleRequisito("\\d", cont)) {
		                        mensajeError.append("- Debe contener al menos un dígito.\n");
		                    }
		                    if (!contraseniaCumpleRequisito("[@$!%*?&]", cont)) {
		                        mensajeError.append("- Debe contener al menos un carácter especial (@$!%*?&).\n");
		                    }
		                    mensajeError.append("- Debe tener al menos 6 caracteres.\n");

		                    JOptionPane.showMessageDialog(null, mensajeError.toString(), "Error", JOptionPane.ERROR_MESSAGE);
		                    return;
		                }
		            } else {
		            	String hashContrasenia = BCrypt.hashpw(cont, BCrypt.gensalt());
		                Usuario u = new Usuario(nombre, correo, hashContrasenia);
		                if(!usuariosRegistrados.containsKey(correo)) {
		                	registrarUsuario(u);
		                    JOptionPane.showMessageDialog(null, "Te has registrado correctamente");
		                    Ventana.log.log(Level.INFO, "Nuevo usuario registrado - Nombre: " + nombre + " Email: " + correo + "Contraseña: " + hashContrasenia);
		                }else {
		                    JOptionPane.showMessageDialog(null, "El correo ya está en uso. Por favor, elija otro.");
		                }
		                dispose();
		                Ventana v = new Ventana();
		                v.setVisible(true);
		            }
			}
        });
        btnRegister.setToolTipText("<html>La contraseña debe contener al menos una mayúscula, una minúscula,<br> un número, un carácter especial y tener al menos 6 caracteres.</html>");
        
        panel.add(lblUsername);
        panel.add(tfUsername);
        panel.add(lblEmail);
        panel.add(tfEmail);
        panel.add(lblPassword);
        panel.add(passwordField);
        panel.add(lblConfirmPassword);
        panel.add(confirmPasswordField);
        panel.add(btnRegister);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
    }
	private boolean contraseniaCumpleRequisito(String regex, String contrasenia) {
		return Pattern.compile(regex).matcher(contrasenia).find();
	}
	
	public void registrarUsuario(Usuario u) {
    	String correo = u.getCorreo();
        usuariosRegistrados.put(correo, u);
        
    }
}
