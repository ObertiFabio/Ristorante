package it.paleocapa.obertif;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

/**
 * Unit test for simple App.
 */
class AppTest {
    Ristorante r = new Ristorante();
    @Test
    void aggiungiCliente() {
        r.aggiungiCliente("fabio");
        assertEquals(1,r.prenotazioni.values().size());
    }

    @Test
    void modificaCliente(){
        r.aggiungiCliente("fabio");
        r.modificaCliente("fabio", "andrea");
        assertEquals(true, r.prenotazioni.containsKey("andrea"));
    }
    @Test
    void rimuoviCliente(){
        r.aggiungiCliente("fabio");
        r.rimuoviCliente("fabio");
        assertEquals(false, r.prenotazioni.containsKey("fabio"));
    }
    @Test
    void aggiungiPrenotazione(){
        r.aggiungiCliente("giacomo");
        Prenotazione p = new Prenotazione("giacomo", LocalDate.of(2017,05,26), LocalDate.of(2017,06,10), 5);
        r.aggiungiPrenotazione(p);
        assertEquals(p, r.prenotazioni.get("giacomo").getFirst());
    }
    @Test
    void modificaPrenotazione(){
        r.aggiungiCliente("giacomo");
        Prenotazione p = new Prenotazione("giacomo", LocalDate.of(2017,05,26), LocalDate.of(2017,06,10), 5);
        r.aggiungiPrenotazione(p);
        r.aggiungiCliente("fabio");
        Prenotazione p1 = new Prenotazione("fabio", LocalDate.of(2017,05,26), LocalDate.of(2017,06,10), 5);
        r.modificaPrenotazione(p,p1);
        assertEquals(p1, r.prenotazioni.get("fabio").getFirst());
    }
}
