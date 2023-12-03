import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Practica_1.Usuario;
import Practica_1.Ventana_RegistroUsuario;

public class Ventana_RegistroUsuario_test {
	
	@Test
	public void testRegistroUsuarioExitoso() {
	    Ventana_RegistroUsuario vRegistro = new Ventana_RegistroUsuario();
	    Usuario usuario = new Usuario("NombrePrueba", "correo_prueba@example.com", "contrasena_prueba");
	    vRegistro.registrarUsuario(usuario);
	    assertTrue(vRegistro.usuariosRegistrados.containsKey("correo_prueba@example.com"));
	}

	@Test
	public void testRegistroUsuarioExistente() {
		Ventana_RegistroUsuario vRegistro = new Ventana_RegistroUsuario();
	    Usuario usuario1 = new Usuario("Nombre1", "correo_existente@example.com", "contrasena_existente");
	    Usuario usuario2 = new Usuario("Nombre2", "correo_existente@example.com", "contrasena_existente");
	    vRegistro.registrarUsuario(usuario1);
	    vRegistro.registrarUsuario(usuario2);
	    assertTrue(vRegistro.usuariosRegistrados.containsKey(usuario1.getCorreo()));  // Registro exitoso
	    assertTrue(vRegistro.usuariosRegistrados.containsKey(usuario2.getCorreo())); // Intento de registrar un usuario con el mismo correo (también debería ser exitoso)
	}
}