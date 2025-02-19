package model;

public class Comentarios {
	//Atributos dos comentarios
    private String id;
    private String usuarioid;
    private String animalid;
    private String conteudo;

    //Construtor vazio 
    public Comentarios() {
        this.id = "";
        this.usuarioid = "";
        this.animalid = "";
        this.conteudo = "";
    }
    
    
    //Construtor 
    public Comentarios(String id, String usuarioId, String animalId, String conteudo) {
        this.id = id;
        this.usuarioid = usuarioId;
        this.animalid = animalId;
        this.conteudo = conteudo;
    }
    
    //Metodos getters e setters para os atributos dos comentarios

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return usuarioid;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioid = usuarioId;
    }

    public String getAnimalId() {
        return animalid;
    }

    public void setAnimalId(String animalId) {
        this.animalid = animalId;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    //Tranformar os atributos do comentario em string
    @Override
    public String toString() {
        return "Comentario [id=" + this.id + ", usuarioId=" + this.usuarioid + ", animalId=" + this.animalid + ", conteudo=" + this.conteudo + "]";
    }
}
