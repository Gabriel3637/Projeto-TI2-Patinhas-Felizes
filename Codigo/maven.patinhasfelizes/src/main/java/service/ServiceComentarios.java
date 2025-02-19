package service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import dao.DaoComentarios;
import model.Comentarios;
import spark.Request;
import spark.Response;

public class ServiceComentarios {
	private DaoComentarios dao;
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
	public ServiceComentarios() {
		dao = new DaoComentarios();
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
        
        //Transforma o JsonObject em um objeto do tipo Comentarios
        Comentarios comentarioinserir = new Comentarios(
        	    jsonObject.get("id").getAsString(),
        	    jsonObject.get("usuarioid").getAsString(),
        	    jsonObject.get("animalid").getAsString(),
        	    jsonObject.get("conteudo").getAsString()
        	);
        
        //Inserir o objeto do tipo Comentarios na tabela
        dao.inserircomentario(comentarioinserir);

        // Confirmar o recebimento do objeto Comentarios
        System.out.println("Comentario recebido: " + comentarioinserir.getId());

        // Enviar uma resposta de sucesso
        return gson.toJson(new ResponseMessage("Comentario recebido com sucesso!"));
	}
	
	//Metodo para ler um item com determinado id passado na url
	public Object get(Request request, Response response) {
		//Receber o id da url
		String id = request.params(":id");
		Object resp = "";
		
		//Ler o comentario da tabela com o respectivo id
		Comentarios comentarioespecifico = dao.getcomentario(id);
		
		//Tranformar o objeto Comentarios em json
		if(comentarioespecifico != null) {
    	    response.header("Content-Type", "application/json");
    	    response.header("Content-Encoding", "UTF-8");
    	    
    	    resp = resp +"{"+
    	    	  "\"id\": " + "\""+ comentarioespecifico.getId() +"\"," +
    	    	  "\"usuarioid\": " + "\""+ comentarioespecifico.getUsuarioId() +"\"," +
    	    	  "\"animalid\": " + "\""+ comentarioespecifico.getAnimalId() +"\"," +
    	    	  "\"conteudo\": " + "\""+ comentarioespecifico.getConteudo() +"\"" +
    	    	"}";
		} else {
			response.status(404);
			resp = "Comentario " + id + " nao encontrado";
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
		
        //Ler o comentario da tabela com o respectivo id
		Comentarios comentarioespecifico = dao.getcomentario(id);
		
		//Transformar o JsonObject em objeto do tipo Comentarios
		if(comentarioespecifico != null) {
			
			comentarioespecifico.setConteudo(jsonObject.get("conteudo").getAsString());
			comentarioespecifico.setAnimalId(jsonObject.get("animalid").getAsString());
			comentarioespecifico.setUsuarioId(jsonObject.get("usuarioid").getAsString());
			
			dao.atualizarcomentario(comentarioespecifico);
			resp = "" + id;
		} else {
			response.status(404);
			resp = "Comentario " + id + " nao encontrado";
		}
		return resp;
	}
	
	//Metodo para excluir um item com o id passado na url
	public Object delete(Request request, Response response) {
		String resp;
		boolean removido = false;
		
		//Ler o id da url
		String id = request.params(":id");
		
		//Ler o comentario da tabela com o respectivo id
		Comentarios comentarioespecifico = dao.getcomentario(id);
		
		//Se o comentario existir exclui-lo
		if(comentarioespecifico != null) {
			removido = dao.excluircomentario(id);
			response.status(200);
			resp = "" + id;
		} else {
			response.status(404);
			resp = "Comentario nao encontrado";
		}
		
		return resp;
	}
	
	//Metodo para ler todos os itens da tabela
	public Object getAll(Request request, Response response) {
		//Ler todos os comentarios
		Comentarios[] listacomentarios = dao.getcomentarios();
		
		//Transformar todos os comentarios em um json de comentarios
		String resp = "[";
		for (Comentarios comentarioespecifico : listacomentarios) {
			resp = resp +"{"+
	    	    	  "\"id\": " + "\""+ comentarioespecifico.getId() +"\"," +
	    	    	  "\"usuarioid\": " + "\""+ comentarioespecifico.getUsuarioId() +"\"," +
	    	    	  "\"animalid\": " + "\""+ comentarioespecifico.getAnimalId() +"\"," +
	    	    	  "\"conteudo\": " + "\""+ comentarioespecifico.getConteudo() +"\"" +
	    	    	"},";
		}
		StringBuilder bs = new StringBuilder(resp);
		bs.setCharAt(bs.length() - 1, ']');
		
	    response.header("Content-Type", "application/json");
	    response.header("Content-Encoding", "UTF-8");
		return bs.toString();
	}
}
