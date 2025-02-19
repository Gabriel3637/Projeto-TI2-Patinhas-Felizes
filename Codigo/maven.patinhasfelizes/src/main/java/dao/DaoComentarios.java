package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import model.Comentarios;

public class DaoComentarios extends DAO{
	
	//Metodo para inserir um objeto do tipo Comentarios na tabela comentarios do banco de dados postgresql
	public boolean inserircomentario(Comentarios comentarioinserir) {
		//Variaveis
		boolean status = false;
		
		//Conectar com o servidor sql
		conectar();
		
		try {
			//Linha de codigo em sql para inserir os atributos do objeto do tipo Comentarios na tabela comentarios no banco de dados
			String sql_inserir = "INSERT INTO comentarios (id, usuarioid, animalid, conteudo) VALUES ( ?, ?, ?, ?);";
			
			//Criar objeto do tipo PreparedStatement para realizar consultas ao banco de dados
			PreparedStatement st = conexao.prepareStatement(sql_inserir);
			
			//Atribuir dados
			st.setString(1, comentarioinserir.getId());
			st.setString(2, comentarioinserir.getUsuarioId());
			st.setString(3, comentarioinserir.getAnimalId());
			st.setString(4, comentarioinserir.getConteudo());
			
			//Executar determinada linha de codigo em sql
			int linhas_afetadas = st.executeUpdate();
			
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
	
	//Metodo para atualizar os valores de um item da tabela comentarios para os atributos do objeto do tipo Comentarios passado como parametros
	public boolean atualizarcomentario(Comentarios comentarioatualizar) {
		//Variaveis
		boolean status = false;
		
		//Conectar com o servidor sql
		conectar();
		
		try {
			//Criar uma linha de codigo em sql para atualizar um item da tabela comentarios no banco de dados para os atributos do objeto do tipo Comentarios
			String sql = "UPDATE comentarios SET usuarioid = ?, animalid = ?, conteudo = ? WHERE id = ?;";
			
			/*
			String sql = "UPDATE comentarios SET usuarioid = '" + comentarioatualizar.getUsuarioId() +
					"', animalid = '" + comentarioatualizar.getAnimalId() + 
					"', conteudo = '" + comentarioatualizar.getConteudo() + 
					"' WHERE id = '" + comentarioatualizar.getId() + "';";
			*/
			
			
			//Criar objeto do tipo PreparedStatement para realizar consultas ao banco de dados
			PreparedStatement st = conexao.prepareStatement(sql);
			
			//Atribuir dados
			st.setString(1, comentarioatualizar.getUsuarioId());
			st.setString(2, comentarioatualizar.getAnimalId());
			st.setString(3, comentarioatualizar.getConteudo());
			st.setString(4, comentarioatualizar.getId());
			
			//Executar a linha de codigo em sql
			int linhas_alteradas = st.executeUpdate();
			
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
	
	//Excluir um item da tabela comentarios do banco de dados segundo um id passado como parametro
	public boolean excluircomentario(String codigo) {
		//Variaveis
		boolean status = false;
		
		//Conectar com o servidor sql
		conectar();
		
		try {
			//Linha de codigo sql para excluir determinado item da tabela comentarios do banco de dados segundo o id passado como parametro
			String sql_delete = "DELETE FROM comentarios WHERE id = ?;";
			
			//Criar objeto do tipo PreparedStatement para realizar consultas ao banco de dados
			PreparedStatement st = conexao.prepareStatement(sql_delete);
			
			//Atribuir dados
			st.setString(1, codigo);
			
			//Executar linha de codigo sql
			int linhas_alteradas = st.executeUpdate();
			
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
	
	//Ler todos os itens da tabela e retornar em forma de array de objeto Comentarios
	public Comentarios[] getcomentarios() {
		//Variaveis
		Comentarios[] cometariostotal = null;
		
		//Conectar com o servidor sql
		conectar();
		
		try {
			//Linha de codigo sql para ler todos os itens da tabela comentarios do banco de dados
			String sql_getall = "SELECT * FROM comentarios;";
			
			
			//Criar objeto do tipo PreparedStatement para realizar consultas ao banco de dados
			PreparedStatement st = conexao.prepareStatement(sql_getall, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			//Executar linha de codigo sql
			ResultSet rs = st.executeQuery();
			
			//Verificar se ha itens para ler
			if(rs.next()) {
				
				//Identificar a quantidade de linhas e criar um array do objeto Comentarios com o dado tamanho
				rs.last();
				cometariostotal = new Comentarios[rs.getRow()];
				rs.beforeFirst();
				
				//Ler todos os itens da tabela transforma-los em objetos do tipo Comentarios e inseri-los no array
				for(int i = 0; rs.next(); i++) {
					cometariostotal[i] = new Comentarios(
							rs.getString("id"),
							rs.getString("usuarioid"),
							rs.getString("animalid"),
							rs.getString("conteudo")
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
		return cometariostotal;
	}
	
	//Ler um item da tabela comentarios do banco de dados segundo um id passado como parametro e retorna-lo como objeto Comentarios
	public Comentarios getcomentario(String id) {
		//Variaveis
		Comentarios comentariototal = null;
		
		//Conectar com o servidor sql
		conectar();
		
		try {
			//Linha sql para ler um item da tabela comentarios do banco de dados segundo um id passado como parametro
			String sql_get = "SELECT * FROM comentarios WHERE id = ?;";
			
			//Criar objeto do tipo PreparedStatement para realizar consultas ao banco de dados
			PreparedStatement st = conexao.prepareStatement(sql_get, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			//Atribuir dados
			st.setString(1, id);
			
			//Ler um item da tabela comentarios
			ResultSet rs = st.executeQuery();
			
			//Ler o item e transforma-lo em um objeto do tipo Comentarios
			if(rs.next()) {
				
				comentariototal = new Comentarios(
							rs.getString("id"),
							rs.getString("usuarioid"),
							rs.getString("animalid"),
							rs.getString("conteudo")
						);
			}
			
			//Encerrar as consultas no banco de dados
			conexao.commit();
			st.close();
		}catch(Exception u) {
			System.err.print(u.getMessage());
			u.printStackTrace();
			
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
		return comentariototal;
	}
}
