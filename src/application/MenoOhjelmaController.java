package application;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

public class MenoOhjelmaController {
	
	//luokassa suurin osa metodeista, joilla mahdollistetaan ohjelman toiminta
	
	
	//tiedosto johon menot tallennetaan
	private String tiedosto = "menot.txt";
	//tälle luokalle välitetään kunkin ohjelman ikkunan komponentit
	//jotta niiden arvoihin päästään käsiksi
	@FXML
	private TextField kategoria1;
	@FXML
	private TextField selite1;
	@FXML
	private TextField summa1;
	@FXML
	private TextField selite2;
	@FXML
	private TextField summa2;
	@FXML
	private TextField kategoria2;
	@FXML
	private TextField paivamaara2;
	@FXML
	private Label tanaan;
	@SuppressWarnings("rawtypes")
	@FXML
	private TableView menotulostus;
	@SuppressWarnings("rawtypes")
	@FXML
	private TableColumn pvm_tulostus, kategoria_tulostus, summa_tulostus, selite_tulostus;
	@FXML
	private TextField haePvm;
	@FXML
	private Label tamanPaivanMenot;
	@SuppressWarnings("rawtypes")
	@FXML
	private BarChart yhteenveto;
	@FXML
	private CategoryAxis CategoryAxis;
	@FXML
	private NumberAxis NumberAxis;

	//metodi, jolla lisätään tämän päivän meno
	//meno lisätään, kun "Lisää meno" -nappia painetaan
	//meno tallennetaan tallennaMeno()-metodilla tiedostoon
	//tulostaa menot ohjelman taulukkoon tulostaTiedot()-metodilla
	@FXML
	public void lisaaTamanPaivanMeno(ActionEvent event) {
		Meno m = new Meno(kategoria1.getText(), Double.parseDouble(summa1.getText()), selite1.getText());
		//päivittää tämän päivän menot yhteensä
		tamanPaivanMenotYhteensa(event);
		tallennaMeno(m);
		tulostaTiedot(event);
	}

	//metodi, jolla lisätään jokin aiempi meno
	//meno lisätään, kun "Lisää meno" -nappia painetaan
	//meno tallennetaan tallennaMeno()-metodilla tiedostoon
	//tulostaa menot ohjelman taulukkoon tulostaTiedot()-metodilla
	@FXML
	public void lisaaMuuMeno(ActionEvent event) {

		Meno m = new Meno(new Paivamaara(paivamaara2.getText()), kategoria2.getText(),
				Double.parseDouble(summa2.getText()), selite2.getText());
		tallennaMeno(m);
		tulostaTiedot(event);
	}
	
