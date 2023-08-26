package br.com.senai.usuariosmktplace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import br.com.senai.usuariosmktplace.core.dao.DaoUsuario;
import br.com.senai.usuariosmktplace.core.dao.FactoryDao;
import br.com.senai.usuariosmktplace.core.domain.Usuario;
import br.com.senai.usuariosmktplace.core.service.UsuarioService;

@SpringBootApplication
public class InitApp {
	
	@Autowired
	private Usuario usuario;
	
	@Autowired
	private FactoryDao factoryDao;
	
	@Autowired
	private DaoUsuario dao;
	
	@Autowired
	private UsuarioService service;
	
	public static void main(String[] args) {
		SpringApplication.run(InitApp.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			
			this.usuario.setNomeCompleto("Juquinha de Almeida");
			System.out.println(usuario.getNomeCompleto());
			
			Usuario usuarioEncontrado = factoryDao.getDaoUsuario().buscarPor("alan.duarte");
			System.out.println(usuarioEncontrado.getNomeCompleto());
			
			System.out.println(service.buscarPor("alan.duarte").getNomeCompleto());
			
		};
	}

}
