package app.model;

public class Help {
	
	@Override
	public String toString() {
		return "Help [ime=" + ime + ", prezime=" + prezime + "]";
	}

	private String ime;
	private String prezime;
	
	public Help() {
		
	}

	public Help(String ime, String prezime) {
		super();
		this.ime = ime;
		this.prezime = prezime;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	
	
}
