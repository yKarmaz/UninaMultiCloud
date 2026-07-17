package entities;

import java.time.LocalDateTime;

public class Fruizione {
	
	private int idElemento;
	private int idUtente;
	private LocalDateTime dataFruizione;
	public Fruizione(int idElemento, int idUtente, LocalDateTime dataFruizione) {
		this.idElemento = idElemento;
		this.idUtente = idUtente;
		this.dataFruizione = dataFruizione;
	}
	public int getIdElemento() {
		return idElemento;
	}
	public void setIdElemento(int idElemento) {
		this.idElemento = idElemento;
	}
	public int getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}
	public LocalDateTime getDataFruizione() {
		return dataFruizione;
	}
	public void setDataFruizione(LocalDateTime dataFruizione) {
		this.dataFruizione = dataFruizione;
	}
	
	public void registraFruizione()
	{
		
	}
	
}
