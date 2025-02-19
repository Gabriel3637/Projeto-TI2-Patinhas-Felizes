package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.sql.PreparedStatement;
import java.sql.Date;

import model.Animais;

public class DaoAnimal extends DAO{
	
	//Metodo para inserir um objeto do tipo Animais na tabela animais do banco de dados postgresql
	public boolean inseriranimal(Animais animalinserir) {
		//Variaveis
		boolean status = false;
		
		//Conectar com o servidor sql
		conectar();
		
		try {
			/*"INSERT INTO animais (id, nome, imagem, especie, raca, vacinas, datanascimento, dataentrada, sexo, porte, castrado, etiquetas, descricao, historia, donoid, datasaida) " 
		             + "VALUES ('" + animalinserir.getId() + "', '" + animalinserir.getNome() + "', '" + animalinserir.getImagem() + "', '" 
		             + animalinserir.getEspecie() + "', '" + animalinserir.getRaca() + "', '" + animalinserir.getVacinas() + "', '" 
		             + animalinserir.getDataNascimento() + "', '" + animalinserir.getDataEntrada() + "', '" + animalinserir.getSexo() + "', '" 
		             + animalinserir.getPorte() + "', " + animalinserir.isCastrado() + ", '" + String.join("; ", animalinserir.getEtiquetas()) + "', '" 
		             + animalinserir.getDescricao() + "', '" + animalinserir.getHistoria() + (animalinserir.getDonoId() != null ? "', '" + animalinserir.getDonoId() + "'," : "', " + animalinserir.getDonoId() + ",") + (animalinserir.getDataSaida() != null ? " '" 
		             + animalinserir.getDataSaida() + "');" : " " + animalinserir.getDataSaida() + ");"*/
			
			//Linha de codigo em sql para inserir os atributos do objeto do tipo Animais na tabela animais no banco de dados
			String sql_inserir = "INSERT INTO animais (id, nome, imagem, especie, raca, vacinas, datanascimento, dataentrada, sexo, porte, castrado, etiquetas, descricao, historia, donoid, datasaida) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			
			//Criar objeto do tipo PreparedStatement para realizar consultas ao banco de dados
			PreparedStatement st = conexao.prepareStatement(sql_inserir);
			
			//Atribuir dados
			st.setString(1, animalinserir.getId());
			st.setString(2, animalinserir.getNome());
			st.setString(3, animalinserir.getImagem());
			st.setString(4, animalinserir.getEspecie());
			st.setString(5, animalinserir.getRaca());
			st.setString(6, animalinserir.getVacinas());
			st.setDate(7, Date.valueOf(animalinserir.getDataNascimento()));
			st.setDate(8, Date.valueOf(animalinserir.getDataEntrada()));
			st.setString(9, String.valueOf(animalinserir.getSexo()));
			st.setString(10, String.valueOf(animalinserir.getPorte()));
			st.setBoolean(11, animalinserir.isCastrado());
			st.setString(12, String.join("; ", animalinserir.getEtiquetas()));
			st.setString(13, animalinserir.getDescricao());
			st.setString(14, animalinserir.getHistoria());
			if(animalinserir.getDonoId() != null) {
				st.setString(15, animalinserir.getDonoId());
			} else {
				st.setNull(15, Types.VARCHAR);
			}
			
			if(animalinserir.getDataSaida() != null) {
				st.setDate(16, Date.valueOf(animalinserir.getDataSaida()));
			} else {
				st.setNull(16, Types.DATE);
			}
			
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
		}finally{
			//Encerrar a conexao com o banco de dados
			close();
		}
		return status;
	}
	
