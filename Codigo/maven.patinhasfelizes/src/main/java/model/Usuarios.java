package model;

import java.time.LocalDate;

public class Usuarios {
	//Atributos do usuario
	private String id;
    private String email;
    private String nome;
    private String senha;
    private String imagem;
    private String[] etiquetas;
    private LocalDate datanascimento;
    private String telefone;
    private String cpf;
    private String moradia; // "casa" ou "apartamento"

    // Construtor vazio
    public Usuarios() {
        this.id = "";
        this.email = "";
        this.nome = "";
        this.senha = "";
        this.imagem = "";
        this.etiquetas = null;
        this.datanascimento = null;
        this.telefone = "";
        this.cpf = "";
        this.moradia = "";
    }

    // Construtor com parâmetros
    public Usuarios(String id, String nome, String email, String senha, String imagem, String dataDeNascimento, 
                   String telefone, String cpf, String moradia, String petiquetas) {
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.senha = senha;
        this.imagem = imagem;
        this.etiquetas = petiquetas.split("; ");
        this.datanascimento = LocalDate.parse(dataDeNascimento);
        this.telefone = telefone;
        this.cpf = cpf;
        this.moradia = moradia;
    }

    //Metodos getters e setters para os atributos dos usuarios
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
    
    public String[] getEtiquetas() {
    	return etiquetas;
    }
    
    public void setEtiquetas(String etiquetas) {
    	this.etiquetas = etiquetas.split("; ");
    }

    public String getDataDeNascimento() {
        return datanascimento.toString();
    }

    public void setDataDeNascimento(String dataDeNascimento) {
        this.datanascimento = LocalDate.parse(dataDeNascimento);
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getMoradia() {
        return moradia;
    }

    public void setMoradia(String moradia) {
        this.moradia = moradia;
    }

    //Transformar os atributos do usuario em string
    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", imagem=" + imagem + ", etiquetas=" + etiquetas +
               ", datanascimento=" + datanascimento + ", telefone=" + telefone + ", cpf=" + cpf + ", moradia=" + moradia + "]";
    }
}
