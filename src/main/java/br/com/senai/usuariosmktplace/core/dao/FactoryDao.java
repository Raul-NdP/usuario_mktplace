package br.com.senai.usuariosmktplace.core.dao;

import br.com.senai.usuariosmktplace.core.dao.postgresql.daoPostgresqlUsuario;

public class FactoryDao {
	
	private static FactoryDao instance;
	
	private FactoryDao() {}
	
	public DaoUsuario getDaoUsuario() {
		return new daoPostgresqlUsuario();
	}
	
	public static FactoryDao getInstance() {
		
		if (instance == null) {
			instance = new FactoryDao();
		}
		
		return instance;
		
	}
	
}
