package application;

//sisältää menojen konstruktorit sekä setterit ja getterit niiden attribuuteille
public class Meno {
	//attribuutit kategoria, selite, summa ja päivämäärä
	private String kategoria, selite;
	private double summa;
	private Paivamaara pvm;
	
	//konstruktori tämän päivän menon kirjaamista varten
	public Meno (String kategoria, double summa, String selite) {
		this.pvm = new Paivamaara();
		this.kategoria = kategoria;
		this.summa = summa;
		this.selite = selite;
	}
	//konstruktori jonkin muun menon kirjaamista varten
	//konstruktorille välitetään jokin päivämäärä, joka ei ole tämän päivän päivämäärä
	public Meno (Paivamaara pvm, String kategoria, double summa, String selite) {
		this.pvm = pvm;
		this.kategoria = kategoria;
		this.summa = summa;
		this.selite = selite;
	}
	
	public String getKategoria() {
		return kategoria;
	}
	public void setKategoria(String kategoria) {
		this.kategoria = kategoria;
	}
	public String getSelite() {
		return selite;
	}
	public void setKuvaus(String selite) {
		this.selite = selite;
	}
	public double getSumma() {
		return summa;
	}
	public void setSumma(double summa) {
		this.summa = summa;
	}
	public Paivamaara getPvm() {
		return pvm;
	}
	public void setPvm(Paivamaara pvm) {
		this.pvm = pvm;
	}
	@Override
	public String toString() {
		return  pvm+ " " +kategoria+ " " +summa+ " " +selite;
	}
}
