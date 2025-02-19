package service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dao.DaoUsuario;
import model.Animais;
import model.Usuarios;
import spark.Request;
import spark.Response;

public class ServiceUsuarios {
	private DaoUsuario dao;
	private static BCryptPasswordEncoder encriptador = new BCryptPasswordEncoder(); //Encriptador
	Gson gson;
	
	//Classe para string de resposta
	static class ResponseMessage {
        private String message;

        public ResponseMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
	
	//Construtor padrao
	public ServiceUsuarios() {
		dao = new DaoUsuario();
		gson = new Gson();
	}
	
	//Metodo para adicionar um item passado durante uma requisicao
	public Object add(Request request, Response response) {
		int tam_etiquetas;
		// Definir o tipo de retorno como JSON
        response.type("application/json");

        // Receber o JSON enviado no corpo da requisição
        String body = request.body();

        // Transformar o JSON em um objeto do tipo JsonObject
        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
        
        //Transformar as etiquetas em array
        tam_etiquetas = jsonObject.getAsJsonArray("etiquetas").size();
        String[] arraydeetiquetas = new String[tam_etiquetas];
        for(int i = 0; i < tam_etiquetas; i++) {
        	arraydeetiquetas[i] = jsonObject.getAsJsonArray("etiquetas").get(i).getAsString();
        }
        
        //Transforma o JsonObject em um objeto do tipo Usuarios
        Usuarios usuarioinserir = new Usuarios(
        	    jsonObject.get("id").getAsString(),
        	    jsonObject.get("nome").getAsString(),
        	    jsonObject.get("email").getAsString(),
        	    jsonObject.get("senha").getAsString(),
        	    !jsonObject.get("imagem").isJsonNull() ? jsonObject.get("imagem").getAsString() : null,
        	    jsonObject.get("datanascimento").getAsString(),
        	    jsonObject.get("telefone").getAsString(),
        	    jsonObject.get("cpf").getAsString(),
        	    jsonObject.get("moradia").getAsString(),
        	    String.join("; ", arraydeetiquetas)
        	);
        
        System.out.println(usuarioinserir.getSenha());
        
        //Inserir o objeto do tipo Usuarios na tabela
        dao.inserirusuario(usuarioinserir);

        // Confirmar o recebimento do objeto Usuarios
        System.out.println("Usuario recebido recebido: " + usuarioinserir.getNome());

        // Enviar uma resposta de sucesso
        return gson.toJson(new ResponseMessage("Usuario recebido com sucesso!"));
	}
	
	//Metodo para ler um item com determinado id passado na url
	public Object get(Request request, Response response) {
		//Receber o id da url
		String id = request.params(":id");
		Object resp = "";
		
		//Ler o usuario da tabela com o respectivo id
		Usuarios usuarioespecifico = dao.getusuario(id);
		
		//Tranformar o objeto Usuarios em json
		if(usuarioespecifico != null) {
    	    response.header("Content-Type", "application/json");
    	    response.header("Content-Encoding", "UTF-8");
    	    
    	    resp = resp +"{"+
    	    	  "\"id\": " + "\""+ usuarioespecifico.getId() +"\"," +
    	    	  "\"nome\": " + "\""+ usuarioespecifico.getNome() +"\"," +
    	    	  "\"email\": " + "\""+ usuarioespecifico.getEmail() +"\"," +
    	    	  "\"imagem\": " + (usuarioespecifico.getImagem() != null ? "\""+ usuarioespecifico.getImagem() +"\"" : null) + "," +
    	    	  "\"etiquetas\": " + "[\""+ String.join("\", \"", usuarioespecifico.getEtiquetas()) +"\"],"+
    	    	  "\"datanascimento\": " + "\""+ usuarioespecifico.getDataDeNascimento() +"\"," +
    	    	  "\"telefone\": " + "\""+ usuarioespecifico.getTelefone() +"\"," +
    	    	  "\"cpf\": " + "\""+ usuarioespecifico.getCpf() +"\"," +
    	    	  "\"moradia\": " + "\""+ usuarioespecifico.getMoradia() +"\"" +
    	    	"}";
		} else {
			response.status(404);
			resp = "Usuario " + id + " nao encontrado";
		}
		return resp;
	}
	
