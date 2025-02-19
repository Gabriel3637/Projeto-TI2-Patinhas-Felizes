package model;
import java.time.LocalDate;

public class Animais {
	
	//Atributos dos animais
	private String id;
	private String nome;
	private String especie; // Especie do animal ((CA)Cachorro (CO)Coelho (GA)Gato (RO)Roedor)
	private char porte; // Porte do animal ((P)pequeno, (M)médio, (G)grande)
	private String donoid;
	private String imagem;
	private String vacinas;
	private String[] etiquetas;
	private LocalDate datasaida;
	private LocalDate dataentrada;
	private String descricao;
	private String historia;
	private String raca;
	private LocalDate datanascimento;
	private boolean castrado;
	private char sexo; // ((M)Masculino ou (F)Feminino)
		
	//Construtor vazio
	public Animais() {
		this.id = "";
		this.nome = "";
		this.especie = "";
		this.porte = '\u0000';
		this.donoid = "";
		this.imagem = "";
		this.vacinas = "";
		this.etiquetas = null;
		this.datasaida = null;
		this.dataentrada = null;
		this.descricao = "";
		this.historia = "";
		this.raca = "";
		this.datanascimento = null;
		this.castrado = false;
		this.sexo = '\u0000';
	}

	//Construtor
	public Animais(String pid, String pnome, String pespecie, char pporte, String pdonoid, String pimagem, String pvacinas,
					String petiquetas, String pdatasaida, String pdataentrada, String pdescricao,
					String phistoria, String praca, String pdatanascimento, boolean pcastrado, char psexo) {
		this.id = pid;
		this.nome = pnome;
		this.especie = pespecie;
		this.porte = pporte;
		this.donoid = pdonoid;
		this.imagem = pimagem;
		this.vacinas = pvacinas;
		this.etiquetas = petiquetas.split("; ");
		this.datasaida = pdatasaida != null ? LocalDate.parse(pdatasaida) : null;
		this.dataentrada = LocalDate.parse(pdataentrada);
		this.descricao = pdescricao;
		this.historia = phistoria;
		this.raca = praca;
		this.datanascimento = LocalDate.parse(pdatanascimento);
		this.castrado = pcastrado;
		this.sexo = psexo;
	}
	
	//Metodos Getters e Setters para os atributos do animal
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

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public char getPorte() {
        return porte;
    }

    public void setPorte(char porte) {
        this.porte = porte;
    }

    public String getDonoId() {
        return donoid;
    }

    public void setDonoId(String donoId) {
        this.donoid = donoId;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getVacinas() {
        return vacinas;
    }

    public void setVacinas(String vacinas) {
        this.vacinas = vacinas;
    }

    public String[] getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(String etiquetas) {
        this.etiquetas = etiquetas.split("; ");
    }

    public String getDataSaida() {
        return this.datasaida != null ? datasaida.toString() : null;
    }

    public void setDataSaida(String dataSaida) {
        this.datasaida = dataSaida != null ? LocalDate.parse(dataSaida) : null;
    }

    public String getDataEntrada() {
        return dataentrada.toString();
    }

    public void setDataEntrada(String dataEntrada) {
        this.dataentrada = LocalDate.parse(dataEntrada);
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getDataNascimento() {
        return datanascimento.toString();
    }

    public void setDataNascimento(String dataNascimento) {
        this.datanascimento = LocalDate.parse(dataNascimento);
    }

    public boolean isCastrado() {
        return castrado;
    }

    public void setCastrado(boolean castrado) {
        this.castrado = castrado;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }
    
    //Tranformar os atributos do animal em string
    @Override
    public String toString() {
        return "Animal [id=" + this.id + ", nome=" + this.nome + ", especie=" + this.especie + ", porte=" + this.porte 
                + ", donoId=" + this.donoid + ", imagem=" + this.imagem + ", vacinas=" + this.vacinas + ", etiquetas=" + this.etiquetas 
                + ", dataSaida=" + this.datasaida + ", dataEntrada=" + this.dataentrada + ", descricao=" + this.descricao 
                + ", historia=" + this.historia + ", raca=" + this.raca + ", dataNascimento=" + this.datanascimento 
                + ", castrado=" + this.castrado + ", sexo=" + this.sexo + "]";
    }

}
