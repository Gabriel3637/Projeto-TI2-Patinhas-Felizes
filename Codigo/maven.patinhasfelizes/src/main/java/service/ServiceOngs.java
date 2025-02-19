package service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dao.DaoOngs;
import model.Ongs;
import model.Usuarios;
import spark.Request;
import spark.Response;

public class ServiceOngs {
	private DaoOngs dao;
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
	public ServiceOngs() {
		dao = new DaoOngs();
		gson = new Gson();
	}
	
	//Metodo para adicionar um item passado durante uma requisicao
	public Object add(Request request, Response response) {
		// Definir o tipo de retorno como JSON
        response.type("application/json");

        // Receber o JSON enviado no corpo da requisição
        String body = request.body();

        // Transformar o JSON em um objeto do tipo JsonObject
        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
        
        //Transforma o JsonObject em um objeto do tipo Ongs
        Ongs ongespecifica = new Ongs(
        	    jsonObject.get("id").getAsString(),
        	    jsonObject.get("nome").getAsString(),
        	    jsonObject.get("cnpj").getAsString(),
        	    jsonObject.get("endereco").getAsString(),
        	    jsonObject.get("telefone").getAsString(),
        	    jsonObject.get("email").getAsString(),
        	    jsonObject.get("senha").getAsString(),
        	    jsonObject.get("logo").getAsString(),
        	    jsonObject.get("historia").getAsString()
        	);
        
        //Inserir o objeto do tipo Ongs na tabela
        dao.inserirong(ongespecifica);

        // Confirmar o recebimento do objeto animal
        System.out.println("Ong recebida: " + ongespecifica.getNome());

        // Enviar uma resposta de sucesso
        return gson.toJson(new ResponseMessage("Comentario recebido com sucesso!"));
	}
	
	//Metodo para ler um item com determinado id passado na url
	public Object get(Request request, Response response) {
		//Receber o id da url
		String id = request.params(":id");
		Object resp = "";
		
		//Ler a ong da tabela com o respectivo id
		Ongs ongespecifica = dao.getong(id);
		
		//Tranformar o objeto Ongs em json
		if(ongespecifica != null) {
    	    response.header("Content-Type", "application/json");
    	    response.header("Content-Encoding", "UTF-8");
    	    
    	    resp = gson.toJson(ongespecifica);
		} else {
			response.status(404);
			resp = "Formulario " + id + " nao encontrado";
		}
		return resp;
	}
	
	//Metodo para atualizar um item com o id passado na url para os valores passados na requisicao
	public Object update(Request request, Response response) {
		String resp;
		
		//Ler o id da url
		String id = request.params(":id");
		
		// Definir o tipo de retorno como JSON
        response.type("application/json");

        // Receber o JSON enviado no corpo da requisição
        String body = request.body();

        // Transformar o JSON em um objeto do tipo JsonObject
        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
		
        //Ler a ong da tabela com o respectivo id
		Ongs ongespecifica = dao.getong(id);
		
		//Transformar o JsonObject em objeto do tipo Ongs
		if(ongespecifica != null) {
			if(encriptador.matches(jsonObject.get("autenticacao").getAsString(), ongespecifica.getSenha())) {
				ongespecifica.setNome(jsonObject.get("nome").getAsString());
				ongespecifica.setCnpj(jsonObject.get("cnpj").getAsString());
				ongespecifica.setEndereco(jsonObject.get("endereco").getAsString());
				ongespecifica.setTelefone(jsonObject.get("telefone").getAsString());
				ongespecifica.setEmail(jsonObject.get("email").getAsString());
				if(!jsonObject.get("senha").isJsonNull()) {
					String senhacomhash = encriptador.encode(jsonObject.get("senha").getAsString());
					ongespecifica.setSenha(senhacomhash);
				}
				ongespecifica.setLogo(jsonObject.get("logo").getAsString());
				ongespecifica.setHistoria(jsonObject.get("historia").getAsString());
				
				dao.atualizarong(ongespecifica);
				resp = "" + id;
			} else {
				response.status(401);
				resp = "Autenticação inválida para a ong: " + id;
			}
		} else {
			response.status(404);
			resp = "Ong " + id + " nao encontrada";
		}
		return resp;
	}
	
	//Metodo para excluir um item com o id passado na url
	public Object delete(Request request, Response response) {
		String resp;
		boolean removido = false;
		
		//Ler o id da url
		String id = request.params(":id");
		
		//Ler a ong da tabela com o respectivo id
		Ongs ongespecifica = dao.getong(id);
		
		//Se a ong existir exclui-la
		if(ongespecifica != null) {
			removido = dao.excluirong(id);
			response.status(200);
			resp = "" + id;
		} else {
			response.status(404);
			resp = "Ong nao encontrada";
		}
		
		return resp;
	}
	