	//Metodo para atualizar um item com o id passado na url para os valores passados na requisicao
	public Object update(Request request, Response response) {
		int tam_etiquetas;
		String resp;
		
		//Ler o id da url
		String id = request.params(":id");
		
		// Definir o tipo de retorno como JSON
        response.type("application/json");

        // Receber o JSON enviado no corpo da requisição
        String body = request.body();

        // Transformar o JSON em um objeto do tipo JsonObject
        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
        
        //Transformar as etiquetas em array
        tam_etiquetas = jsonObject.getAsJsonArray("etiquetas").size();
        String[] arraydeetiquetas = new String[tam_etiquetas];
        for(int i = 0; i < tam_etiquetas; i++) {
        	arraydeetiquetas[i] = jsonObject.getAsJsonArray("etiquetas").get(i).getAsString();
        }
		
        //Ler o usuario da tabela com o respectivo id
		Usuarios usuarioespecifico = dao.getusuario(id);
		
		//Transformar o JsonObject em objeto do tipo Animais
		if(usuarioespecifico != null) {
			if(encriptador.matches(jsonObject.get("autenticacao").getAsString(), usuarioespecifico.getSenha())) {
				usuarioespecifico.setNome(jsonObject.get("nome").getAsString());
				usuarioespecifico.setEmail(jsonObject.get("email").getAsString());
				if(!jsonObject.get("senha").isJsonNull()) {
					String senhacomhash = encriptador.encode(jsonObject.get("senha").getAsString());
					usuarioespecifico.setSenha(senhacomhash);
				}
				usuarioespecifico.setImagem(!jsonObject.get("imagem").isJsonNull() ? jsonObject.get("imagem").getAsString() : null);
				usuarioespecifico.setEtiquetas(String.join("; ", arraydeetiquetas));
				usuarioespecifico.setDataDeNascimento(jsonObject.get("datanascimento").getAsString());
				usuarioespecifico.setTelefone(jsonObject.get("telefone").getAsString());
				usuarioespecifico.setCpf(jsonObject.get("cpf").getAsString()); 
				usuarioespecifico.setMoradia(jsonObject.get("moradia").getAsString());
			
			
			//Atualizar o animal
			dao.atualizarusuario(usuarioespecifico);
			resp = "" + id;
			} else {
				response.status(401);
				resp = "Autenticação inválida para o usuario: " + id;
			}
		} else {
			response.status(404);
			resp = "Usuario " + id + " nao encontrado";
		}
		return resp;
	}
	
	//Metodo para excluir um item com o id passado na url
	public Object delete(Request request, Response response) {
		String resp;
		boolean removido = false;
		
		//Ler o id da url
		String id = request.params(":id");
		
		//Ler o usuario da tabela com o respectivo id
		Usuarios usuarioespecifico = dao.getusuario(id);
		
		//Se o usuario existir exclui-lo
		if(usuarioespecifico != null) {
			removido = dao.excluirusuario(id);
			response.status(200);
			resp = "" + id;
		} else {
			response.status(404);
			resp = "Usuario nao encontrado";
		}
		
		return resp;
	}
	
	//Metodo para ler todos os itens da tabela
	public Object getAll(Request request, Response response) {

		//Ler todos os usuarios
		Usuarios[] listausuarios = dao.getusuarios();
		String resp = "";
		
		//Transformar todos os usuarios em um json de usuarios
		if(listausuarios != null) {
			resp = "[";
			for (Usuarios usuarioespecifico : listausuarios) {
				resp = resp +"{"+
		    	    	  "\"id\": " + "\""+ usuarioespecifico.getId() +"\"," +
		    	    	  "\"nome\": " + "\""+ usuarioespecifico.getNome() +"\"," +
		    	    	  "\"email\": " + "\""+ usuarioespecifico.getEmail() +"\"," +
		    	    	  "\"imagem\": " + (usuarioespecifico.getImagem() != null ? "\""+ usuarioespecifico.getImagem() +"\"" : null) + "," +
		    	    	  "\"etiquetas\": " + "[\""+ String.join("\", \"", usuarioespecifico.getEtiquetas()) +"\"],"+
		    	    	  "\"datanascimento\": " + "\""+ usuarioespecifico.getDataDeNascimento() +"\"," +
		    	    	  "\"telefone\": " + "\""+ usuarioespecifico.getTelefone() +"\"," +
		    	    	  "\"cpf\": " + "\""+ usuarioespecifico.getCpf() +"\"," +
		    	    	  "\"moradia\": " + "\""+ usuarioespecifico.getMoradia() +"\"" +
		    	    	"},";
			}
		} else {
			resp = "[]";
		}
		StringBuilder bs = new StringBuilder(resp);
		bs.setCharAt(bs.length() - 1, ']');
		
	    response.header("Content-Type", "application/json");
	    response.header("Content-Encoding", "UTF-8");
		return bs.toString();
	}
	
