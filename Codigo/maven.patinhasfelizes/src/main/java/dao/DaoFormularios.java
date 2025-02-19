package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Formularios;

public class DaoFormularios extends DAO {
	
	//Metodo para inserir um objeto do tipo Formularios na tabela formularios do banco de dados postgresql
	public boolean inserirformulario(Formularios formularioinserir) {
		//Variaveis
		boolean status = false;
		
		//Conectar com o servidor sql
		conectar();
		
		try {
			//Linha de codigo em sql para inserir os atributos do objeto do tipo Formularios na tabela formularios no banco de dados
			String sql_inserir = "INSERT INTO formularios (id, usuarioid, animalid) " + "VALUES ( ?, ?, ?);";
			
			//Criar objeto do tipo PreparedStatement para realizar consultas ao banco de dados
			PreparedStatement st = conexao.prepareStatement(sql_inserir);
			
			//Atribuir dados
			st.setString(1, formularioinserir.getId());
			st.setString(2, formularioinserir.getUsuarioId());
			st.setString(3, formularioinserir.getAnimalId());
			
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
	
	//Metodo para atualizar os valores de um item da tabela formularios para os atributos do objeto do tipo Formularios passado como parametros
	public boolean atualizarformulario(Formularios formularioatualizar) {
		//Variaveis
		boolean status = false;
		
		//Conectar com o servidor sql
		conectar();
		
		try {
			
			//Criar uma linha de codigo em sql para atualizar um item da tabela formularios no banco de dados para os atributos do objeto do tipo Formularios
			String sql = "UPDATE formularios SET usuarioid = ?, animalid = ? WHERE id = ?;";
			
			//Criar objeto do tipo PreparedStatement para realizar consultas ao banco de dados
			PreparedStatement st = conexao.prepareStatement(sql);
			
			//Atribuir valores
			st.setString(1,formularioatualizar.getUsuarioId());
			st.setString(2,formularioatualizar.getAnimalId());
			st.setString(3,formularioatualizar.getId());
			
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
	
	//Excluir um item da tabela formularios do banco de dados segundo um id passado como parametro
	public boolean excluirformulario(String codigo) {
		//Variaveis
		boolean status = false;
		
		//Conectar com o servidor sql
		conectar();
		
		try {
			//Linha para excluir um item da tabela comentarios do banco de dados segundo um id passado como parametro
			String sql_delete = "DELETE FROM formularios WHERE id = ?";
			
			//Criar objeto do tipo PreparedStatement para realizar consultas ao banco de dados
			PreparedStatement st = conexao.prepareStatement(sql_delete);
			
			//Atribuir dados
			st.setString(1, codigo);
			
			//Excluir um item da tabela comentarios do banco de dados segundo um id passado como parametro
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
	
	//Ler todos os itens da tabela e retornar em forma de array de objeto Formularios
	public Formularios[] getformularios() {
		//Variaveis
		Formularios[] formulariostotal = null;
		
		//Conectar com o servidor sql
		conectar();
		
		try {
			//Linha de codigo sql para ler todos os itens da tabela formularios do banco de dados
			String sql_getall = "SELECT * FROM formularios";
			
			//Criar objeto do tipo PreparedStatement para realizar consultas ao banco de dados
			PreparedStatement st = conexao.prepareStatement(sql_getall, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			//Executar linha de codigo sql
			ResultSet rs = st.executeQuery();
			
			//Verificar se ha itens para ler
			if(rs.next()) {
				
				//Identificar a quantidade de linhas e criar um array do objeto Formularios com o dado tamanho
				rs.last();
				formulariostotal = new Formularios[rs.getRow()];
				rs.beforeFirst();
				
				//Ler todos os itens da tabela transforma-los em objetos do tipo Formularios e inseri-los no array
				for(int i = 0; rs.next(); i++) {
					formulariostotal[i] = new Formularios(
						rs.getString("id"),
						rs.getString("usuarioid"),
						rs.getString("animalid")
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
		return formulariostotal;
	}
	
	
	//Ler um item da tabela formularios do banco de dados segundo um id passado como parametro e retorna-lo como objeto Formularios
	public Formularios getformulario(String id) {
		//Variaveis
		Formularios formulariototal = null;
		
		//Conectar com o servidor sql
		conectar();
		
		try {
			//Linha sql para ler um item da tabela formularios do banco de dados segundo um id passado como parametro
			String sql_get = "SELECT * FROM formularios WHERE id = ?;";
			
			//Criar objeto do tipo PreparedStatement para realizar consultas ao banco de dados
			PreparedStatement st = conexao.prepareStatement(sql_get, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			//Atribuir dados
			st.setString(1, id);
			
			//Ler um item da tabela formularios do banco de dados segundo um id passado como parametro
			ResultSet rs = st.executeQuery();
			
			//Ler o item e transforma-lo em um objeto do tipo Formularios
			if(rs.next()) {
				
				formulariototal = new Formularios(
					rs.getString("id"),
					rs.getString("usuarioid"),
					rs.getString("animalid")
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
		return formulariototal;
	}
}
