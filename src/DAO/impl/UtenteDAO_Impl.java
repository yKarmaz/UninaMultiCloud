package DAO.impl;
import java.sql.*;
import DAO.UtenteDao;
import entities.Utente;
public class UtenteDAO_Impl implements UtenteDao{

	Connection connessione;
	
	
	public UtenteDAO_Impl(Connection connessione) {
		this.connessione = connessione;
	}

	@Override
	public int salvaUtente(Utente utente) {
		String query = "INSERT INTO utenti(nome, cognome, username, psswrd, email, matricola) VALUES (?,?,?,?,?,?)";
		try(PreparedStatement statement = connessione.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
		{
			statement.setString(1, utente.getNome());
            statement.setString(2, utente.getCognome());
            statement.setString(3, utente.getUsername());
            statement.setString(4, utente.getPassword());
            statement.setString(5, utente.getEmail());
            statement.setString(6, utente.getMatricola());
            
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected > 0)
            {
            	ResultSet chiaveGenerata = statement.getGeneratedKeys();
            	if(chiaveGenerata.next()) return chiaveGenerata.getInt(1);
            }
		}catch (SQLException e)
		{
			System.err.println("Errore durante il salvataggio dell'utente:");
            e.printStackTrace();
		}
		return -1;
	}

	@Override
	public Utente trovaUtente(String username, String password) {
		String query = "SELECT * FROM utenti WHERE username = ? AND psswrd = ?";
		try(PreparedStatement statement = connessione.prepareStatement(query))
		{
			statement.setString(1, username);
			statement.setString(2, password);
			ResultSet rs = statement.executeQuery();
			if(rs.next())
			{
				
				return new Utente(
						rs.getInt("id_utente"),
						rs.getString("matricola"),
						rs.getString("nome"),
						rs.getString("cognome"),
						rs.getString("username"),
						rs.getString("email"),
						rs.getString("psswrd") 
					);
						
						
			}
			
		} catch (SQLException e) {
			System.err.println("Errore durante la ricerca dell'utente:");
			e.printStackTrace();
		}
		return null;
	}

	
	@Override
	public Utente trovaUtenteDaID(int ID) {
		String query = "SELECT * FROM utenti WHERE id_utente = ?";
		try(PreparedStatement statement = connessione.prepareStatement(query))
		{
			statement.setInt(1, ID);
			
			ResultSet rs = statement.executeQuery();
			if(rs.next())
			{
				
				return new Utente(
						rs.getInt("id_utente"),
						rs.getString("matricola"),
						rs.getString("nome"),
						rs.getString("cognome"),
						rs.getString("username"),
						rs.getString("email"),
						rs.getString("psswrd") 
					);
						
						
			}
			
		} catch (SQLException e) {
			System.err.println("Errore durante la ricerca dell'utente:");
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	@Override
	public boolean modificaUtente(Utente utente) {
		String query = "UPDATE utenti SET nome = ?, cognome = ?, username = ?, psswrd = ?, email = ?, matricola = ? WHERE id_utente = ?";
		try (PreparedStatement statement = connessione.prepareStatement(query)) {
			statement.setString(1, utente.getNome());
			statement.setString(2, utente.getCognome());
			statement.setString(3, utente.getUsername());
			statement.setString(4, utente.getPassword());
			statement.setString(5, utente.getEmail());
			statement.setString(6, utente.getMatricola());
			statement.setInt(7, utente.getIdUtente()); 
			
			int rowsAffected = statement.executeUpdate();
			System.out.println(rowsAffected);
			return rowsAffected > 0; 
		} catch (SQLException e) {
			System.err.println("Errore durante la modifica dell'utente:");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean cancellaUtente(Utente utente) {
		String query = "DELETE FROM utenti WHERE id_utente = ?";
		try (PreparedStatement statement = connessione.prepareStatement(query)) {
			statement.setInt(1, utente.getIdUtente());
			
			int rowsAffected = statement.executeUpdate();
			return rowsAffected > 0; 
		} catch (SQLException e) {
			System.err.println("Errore durante l'eliminazione dell'utente:");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public int contaContenutiPubblicati(Utente u) {
		String query = "SELECT COUNT(*) AS Conteggio FROM ContenutiMultimediali WHERE id_utente = ?";
		try(PreparedStatement statement = connessione.prepareStatement(query))
		{
			statement.setInt(1, u.getIdUtente());
			try(ResultSet rs = statement.executeQuery())
			{
				if(rs.next())
				{
					return rs.getInt("Conteggio");
				}
			}
		}catch(SQLException e)
		{
			System.out.println("Errore nel conteggio di elementi pubblicati");
			e.printStackTrace();
		}
		return 0;
	}

}
