package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import model.Ongs;

public class DaoOngs extends DAO{
	
	private static BCryptPasswordEncoder encriptador = new BCryptPasswordEncoder(); //Encriptador
	
	//Metodo para inserir um objeto do tipo Ongs na tabela ong do banco de dados postgresql
	public boolean inserirong(Ongs onginserir) {
		//Variaveis
		boolean status = false;
		String senhacomhash = encriptador.encode(onginserir.getSenha());
		
		//Conectar com o servidor sql
		conectar();
		
		try {
			/*
			"INSERT INTO ong (id, nome, cnpj, endereco, telefone, email, senha, logo, historia) " 
							+ "VALUES ('" + onginserir.getId() + "', '" + onginserir.getNome() + "', '" + onginserir.getCnpj() + "', '" +
							onginserir.getEndereco()  + "', '" + onginserir.getTelefone() + "', '" + onginserir.getEmail() + "', '" +
							senhacomhash + "', '" + onginserir.getLogo() + "', '" + onginserir.getHistoria()+ "');"
			*/
			
			//Linha de codigo em sql para inserir os atributos do objeto do tipo Ongs na tabela ong no banco de dados
			String sql_inserir = "INSERT INTO ong (id, nome, cnpj, endereco, telefone, email, senha, logo, historia) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
			
			//Criar objeto do tipo PreparedStatement para realizar consultas ao banco de dados
			PreparedStatement st = conexao.prepareStatement(sql_inserir);
			
			//Atribuir dados
			st.setString(1, onginserir.getId());
			st.setString(2, onginserir.getNome());
			st.setString(3, onginserir.getCnpj());
			st.setString(4, onginserir.getEndereco());
			st.setString(5, onginserir.getTelefone());
			st.setString(6, onginserir.getEmail());
			st.setString(7, onginserir.getSenha());
			st.setString(8, onginserir.getLogo());
			st.setString(9, onginserir.getHistoria());
			
			//Executar determinada linha de codigo em sql
			st.executeUpdate();
			
			//Encerrar as consultas no banco de dados
			conexao.commit();
			st.close();
			status = true;
		}catch(SQLException u) {
			System.err.print(u.getMessage());
			
			if(conexao != null) {
				try {
					System.err.print("A transação está sendo revertida");
					conexao.rollback();
				} catch (SQLException excep) {
					System.err.print("Erro na operacao!");
				}
				throw new RuntimeException(u);
			}
			throw new RuntimeException(u);
		}finally {
			//Encerrar a conexao com o banco de dados
			close();
		}
		return status;
	}
	
	//Metodo para atualizar os valores de um item da tabela ong para os atributos do objeto do tipo Ongs passado como parametros
	public boolean atualizarong(Ongs ongatualizar) {
		//Variaveis
		boolean status = false;
		
		
		
		//Conectar com o servidor sql
		conectar();
		
		try {
			
			/*
			"UPDATE ong SET nome = '" + ongatualizar.getNome() 
			+ "', cnpj = '" + ongatualizar.getCnpj() + "', endereco = '" + ongatualizar.getEndereco() + 
			"', telefone = '" + ongatualizar.getTelefone() + "', email = '" + ongatualizar.getEmail() + 
			"', senha = '" + ongatualizar.getSenha() + "', logo = '" + ongatualizar.getLogo() +
			"', historia = '" + ongatualizar.getHistoria()
			+ "' WHERE id = '" + ongatualizar.getId() + "';"
			*/
			
			//Criar uma linha de codigo em sql para atualizar um item da tabela ong no banco de dados para os atributos do objeto do tipo Ongs
			String sql = "UPDATE ong SET nome = ?, cnpj = ?, endereco = ?, telefone = ?, email = ?, senha = ?, logo = ?, historia = ? WHERE id = ?;";
			
			//Criar objeto do tipo PreparedStatement para realizar consultas ao banco de dados
			PreparedStatement st = conexao.prepareStatement(sql);
			
			//Atribuir dados
			st.setString(1, ongatualizar.getNome());
			st.setString(2, ongatualizar.getCnpj());
			st.setString(3, ongatualizar.getEndereco());
			st.setString(4, ongatualizar.getTelefone());
			st.setString(5, ongatualizar.getEmail());
			st.setString(6, ongatualizar.getSenha());
			st.setString(7, ongatualizar.getLogo());
			st.setString(8, ongatualizar.getHistoria());
			st.setString(9, ongatualizar.getId());
			
			//Executar a linha de codigo em sql
			st.executeUpdate();
			
			//Encerrar as consultas no banco de dados
			conexao.commit();
			st.close();
			status = true;
		}catch(SQLException u) {
			System.err.print(u.getMessage());
			
			if(conexao != null) {
				try {
					System.err.print("A transação está sendo revertida");
					conexao.rollback();
				} catch (SQLException excep) {
					System.err.print("Erro na operacao!");
				}
				throw new RuntimeException(u);
			}
			throw new RuntimeException(u);
		}finally {
			//Encerrar a conexao com o banco de dados
			close();
		}
		return status;
	}
	
