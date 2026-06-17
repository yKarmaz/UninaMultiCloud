package entities;

import java.time.LocalDate;

public class Fruizione {
	
	private int idElemento;
	private int idUtente;
	private LocalDate dataFruizione;
	public Fruizione(int idElemento, int idUtente) {
		this.idElemento = idElemento;
		this.idUtente = idUtente;
		dataFruizione = LocalDate.now();
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
	public LocalDate getDataFruizione() {
		return dataFruizione;
	}
	public void setDataFruizione(LocalDate dataFruizione) {
		this.dataFruizione = dataFruizione;
	}
	
	public void registraFruizione()
	{
		
	}
	
}
