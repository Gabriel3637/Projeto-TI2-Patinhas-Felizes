package dao;

import java.sql.ResultSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.sql.Date;

import model.Usuarios;

public class DaoUsuario extends DAO{

	private static BCryptPasswordEncoder encriptador = new BCryptPasswordEncoder(); //Encriptador
	
	//Metodo para inserir um objeto do tipo Usuarios na tabela usuarios do banco de dados postgresql
	public boolean inserirusuario(Usuarios usuarioinserir) {
		//Variaveis
		boolean status = false;
		System.out.println(usuarioinserir.getSenha());
		String senhacomhash = encriptador.encode(usuarioinserir.getSenha());
		
		//Conectar com o servidor sql
		conectar();
		try {
			/*
			"INSERT INTO usuarios (id, email, nome, senha, imagem, etiquetas, datanascimento, telefone, cpf, moradia) " 
			           + "VALUES ('" + usuarioinserir.getId() + "', '" + usuarioinserir.getEmail() + "', '" 
			           + usuarioinserir.getNome() + "', '" + senhacomhash
			           + (usuarioinserir.getImagem() != null ? "', '" + usuarioinserir.getImagem() + "', " : "', " + usuarioinserir.getImagem() + ", ") + 
			           (usuarioinserir.getEtiquetas() != null ? "'" + String.join("; ", usuarioinserir.getEtiquetas()) + "', '" : String.join("; ", usuarioinserir.getEtiquetas()) + ", '") 
			           + usuarioinserir.getDataDeNascimento() + "', '" + usuarioinserir.getTelefone() + "', '" 
			           + usuarioinserir.getCpf() + "', '" + usuarioinserir.getMoradia() + "');"
			*/
			
			//Linha de codigo em sql para inserir os atributos do objeto do tipo Usuarios na tabela usuarios no banco de dados
			String sql_inserir = "INSERT INTO usuarios (id, email, nome, senha, imagem, etiquetas, datanascimento, telefone, cpf, moradia) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			
			//Criar objeto do tipo PreparedStatement para realizar consultas ao banco de dados
			PreparedStatement st = conexao.prepareStatement(sql_inserir);
			
			//Atribuir dados
			st.setString(1, usuarioinserir.getId());
			st.setString(2, usuarioinserir.getEmail());
			st.setString(3, usuarioinserir.getNome());
			st.setString(4, senhacomhash);
			if(usuarioinserir.getImagem() != null ) {
				st.setString(5, usuarioinserir.getImagem());				
			} else {
				st.setNull(5, Types.VARCHAR);
			}
			if(usuarioinserir.getEtiquetas() != null) {
				st.setString(6, String.join("; ", usuarioinserir.getEtiquetas()));
			} else {
				st.setNull(6, Types.VARCHAR);
			}
			st.setDate(7, Date.valueOf(usuarioinserir.getDataDeNascimento()));
			st.setString(8, usuarioinserir.getTelefone());
			st.setString(9, usuarioinserir.getCpf());
			st.setString(10, usuarioinserir.getMoradia());
			
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
	
	//Metodo para atualizar os valores de um item da tabela usuarios para os atributos do objeto do tipo Usuarios passado como parametros
	public boolean atualizarusuario(Usuarios usuarioatualizar) {
		//Variaveis
		boolean status = false;
		
		//Conectar com o servidor sql
		conectar();
		try {
			/*
			"UPDATE usuarios SET email = '" + usuarioatualizar.getEmail() + "', nome = '" + usuarioatualizar.getNome() + 
					"', senha = '" + usuarioatualizar.getSenha() + "', imagem =" +
					(usuarioatualizar.getImagem() != null ? " '" + usuarioatualizar.getImagem() + "', " : " " + usuarioatualizar.getImagem() + ", ") + 
					"etiquetas =" +
					(usuarioatualizar.getEtiquetas() != null ? " '" + String.join("; ", usuarioatualizar.getEtiquetas()) + "', " : " " + String.join("; ", usuarioatualizar.getEtiquetas()) + ", ") 
					+"datanascimento = '" + 
					usuarioatualizar.getDataDeNascimento() + "', telefone = '" + usuarioatualizar.getTelefone() + 
					"', cpf = '" + usuarioatualizar.getCpf() + "', moradia = '" + usuarioatualizar.getMoradia() + 
					"' WHERE id = '" + usuarioatualizar.getId() + "';";
			*/
			
			//Criar uma linha de codigo em sql para atualizar um item da tabela usuarios no banco de dados para os atributos do objeto do tipo Usuarios
			String sql = "UPDATE usuarios SET email = ?, nome = ?, senha = ?, imagem = ?, etiquetas = ?, datanascimento = ?, telefone = ?, cpf = ?, moradia = ? WHERE id = ?;";
			
			//Criar objeto do tipo PreparedStatement para realizar consultas ao banco de dados
			PreparedStatement st = conexao.prepareStatement(sql);
			
			//Atribuir dados
			st.setString(1, usuarioatualizar.getEmail());
			st.setString(2, usuarioatualizar.getNome());
			st.setString(3, usuarioatualizar.getSenha());
			if(usuarioatualizar.getImagem() != null ) {
				st.setString(4, usuarioatualizar.getImagem());				
			} else {
				st.setNull(4, Types.VARCHAR);
			}
			if(usuarioatualizar.getEtiquetas() != null) {
				st.setString(5, String.join("; ", usuarioatualizar.getEtiquetas()));
			} else {
				st.setNull(5, Types.VARCHAR);
			}
			st.setDate(6, Date.valueOf(usuarioatualizar.getDataDeNascimento()));
			st.setString(7, usuarioatualizar.getTelefone());
			st.setString(8, usuarioatualizar.getCpf());
			st.setString(9, usuarioatualizar.getMoradia());
			st.setString(10, usuarioatualizar.getId());
			
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
	
	//Excluir um item da tabela usuarios do banco de dados segundo um id passado como parametro
	public boolean excluirusuario(String codigo) {
		//Variaveis
		boolean status = false;
		
		//Conectar com o servidor sql
		conectar();
		try {
			/*"DELETE FROM comentarios WHERE usuarioid = '" + codigo + "';"
					+ "DELETE FROM formularios WHERE usuarioid = '" + codigo + "';"
					+"DELETE FROM usuarios WHERE id = '" + codigo + "';"*/
			
			//Linha de codigo sql para excluir determinado item da tabela usuarios do banco de dados segundo o id passado como parametro e os itens das tabelas comentarios e formularios que possuam chaves estrangeiras ligadas ao usuario
			String sql_delete = "DELETE FROM comentarios WHERE usuarioid = ?;"
					+ "DELETE FROM formularios WHERE usuarioid = ?;"
					+"DELETE FROM usuarios WHERE id = ?;";
			
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
	
	//Ler todos os itens da tabela e retornar em forma de array de objeto Usuarios 
	public Usuarios[] getusuarios() {
		//Variaveis
		Usuarios[] usuariostotal = null;
		
		//Conectar com o servidor sql
		conectar();
		try {
			//Linha de codigo sql para ler todos os itens da tabela usuarios do banco de dados
			String sql_getall = "SELECT * FROM usuarios;";
			
			//Criar objeto do tipo PreparedStatement para realizar consultas ao banco de dados
			PreparedStatement st = conexao.prepareStatement(sql_getall ,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			//Executar linha de codigo sql
			ResultSet rs = st.executeQuery();
			
			//Verificar se ha itens para ler
			if(rs.next()) {
				
				//Identificar a quantidade de linha e criar um array do objeto Usuarios com o dado tamanho
				rs.last();
				usuariostotal = new Usuarios[rs.getRow()];
				rs.beforeFirst();
				
				//Ler todos os itens da tabela transforma-los em objetos do tipo Usuarios e inseri-los no array
				for(int i = 0; rs.next(); i++) {
					usuariostotal[i] = new Usuarios(
						    rs.getString("id"),
						    rs.getString("nome"),
						    rs.getString("email"),
						    rs.getString("senha"),
						    rs.getString("imagem"),
						    rs.getString("datanascimento"),
						    rs.getString("telefone"),
						    rs.getString("cpf"),
						    rs.getString("moradia"),
						    rs.getString("etiquetas")
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
		return usuariostotal;
	}
	
	//Ler um item da tabela usuarios do banco de dados segundo um id passado como parametro e retorna-lo como objeto Usuarios
	public Usuarios getusuario(String id) {
		//Variaveis
		Usuarios usuariototal = null;
		
		//Conectar com o servidor sql
		conectar();
		try {
			/*"SELECT * FROM usuarios WHERE id = '" + id + "';" */
			
			//Linha sql par ler um item da tabela usuarios do banco de dados segundo um id passado como parametro
			String sql_get = "SELECT * FROM usuarios WHERE id = ?;";
					
			//Criar objeto do tipo PreparedStatement para realizar consultas ao banco de dados
			PreparedStatement st = conexao.prepareStatement(sql_get, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			//Atribuir dados
			st.setString(1, id);
			
			//Ler um item da tabela usuarios do banco de dados segundo um id passado como parametro
			ResultSet rs = st.executeQuery();
			
			//Ler o item e transforma-lo em um objeto do tipo Usuarios
			if(rs.next()) {
				
				usuariototal = new Usuarios(
					    rs.getString("id"),
					    rs.getString("nome"),
					    rs.getString("email"),
					    rs.getString("senha"),
					    rs.getString("imagem"),
					    rs.getString("datanascimento"),
					    rs.getString("telefone"),
					    rs.getString("cpf"),
					    rs.getString("moradia"),
					    rs.getString("etiquetas")
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

		return usuariototal;
	}
}
