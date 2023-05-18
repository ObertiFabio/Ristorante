package it.paleocapa.obertif;
import java.util.HashMap;
import java.util.*;
import java.time.*;
import java.util.stream.*;

public class Ristorante
{
    HashMap<String, LinkedList<Prenotazione>> prenotazioni;
    public Object prenotazione;
    
    public Ristorante(){
        prenotazioni = new HashMap<String, LinkedList<Prenotazione>>();
    }
    
    public void aggiungiCliente(String nome){
        if(prenotazioni.containsKey(nome)){
            throw new ClienteGiaInseritoException();
        }
        prenotazioni.put(nome, new LinkedList<Prenotazione>());
    }
    
    public void modificaCliente(String nomeVecchio, String nomeNuovo){
        if(!prenotazioni.containsKey(nomeVecchio)){
            throw new ClienteNonEsistenteException();
        }
        prenotazioni.get(nomeVecchio).forEach( n -> n.cliente = nomeNuovo);    
        prenotazioni.put(nomeNuovo, prenotazioni.get(nomeVecchio));
        prenotazioni.remove(nomeVecchio);
    }
    
    public void rimuoviCliente(String nome){
        if(!prenotazioni.containsKey(nome)){
            throw new ClienteNonEsistenteException();
        }
        prenotazioni.remove(nome);
    }
    
    public void aggiungiPrenotazione(Prenotazione p){
        if(!prenotazioni.containsKey(p.cliente)){
            throw new ClienteNonEsistenteException();
        }
        prenotazioni.get(p.cliente).add(p);
    }
    
    public void modificaPrenotazione(Prenotazione prenotVecchia, Prenotazione prenotNuova){
       if(!prenotazioni.get(prenotVecchia.cliente).contains(prenotVecchia)){
            throw new PrenotazioneNonEsistenteException();
       }
       if(!prenotazioni.containsKey(prenotVecchia.cliente)){
            throw new ClienteNonEsistenteException();
       }
       eliminaPrenotazione(prenotVecchia);
       if(!prenotazioni.containsKey(prenotNuova.cliente)){
            aggiungiCliente(prenotNuova.cliente);
       }
        aggiungiPrenotazione(prenotNuova);
    }
    
    public void eliminaPrenotazione(Prenotazione p){
        if(!prenotazioni.get(p.cliente).contains(p)){
            throw new PrenotazioneNonEsistenteException();
        }
        
        if(!prenotazioni.containsKey(p.cliente)){
            throw new ClienteNonEsistenteException();
        }
        
        prenotazioni.get(p.cliente).remove(p);
    }
    
    public Stream ricercaPerCliente(String nome){
        if(!prenotazioni.containsKey(nome)){
            throw new ClienteNonEsistenteException();
        }
        return prenotazioni.get(nome).stream();
    }
    
    public Stream<Prenotazione> ricercaPerData(LocalDate dataDaRicercare){
        return prenotazioni.values().stream().flatMap(Collection::stream).filter(p -> p.dataPrenotazione.compareTo(dataDaRicercare)==0);
    }
    
    public int copertiPerData(LocalDate dataDaRicercare){
        return prenotazioni.values().stream().flatMap(Collection::stream).filter(a -> a.dataPrenotazione.compareTo(dataDaRicercare)==0).reduce(0, (partial, e) -> partial+e.coperti, Integer::sum);
    }
    
    public int copertiPerCliente(String nome){
        return prenotazioni.get(nome).stream().reduce(0, (partial, e) -> partial+e.coperti, Integer::sum);
    }
    
    public int copertiInRange(LocalDate firstDate, LocalDate secondDate){
        return prenotazioni.values().stream().flatMap(Collection::stream).filter(p -> p.dataPrenotazione.isAfter(firstDate) && p.dataPrenotazione.isBefore(secondDate)).reduce(0, (partial, e) -> partial+e.coperti, Integer::sum);
    }
    
    public LocalDate maggiorNumeroCoperti(){
        LinkedList<LocalDate> date = new LinkedList<LocalDate>();
        int max = -1;
        LocalDate maxDate = LocalDate.of(1,1,1);
        prenotazioni.values().stream().flatMap(Collection::stream).filter(a -> a.dataPrenotazione.isBefore(LocalDate.now()) && !date.contains(a.getDataPrenotazione())).forEach(a -> date.add(a.getDataPrenotazione()));
        //non funziona con forEach();
        Iterator<LocalDate> i = date.iterator();
        while(i.hasNext()){
            LocalDate nuovaDate = i.next();
            if(max < copertiPerData(nuovaDate)){
                maxDate = nuovaDate;
                max = copertiPerData(nuovaDate);
            }
        }
        return maxDate;
    }
    
    public String clientePiuCoperti(){
        LinkedList<String> clienti = new LinkedList<String>();
        int max = -1;
        String maxCoperti = "";
        prenotazioni.keySet().stream().forEach(p -> clienti.add(p));
        Iterator<String> i = clienti.iterator();
        while(i.hasNext()){
            String current = i.next();
            if(max < copertiPerCliente(current)){
                max = copertiPerCliente(current);
                maxCoperti = current;
            }
        }
        return maxCoperti;
    }
    
    public class ClienteGiaInseritoException extends RuntimeException{};
    public class ClienteNonEsistenteException extends RuntimeException{};
    public class PrenotazioneNonEsistenteException extends RuntimeException{};
}