	//Metodo para atualizar os valores de um item da tabela animais para os atributos do objeto do tipo Animais passado como parametros
	public boolean atualizaranimal(Animais animalatualizar) {
		//Variaveis
		boolean status = false;
		
		//Conectar com o servidor sql
		conectar();
		
		try {
			//Criar uma linha de codigo em sql para atualizar um item da tabela animais no banco de dados para os atributos do objeto do tipo Animais
			String sql = "UPDATE animais SET nome = ?, imagem = ?, especie = ?, raca = ?, vacinas = ?, datanascimento = ?, dataentrada = ?, sexo = ?, porte = ?, castrado = ?, etiquetas = ?, descricao = ?, historia = ?, donoid = ?, datasaida = ? WHERE id = ?;";
			
			/*
			"UPDATE animais SET nome = '" + animalatualizar.getNome() + "', imagem = '" + animalatualizar.getImagem() + "', especie = '" 
		             + animalatualizar.getEspecie() + "', raca = '" + animalatualizar.getRaca() + "', vacinas = '" + animalatualizar.getVacinas() + "', datanascimento = '" 
		             + animalatualizar.getDataNascimento() + "', dataentrada = '" + animalatualizar.getDataEntrada() + "', sexo = '" + animalatualizar.getSexo() 
		             + "', porte = '" + animalatualizar.getPorte() + "', castrado = " + animalatualizar.isCastrado() + ", etiquetas = '" 
		             + String.join("; ", animalatualizar.getEtiquetas()) + "', descricao = '" + animalatualizar.getDescricao() + "', historia = '" 
		             + animalatualizar.getHistoria() + (animalatualizar.getDonoId() != null ? "', donoid = '" + animalatualizar.getDonoId() + "'" : "', donoid = " + animalatualizar.getDonoId()) + (animalatualizar.getDataSaida() != null ? ", datasaida = '" + animalatualizar.getDataSaida() 
		             + "'" : ", datasaida = " + animalatualizar.getDataSaida()) + " WHERE id = '" + animalatualizar.getId() + "'"*/

			
			//Criar objeto do tipo PreparedStatement para realizar consultas ao banco de dados
			PreparedStatement st = conexao.prepareStatement(sql);
			
			//Atribuir dados
			st.setString(1, animalatualizar.getNome());
			st.setString(2, animalatualizar.getImagem());
			st.setString(3, animalatualizar.getEspecie());
			st.setString(4, animalatualizar.getRaca());
			st.setString(5, animalatualizar.getVacinas());
			st.setDate(6, Date.valueOf(animalatualizar.getDataNascimento()));
			st.setDate(7, Date.valueOf(animalatualizar.getDataEntrada()));
			st.setString(8, String.valueOf(animalatualizar.getSexo()));
			st.setString(9, String.valueOf(animalatualizar.getPorte()));
			st.setBoolean(10, animalatualizar.isCastrado());
			st.setString(11, String.join("; ", animalatualizar.getEtiquetas()));
			st.setString(12, animalatualizar.getDescricao());
			st.setString(13, animalatualizar.getHistoria());
			if(animalatualizar.getDonoId() != null) {
				st.setString(14, animalatualizar.getDonoId());
			} else {
				st.setNull(14, Types.VARCHAR);
			}
			
			if(animalatualizar.getDataSaida() != null) {
				st.setDate(15, Date.valueOf(animalatualizar.getDataSaida()));
			} else {
				st.setNull(15, Types.DATE);
			}
			
			st.setString(16, animalatualizar.getId());
			
			//Executar a linha de codigo sql
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
	
	//Excluir um item da tabela animais do banco de dados segundo um id passado como parametro
	public boolean excluianimal(String codigo) {
		//Variaveis
		boolean status = false;
		
		//Conectar com o servidor sql
		conectar();
		
		try {
			/*"DELETE FROM comentarios WHERE animalid = '" + codigo + "';"
					+ "DELETE FROM formularios WHERE animalid = '" + codigo + "';"
					+ "DELETE FROM animais WHERE id = '" + codigo + "';"*/
		
			//Linha de codigo sql para excluir determinado item da tabela animais do banco de dados segundo o id passado como parametro e os itens das tabelas comentarios e formularios que possuam chaves estrangeiras ligadas ao animal
			String sql_delete = "DELETE FROM comentarios WHERE animalid = ?;"
					+ "DELETE FROM formularios WHERE animalid = ?;"
					+ "DELETE FROM animais WHERE id = ?;";
			
			
			//Criar objeto do tipo PreparedStatement para realizar consultas ao banco de dados
			PreparedStatement st = conexao.prepareStatement(sql_delete);
			
			//Atribuir dados
			st.setString(1, codigo);
			st.setString(2, codigo);
			st.setString(3, codigo);
			
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
	
	
	//Ler todos os itens da tabela e retornar em forma de array de objeto Animais 
	public Animais[] getanimais() {
		//Variaveis
		Animais[] animaistotal = null;
		
		//Conectar com o servidor sql
		conectar();
		
		try {
			//Linha de codigo sql para ler todos os itens da tabela animais do banco de dados
			String sql_getall = "SELECT * FROM animais";
		
			//Criar objeto do tipo PrepareStatement para realizar consultas ao banco de dados
			PreparedStatement st = conexao.prepareStatement(sql_getall, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			//Executar linha de codigo sql
			ResultSet rs = st.executeQuery();
			
			//Verificar se ha itens para ler
			if(rs.next()) {
				
				//Identificar a quantidade de linha e criar um array do objeto Animais com o dado tamanho
				rs.last();
				animaistotal = new Animais[rs.getRow()];
				rs.beforeFirst();
				
				//Ler todos os itens da tabela transforma-los em objetos do tipo Animais e inseri-los no array
				for(int i = 0; rs.next(); i++) {
					animaistotal[i] = new Animais(
						    rs.getString("id"), 
						    rs.getString("nome"), 
						    rs.getString("especie"), 
						    rs.getString("porte").charAt(0), 
						    rs.getString("donoid"), 
						    rs.getString("imagem"), 
						    rs.getString("vacinas"), 
						    rs.getString("etiquetas"), 
						    rs.getString("datasaida"), 
						    rs.getString("dataentrada"), 
						    rs.getString("descricao"), 
						    rs.getString("historia"), 
						    rs.getString("raca"), 
						    rs.getString("datanascimento"), 
						    rs.getBoolean("castrado"), 
						    rs.getString("sexo").charAt(0)
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
		return animaistotal;
	}
	
	//Ler um item da tabela animais do banco de dados segundo um id passado como parametro e retorna-lo como objeto Animais
	public Animais getanimal(String id) {
		//Variaveis
		Animais animaltotal = null;
		
		//Conectar com o servidor sql
		conectar();
		try {
			//Linaha de codigo sql para ler um item da tabela animais do banco de dados segundo um id passado como parametro
			String sql_get = "SELECT * FROM animais WHERE id = ?;";
			
			//Criar objeto do tipo Statement para realizar consultas ao banco de dados
			PreparedStatement st = conexao.prepareStatement(sql_get, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			st.setString(1, id);
			
			//Executar linha codigo sql
			ResultSet rs = st.executeQuery();
			
			//Ler o item e transforma-lo em um objeto do tipo Animais
			if(rs.next()) {
				animaltotal = new Animais( rs.getString("id"), 
					    rs.getString("nome"), 
					    rs.getString("especie"), 
					    rs.getString("porte").charAt(0), 
					    rs.getString("donoid"), 
					    rs.getString("imagem"), 
					    rs.getString("vacinas"), 
					    rs.getString("etiquetas"), 
					    rs.getString("datasaida"), 
					    rs.getString("dataentrada"), 
					    rs.getString("descricao"), 
					    rs.getString("historia"), 
					    rs.getString("raca"), 
					    rs.getString("datanascimento"), 
					    rs.getBoolean("castrado"), 
					    rs.getString("sexo").charAt(0));
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
		return animaltotal;
	}
}

