package service;

import dao.DaoAnimal;
import model.Animais;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ServiceAnimais {
	private DaoAnimal dao;
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
	public ServiceAnimais() {
		dao = new DaoAnimal();
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
        
        //Transforma o JsonObject em um objeto do tipo Animais
        Animais animalinserir = new Animais(
        	    jsonObject.get("id").getAsString(),
        	    jsonObject.get("nome").getAsString(),
        	    jsonObject.get("especie").getAsString(),
        	    jsonObject.get("porte").getAsString().charAt(0),
        	    !jsonObject.get("donoid").isJsonNull() ? jsonObject.get("donoid").getAsString() : null,
        	    jsonObject.get("imagem").getAsString(),
        	    jsonObject.get("vacinas").getAsString(),
        	    String.join("; ", arraydeetiquetas),
        	    !jsonObject.get("datasaida").isJsonNull() ? jsonObject.get("datasaida").getAsString() : null, 
        	    jsonObject.get("dataentrada").getAsString(),
        	    jsonObject.get("descricao").getAsString(),
        	    jsonObject.get("historia").getAsString(),
        	    jsonObject.get("raca").getAsString(),
        	    jsonObject.get("datanascimento").getAsString(),
        	    jsonObject.get("castrado").getAsBoolean(),
        	    jsonObject.get("sexo").getAsString().charAt(0) 
        	);
        
        //Inserir o objeto do tipo Animais na tabela
        dao.inseriranimal(animalinserir);

        // Confirmar o recebimento do objeto animal
        System.out.println("Animal recebido: " + animalinserir.getNome());

        // Enviar uma resposta de sucesso
        return gson.toJson(new ResponseMessage("Animal recebido com sucesso!"));
	}
	
	//Metodo para ler um item com determinado id passado na url
	public Object get(Request request, Response response) {
		//Receber o id da url
		String id = request.params(":id");
		Object resp = "";
		
		//Ler o animal da tabela com o respectivo id
		Animais animalespecifico = dao.getanimal(id);
		
		//Tranformar o objeto Animais em json
		if(animalespecifico != null) {
    	    response.header("Content-Type", "application/json");
    	    response.header("Content-Encoding", "UTF-8");
    	    
    	    resp = resp +"{"+
    	    	  "\"id\": " + "\""+ animalespecifico.getId() +"\"," +
    	    	  "\"imagem\": " + "\""+ animalespecifico.getImagem() +"\"," +
    	    	  "\"especie\": " + "\""+ animalespecifico.getEspecie() +"\"," +
    	    	  "\"nome\": " + "\""+ animalespecifico.getNome() +"\"," +
    	    	  "\"descricao\": " + "\""+ animalespecifico.getDescricao() +"\"," +
    	    	  "\"sexo\": " + "\""+ animalespecifico.getSexo() +"\"," +
    	    	  "\"datanascimento\": " + "\""+ animalespecifico.getDataNascimento() +"\"," +
    	    	  "\"dataentrada\": " + "\""+ animalespecifico.getDataEntrada() +"\"," +
    	    	  "\"raca\": " + "\""+ animalespecifico.getRaca() +"\"," +
    	    	  "\"porte\": " + "\""+ animalespecifico.getPorte() +"\"," +
    	    	  "\"vacinas\": " + "\""+ animalespecifico.getVacinas() +"\"," +
    	    	  "\"castrado\": " + animalespecifico.isCastrado() +"," +
    	    	  "\"historia\": " + "\""+ animalespecifico.getHistoria() +"\"," +
    	    	  "\"etiquetas\": " + "[\""+ String.join("\", \"", animalespecifico.getEtiquetas()) +"\"]," +
    	    	  "\"donoid\": " + (animalespecifico.getDonoId() != null ? "\""+ animalespecifico.getDonoId() +"\"" : null) + "," +
    	    	  "\"datasaida\": " + (animalespecifico.getDataSaida() != null ? "\""+ animalespecifico.getDataSaida() +"\"" : null) +
    	    	"}";
		} else {
			response.status(404);
			resp = "Animal " + id + " nao encontrado";
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
		
        //Ler o animal da tabela com o respectivo id
		Animais animalespecifico = dao.getanimal(id);
		
		//Transformar o JsonObject em objeto do tipo Animais
		if(animalespecifico != null) {
			
			animalespecifico.setNome(jsonObject.get("nome").getAsString());
			animalespecifico.setEspecie(jsonObject.get("especie").getAsString());
			animalespecifico.setPorte(jsonObject.get("porte").getAsString().charAt(0)); 
			animalespecifico.setDonoId(!jsonObject.get("donoid").isJsonNull() ? jsonObject.get("donoid").getAsString() : null);
			animalespecifico.setImagem(jsonObject.get("imagem").getAsString());
			animalespecifico.setVacinas(jsonObject.get("vacinas").getAsString());
			animalespecifico.setEtiquetas(String.join("; ", arraydeetiquetas));
			animalespecifico.setDataSaida(!jsonObject.get("datasaida").isJsonNull() ? jsonObject.get("datasaida").getAsString() : null);
			animalespecifico.setDataEntrada(jsonObject.get("dataentrada").getAsString());
			animalespecifico.setDescricao(jsonObject.get("descricao").getAsString()); 
			animalespecifico.setHistoria(jsonObject.get("historia").getAsString()); 
			animalespecifico.setRaca(jsonObject.get("raca").getAsString()); 
			animalespecifico.setDataNascimento(jsonObject.get("datanascimento").getAsString()); 
			animalespecifico.setCastrado(jsonObject.get("castrado").getAsBoolean());
			animalespecifico.setSexo(jsonObject.get("sexo").getAsString().charAt(0));
			
			//Atualizar o animal
			dao.atualizaranimal(animalespecifico);
			resp = "" + id;
		} else {
			response.status(404);
			resp = "Produto " + id + " nao encontrado";
		}
		return resp;
	}
	
	//Metodo para excluir um item com o id passado na url
	public Object delete(Request request, Response response) {
		String resp;
		boolean removido = false;
		
		//Ler o id da url
		String id = request.params(":id");
		
		//Ler o animal da tabela com o respectivo id
		Animais animalespecifico = dao.getanimal(id);
		
		//Se o animal existir exclui-lo
		if(animalespecifico != null) {
			removido = dao.excluianimal(id);
			response.status(200);
			resp = "" + id;
		} else {
			response.status(404);
			resp = "Produto nao encontrado";
		}
		
		return resp;
	}
	
	//Metodo para ler todos os itens da tabela
	public Object getAll(Request request, Response response) {
		//Ler todos os animais
		Animais[] listaanimais = dao.getanimais();
		
		String resp = "";
		
		//Transformar todos os aninmais em um json de animais
		if(listaanimais != null) {
			resp = "[";
			for (Animais animalespecifico : listaanimais) {
				resp = resp +"{"+
		    	    	  "\"id\": " + "\""+ animalespecifico.getId() +"\"," +
		    	    	  "\"imagem\": " + "\""+ animalespecifico.getImagem() +"\"," +
		    	    	  "\"especie\": " + "\""+ animalespecifico.getEspecie() +"\"," +
		    	    	  "\"nome\": " + "\""+ animalespecifico.getNome() +"\"," +
		    	    	  "\"descricao\": " + "\""+ animalespecifico.getDescricao() +"\"," +
		    	    	  "\"sexo\": " + "\""+ animalespecifico.getSexo() +"\"," +
		    	    	  "\"datanascimento\": " + "\""+ animalespecifico.getDataNascimento() +"\"," +
		    	    	  "\"dataentrada\": " + "\""+ animalespecifico.getDataEntrada() +"\"," +
		    	    	  "\"raca\": " + "\""+ animalespecifico.getRaca() +"\"," +
		    	    	  "\"porte\": " + "\""+ animalespecifico.getPorte() +"\"," +
		    	    	  "\"vacinas\": " + "\""+ animalespecifico.getVacinas() +"\"," +
		    	    	  "\"castrado\": " + animalespecifico.isCastrado() +"," +
		    	    	  "\"historia\": " + "\""+ animalespecifico.getHistoria() +"\"," +
		    	    	  "\"etiquetas\": " + "[\""+ String.join("\", \"", animalespecifico.getEtiquetas()) +"\"]," +
		    	    	  "\"donoid\": " + (animalespecifico.getDonoId() != null ? "\""+ animalespecifico.getDonoId() +"\"" : null) + "," +
		    	    	  "\"datasaida\": " + (animalespecifico.getDataSaida() != null ? "\""+ animalespecifico.getDataSaida() +"\"" : null) +
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