package DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DAO.PlaylistDao;
import DAO.UtenteDao;
import DAO.ElementoMultimedialeDao;
import entities.*;
public class PlaylistDAO_Impl implements PlaylistDao{
	Connection connessione;
	
	public PlaylistDAO_Impl(Connection conn)
	{
		connessione = conn;
	}
		
	@Override
	public Playlist salvaPlaylist(Playlist playlist) {
	    
	    String queryPadre = "INSERT INTO playlist (nome, id_utente) VALUES (?, ?)";
	    String queryFigliaPubblica = "INSERT INTO playlist_pubblica (id_playlist, numvisualizzazioni) VALUES (?, ?)";
	    String queryFigliaCondivisa = "INSERT INTO playlist_condivisa (id_playlist, url_invito) VALUES (?, ?)";
	    String queryFigliaPrivata = "INSERT INTO playlist_privata (id_playlist) VALUES (?)";
	    
	    PreparedStatement stmtPadre = null;
	    PreparedStatement stmtFiglio = null;
	    
	    try {
	        // Disattiviamo l'autocommit per la transazione
	        connessione.setAutoCommit(false);
	        
	        // --- STEP 1: Inserimento padre (Specifichiamo esplicitamente "id_playlist" per Postgres) ---
	        stmtPadre = connessione.prepareStatement(queryPadre, new String[] { "id_playlist" }); 
	        stmtPadre.setString(1, playlist.getNome());
	        stmtPadre.setInt(2, playlist.getProprietario().getIdUtente());
	        
	        int righePadre = stmtPadre.executeUpdate();
	        int idGenerato = 0;
	        
	        if (righePadre > 0) {
	            try (ResultSet generatedKeys = stmtPadre.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    idGenerato = generatedKeys.getInt(1); // Recuperiamo il valore reale generato dal SERIAL
	                    playlist.setIdPlaylist(idGenerato); // Aggiorniamo l'oggetto in RAM
	                }
	            }
	        }
	        
	        if (idGenerato == 0) {
	            throw new SQLException("Impossibile recuperare l'ID autogenerato per la Playlist.");
	        }
	        
	        
	        // --- STEP 2: Inserimento figlio ---
	        // 2a. Inserimento nella tabella figlia playlist_pubblica
	        if (playlist instanceof PlaylistPubblica) {
	            PlaylistPubblica playlistPubblica = (PlaylistPubblica) playlist;
	            
	            
	            stmtFiglio = connessione.prepareStatement(queryFigliaPubblica);
	            stmtFiglio.setInt(1, idGenerato);
	            stmtFiglio.setLong(2, 0); // numvisualizzazioni a 0
	            stmtFiglio.executeUpdate();
	            stmtFiglio.close(); // Chiudiamo questo statement
	            //Inserimento nella tabella associativa appartiene_categoria
	            String nomeCategoria = playlistPubblica.getCategoria();
	            if (nomeCategoria != null && !nomeCategoria.isEmpty()) {
	                String queryCercaIdCat = "SELECT id_categoria FROM categorie WHERE nomecategoria = ?";
	                String queryInsAssociazione = "INSERT INTO appartiene_categoria (id_categoria, id_playlist) VALUES (?, ?)";
	                
	                // Cerchiamo l'ID numerico della categoria a partire dal nome univoco
	                try (PreparedStatement stmtCerca = connessione.prepareStatement(queryCercaIdCat)) {
	                    stmtCerca.setString(1, nomeCategoria);
	                    try (ResultSet rs = stmtCerca.executeQuery()) {
	                        int idCategoria = -1;
	                        if (rs.next()) {
	                            idCategoria = rs.getInt("id_categoria");
	                        }
	                        
	                        if (idCategoria != -1) {
	                            
	                            try (PreparedStatement stmtAssoc = connessione.prepareStatement(queryInsAssociazione)) {
	                                stmtAssoc.setInt(1, idCategoria);
	                                stmtAssoc.setInt(2, idGenerato);
	                                stmtAssoc.executeUpdate();
	                            }
	                        } else {
	                            System.err.println("Attenzione: La categoria '" + nomeCategoria + "' non esiste sul DB. Associazione saltata.");
	                        }
	                    }
	                }
	            }
	            
	            
	        }
	        // 2b. Inserimento nella tabella figlia playlist_condivisa
	        else if (playlist instanceof PlaylistCondivisa) {
	            PlaylistCondivisa playlistCondivisa = (PlaylistCondivisa) playlist;
	            stmtFiglio = connessione.prepareStatement(queryFigliaCondivisa);
	            stmtFiglio.setInt(1, idGenerato);
	            stmtFiglio.setString(2, playlistCondivisa.getURL_Invito());
	            stmtFiglio.executeUpdate();
	            
	        } 
	        //2c. Inserimento nella tabella figlia playlist_privata
	        else { 
	            stmtFiglio = connessione.prepareStatement(queryFigliaPrivata);
	            stmtFiglio.setInt(1, idGenerato);
	            stmtFiglio.executeUpdate();
	        }
	        
	        
	        
	        // --- STEP 3: Commit ---
	        connessione.commit();
	        
	        return playlist; 
	        
	    } catch (SQLException e) {
	        System.err.println("Errore durante il salvataggio della playlist. Eseguo rollback...");
	        try {
	            if (connessione != null) {
	                connessione.rollback();
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        e.printStackTrace();
	    } finally {
	        try {
	            connessione.setAutoCommit(true);
	            if (stmtPadre != null) stmtPadre.close();
	            if (stmtFiglio != null) stmtFiglio.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return null;
	}

	@Override
	public Playlist trovaPlaylist(Playlist playlist) {
		String query = "SELECT p.ID_Playlist, p.nome, p.ID_Utente, "
				+ "pub.numvisualizzazioni, "
				+ "cond.URL_Invito, "
				+ "priv.ID_Playlist AS is_privata "
				+ "FROM Playlist p "
				+ "LEFT JOIN Playlist_Pubblica pub ON p.ID_Playlist = pub.ID_Playlist "
				+ "LEFT JOIN Playlist_Condivisa cond ON p.ID_Playlist = cond.ID_Playlist "
				+ "LEFT JOIN Playlist_privata priv ON p.ID_Playlist = priv.ID_Playlist "
				+ "WHERE p.ID_Playlist = ?";
		try(PreparedStatement statement = connessione.prepareStatement(query))
		{
			statement.setInt(1, playlist.getID());
			try(ResultSet rs = statement.executeQuery())
			{
				if(rs.next())
				{
					int ID = playlist.getID();
					int IDProprietario = rs.getInt("ID_Utente");
					String nomePlaylist = rs.getString("nome");
					UtenteDao utenteDao = new UtenteDAO_Impl(connessione);
					Utente proprietario = utenteDao.trovaUtenteDaID(IDProprietario);
					
					if(playlist instanceof PlaylistPubblica)
					{
						PlaylistPubblica playlistPubblica = (PlaylistPubblica) playlist;
						return new PlaylistPubblica(
								ID,
								nomePlaylist,
								proprietario,
								playlistPubblica.getCategoria()
								);
					} else if(playlist instanceof PlaylistCondivisa) {
						
						return new PlaylistCondivisa(
									ID,
									nomePlaylist,
									proprietario
								);
					} else {
						
						return new PlaylistPrivata(
								ID,
								nomePlaylist,
								proprietario
								);
								
						
					}
				} 
			}
		}catch(SQLException e)
		{
			System.out.println("Errore nella ricerca della playlist");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean modificaPlaylist(Playlist playlist) {
	    // 1. Query per aggiornare i dati comuni nella tabella padre
	    String queryPadre = "UPDATE playlist SET nome = ? WHERE id_playlist = ?";
	    
	    // 2. Query di eliminazione preventiva dalle tabelle figlie per evitare duplicati o sdoppiamenti
	    String eliminaPubblica = "DELETE FROM playlist_pubblica WHERE id_playlist = ?";
	    String eliminaCondivisa = "DELETE FROM playlist_condivisa WHERE id_playlist = ?";
	    String eliminaPrivata = "DELETE FROM playlist_privata WHERE id_playlist = ?";
	    
	    // 3. Query di inserimento nella nuova tabella figlia di destinazione
	    String inserisciPubblica = "INSERT INTO playlist_pubblica (id_playlist, numvisualizzazioni) VALUES (?, ?)";
	    String inserisciCondivisa = "INSERT INTO playlist_condivisa (id_playlist, url_invito) VALUES (?, ?)";
	    String inserisciPrivata = "INSERT INTO playlist_privata (id_playlist) VALUES (?)";
	    
	    PreparedStatement stmtPadre = null;
	   
	    PreparedStatement stmtInserisci = null;
	    
	    try {
	        // Disattiviamo l'autocommit per la transazione
	        connessione.setAutoCommit(false);
	        
	        // --- STEP 1: Aggiorna il nome della Playlist nella tabella padre ---
	        stmtPadre = connessione.prepareStatement(queryPadre);
	        stmtPadre.setString(1, playlist.getNome());
	        stmtPadre.setInt(2, playlist.getID());
	        int righeAggiornate = stmtPadre.executeUpdate();
	        
	        if (righeAggiornate == 0) {
	            // Se la playlist non esiste sul DB, annulliamo tutto
	            connessione.rollback();
	            return false;
	        }
	        
	        // --- STEP 2: Rimuoviamo la playlist da TUTTE le tabelle figlie ---
	        // Questo garantisce che non violiamo il vincolo di disgiunzione 
	        
	        try (PreparedStatement stmtDelPub = connessione.prepareStatement(eliminaPubblica);
	             PreparedStatement stmtDelCond = connessione.prepareStatement(eliminaCondivisa);
	             PreparedStatement stmtDelPriv = connessione.prepareStatement(eliminaPrivata)) {
	            
	            stmtDelPub.setInt(1, playlist.getID());
	            stmtDelCond.setInt(1, playlist.getID());
	            stmtDelPriv.setInt(1, playlist.getID());
	            
	            stmtDelPub.executeUpdate();
	            stmtDelCond.executeUpdate();
	            stmtDelPriv.executeUpdate();
	        }
	        
	        // --- STEP 3: Inseriamo il record nella tabella figlia coerente con il tipo attuale dell'oggetto ---
	        if (playlist instanceof PlaylistPubblica) {
	            PlaylistPubblica pub = (PlaylistPubblica) playlist;
	            stmtInserisci = connessione.prepareStatement(inserisciPubblica);
	            stmtInserisci.setInt(1, pub.getID());
	            stmtInserisci.setLong(2, pub.getNumVisualizzazioni()); // Mantiene il contatore visivo corrente
	            
	        } else if (playlist instanceof PlaylistCondivisa) {
	            PlaylistCondivisa cond = (PlaylistCondivisa) playlist;
	            stmtInserisci = connessione.prepareStatement(inserisciCondivisa);
	            stmtInserisci.setInt(1, cond.getID());
	        
	            // Se l'URL di invito è nullo, va generato         
	            if (cond.getURL_Invito() != null) {
	                stmtInserisci.setString(2, cond.generaURL());
	            } else {
	                stmtInserisci.setNull(2, java.sql.Types.VARCHAR);
	            }
	            
	        } else { // PlaylistPrivata
	            stmtInserisci = connessione.prepareStatement(inserisciPrivata);
	            stmtInserisci.setInt(1, playlist.getID());
	        }
	        
	        stmtInserisci.executeUpdate();
	        
	        // --- STEP 4: Conferma definitiva ---
	        connessione.commit();
	        return true;
	        
	    } catch (SQLException e) {
	        System.err.println("Errore durante la modifica della playlist. Eseguo il rollback...");
	        try {
	            if (connessione != null) {
	                connessione.rollback();
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        e.printStackTrace();
	    } finally {
	        try {
	            connessione.setAutoCommit(true);
	            if (stmtPadre != null) stmtPadre.close();
	            if (stmtInserisci != null) stmtInserisci.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    return false;
	}

	@Override
	public boolean cancellaPlaylist(Playlist playlist) {
	    
	    String queryFigliaPubblica = "DELETE FROM playlist_pubblica WHERE id_playlist = ?";
	    String queryFigliaCondivisa = "DELETE FROM playlist_condivisa WHERE id_playlist = ?";
	    String queryFigliaPrivata = "DELETE FROM playlist_privata WHERE id_playlist = ?";
	    
	    String queryPadre = "DELETE FROM playlist WHERE id_playlist = ?";
	    
	    PreparedStatement stmtFiglia = null;
	    PreparedStatement stmtPadre = null;
	    
	    try {
	        // Disattiviamo l'autocommit per la transazione
	        connessione.setAutoCommit(false);
	       
	        if (playlist instanceof PlaylistPubblica) {
	            stmtFiglia = connessione.prepareStatement(queryFigliaPubblica);
	        } else if (playlist instanceof PlaylistCondivisa) {
	            stmtFiglia = connessione.prepareStatement(queryFigliaCondivisa);
	        } else {
	            stmtFiglia = connessione.prepareStatement(queryFigliaPrivata);
	        }
	        
	        stmtFiglia.setInt(1, playlist.getID());
	        stmtFiglia.executeUpdate();
	        
	        stmtPadre = connessione.prepareStatement(queryPadre);
	        stmtPadre.setInt(1, playlist.getID());
	        int righeCancellate = stmtPadre.executeUpdate();
	        connessione.commit();
	        
	        return righeCancellate > 0;
	        
	    } catch (SQLException e) {
	        
	        System.err.println("Errore durante la cancellazione della playlist");
	        try {
	            if (connessione != null) {
	                connessione.rollback();
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        e.printStackTrace();
	    } finally {
	        
	        try {
	            connessione.setAutoCommit(true);
	            if (stmtFiglia != null) stmtFiglia.close();
	            if (stmtPadre != null) stmtPadre.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    return false;
	}

	@Override
	public ArrayList<Playlist> listaPlaylistProprie(Utente utente) {
	    ArrayList<Playlist> listaPlaylist = new ArrayList<>();
	    
	    // Controllo preventivo
	    if (utente == null) {
	        return listaPlaylist;
	    }

	    
	    String query = "SELECT p.ID_Playlist, p.nome, "
	                 + "pub.numvisualizzazioni, "
	                 + "cond.URL_Invito, "
	                 + "priv.ID_Playlist AS is_privata "
	                 + "FROM Playlist p "
	                 + "LEFT JOIN Playlist_Pubblica pub ON p.ID_Playlist = pub.ID_Playlist "
	                 + "LEFT JOIN Playlist_Condivisa cond ON p.ID_Playlist = cond.ID_Playlist "
	                 + "LEFT JOIN Playlist_privata priv ON p.ID_Playlist = priv.ID_Playlist "
	                 + "WHERE p.ID_Utente = ?";

	    try (PreparedStatement statement = connessione.prepareStatement(query)) {
	        statement.setInt(1, utente.getIdUtente());
	        
	        try (ResultSet rs = statement.executeQuery()) {
	            while (rs.next()) {
	                int idPlaylist = rs.getInt("ID_Playlist");
	                String nomePlaylist = rs.getString("nome");

	                //controllo per attributi specifici delle figlie
	                if (rs.getObject("numvisualizzazioni") != null) {
	                    listaPlaylist.add(new PlaylistPubblica(idPlaylist, nomePlaylist, utente, "Generica"));
	                } 
	                else if (rs.getObject("URL_Invito") != null) {
	                    listaPlaylist.add(new PlaylistCondivisa(idPlaylist, nomePlaylist, utente));
	                } 
	                else {
	                    listaPlaylist.add(new PlaylistPrivata(idPlaylist, nomePlaylist, utente));
	                }
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Errore nel recupero delle playlist dell'utente: " + utente.getIdUtente());
	        e.printStackTrace();
	    }
	    
	    return listaPlaylist;
	}

	@Override
	public ArrayList<Playlist> listaPlaylistInCondivisioneConMeDaMe(Utente utente) {
		
		ArrayList<Playlist> listaPlaylist = new ArrayList<>();
		String query = "SELECT * FROM accesso_o_modifica WHERE id_utente = ?";
		try(PreparedStatement statement = connessione.prepareStatement(query))
		{
			statement.setInt(1, utente.getIdUtente());
			try(ResultSet rs = statement.executeQuery())
			{
				while(rs.next())
				{
					int idPlaylist = rs.getInt(2);
					Playlist supporto = new PlaylistCondivisa(idPlaylist, null, null);
					Playlist playlistCompleta = trovaPlaylist(supporto);
					if(playlistCompleta != null)
					{
						listaPlaylist.add(playlistCompleta);
					}
				}
			}
			
			
			
		}catch(SQLException e)
		{
			System.out.println("Errore nel recupero delle playlist ");
			e.printStackTrace();
		}
		query = "SELECT p.id_playlist FROM playlist_condivisa pc JOIN playlist p ON pc.id_playlist = p.id_playlist WHERE id_utente = ?";
		try(PreparedStatement statement = connessione.prepareStatement(query))
		{
			statement.setInt(1, utente.getIdUtente());
			try(ResultSet rs = statement.executeQuery())
			{
				while(rs.next())
				{
					int idPlaylist = rs.getInt(1);
					Playlist supporto = new PlaylistCondivisa(idPlaylist, null, null);
					Playlist playlistCompleta = trovaPlaylist(supporto);
					if(playlistCompleta != null)
					{
						listaPlaylist.add(playlistCompleta);
					}
				}
			}
			
			
			
		}catch(SQLException e)
		{
			System.out.println("Errore nel recupero delle playlist ");
			e.printStackTrace();
		}
		return listaPlaylist;
	}

	@Override
	public ArrayList<Playlist> trovaTutteLePubbliche() {
	    ArrayList<Playlist> playlistPubbliche = new ArrayList<>();
	    String query = "SELECT * FROM playlist_pubblica";
	    try(PreparedStatement statement = connessione.prepareStatement(query))
	    {
	        try(ResultSet rs = statement.executeQuery())
	        {
	            while(rs.next())
	            {
	                int idPlaylist = rs.getInt(1);
	                Playlist supporto = new PlaylistPubblica(idPlaylist, null, null, null);
	                Playlist playlistCompleta = trovaPlaylist(supporto);
	                
	                if(playlistCompleta != null)
	                {
	                    
	                    if(playlistCompleta instanceof PlaylistPubblica) {
	                        String categoriaReale = getCategoriaPlaylist(playlistCompleta); //metodo del DAO

	                        ((PlaylistPubblica) playlistCompleta).setCategoria(categoriaReale); 
	                    }
	                    
	                    playlistPubbliche.add(playlistCompleta);
	                }
	            }
	        }
	        return playlistPubbliche;
	        
	    }catch(SQLException e)
	    {
	        System.out.println("Errore nel recupero delle playlist pubbliche");
	        e.printStackTrace();
	    }
	    return null;
	}

	@Override
	public ArrayList<Playlist> trovaPlaylistPrivateUtente(int idUtente) {
		ArrayList<Playlist> playlistPrivateDiUtente = new ArrayList<>();
		String query = "SELECT * FROM Playlist_privata pp JOIN Playlist p ON pp.id_Playlist = p.id_playlist WHERE id_utente = ?";
		try(PreparedStatement statement = connessione.prepareStatement(query))
		{
			statement.setInt(1, idUtente);
			try(ResultSet rs = statement.executeQuery())
			{
				while(rs.next())
				{
					int idPlaylist = rs.getInt("id_Playlist");
					Playlist supporto = new PlaylistPrivata(idPlaylist, null, null);
					Playlist playlistCompleta = trovaPlaylist(supporto);
					if(playlistCompleta != null)
					{
						playlistPrivateDiUtente.add(playlistCompleta);
					}
				}
			}
			
			return playlistPrivateDiUtente;
			
		}catch(SQLException e)
		{
			System.out.println("Errore nel recupero delle playlist private dell'utente");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean aggiungiBrano(Playlist playlist, ElementoMultimediale elemento) {
		String query = "INSERT INTO Contiene(id_elemento, id_playlist) VALUES (?, ?)";
		try(PreparedStatement statement = connessione.prepareStatement(query))
		{
			statement.setInt(1, elemento.getIdElemento());
			statement.setInt(2, playlist.getID());
			int rowsAffected = statement.executeUpdate();
			if(rowsAffected > 0)
			{
				playlist.addElemento(elemento);
				return true;
			}
		}catch(SQLException e)
		{
			System.out.println("Errore nell'inserimento del brano nella playlist");
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public ArrayList<ElementoMultimediale> estraiBraniDaPlaylist(Playlist playlist) {
	    ArrayList<ElementoMultimediale> listaBraniDaPlaylist = new ArrayList<>();
	    
	    
	    String query = "SELECT id_elemento FROM Contiene WHERE id_playlist = ?";
	                   
	    try (PreparedStatement statement = connessione.prepareStatement(query)) {
	        statement.setInt(1, playlist.getID());
	        
	        try (ResultSet rs = statement.executeQuery()) {
	            
	            ElementoMultimedialeDao elementoDao = new ElementoMultimedialeDAO_Impl(connessione);
	            
	            while (rs.next()) {
	                int idElemento = rs.getInt("id_elemento");
	                ElementoMultimediale el = elementoDao.trovaContenutoDalD(idElemento);
	                
	                if (el != null) {
	                    listaBraniDaPlaylist.add(el);
	                }
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Errore durante l'estrazione dei brani dalla playlist: " + playlist.getNome());
	        e.printStackTrace();
	    }
	    
	    return listaBraniDaPlaylist; 
	}
	
	@Override
	public ArrayList<String> getAllCategorie()
	{
		ArrayList<String> listaCategorieArrayList = new ArrayList<>();
		String query = "SELECT nomecategoria FROM categorie";
		try(PreparedStatement statement = connessione.prepareStatement(query))
		{
			try(ResultSet rs = statement.executeQuery())
			{
				while(rs.next())
				{
					listaCategorieArrayList.add(rs.getString(1));
				}
			}
			
		}catch(SQLException e)
		{
			System.out.println("Errore nel recupero delle categorie");
			e.printStackTrace();
		}
		return listaCategorieArrayList;
	}
	
	@Override
	public String getCategoriaPlaylist(Playlist p) {
		String query = "SELECT nomecategoria FROM Categorie c JOIN appartiene_categoria ac ON c.id_categoria = ac.id_categoria WHERE ac.id_Playlist = ?";
		String categoria = "N/A";
		try(PreparedStatement statement = connessione.prepareStatement(query))
		{
			statement.setInt(1, p.getID());
			try(ResultSet rs = statement.executeQuery())
			{
				if(rs.next())
				{
					categoria = rs.getString("nomecategoria");
				}
			}
		}catch(SQLException e)
		{
			System.out.println("Errore nella ricerca della categoria della playlist");
			e.printStackTrace();
		}
		return categoria;
	}

}