	//Metodo para ler todos os itens da tabela
	public Object getAll(Request request, Response response) {
		//Ler todas as ongs
		Ongs[] listaongs = dao.getongs();
		
		String resp = "";
		//Transformar todos as ongs em um json de ongs
		if(listaongs != null) {
			resp = "[";
			for (Ongs ongespecifica : listaongs) {
				resp = resp +"{"+
		    	    	  "\"id\": " + "\""+ ongespecifica.getId() +"\"," +
		    	    	  "\"nome\": " + "\""+ ongespecifica.getNome() +"\"," +
		    	    	  "\"cnpj\": " + "\""+ ongespecifica.getCnpj() +"\"," +
		    	    	  "\"endereco\": " + "\""+ ongespecifica.getEndereco() +"\"," +
		    	    	  "\"telefone\": " + "\""+ ongespecifica.getTelefone() +"\"," +
		    	    	  "\"email\": " + "\""+ ongespecifica.getEmail() +"\"," +
		    	    	  "\"senha\": " + "\""+ ongespecifica.getSenha() +"\"," +
		    	    	  "\"logo\": " + "\""+ ongespecifica.getLogo() +"\"," +
		    	    	  "\"historia\": " + "\""+ ongespecifica.getHistoria() +"\"" +
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
	
	//Verificar senha do login com ong
	public Object verify(Request request, Response response) {
		//Variaveis
		String email_ongespecifica, senha_ongespecifica, email_onglogada, senha_onglogada, resp = "";
		Ongs ong_encontrada = null;
		int lista_tam;
		
		response.header("Content-Type", "application/json");
	    response.header("Content-Encoding", "UTF-8");
	    
	    //Ler todas as ongs registradas
	    Ongs listaongs[] = dao.getongs();
	    
	    //Ler o corpo da requisicao
	    String body = request.body();
	    JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
	    
		//Separa o email e senha
	    email_onglogada = jsonObject.get("email").getAsString();
		senha_onglogada = jsonObject.get("senha").getAsString();
		
		//Coparar com todos os usuarios e verificar
		lista_tam = listaongs.length;
		for(int i = 0; i < lista_tam; i++) {
			ong_encontrada = listaongs[i];
			email_ongespecifica = ong_encontrada.getEmail();
			senha_ongespecifica = ong_encontrada.getSenha();
			if(email_onglogada.compareTo(email_ongespecifica) == 0) {
				System.out.println("Email encontrado!");
				if(encriptador.matches(senha_onglogada, senha_ongespecifica)) {
					i = lista_tam;
					System.out.println("Senha compativel!");
				} else {
					ong_encontrada = null;
				}
			} else {
				ong_encontrada = null;
			}
		}
		
		//Retornar os dados da ong caso encontrada ou error 404 caso contrario
		if(ong_encontrada == null) {
			response.status(404);
			resp = "Ong nao encontrada";
			System.out.println("Ong nao encontrada!");
		} else {
			response.status(200);
			resp = resp +"{"+
	    	    	  "\"id\": " + "\""+ ong_encontrada.getId() +"\"," +
	    	    	  "\"nome\": " + "\""+ ong_encontrada.getNome() +"\"," +
	    	    	  "\"cnpj\": " + "\""+ ong_encontrada.getCnpj() +"\"," +
	    	    	  "\"endereco\": " + "\""+ ong_encontrada.getEndereco() +"\"," +
	    	    	  "\"telefone\": " + "\""+ ong_encontrada.getTelefone() +"\"," +
	    	    	  "\"email\": " + "\""+ ong_encontrada.getEmail() +"\"," +
	    	    	  "\"logo\": " + "\""+ ong_encontrada.getLogo() +"\"," +
	    	    	  "\"historia\": " + "\""+ ong_encontrada.getHistoria() +"\"" +
	    	    	"}";
		}
		
		//Retorno
		return resp;
	}
	
	//Metodo para verificar a senha da ong apartir do id
		public Object verifyid(Request request, Response response) {
			//Variaveis
			String id = request.params(":id");
			String resp = "";
			Ongs ong_recebida = dao.getong(id);
			
			//Ler o corpo da requisicao
			String body = request.body();
			JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
			

			
			if(ong_recebida != null) {
				if(encriptador.matches(jsonObject.get("senha").getAsString(), ong_recebida.getSenha())) {
					resp = resp +"{"+
			    	    	  "\"id\": " + "\""+ ong_recebida.getId() +"\"," +
			    	    	  "\"nome\": " + "\""+ ong_recebida.getNome() +"\"," +
			    	    	  "\"cnpj\": " + "\""+ ong_recebida.getCnpj() +"\"," +
			    	    	  "\"endereco\": " + "\""+ ong_recebida.getEndereco() +"\"," +
			    	    	  "\"telefone\": " + "\""+ ong_recebida.getTelefone() +"\"," +
			    	    	  "\"email\": " + "\""+ ong_recebida.getEmail() +"\"," +
			    	    	  "\"logo\": " + "\""+ ong_recebida.getLogo() +"\"," +
			    	    	  "\"historia\": " + "\""+ ong_recebida.getHistoria() +"\"" +
			    	    	"}";
				} else {
					response.status(401);
					resp = "Autenticação inválida para a ong: " + id;
				}
			} else {
				response.status(404);
				resp = "Ong nao encontrada";
				System.out.println("Ong nao encontrada!");
			}
			return resp;
		}
}