	//Metodo para ler os usuarios da tabela retornar o json com os dados do usuario
	public Object verify(Request request, Response response) {
		//Variaveis
		String email_usuarioespecifico, senha_usuarioespecifico, email_usuariologado, senha_usuariologado, resp = "";
		Usuarios usuario_encontrado = null, usuariio_especifico = null;
		int lista_tam;
		
		response.header("Content-Type", "application/json");
	    response.header("Content-Encoding", "UTF-8");
		
		//Ler todos os usuarios
		Usuarios[] listausuarios = dao.getusuarios();
			
		//Ler o corpo da requisicao
		String body = request.body();
		JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
		
		//Separa o email e senha
		email_usuariologado = jsonObject.get("email").getAsString();
		senha_usuariologado = jsonObject.get("senha").getAsString();
		
		
		//Coparar com todos os usuarios e verificar
		lista_tam = listausuarios.length;
		for(int i = 0; i < lista_tam; i++) {
			usuario_encontrado = listausuarios[i];
			email_usuarioespecifico = usuario_encontrado.getEmail();
			senha_usuarioespecifico = usuario_encontrado.getSenha();
			if(email_usuariologado.compareTo(email_usuarioespecifico) == 0) {
				System.out.println("Email encontrado!");
				if(encriptador.matches(senha_usuariologado, senha_usuarioespecifico)) {
					i = lista_tam;
					System.out.println("Senha compativel!");
				} else {
					usuario_encontrado = null;
				}
			} else {
				usuario_encontrado = null;
			}
		}
		
		//Retornar os dados do usuario caso encontrado ou error 404 caso contrario
		if(usuario_encontrado == null) {
			response.status(404);
			resp = "Usuario nao encontrado";
			System.out.println("Usuario nao encontrado!");
		} else {
			response.status(200);
			resp = resp +"{"+
	    	    	  "\"id\": " + "\""+ usuario_encontrado.getId() +"\"," +
	    	    	  "\"nome\": " + "\""+ usuario_encontrado.getNome() +"\"," +
	    	    	  "\"email\": " + "\""+ usuario_encontrado.getEmail() +"\"," +
	    	    	  "\"imagem\": " + (usuario_encontrado.getImagem() != null ? "\""+ usuario_encontrado.getImagem() +"\"" : null) + "," +
	    	    	  "\"etiquetas\": " + "[\""+ String.join("\", \"", usuario_encontrado.getEtiquetas()) +"\"],"+
	    	    	  "\"datanascimento\": " + "\""+ usuario_encontrado.getDataDeNascimento() +"\"," +
	    	    	  "\"telefone\": " + "\""+ usuario_encontrado.getTelefone() +"\"," +
	    	    	  "\"cpf\": " + "\""+ usuario_encontrado.getCpf() +"\"," +
	    	    	  "\"moradia\": " + "\""+ usuario_encontrado.getMoradia() +"\"" +
	    	    	"}";
		}
		
		
		return resp;
	}
	
	//Metodo para verificar a senha do usuario apartir do id
	public Object verifyid(Request request, Response response) {
		//Variaveis
		String id = request.params(":id");
		String resp = "";
		Usuarios usuario_recebido = dao.getusuario(id);
		
		//Ler o corpo da requisicao
		String body = request.body();
		JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
		
		
		if(usuario_recebido != null) {
			if(encriptador.matches(jsonObject.get("senha").getAsString(), usuario_recebido.getSenha())) {
				resp = resp +"{"+
		    	    	  "\"id\": " + "\""+ usuario_recebido.getId() +"\"," +
		    	    	  "\"nome\": " + "\""+ usuario_recebido.getNome() +"\"," +
		    	    	  "\"email\": " + "\""+ usuario_recebido.getEmail() +"\"," +
		    	    	  "\"imagem\": " + (usuario_recebido.getImagem() != null ? "\""+ usuario_recebido.getImagem() +"\"" : null) + "," +
		    	    	  "\"etiquetas\": " + "[\""+ String.join("\", \"", usuario_recebido.getEtiquetas()) +"\"],"+
		    	    	  "\"datanascimento\": " + "\""+ usuario_recebido.getDataDeNascimento() +"\"," +
		    	    	  "\"telefone\": " + "\""+ usuario_recebido.getTelefone() +"\"," +
		    	    	  "\"cpf\": " + "\""+ usuario_recebido.getCpf() +"\"," +
		    	    	  "\"moradia\": " + "\""+ usuario_recebido.getMoradia() +"\"" +
		    	    	"}";
			} else {
				response.status(401);
				resp = "Autenticação inválida para o usuario: " + id;
			}
		} else {
			response.status(404);
			resp = "Usuario nao encontrado";
			System.out.println("Usuario nao encontrado!");
		}
		return resp;
	}
	
}
