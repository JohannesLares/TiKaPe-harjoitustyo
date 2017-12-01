package tikape;

public class AnnoksenAine {
	private int annos;
	private int aine;
	private int jarjestys;
	private String maara;
	private String ohje;
	private String nimi;
	
	public AnnoksenAine(String nimi, int annos, int aine, int jarjestys, String maara, String ohje) {
		this.annos = annos;
		this.aine = aine;
		this.jarjestys = jarjestys;
		this.maara = maara;
		this.ohje = ohje;
		this.nimi = nimi;
	}
	
	public int getAnnos() {
		return annos;
	}
	public void setAnnos(int annos) {
		this.annos = annos;
	}
	public int getAine() {
		return aine;
	}
	public void setAine(int aine) {
		this.aine = aine;
	}
	public int getJarjestys() {
		return jarjestys;
	}
	public void setJarjestys(int jarjestys) {
		this.jarjestys = jarjestys;
	}
	public String getMaara() {
		return maara;
	}
	public void setMaara(String maara) {
		this.maara = maara;
	}
	public String getOhje() {
		return ohje;
	}
	public void setOhje(String ohje) {
		this.ohje = ohje;
	}
	public String getNimi() {
		return this.nimi;
	}
	public void setNimi(String nimi) {
		this.nimi = nimi;
	}
}
