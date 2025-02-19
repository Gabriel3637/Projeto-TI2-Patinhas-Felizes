package model;

public class Tag {
	String nome;
	double acuracia;
	
	//Construtor nulo
	public Tag() {
		this.nome = null;
		this.acuracia = 0.0;
	}
	
	//Construtor com parametros
	public Tag(String pnome, double pacuracia) {
		this.nome = pnome;
		this.acuracia = pacuracia;
	}
	
	//Geters e Seters
	public void setNome(String pnome) {
		this.nome = pnome;
	}
	
	public void setAcuracia(double pacuracia) {
		this.acuracia = pacuracia;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public double getAcuracia() {
		return this.acuracia;
	}
}
