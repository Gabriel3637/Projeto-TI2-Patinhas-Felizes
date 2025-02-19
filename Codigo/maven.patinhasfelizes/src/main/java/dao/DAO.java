package dao;

import java.sql.Connection;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
	//Atributo de conexao com servidor sql
	protected Connection conexao;
	
	//Construtor padrao
	public DAO() {
		conexao = null;
	}
	
	//Metodo para realizar a conexao com o servidor sql
	public boolean conectar() {
		boolean status = false;
		try {
			//Ler o arquivo database.config com os enderecos a senha e o usuario do servidor sql
			Properties config = new Properties();
	        FileInputStream fis = new FileInputStream("src/main/java/dao/database.config");
	        config.load(fis);

	        //Atribuir os enderecos e as senhas as suas respectivas variaveis
			String drivername = "org.postgresql.Driver";
			String servername = config.getProperty("db.servername");
			String mydatabase = config.getProperty("db.mydatabase");
			int porta = 5432;
			String url = "jdbc:postgresql://" + servername + ":" + porta +"/" + mydatabase;
			String username = config.getProperty("db.username");
			String password = config.getProperty("db.password");
			
			//Realizar as conexoes com o servidor sql
			Class.forName(drivername);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
			conexao.setAutoCommit(false);
		} catch(ClassNotFoundException e){
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch(SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		} catch(IOException e) {
			System.err.println("Nao foi possivel ler o .config -- " + e.getMessage());
		}
		
		return status;
	}
	
	//Metodo para encerrar a conexao com o servidor sql
	public boolean close() {
		boolean status = false;
		try {
			//Encerrar a conexao com o servidor sql
			conexao.close();
			status = true;
		}catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		
		return status;
	}
}
