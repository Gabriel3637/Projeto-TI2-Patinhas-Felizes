package service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dao.DaoFormularios;
import model.Comentarios;
import model.Formularios;
import spark.Request;
import spark.Response;

public class ServiceFormularios {
	private DaoFormularios dao;
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
	public ServiceFormularios() {
		dao = new DaoFormularios();
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
        
        //Transforma o JsonObject em um objeto do tipo Formularios
        Formularios formularioespecifico = new Formularios(
        	    jsonObject.get("id").getAsString(),
        	    jsonObject.get("usuarioid").getAsString(),
        	    jsonObject.get("animalid").getAsString()
        	);
        
        //Inserir o objeto do tipo Formularios na tabela
        dao.inserirformulario(formularioespecifico);

        // Confirmar o recebimento do objeto Formularios
        System.out.println("Formulario recebido: " + formularioespecifico.getId());

        // Enviar uma resposta de sucesso
        return gson.toJson(new ResponseMessage("Formulario recebido com sucesso!"));
	}
	
	//Metodo para ler um item com determinado id passado na url
	public Object get(Request request, Response response) {
		//Receber o id da url
		String id = request.params(":id");
		Object resp = "";
		
		//Ler o formulario da tabela com o respectivo id
		Formularios formularioespecifico = dao.getformulario(id);
		
		//Tranformar o objeto Formularios em json
		if(formularioespecifico != null) {
    	    response.header("Content-Type", "application/json");
    	    response.header("Content-Encoding", "UTF-8");
    	    
    	    resp = resp +"{"+
    	    	  "\"id\": " + "\""+ formularioespecifico.getId() +"\"," +
    	    	  "\"usuarioid\": " + "\""+ formularioespecifico.getUsuarioId() +"\"," +
    	    	  "\"animalid\": " + "\""+ formularioespecifico.getAnimalId() +"\"" +
    	    	"}";
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
		
        //Ler o formulario da tabela com o respectivo id
		Formularios formularioespecifico = dao.getformulario(id);
		
		//Transformar o JsonObject em objeto do tipo Formularios
		if(formularioespecifico != null) {
			
			formularioespecifico.setAnimalId(jsonObject.get("animalid").getAsString());
			formularioespecifico.setUsuarioId(jsonObject.get("usuarioid").getAsString());
			
			dao.atualizarformulario(formularioespecifico);
			resp = "" + id;
		} else {
			response.status(404);
			resp = "Formulario " + id + " nao encontrado";
		}
		return resp;
	}
	
	//Metodo para excluir um item com o id passado na url
	public Object delete(Request request, Response response) {
		String resp;
		boolean removido = false;
		
		//Ler o id da url
		String id = request.params(":id");
		
		//Ler o formulario da tabela com o respectivo id
		Formularios formularioespecifico = dao.getformulario(id);
		
		//Se o formulario existir exclui-lo
		if(formularioespecifico != null) {
			removido = dao.excluirformulario(id);
			response.status(200);
			resp = "" + id;
		} else {
			response.status(404);
			resp = "Formulario nao encontrado";
		}
		
		return resp;
	}
	
	//Metodo para ler todos os itens da tabela
	public Object getAll(Request request, Response response) {
		//Ler todos os formularios
		Formularios[] listaformularios = dao.getformularios();
		String resp = "";
		//Transformar todos os formularios em um json de formularios
		if(listaformularios != null) {
			resp = "[";
			for (Formularios formularioespecifico : listaformularios) {
				resp = resp +"{"+
		    	    	  "\"id\": " + "\""+ formularioespecifico.getId() +"\"," +
		    	    	  "\"usuarioid\": " + "\""+ formularioespecifico.getUsuarioId() +"\"," +
		    	    	  "\"animalid\": " + "\""+ formularioespecifico.getAnimalId() +"\"" +
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
}
