package it.paleocapa.obertif;
import java.time.*;

public class Prenotazione
{
    String cliente;
    LocalDate dataInserimentoPrenotazione, dataPrenotazione ;
    int coperti;
    public Prenotazione(String cliente, LocalDate dataInserimentoPrenotazione, LocalDate dataPrenotazione, int coperti){
        this.cliente = cliente;
        this.dataInserimentoPrenotazione = dataInserimentoPrenotazione;
        this.dataPrenotazione = dataPrenotazione;
        this.coperti = coperti;
    }
    
    public LocalDate getDataPrenotazione(){
        return dataPrenotazione;
    }
    public String getCliente(){
        return cliente;
    }
    public String toString(){
        return "Prenotazione a nome di : " + cliente + " effettuata il giorno " + dataInserimentoPrenotazione + " per il " + dataPrenotazione + " con : " + coperti + " coperti";
    }
}
