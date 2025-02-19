package service;

import sendmail.EmailSender;
import spark.Request;
import spark.Response;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.ConteudoEmail;

public class Serviceemail {
	EmailSender correioEmail;
	
	public Object sendemail(Request request, Response response) {
		Object resp = false;
		try {
			ConteudoEmail dadosemail = new ConteudoEmail(); 
			this.correioEmail = new EmailSender();
			
			//Ler o corpo da requisicao
			String body = request.body();
			JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
			
			//Criar um objeto do tipo ConteudoEmail com os dados do corpo da requisicao
			dadosemail.setAssunto("Animal encontrado!");
			dadosemail.setMensagem(jsonObject.get("mensagem").getAsString());
			dadosemail.setUsuario(jsonObject.get("nomeusuario").getAsString());
			dadosemail.setId(jsonObject.get("usuarioid").getAsString());
			dadosemail.setTelefone(jsonObject.get("telefone").getAsString());
			dadosemail.setEmailcontato(jsonObject.get("email").getAsString());
			dadosemail.setNomeAnimal(jsonObject.get("nomeanimal").getAsString());
			dadosemail.setEspecie(jsonObject.get("especie").getAsString());
			dadosemail.setRaca(jsonObject.get("raca").getAsString());
			dadosemail.setEndereco(jsonObject.get("endereco").getAsString());
			dadosemail.setImganexo(jsonObject.get("imgserial").getAsString());
			
			//Eviara o objeto do tipo Email
			correioEmail.send(dadosemail);
			resp = true;
			response.status(200);
		}catch(Exception e) {
			e.printStackTrace();
			response.status(404);
		}
		return resp;
	}
}
