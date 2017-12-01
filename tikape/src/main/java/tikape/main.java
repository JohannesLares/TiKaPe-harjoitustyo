package tikape;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class main {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

		Connection conn = DriverManager.getConnection("jdbc:sqlite:baari.db");

		
		Spark.get("/", (req, res) -> {
			Controller cntrl = new Controller(conn);
	        HashMap map = new HashMap<>();
	        map.put("ketju", "");
	        return new ModelAndView(map, "index"); 
	    }, new ThymeleafTemplateEngine());
		
		Spark.get("/aineet", (req, res) -> {
		   Controller cntrl = new Controller(conn);
		   HashMap map = new HashMap<>();
		   map.put("aine", cntrl.getAineet());
		   return new ModelAndView(map, "aineet"); 
		}, new ThymeleafTemplateEngine());
		
		Spark.get("/annokset", (req, res) -> {
			   Controller cntrl = new Controller(conn);
			   HashMap map = new HashMap<>();
			   map.put("annos", cntrl.getAnnokset());
			   return new ModelAndView(map, "annokset"); 
		}, new ThymeleafTemplateEngine());
		
		Spark.post("/aineet", (req, res) -> {
			Controller cntrl = new Controller(conn);
			cntrl.addAine(req.queryParams("nimi"));
			res.redirect("/aineet");
			return "";
		});
		
		Spark.get("/annos/:id", (req, res) -> {
			Controller cntrl = new Controller(conn);
			HashMap map = new HashMap<>();
			map.put("aine", cntrl.getAnnos(Integer.parseInt(req.params(":id"))));
			map.put("nimi", cntrl.getAnnoksenNimi(Integer.parseInt(req.params(":id"))));
			map.put("id", req.params(":id"));
			return new ModelAndView(map, "annos");
		}, new ThymeleafTemplateEngine());
		
		Spark.post("/poistaannos", (req, res) -> {
			Controller cntrl = new Controller(conn);
			cntrl.poistaAnnos(Integer.parseInt(req.queryParams("id")));
			res.redirect("/annokset");
			return "";
		});
		
		Spark.post("/poistaaines", (req, res) -> {
			Controller cntrl = new Controller(conn);
			cntrl.poistaAines(Integer.parseInt(req.queryParams("id")));
			res.redirect("/aineet");
			return "";
		});
		
		Spark.get("/lisaaAnnos", (req, res) -> {
			Controller cntrl = new Controller(conn);
			HashMap map = new HashMap<>();
			map.put("aine", cntrl.getAineet());
			return new ModelAndView(map, "uusiannos");
		}, new ThymeleafTemplateEngine());
		
		Spark.post("/uusiAnnos", (req, res) -> {
			Controller cntrl = new Controller(conn);
			for(int i=0; i < 100; i++) {
				if(req.queryParams("aine"+i) != null) {
					cntrl.uusiAnnos(req.queryParams("nimi"), req.queryParams("aine"+i), req.queryParams("jarjestys"+i), req.queryParams("ohje"+i), req.queryParams("maara"+i));
				}
			}
			res.redirect("/annokset");
			return "";
		});

	}
}
