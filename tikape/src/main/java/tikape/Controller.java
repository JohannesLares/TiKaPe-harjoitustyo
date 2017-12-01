package tikape;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
	
	private Connection conn;
	
	public Controller(Connection conn) {
		this.conn = conn;
	}
	
	public List<Aine> getAineet(){
		List<Aine> aineet = new ArrayList<>();
		try {
			ResultSet rs = conn.prepareStatement("SELECT nimi, id FROM RaakaAine").executeQuery();
			while(rs.next()) {
				aineet.add(new Aine(rs.getString("nimi"), rs.getInt("id")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aineet;
		
	}
	
	public List<Annos> getAnnokset(){
		List<Annos> annokset = new ArrayList<>();
		try {
			ResultSet rs = conn.prepareStatement("SELECT nimi, id FROM Annos").executeQuery();
			while(rs.next()) {
				annokset.add(new Annos(rs.getString("nimi"), rs.getInt("id")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return annokset;
		
	}
	
	public void addAine(String nimi) {
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO RaakaAine(nimi) VALUES (?)");
			stmt.setString(1, nimi);
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<AnnoksenAine> getAnnos(int id){
		List<AnnoksenAine> aa = new ArrayList<>();
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM AnnosRaakaAine INNER JOIN RaakaAine ON AnnosRaakaAine.raaka_aine_id = RaakaAine.id WHERE AnnosRaakaAine.annos_id = ? ORDER BY jarjestys ASC");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				aa.add(new AnnoksenAine(rs.getString("nimi"), rs.getInt("annos_id"), rs.getInt("raaka_aine_id"), rs.getInt("jarjestys"), rs.getString("maara"), rs.getString("ohje")));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return aa;
	}

	public String getAnnoksenNimi(int id) {
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT nimi FROM Annos WHERE id = ?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				return rs.getString("nimi");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void poistaAnnos(int id) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM Annos WHERE id = ?");
			stmt.setInt(1, id);
			stmt.executeUpdate();
			stmt=null;
			stmt = conn.prepareStatement("DELETE FROM AnnosRaakaAine WHERE annos_id = ?");
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Toi toka stmt ei varmaa toimi...");
		}
		
		
	}

	public void poistaAines(int id) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM RaakaAine WHERE id = ?");
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Toi toka stmt ei varmaa toimi...");
		}
	}

	public void uusiAnnos(String nimi, String aine, String jarjestys, String ohje, String maara) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement stmtt = conn.prepareStatement("SELECT * FROM Annos WHERE nimi=?");
			stmtt.setString(1, nimi);
			ResultSet rs = stmtt.executeQuery();
			if(!rs.next()) {
				PreparedStatement stmt = conn.prepareStatement("INSERT INTO Annos(nimi) VALUES (?)");
				stmt.setString(1, nimi);
				stmt.executeUpdate();
			}
			stmtt = null;
			stmtt = conn.prepareStatement("SELECT id FROM Annos WHERE nimi=? LIMIT 1");
			stmtt.setString(1, nimi);
			ResultSet rss = stmtt.executeQuery();
			int ann_id = 0;
			while(rss.next()) {
				ann_id = rss.getInt("id");
			}
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO AnnosRaakaAine(annos_id, raaka_aine_id, jarjestys, ohje, maara) VALUES (?,?,?,?,?)");
			stmt.setInt(1, ann_id);
			stmt.setInt(2, Integer.parseInt(aine));
			stmt.setInt(3, Integer.parseInt(jarjestys));
			stmt.setString(4, ohje);
			stmt.setString(5, maara);
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
