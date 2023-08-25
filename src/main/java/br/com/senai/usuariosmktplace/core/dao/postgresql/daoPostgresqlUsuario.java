package br.com.senai.usuariosmktplace.core.dao.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.senai.usuariosmktplace.core.dao.DaoUsuario;
import br.com.senai.usuariosmktplace.core.dao.ManagerDb;
import br.com.senai.usuariosmktplace.core.domain.Usuario;

public class daoPostgresqlUsuario implements DaoUsuario {

	private final String INSERT = "INSERT INTO usuarios (login, nome, senha) "
								+ "VALUES (?, ?, ?)";
	
	private final String UPDATE = "UPDATE usuarios "
								+ "SET nome = ?, senha = ? "
								+ "WHERE login = ?";
	
	private final String SELECT_BY_LOGIN = "SELECT u.login, u.nome, u.senha "
										+ "FROM usuarios u "
										+ "WHERE login = ?";
	
	private Connection conexao;
	
	public daoPostgresqlUsuario() {
		this.conexao = ManagerDb.getInstance().getConexao();
	}
	
	@Override
	public void inserir(Usuario usuario) {
		
		PreparedStatement ps = null;
		
		try {
			
			ps = conexao.prepareStatement(INSERT);
			ps.setString(1, usuario.getLogin());
			ps.setString(2, usuario.getNomeCompleto());
			ps.setString(3, usuario.getSenha());
			ps.execute();
			
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao inserir o usuário. "
					+ "Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
		}
		
	}

	@Override
	public void alterar(Usuario usuario) {
	
		PreparedStatement ps = null;
		
		try {
			
			ManagerDb.getInstance().configurarAutocommitDa(conexao, false);
			ps = conexao.prepareStatement(UPDATE);
			ps.setString(1, usuario.getNomeCompleto());
			ps.setString(2, usuario.getSenha());
			ps.setString(3, usuario.getLogin());
			
			boolean isAlteracaoOk = ps.executeUpdate() == 1;
			
			if (isAlteracaoOk) {
				conexao.commit();
			} else {
				conexao.rollback();
			}
			
			ManagerDb.getInstance().configurarAutocommitDa(conexao, true);
			
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao alterar o usuário. "
					+ "Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
		}
		
	}

	@Override
	public Usuario buscarPor(String login) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			ps = conexao.prepareStatement(SELECT_BY_LOGIN);
			ps.setString(1, login);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				return extrairDo(rs);
			}
			
			return null;
			
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao buscar o usuário. "
					+ "Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
			ManagerDb.getInstance().fechar(rs);
		}
		
	}
	
	public Usuario extrairDo(ResultSet rs) {
		
		try {
			
			String login = rs.getString("login");
			String nome = rs.getString("nome");
			String senha = rs.getString("senha");
			
			return new Usuario(login, senha, nome);
			
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao extrair o usuário. "
					+ "Motivo: " + e.getMessage());
		}
		
	}

}

