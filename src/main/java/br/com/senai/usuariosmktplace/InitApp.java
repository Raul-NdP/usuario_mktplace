package br.com.senai.usuariosmktplace;

<<<<<<< HEAD
import br.com.senai.usuariosmktplace.core.domain.Usuario;
=======
import br.com.senai.usuariosmktplace.core.service.UsuarioService;
>>>>>>> feature/novo-usuario

public class InitApp {

	public static void main(String[] args) {
		
<<<<<<< HEAD
		Usuario usuario = new Usuario("jose.silva", "José da Silva", "jose2023");
		
		System.out.println(usuario.getLogin());
=======
		UsuarioService service = new UsuarioService();
		
		service.criarPor("Renan Alberto Souza", "AHAHA123456");
>>>>>>> feature/novo-usuario
		
	}

}
