package model;

public class Ongs {
		//Atributos da ong
	    private String id;
	    private String nome;
	    private String cnpj;
	    private String endereco;
	    private String telefone;
	    private String email;
	    private String senha;
	    private String logo;
	    private String historia;

	    // Construtor
    public Ongs(String id, String nome, String cnpj, String endereco, String telefone, String email, String senha, String logo, String historia) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.logo = logo;
        this.historia = historia;
    }

    //Metodos getters e setters para os atributos da ong
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    // Transformar os atributos da ong em string
    @Override
    public String toString() {
        return "Ong[" +
                "id=" + id +
                ", nome=" + this.nome +
                ", cnpj=" + this.cnpj +
                ", endereco=" + this.endereco +
                ", telefone=" + this.telefone +
                ", email=" + this.email +
                ", senha=" + this.senha +
                ", logo=" + this.logo +
                ", historia=" + this.historia +
                ']';
    }
}
