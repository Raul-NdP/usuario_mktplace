package br.com.senai.usuariosmktplace;

import br.com.senai.usuariosmktplace.core.domain.Usuario;

public class InitApp {

	public static void main(String[] args) {
		
		Usuario usuario = new Usuario("jose.silva", "José da Silva", "jose2023");
		
		System.out.println(usuario.getLogin());
		
	}

}
