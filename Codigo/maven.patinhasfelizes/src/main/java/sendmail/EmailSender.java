package sendmail;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;

import model.ConteudoEmail;

import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Properties;

public class EmailSender {
	
	public EmailSender() {}
	
    public void send(ConteudoEmail dados_enviar) throws Exception {
        try {
        	//Ler o arquivo dadosemail.config com o email do destinatário, do remetente e senha do remetente
	    	Properties config = new Properties();
	    	FileInputStream fis = new FileInputStream("src/main/java/sendmail/dadosemail.config");
	    	config.load(fis);
	    	
	    	String email_remetente = config.getProperty("em.remetente");
	    	String email_destinatario = config.getProperty("em.destinatario");
	    	String senha_remetente = config.getProperty("em.senha");
	    	
            ByteArrayDataSource dataSource = new ByteArrayDataSource(dados_enviar.getImganexo(), "image/jpeg");

            // Criar o e-mail com múltiplas partes
            HtmlEmail email = new HtmlEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(587);
            email.setAuthenticator(new DefaultAuthenticator(email_remetente, senha_remetente));
            email.setStartTLSEnabled(true);
            email.setFrom(email_remetente);
            email.setSubject(dados_enviar.getAssunto());
            
            //Adicionar o anexo da imagem
            String cid = email.embed(dataSource, "imagem.jpg");
            
            //Modificara a exibiçao do email
            email.setMsg("<!DOCTYPE html>"
	        		+ "<html lang=\"pt-br\">"
	        		+ "<head>"
	        		+     "<meta charset=\"UTF-8\">"
	        		+     "<style>"
	        		+         "*{"
	        		+             "font-family: Arial, Helvetica, sans-serif;"
	        		+         "}"
	        		+ ""
	        		+         "h1, p, h2{"
	        		+             "text-align: center;"
	        		+         "}"
	        		+         "img{"
	        		+             "display: block;"
	        		+             "margin: auto;"
	        		+             "border-radius: 100%;"
	        		+             "box-shadow: 3px 3px 3px rgba(0, 0, 0, 0.411);"
	        		+ 			   "width: 50%;"
	        		+         "}"
	        		+         "span{"
	        		+             "font-weight: bolder;"
	        		+         "}"
	        		+     "</style>"
	        		+ "</head>"
	        		+ "<body>"
	        		+     "<main>"
	        		+         "<h1>"
	        		+             "Animal Encontrado!"
	        		+         "</h1>"
	        		+         "<h2>"
	        		+             dados_enviar.getNomeAnimal()
	        		+         "</h2>"
	        		+         "<div>"
	        		+             "<p>"
	        		+                 "<span>Usuario: </span>" + dados_enviar.getUsuario() + "<br>"
	        		+                 "<span>ID: </span>" + dados_enviar.getId() + "<br>"
	        		+                 "<span>Telefone: </span>" + dados_enviar.getTelefone() + "<br>"
	        		+                 "<span>Email: </span>" + dados_enviar.getEmailcontato() + "<br>"
	        		+             "</p>"
	        		+             "<p>"
	        		+                dados_enviar.getMensagem()
	        		+             "</p>"
	        		+             "<img src=\"cid:" + cid + "\" alt=\"Foto do animal encontrado\">"
	        		+             "<p>"
	        		+                 "<span>Espécie: </span>" + dados_enviar.getEspecie() + "<br>"
	        		+                 "<span>Raça: </span>" + dados_enviar.getRaca() + "<br>"
	        		+                 "<span>Endereço: </span>" + dados_enviar.getEndereco() + "<br>"
	        		+                 "<span></span>"
	        		+             "</p>"
	        		+         "</div>"
	        		+     "</main>"
	        		+ "</body>"
	        		+ "</html>");
            email.addTo(email_destinatario);

            // Enviar o e-mail
            email.send();
            System.out.println("E-mail enviado com sucesso!");
        } catch (EmailException e) {
            e.printStackTrace();
            throw new Exception("Error!");
        } catch (IOException e) {
        	e.printStackTrace();
        	throw new Exception("Error!");
        }
    }
}

