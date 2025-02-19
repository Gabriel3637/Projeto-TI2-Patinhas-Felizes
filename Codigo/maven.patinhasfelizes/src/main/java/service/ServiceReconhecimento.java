package service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cognitiveservice.ImageAnalysisQuickStart;
import model.Tag;
import spark.Request;
import spark.Response;

public class ServiceReconhecimento {
	ImageAnalysisQuickStart reconhecedor;
	
	public Object reconhecer(Request request, Response response) {
		//Variaveis
		Object resp = "";
		
		//Criar objeto para reconhecer a imagem
		try {
			this.reconhecedor = new ImageAnalysisQuickStart();
			
			//Ler o corpo da requisicao
			String body = request.body();
			JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
			
			//Ler o corpo da response do reconhecimento
			String reconhecimento_str = this.reconhecedor.Reconheceranimal(jsonObject.get("imagem").getAsString());
			JsonObject reconhecimento_jsonObject = JsonParser.parseString(reconhecimento_str).getAsJsonObject();
			
			

	        // Acessar "customModelResult"
	        JsonObject customModelResult = reconhecimento_jsonObject.getAsJsonObject("customModelResult");

	        // Acessar "objectsResult"
	        JsonObject objectsResult = customModelResult.getAsJsonObject("objectsResult");

	        // Acessar "values" (JsonArray)
	        JsonArray valuesArray = objectsResult.getAsJsonArray("values");

	        // Procurar o objeto com maior acuracia
	        
	        Tag maior = new Tag();
	        Tag tag_atual = new Tag();
	        for (JsonElement valueElement : valuesArray) {
	            JsonObject value = valueElement.getAsJsonObject();
	            
                // Acessar "tags"
                JsonArray tagsArray = value.getAsJsonArray("tags");
                for (JsonElement tagElement : tagsArray) {
                    JsonObject tag = tagElement.getAsJsonObject();
                    tag_atual.setNome(tag.get("name").getAsString());
                    tag_atual.setAcuracia(tag.get("confidence").getAsDouble());
                    
                    
                    if(maior.getAcuracia() < tag_atual.getAcuracia()) {
                    	maior.setNome(tag_atual.getNome());
                    	maior.setAcuracia(tag_atual.getAcuracia());
                    }
                    System.out.println("Tag Name: " + tag.get("name").getAsString());
                    System.out.println("Confidence: " + tag.get("confidence").getAsDouble());
                }
	        }
	        
	        //Criar json com a especie e raca do animal reconhecido
	        String[] dados_animal = maior.getNome().split("/");
	        if(dados_animal.length > 1) {
	        	resp = "{" +
						"\"raca\": \"" + dados_animal[1] + "\"," +
						"\"especie\": \"" + dados_animal[0] + "\"}";
	        } else {
	        	resp = "{" +
						"\"raca\": \"Vira-Lata\"," +
						"\"especie\": \"" + dados_animal[0] + "\"}";
	        }

			response.status(200);
			
		}catch(Exception e){
			response.status(404);
			resp = "Animmal nao reconhecido";
			System.out.println("Animal nao reconhecido!");
		}
		
		//Retorno
		return resp;
	}

}
