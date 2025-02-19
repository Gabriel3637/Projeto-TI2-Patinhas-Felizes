package model;

public class Formularios {
	
	//Atributos do formulario
	private String id;
    private String usuarioid;
    private String animalid;

    //Construtor vazio
    public Formularios() {
        this.id = "";
        this.usuarioid = "";
        this.animalid = "";
    }

    //Construtor
    public Formularios(String id, String usuarioId, String animalId) {
        this.id = id;
        this.usuarioid = usuarioId;
        this.animalid = animalId;
    }

    //Metodos getters e setters para os atributos do formularios
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
    
    //Transformar os atributos do formulario em string

    @Override
    public String toString() {
        return "Formulario [id=" + id + ", usuarioId=" + usuarioid + ", animalId=" + animalid + "]";
    }
}
