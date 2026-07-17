package DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import DAO.FruizioneDao;
import entities.ElementoMultimediale;
import entities.Fruizione;
import entities.Utente;

public class FruizioneDAO_Impl implements FruizioneDao {

	Connection conn;
	public FruizioneDAO_Impl(Connection conn)
	{
		this.conn = conn;
	}
	
	@Override
	public Fruizione SalvaFruizione(Fruizione fruizione) {
		String query = "INSERT INTO Fruizioni(ID_Utente, ID_Elemento, DataFruizione) VALUES (?, ?, ?)";
		try(PreparedStatement statement = conn.prepareStatement(query))
		{
			statement.setInt(1, fruizione.getIdUtente());
			statement.setInt(2, fruizione.getIdElemento());
			statement.setTimestamp(3, Timestamp.valueOf(fruizione.getDataFruizione()));
			int rowsAffected = statement.executeUpdate();
			
			if(rowsAffected > 0)
			{
				return fruizione;
			}
		}catch(SQLException e)
		{
			System.out.println("Errore nell'aggiunta di una nuova fruizione");
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Fruizione trovaFruizione(Fruizione fruizione) {
		String query = "SELECT * FROM Fruizioni WHERE ID_Utente = ? AND ID_Elemento = ? AND dataFruizione = ?";
		try(PreparedStatement statement = conn.prepareStatement(query))
		{
			statement.setInt(1, fruizione.getIdUtente());
			statement.setInt(2, fruizione.getIdElemento());
			statement.setTimestamp(3, Timestamp.valueOf(fruizione.getDataFruizione()));
			
			ResultSet rs = statement.executeQuery();
			if(rs.next())
			{
				
				return new Fruizione(
							rs.getInt("Id_Utente"),
							rs.getInt("Id_Elemento"),
							rs.getTimestamp("dataFruizione").toLocalDateTime()
						);
			}
			
		}catch(SQLException e)
		{
			System.out.println("Errore nella ricerca della fruizione");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Fruizione modificaFruizione(Fruizione vecchiaFruizione, Fruizione nuovaFruizione) {
		
		String query = "UPDATE Fruizioni SET ID_Utente = ?, iD_Elemento = ?, dataFruizione = ? WHERE ID_Utente = ? AND ID_Elemento = ? AND dataFruizione = ?";
		try(PreparedStatement statement = conn.prepareStatement(query))
		{
			statement.setInt(1, nuovaFruizione.getIdUtente());
			statement.setInt(2, nuovaFruizione.getIdElemento());
			statement.setTimestamp(3, Timestamp.valueOf(nuovaFruizione.getDataFruizione()));
			statement.setInt(4, vecchiaFruizione.getIdUtente());
			statement.setInt(5, vecchiaFruizione.getIdElemento());
			statement.setTimestamp(6, Timestamp.valueOf(vecchiaFruizione.getDataFruizione()));
			
			int rowsAffected = statement.executeUpdate();
			if(rowsAffected > 0)
			{
				return nuovaFruizione;
			}
			
			
			
		}catch(SQLException e)
		{
			System.out.println("Errore nella modifica della fruizione");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean cancellaFruizione(Fruizione fruizione) {
		String query = "DELETE FROM Fruizioni WHERE ID_Utente = ? AND ID_Elemento = ? AND DataFruizione = ?";
		try(PreparedStatement statement = conn.prepareStatement(query))
		{
			statement.setInt(1, fruizione.getIdUtente());
			statement.setInt(2, fruizione.getIdElemento());
			statement.setTimestamp(3, Timestamp.valueOf(fruizione.getDataFruizione()));
			
			int rowsAffected = statement.executeUpdate();
			if(rowsAffected > 0)
			{
				return true;
			}
		}catch(SQLException e)
		{
			System.out.println("Errore nella cancellazione della fruizione");
			e.printStackTrace();
		}
		return false;
	}

}