	//metodi, jolla tallennetaan meno tiedostoon
	public void tallennaMeno(Meno meno) {
		try (PrintWriter writer = new PrintWriter(new FileWriter(tiedosto, true))) {
			//lisää tekstitiedostoon kunkin menon attribuutin omalle rivilleen
			writer.println(meno.getPvm());
			writer.println(meno.getKategoria());
			writer.println(meno.getSumma());
			writer.println(meno.getSelite());
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@SuppressWarnings({ "resource" })
	@FXML
	//metodi menojen tulostamista varten
	public void tulostaTiedot(ActionEvent event) {
		ArrayList<Meno> menot = new ArrayList<Meno>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(tiedosto));
			//luetaan menot tiedostosta ja tehdään niistä olioita 
			//jotka lisätään ArrayListiin
			//menojen attribuutit omilla riveillään tiedostossa
			//ensimmäinen rivi vastaa päivämäärää, toinen kategoriaa jne.
			int counter = 0;
			String line;
			String pvm = "";
			String kategoria = "";
			String summa = "";
			String selite = "";
			while ((line = reader.readLine()) != null) {
				counter++;
				if (counter % 4 == 1) {
					pvm = line;
				} else if (counter % 4 == 2) {
					kategoria = line;
				} else if (counter % 4 == 3) {
					summa = line;
				} else if (counter % 4 == 0) {
					selite = line;
					menot.add(new Meno(new Paivamaara(pvm), kategoria, Double.parseDouble(summa), selite));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//tulostetaan menot taulukkoon
		teeMenoTaulukko(menot);
		//päivitetään tämän päivän menot yhteensä
		tamanPaivanMenotYhteensa(event);
	}

	//metodi, jolla haetaan menoja päivämäärällä
	public void haePaivamaaralla(ActionEvent event) {
		ArrayList<Meno> menot = new ArrayList<Meno>();
		//luetaan menot tiedostosta
		try (BufferedReader reader = new BufferedReader(new FileReader(tiedosto))) {

			String line;
			while ((line = reader.readLine()) != null) {
				//jos tiedosto sisältää annetun päivämäärän
				//tehdään olio, joka lisätään menot-ArrayListiin
				if (line.contains(haePvm.getText())) {
					menot.add(new Meno(new Paivamaara(line), reader.readLine(), Double.parseDouble(reader.readLine()),
							reader.readLine()));
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//tulostetaan kyseisen päivämäärän menot taulukkoon
		teeMenoTaulukko(menot);

	}
	//laskee tämän päivän menot yhteensä
	public void tamanPaivanMenotYhteensa(ActionEvent event) {
		//ArrayList tämän päivän menoja varten
		ArrayList<Meno> menot = new ArrayList<Meno>();
		//luetaan tiedostosta tämän päivän menot
		try (BufferedReader reader = new BufferedReader(new FileReader(tiedosto))) {

			String line;
			while ((line = reader.readLine()) != null) {
				//jos tiedosto sisältää menon
				//joka vastaa tätä päivää
				//tehdään siitä olio ja lisätään se ArrayListiin
				if (line.equals(new Paivamaara().toString())) {
					menot.add(new Meno(new Paivamaara(line), reader.readLine(), Double.parseDouble(reader.readLine()),
							reader.readLine()));
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//lasketaan menot-ArrayListissä olevat summat yhteen
		double yhteensa = 0;
		for (Meno meno : menot) {
			yhteensa += meno.getSumma();
		}
		//päivitetään tämän päivän menoja kuvaava Label
		tamanPaivanMenot.setText("Tämän päivän menot yhteensä: " + yhteensa + " euroa");

	}
	//metodi, jolla tehdään menot sisältävä taulukko
	@SuppressWarnings("unchecked")
	public void teeMenoTaulukko(ArrayList<Meno> menot) {
		yhteenveto.setVisible(false);
		//poistetaan mahdollinen vanha taulukko näkyvistä
		menotulostus.setVisible(false);
		//lisätään taulukkoon menon attribuutit
		pvm_tulostus.setCellValueFactory(new PropertyValueFactory<>("pvm"));
		kategoria_tulostus.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
		summa_tulostus.setCellValueFactory(new PropertyValueFactory<>("summa"));
		selite_tulostus.setCellValueFactory(new PropertyValueFactory<>("selite"));
		menotulostus.setItems(FXCollections.observableArrayList(menot));
		//asetetaan päivitetty taulukko näkyviin
		menotulostus.setVisible(true);
	}
	
	@SuppressWarnings({ "unchecked", "resource" })
	@FXML
	//metodi, jolla tehdään pylväskaavio menoista kussakin kategoriassa
	//en oikein saanut tätä tehtyä
	public void Yhteenveto(ActionEvent event) {
		ArrayList<Meno> menot = new ArrayList<Meno>();
		//luetaan menot tiedostosta ja lisätään ne ArrayListiin
		try {
			BufferedReader reader = new BufferedReader(new FileReader(tiedosto));

			int counter = 0;
			String line;
			String pvm = "";
			String kategoria = "";
			String summa = "";
			String selite = "";
			while ((line = reader.readLine()) != null) {
				counter++;
				if (counter % 4 == 1) {
					pvm = line;
				} else if (counter % 4 == 2) {
					kategoria = line;
				} else if (counter % 4 == 3) {
					summa = line;
				} else if (counter % 4 == 0) {
					selite = line;
					menot.add(new Meno(new Paivamaara(pvm), kategoria, Double.parseDouble(summa), selite));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//lisätään X-akselille kategoriat
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		for (Meno meno : menot) {
			CategoryAxis.getCategories().add(meno.getKategoria());
			if (CategoryAxis.getCategories().contains(meno.getKategoria())) {
				break;
			}
		}
		//välitetään ja lisätään menojen kategorioiden nimet ja summat pylväskaavioon
		for (Meno meno : menot) {
			series.getData().add(new XYChart.Data<>(meno.getKategoria(), meno.getSumma()));
		}
		yhteenveto.getData().add(series);
		menotulostus.setVisible(false);
		//asetetaan yhteenveto näkyväksi
		yhteenveto.setVisible(true);
	}

	@FXML
	//metodi, joka lisää tämän päivän päivämäärän
	//jotta voidaan nähdä, mikä päivä on
	public void lisaaPaivamaara() {
		tanaan.setText("Syötä meno " + "(" + new Paivamaara() + ")");
	}
}
