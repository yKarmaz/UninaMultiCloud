package databaseConnection;
import java.sql.*;
import java.time.LocalDate;
import DAO.*;
import DAO.UtenteDao;
import DAO.impl.*;
import entities.*;


public class DBConnection {

	public static void main(String args[ ]) throws Exception { 
		 /*
		  * 
		  * try {
			 Class.forName("org.postgresql.Driver");
			 String url = "jdbc:postgresql://localhost:5432/UninaMultiCloud";
			 String nomeUtente = "postgres";
			 String password = "AleDell2006!";
			 Connection conn = DriverManager.getConnection(url, nomeUtente, password); 
			 System.out.println("Connessione OK \n");
			 
			 ElementoMultimedialeDao elementoDao = new ElementoMultimedialeDAO_Impl(conn);
			 
			 
			 Utente generico = new Utente(10, password, password, password, password, password, password);
			 
			 ElementoMultimediale contenutoTest = new Audio(21, "descrizione modificata TEST", "Java", 120, LocalDate.now(), "ImmagineTest.png", generico, 120);
			 
			 
			 elementoDao.cancellaContenuto(contenutoTest);	 
			 
			 conn.close();
		}
		catch (ClassNotFoundException e) {
			 System.out.println("DB driver non trovato \n");
			 System.out.println(e);
		}
		catch(SQLException e) {
			 System.out.println("Connessione Fallita \n"); 
			 e.printStackTrace();
			 System.out.println(e);
		 }
		 
		 */
		 
		 
		 
		 
		 
		 
	}

}
