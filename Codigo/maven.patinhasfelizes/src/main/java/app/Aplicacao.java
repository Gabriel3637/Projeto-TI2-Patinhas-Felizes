package app;

import static spark.Spark.*;
import service.ServiceAnimais;
import service.ServiceComentarios;
import service.ServiceFormularios;
import service.ServiceOngs;
import service.ServiceReconhecimento;
import service.ServiceUsuarios;
import service.Serviceemail;

public class Aplicacao {
	private static ServiceAnimais animaisservice = new ServiceAnimais();
	private static ServiceUsuarios usuariosservice = new ServiceUsuarios();
	private static ServiceComentarios comentariosservice = new ServiceComentarios();
	private static ServiceFormularios formulariosservice = new ServiceFormularios();
	private static ServiceOngs ongservice = new ServiceOngs();
	private static ServiceReconhecimento reconhecimentoservice = new ServiceReconhecimento();
	private static Serviceemail encontreianimalservice = new Serviceemail();
	
	public static void main(String[] args) {
		
		port(6789);
		
		// Habilitar CORS
		before((request, response) -> {
			response.header("Access-Control-Allow-Origin", "*");
			response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
			response.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
		});
		
		// Rota para lidar com requisições OPTIONS
		options("/*", (request, response) -> {
			String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
			if (accessControlRequestHeaders != null) {
				response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
			}
			
			String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
			if (accessControlRequestMethod != null) {
				response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
			}
			
			return "OK";
		});
		
		//CRUD da tabela animais
		post("/animais", (request, response) -> animaisservice.add(request, response));//CREATE
		
		get("/animais/:id", (request, response) -> animaisservice.get(request, response));//READ
		
		put("/animais/:id", (request, response) -> animaisservice.update(request, response));//UPDATE
		
		delete("/animais/:id", (request, response) -> animaisservice.delete(request, response));//DELETE
		
		get("/animais", (request, response) -> animaisservice.getAll(request, response));//READ
		
		//CRUD da tabela usuarios
		
		post("/usuarios", (request, response) -> usuariosservice.add(request, response));//CREATE
		
		get("/usuarios/:id", (request, response) -> usuariosservice.get(request, response));//READ
		
		put("/usuarios/:id", (request, response) -> usuariosservice.update(request, response));//UPDATE
		
		delete("/usuarios/:id", (request, response) -> usuariosservice.delete(request, response));//DELETE
		
		get("/usuarios", (request, response) -> usuariosservice.getAll(request, response));//READ
		
		//CRUD da tabela comentarios
		
		post("/comentarios", (request, response) -> comentariosservice.add(request, response));//CREATE
		
		get("/comentarios/:id", (request, response) -> comentariosservice.get(request, response));//READ
		
		put("/comentarios/:id", (request, response) -> comentariosservice.update(request, response));//UPDATE
		
		delete("/comentarios/:id", (request, response) -> comentariosservice.delete(request, response));//DELETE
		
		get("/comentarios", (request, response) -> comentariosservice.getAll(request, response));//READ
		
		//CRUD da tabela formularios
		
		post("/formularios", (request, response) -> formulariosservice.add(request, response));//CREATE
		
		get("/formularios/:id", (request, response) -> formulariosservice.get(request, response));//READ
		
		put("/formularios/:id", (request, response) -> formulariosservice.update(request, response));//UPDATE
		
		delete("/formularios/:id", (request, response) -> formulariosservice.delete(request, response));//DELETE
		
		get("/formularios", (request, response) -> formulariosservice.getAll(request, response));//READ
		
		//CRUD da tabela ong
		
		post("/ongs", (request, response) -> ongservice.add(request, response));//CREATE
		
		get("/ongs/:id", (request, response) -> ongservice.get(request, response));//READ
		
		put("/ongs/:id", (request, response) -> ongservice.update(request, response));//UPDATE
		
		delete("/ongs/:id", (request, response) -> ongservice.delete(request, response));//DELETE
		
		get("/ongs", (request, response) -> ongservice.getAll(request, response));//READ
		
		//Comparar senha de login coom banco de dados
		
		post("/login", (request, response) -> usuariosservice.verify(request, response));
		
		post("/login/:id", (request, response) -> usuariosservice.verifyid(request, response));
		
		post("/loginong", (request, response) -> ongservice.verify(request, response));
		
		post("/loginong/:id", (request, response) -> ongservice.verifyid(request, response));
		
		//Realizar o reconhecimento do animal na imagem
		post("/reconhecer", (request, response) -> reconhecimentoservice.reconhecer(request, response));
		
		//Enviar o email para a ong
		post("/enviaranimal", (request, response) -> encontreianimalservice.sendemail(request, response));
	}
}