	//Excluir um item da tabela ong do banco de dados segundo um id passado como parametro
	public boolean excluirong(String codigo) {
		//Variaveis
		boolean status = false;
		
		//Conectar com o servidor sql
		conectar();
		
		try {
			//Linha de codigo sql para excluir determinado item da tabela ong do banco de dados segundo o id passado como parametro
			String sql_delete = "DELETE FROM ong WHERE id = ?;";
			
			//Criar objeto do tipo PreparedStatement para realizar consultas ao banco de dados
			PreparedStatement st = conexao.prepareStatement(sql_delete);
			
			//Atribuir dados
			st.setString(1, codigo);
			
			//Executar linha de codigo sql
			st.executeUpdate();
			
			//Encerrar as consultas no banco de dados
			conexao.commit();
			st.close();
			status = true;
		}catch(SQLException u) {
			System.err.print(u.getMessage());
			
			if(conexao != null) {
				try {
					System.err.print("A transação está sendo revertida");
					conexao.rollback();
				} catch (SQLException excep) {
					System.err.print("Erro na operacao!");
				}
				throw new RuntimeException(u);
			}
			throw new RuntimeException(u);
		}finally {
			//Encerrar a conexao com o banco de dados
			close();
		}
		return status;
	}
	
	//Ler todos os itens da tabela e retornar em forma de array de objeto Ongs
	public Ongs[] getongs() {
		//Variaveis
		Ongs[] ongstotal = null;
		
		//Conectar com o servidor sql
		conectar();
		
		try {
			//Linha de codigo sql para ler todos os itens da tabela ong do banco de dados
			String sql_getall = "SELECT * FROM ong";
			
			//Criar objeto do tipo PreparedStatement para realizar consultas ao banco de dados
			PreparedStatement st = conexao.prepareStatement(sql_getall, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			//Executar linha de codigo sql
			ResultSet rs = st.executeQuery();
			
			//Verificar se ha itens para ler
			if(rs.next()) {
				
				//Identificar a quantidade de linhas e criar um array do objeto Ongs com o dado tamanho
				rs.last();
				ongstotal = new Ongs[rs.getRow()];
				rs.beforeFirst();
				
				//Ler todos os itens da tabela transforma-los em objetos do tipo Ongs e inseri-los no array
				for(int i = 0; rs.next(); i++) {
					ongstotal[i] = new Ongs(
							rs.getString("id"),
							rs.getString("nome"),
							rs.getString("cnpj"),
							rs.getString("endereco"),
							rs.getString("telefone"),
							rs.getString("email"),
							rs.getString("senha"),
							rs.getString("logo"),
							rs.getString("historia")
					);
				}
			}
			
			//Encerrar as consultas no banco de dados
			conexao.commit();
			st.close();
		}catch(Exception u) {
			System.err.print(u.getMessage());
			
			if(conexao != null) {
				try {
					System.err.print("A transação está sendo revertida");
					conexao.rollback();
				} catch (SQLException excep) {
					System.err.print("Erro na operacao!");
				}
				throw new RuntimeException(u);
			}
			throw new RuntimeException(u);
		}finally {
			//Encerrar a conexao com o banco de dados
			close();
		}
		return ongstotal;
	}
	
	//Ler um item da tabela ong do banco de dados segundo um id passado como parametro e retorna-lo como objeto Ongs
	public Ongs getong(String id) {
		//Variaveis
		Ongs ongtotal = null;
		
		//Conectar com o servidor sql
		conectar();
		
		try {
			//Linha sql para ler um item da tabela ong do banco de dados segundo um id passado como parametro
			String sql_get = "SELECT * FROM ong WHERE id = ?;";
			
			//Criar objeto do tipo PreparedStatement para realizar consultas ao banco de dados
			PreparedStatement st = conexao.prepareStatement(sql_get, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			//Atribuir dados
			st.setString(1, id);
			
			//Ler um item da tabela ong do banco de dados segundo um id passado como parametro
			ResultSet rs = st.executeQuery();
			
			//Ler o item e transforma-lo em um objeto do tipo Ongs
			if(rs.next()) {
				
				ongtotal = new Ongs(
					rs.getString("id"),
					rs.getString("nome"),
					rs.getString("cnpj"),
					rs.getString("endereco"),
					rs.getString("telefone"),
					rs.getString("email"),
					rs.getString("senha"),
					rs.getString("logo"),
					rs.getString("historia")
				);
			}
			
			//Encerrar as consultas no banco de dados
			conexao.commit();
			st.close();
		}catch(Exception u) {
			System.err.print(u.getMessage());
			
			if(conexao != null) {
				try {
					System.err.print("A transação está sendo revertida");
					conexao.rollback();
				} catch (SQLException excep) {
					System.err.print("Erro na operacao!");
				}
				throw new RuntimeException(u);
			}
			throw new RuntimeException(u);
		}finally {
			//Encerrar a conexao com o banco de dados
			close();
		}
		return ongtotal;
	}
}
