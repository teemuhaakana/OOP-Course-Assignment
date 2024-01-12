package application;


import java.util.StringTokenizer;

//luokka sisältää erilaisia konstruktoreja päivämäärille
public class Paivamaara{
	private int paiva;
	private int kuukausi;
	private int vuosi;

	//konstruktori, jolla saadaan tämän päivän päivämäärä
	public Paivamaara() {
		java.time.LocalDate tanaan = java.time.LocalDate.now();
		paiva = tanaan.getDayOfMonth();
		kuukausi = tanaan.getMonthValue();
		vuosi = tanaan.getYear();	
	}


	//konstruktori, jota käytetään, kun päivämäärä on String-tyyppiä ("pp.kk.vv")
	public Paivamaara(String dateString) {
		StringTokenizer st = new StringTokenizer(dateString, ".");
		paiva = Integer.parseInt(st.nextToken());
		kuukausi= Integer.parseInt(st.nextToken());
		vuosi= Integer.parseInt(st.nextToken());
	}
	
	public int getPaiva() {
		return paiva;
	}

	public void setPaiva(int paiva) {
		this.paiva = paiva;
	}

	public int getKuukausi() {
		return kuukausi;
	}

	public void setKuukausi(int kuukausi) {
		this.kuukausi = kuukausi;
	}

	public int getVuosi() {
		return vuosi;
	}

	public void setVuosi(int vuosi) {
		this.vuosi = vuosi;
	}
	@Override
	public String toString() {
		return paiva + "." + kuukausi + "." + vuosi ;
	}


}
