package DAO.impl;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entities.Audio;
import entities.ElementoMultimediale;
import entities.Utente;
import entities.Video;
import DAO.ElementoMultimedialeDao;
import DAO.UtenteDao;

public class ElementoMultimedialeDAO_Impl implements ElementoMultimedialeDao {
    Connection connessione;

    public ElementoMultimedialeDAO_Impl(Connection connessione) {
        this.connessione = connessione;
    }
  //commento

    @Override
    public int salvaContenuto(ElementoMultimediale e) {
        String query = "INSERT INTO contenutiMultimediali(titolo, descrizione, durata, dataCreazione, immagineCopertina, tipoElemento, bitrate, risoluzione, id_utente) VALUES (?, ?, ?, ?, ?, ?::tipologia, ?, ?, ?)";
        
        try (PreparedStatement statement = connessione.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, e.getTitolo());
            statement.setString(2, e.getDescrizione());
            statement.setInt(3, e.getDurata());
            statement.setObject(4, e.getDataCreazione());
            statement.setString(5, e.getImmagineCopertina());
            
            if (e instanceof Video) {
                Video v = (Video) e;
                statement.setString(6, "video");
                statement.setNull(7, Types.INTEGER);
                statement.setString(8, v.getRisoluzione());
            } else if (e instanceof Audio) {
                Audio a = (Audio) e;
                statement.setString(6, "audio");
                statement.setInt(7, a.getBitRate());
                statement.setNull(8, Types.VARCHAR);
            }
            statement.setInt(9, e.getProprietario().getIdUtente());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException ex) {
            System.err.println("Errore durante il salvataggio del contenuto:");
            ex.printStackTrace();
        }
        return -1;
    }

    @Override
    public ElementoMultimediale trovaContenutoDalD(int id) {
        String query = "SELECT * FROM contenutiMultimediali WHERE id_elemento = ?";
        
        try (PreparedStatement statement = connessione.prepareStatement(query)) {
            statement.setInt(1, id);
            
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mappaRisultatoAdElemento(rs);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Errore durante il recupero del contenuto multimediale:");
            ex.printStackTrace();
        }
        return null;
    }

    // NUOVO METODO: Essenziale per popolare le tabelle del catalogo
    @Override
    public List<ElementoMultimediale> cercaElementi(String testo, String tipoMedia) {
        List<ElementoMultimediale> lista = new ArrayList<>();
        String query = "SELECT * FROM contenutiMultimediali WHERE 1=1 ";
        
        if (testo != null && !testo.trim().isEmpty()) {
            query += " AND titolo ILIKE ? ";
        }
        if (tipoMedia != null && !tipoMedia.equals("Tutti")) {
            query += " AND tipoElemento = ?::tipologia ";
        }

        try (PreparedStatement stmt = connessione.prepareStatement(query)) {
            int paramIndex = 1;
            if (testo != null && !testo.trim().isEmpty()) {
                stmt.setString(paramIndex++, "%" + testo + "%");
            }
            if (tipoMedia != null && !tipoMedia.equals("Tutti")) {
                stmt.setString(paramIndex, tipoMedia.toLowerCase());
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ElementoMultimediale el = mappaRisultatoAdElemento(rs);
                    if (el != null) {
                        lista.add(el);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca degli elementi:");
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public boolean modificaContenuto(ElementoMultimediale e) {
        String query = "UPDATE contenutiMultimediali SET titolo = ?, descrizione = ?, durata = ?, " +
                       "dataCreazione = ?, immagineCopertina = ?, tipoElemento = ?::tipologia, bitrate = ?, " +
                       "risoluzione = ?, id_utente = ? WHERE id_elemento = ?";
                       
        try (PreparedStatement statement = connessione.prepareStatement(query)) {
            statement.setString(1, e.getTitolo());
            statement.setString(2, e.getDescrizione());
            statement.setInt(3, e.getDurata());
            statement.setObject(4, e.getDataCreazione());
            statement.setString(5, e.getImmagineCopertina());
            
            if (e instanceof Video) {
                Video v = (Video) e;
                statement.setString(6, "video");
                statement.setNull(7, Types.INTEGER);
                statement.setString(8, v.getRisoluzione());
            } else if (e instanceof Audio) {
                Audio a = (Audio) e;
                statement.setString(6, "audio");
                statement.setInt(7, a.getBitRate());
                statement.setNull(8, Types.VARCHAR);
            } else {
                statement.setString(6, "generico");
                statement.setNull(7, Types.INTEGER);
                statement.setNull(8, Types.VARCHAR);
            }
            
            statement.setInt(9, e.getProprietario().getIdUtente());
            statement.setInt(10, e.getIdElemento());
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; 
            
        } catch (SQLException ex) {
            System.err.println("Errore durante la modifica dell'elemento multimediale:");
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean cancellaContenuto(ElementoMultimediale e) {
        String query = "DELETE FROM contenutiMultimediali WHERE id_elemento = ?";
        
        try (PreparedStatement statement = connessione.prepareStatement(query)) {
            statement.setInt(1, e.getIdElemento());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            System.err.println("Errore durante l'eliminazione dell'elemento multimediale:");
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Metodo di supporto per evitare duplicazioni di codice:
     * Legge una riga del ResultSet e costruisce l'Entity corretta (Audio o Video)
     */
    private ElementoMultimediale mappaRisultatoAdElemento(ResultSet rs) throws SQLException {
        // CORREZIONE FATALE: Estraggo l'id_utente, non campi inesistenti come "username".
        int idProprietario = rs.getInt("id_utente");
        
        UtenteDao utenteDao = new UtenteDAO_Impl(connessione);
        Utente proprietario = utenteDao.trovaUtenteDaID(idProprietario);

        int idElemento = rs.getInt("id_elemento");
        String tipoElemento = rs.getString("tipoElemento");
        String descrizione = rs.getString("descrizione");
        String titolo = rs.getString("titolo");
        int durata = rs.getInt("durata");
        LocalDate dataCreazione = rs.getObject("dataCreazione", LocalDate.class);
        String immagineCopertina = rs.getString("immagineCopertina");

        if ("video".equals(tipoElemento)) {
            String risoluzione = rs.getString("risoluzione");
            return new Video(
                idElemento, descrizione, titolo, durata, dataCreazione, immagineCopertina, proprietario, risoluzione
            );
        } else if ("audio".equals(tipoElemento)) {
            int bitRate = rs.getInt("bitrate");
            return new Audio(
                idElemento, descrizione, titolo, durata, dataCreazione, immagineCopertina, proprietario, bitRate
            );
        }
        return null;
    }
}