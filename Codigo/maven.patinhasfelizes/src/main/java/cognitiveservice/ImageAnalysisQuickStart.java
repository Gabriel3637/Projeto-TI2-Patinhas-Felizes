package cognitiveservice;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Base64;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImageAnalysisQuickStart {
	private String endpoint;
	private String key;
	
	//Construtor padrao
	public ImageAnalysisQuickStart() throws Exception{
		try {
	    	//Ler o arquivo cognitiveservice.config com o endpoint e a chave de acesso
	    	Properties config = new Properties();
	    	FileInputStream fis = new FileInputStream("src/main/java/cognitiveservice/cognitiveservice.config");
	    	config.load(fis);
	    	
			//Atribuir os valores de acesso a api
			this.endpoint = config.getProperty("cs.visionendpoint");
			this.key = config.getProperty("cs.visionkey");
		} catch (FileNotFoundException e) {
			throw new Exception("Erro! Arquuivo não encontrado!");
		} catch (IOException e) {
			throw new Exception("Erro! Nao foi possivel carregar o arquivo!");
		}
	}
	
	//Metodo para receber imagem serializada em base 64 como parametro e retornar o animal reconhecido
    public String Reconheceranimal(String base64Image) {
    	String resp = "";
        try {
            // Criar URI
            URI uri = new URI(this.endpoint);

            // Converter URI para URL
            URL url = uri.toURL();
            
            // Abrir conexão
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            // Definir método como POST
            conn.setRequestMethod("POST");
            
            // Definir cabeçalhos
            conn.setRequestProperty("Ocp-Apim-Subscription-Key", this.key);
            conn.setRequestProperty("Content-Type", "application/octet-stream");

            // Habilitar envio de dados
            conn.setDoOutput(true);

			// Ler a imagem como bytes
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            //byte[] imageBytes = Files.readAllBytes(Paths.get(base64Image));
            
            // Enviar dados binários da imagem
            try (OutputStream os = conn.getOutputStream()) {
                os.write(imageBytes, 0, imageBytes.length);
            }
            
            // Verificar o código de resposta
            int responseCode = conn.getResponseCode();
            System.out.println("Código de resposta: " + responseCode);
            
            // Ler a resposta
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                resp = response.toString();
                System.out.println("Resposta: " + response.toString());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }
    /*public static void main(String[] args) {
    	try {
    		ImageAnalysisQuickStart teste = new ImageAnalysisQuickStart();
    		teste.Reconheceranimal("/home/gabriel/Imagens/image.jpeg");
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }*/
}

