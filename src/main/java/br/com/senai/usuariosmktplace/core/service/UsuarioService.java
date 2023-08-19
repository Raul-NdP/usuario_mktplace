package br.com.senai.usuariosmktplace.core.service;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

import br.com.senai.usuariosmktplace.core.dao.DaoUsuario;
import br.com.senai.usuariosmktplace.core.dao.FactoryDao;
import br.com.senai.usuariosmktplace.core.domain.Usuario;

public class UsuarioService {
	
	private DaoUsuario dao;

	public UsuarioService() {
		dao = FactoryDao.getInstance().getDaoUsuario();
	}
	
	public void inserir(Usuario usuario) {
		
	}
	
	public void alterar(Usuario usuario) {
		
	}
	
	public Usuario buscarPor(String login) {
		
		return null;
		
	}
	
	public void validar(Usuario usuario) {
		if (usuario != null) {
			
			boolean isNomeInvalido = usuario.getNomeCompleto() == null;
			
			if (isNomeInvalido) {
				throw new IllegalArgumentException("O nome é obrigatório");
			}
			
			boolean isLoginInvalido = usuario.getLogin() == null
					|| usuario.getLogin().length() < 5
					|| usuario.getLogin().length() > 50;
			
			if (isLoginInvalido) {
				throw new IllegalArgumentException("O login é obrigatório e deve possuir entre 5 e 50 caracteres");
			}
			
			boolean isSenhaeInvalida = usuario.getSenha() == null
					|| usuario.getSenha().length() < 6
					|| usuario.getSenha().length() > 15;
			
			if (isSenhaeInvalida) {
				throw new IllegalArgumentException("A senha é obrigatória e deve possuir entre 6 e 15 caracteres");
			}
			
		} else {
			throw new NullPointerException("O usuário não pode ser nulo");
		}
	}
	
	public String removerAcentoDo(String nomeCompleto) {
		
		return Normalizer.normalize(nomeCompleto, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		
	}
	
	public List<String> fracionar(String nomeCompleto) {
		
		List<String> nomeFracionado = new ArrayList<String>();
		
		if (nomeCompleto != null && !nomeCompleto.isBlank()) {
			
			String[] partesNome = nomeCompleto.split(" ");
			
			for (String parte : partesNome) {
				boolean isNaoContemArtigo = !parte.equalsIgnoreCase("e")
						&& !parte.equalsIgnoreCase("de")
						&& !parte.equalsIgnoreCase("da")
						&& !parte.equalsIgnoreCase("dos")
						&& !parte.equalsIgnoreCase("das");
				
				if (isNaoContemArtigo) {
					nomeFracionado.add(parte.toLowerCase());
				}
			}
		}
		return nomeFracionado;
	}
	
	public String gerarLoginPor(String nomeCompleto) {
		
		nomeCompleto = this.removerAcentoDo(nomeCompleto);
		List<String> partesNome = this.fracionar(nomeCompleto);
		
		String loginGerado = null;
		Usuario usuarioEncontrado = null;
		
		if (!partesNome.isEmpty()) {
			for (int i = 1; i < partesNome.size(); i++) {
				loginGerado = partesNome.get(0) + "." + partesNome.get(i);
				usuarioEncontrado = dao.buscarPor(loginGerado);
				if (usuarioEncontrado == null) {
					return loginGerado;
				}
			}
			
			int proximoSequencial = 0;
			String loginDisponivel = null;
			
			while (usuarioEncontrado != null) {
				loginDisponivel = loginGerado + ++ proximoSequencial;
				usuarioEncontrado = dao.buscarPor(loginDisponivel);
			}
			
			loginGerado = loginDisponivel;
		}
		return loginGerado;
	}
	
	public String gerarHashDa(String senha) {
		
		return new DigestUtils(MessageDigestAlgorithms.MD5).digestAsHex(senha);
		
	}
	
}
