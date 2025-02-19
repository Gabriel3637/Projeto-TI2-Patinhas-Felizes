package model;

import java.util.Base64;

public class ConteudoEmail {
	public String assunto;
	public String mensagem;
	public String usuario;
	public String id;
	public String raca;
	public String especie;
	public String nomeAnimal;
	public String endereco;
	public String telefone;
	public String emailcontato;
	public byte[] imganexo;
	
	//Construtor padrao
	public ConteudoEmail() {
		this.assunto = null;
		this.mensagem = null;
		this.imganexo = null;
	}
	
	//Contrutor com parametros
	public ConteudoEmail(String passunto, String pmensagem, String pimganexo) {
		this.assunto = passunto;
		this.mensagem = pmensagem;
		byte[] decodedimagemBytes = Base64.getDecoder().decode(pimganexo);
		this.imganexo = decodedimagemBytes;
	}
	
	//Getters e setters
	public String getAssunto() {
		return this.assunto;
	}
	
	public String getMensagem() {
		return this.mensagem;
	}
	
	public byte[] getImganexo() {
		return this.imganexo;
	}
	
	public String getUsuario() {
		return this.usuario;
	}

	public String getId() {
		return this.id;
	}
	
	public String getRaca() {
		return this.raca;
	}
	
	
	public String getEspecie() {
		return this.especie;
	}
	
	public String getNomeAnimal() {
		return this.nomeAnimal;
	}

	public String getEndereco() {
		return this.endereco;
	}
	
	public String getTelefone() {
		return this.telefone;
	}
	
	public String getEmailcontato() {
		return this.emailcontato;
	}
	
	public void setAssunto(String passunto) {
		this.assunto = passunto;
	}
	
	public void setMensagem(String pmensagem) {
		this.mensagem = pmensagem;
	}
	
	public void setImganexo(String pimganexo) {
		this.imganexo = Base64.getDecoder().decode(pimganexo);
	}
	
	public void setImganexo(byte[] pimganexo) {
		this.imganexo = pimganexo;
	}

    public void setUsuario(String pusuario) {
        this.usuario = pusuario;
    }

    public void setId(String pid) {
        this.id = pid;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public void setEspecie(String pespecie) {
        this.especie = pespecie;
    }

    public void setEndereco(String pendereco) {
        this.endereco = pendereco;
    }

    public void setTelefone(String ptelefone) {
        this.telefone = ptelefone;
    }
    
    public void setEmailcontato(String pemailcontato) {
        this.emailcontato = pemailcontato;
    }

    public void setNomeAnimal(String pnomeAnimal) {
    	this.nomeAnimal = pnomeAnimal;
    }	
    
}
