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
    @Test
    void eliminaPrenotazione(){
        r.aggiungiCliente("giacomo");
        Prenotazione p = new Prenotazione("giacomo", LocalDate.of(2017,05,26), LocalDate.of(2017,06,10), 5);
        r.aggiungiPrenotazione(p);
        r.eliminaPrenotazione(p);
        assertEquals(0, r.prenotazioni.get("giacomo").size());
    }
    
    @Test
    void ricercaPerCliente(){
        r.aggiungiCliente("giacomo");
        r.aggiungiCliente("filippo");
        Prenotazione p = new Prenotazione("giacomo", LocalDate.of(2017,05,26), LocalDate.of(2017,06,10), 5);
        Prenotazione p1 = new Prenotazione("giacomo", LocalDate.of(2017,05,26), LocalDate.of(2017,06,10), 5);
        Prenotazione p2 = new Prenotazione("filippo", LocalDate.of(2017,05,26), LocalDate.of(2017,06,10), 5);
        r.aggiungiPrenotazione(p);
        r.aggiungiPrenotazione(p1);
        r.aggiungiPrenotazione(p2);
        assertEquals(2, r.ricercaPerCliente("giacomo").count());
    }

    @Test
    void ricercaPerData(){
        r.aggiungiCliente("giacomo");
        r.aggiungiCliente("filippo");
        Prenotazione p = new Prenotazione("giacomo", LocalDate.of(2017,05,26), LocalDate.of(2017,06,10), 5);
        Prenotazione p1 = new Prenotazione("giacomo", LocalDate.of(2017,05,28), LocalDate.of(2017,06,10), 5);
        Prenotazione p2 = new Prenotazione("filippo", LocalDate.of(2017,05,26), LocalDate.of(2017,06,10), 5);
        r.aggiungiPrenotazione(p);
        r.aggiungiPrenotazione(p1);
        r.aggiungiPrenotazione(p2);
        assertEquals(3, r.ricercaPerData(LocalDate.of(2017,06,10)).count());
    }

    @Test
    void copertiPerData(){
        r.aggiungiCliente("giacomo");
        r.aggiungiCliente("filippo");
        Prenotazione p = new Prenotazione("giacomo", LocalDate.of(2017,05,26), LocalDate.of(2016,06,10), 5);
        Prenotazione p1 = new Prenotazione("giacomo", LocalDate.of(2017,05,28), LocalDate.of(2018,06,10), 5);
        Prenotazione p2 = new Prenotazione("filippo", LocalDate.of(2017,05,26), LocalDate.of(2017,06,10), 5);
        r.aggiungiPrenotazione(p);
        r.aggiungiPrenotazione(p1);
        r.aggiungiPrenotazione(p2);
        assertEquals(5, r.copertiPerData(LocalDate.of(2017,06,10)));
    }

    @Test
    void copertiInRange(){
        r.aggiungiCliente("giacomo");
        r.aggiungiCliente("filippo");
        Prenotazione p = new Prenotazione("giacomo", LocalDate.of(2017,05,26), LocalDate.of(2016,06,10), 5);
        Prenotazione p1 = new Prenotazione("giacomo", LocalDate.of(2017,05,28), LocalDate.of(2018,06,10), 5);
        Prenotazione p2 = new Prenotazione("filippo", LocalDate.of(2017,05,26), LocalDate.of(2017,06,10), 5);
        Prenotazione p3 = new Prenotazione("filippo", LocalDate.of(2017,05,26), LocalDate.of(2017,06,10), 5);
        r.aggiungiPrenotazione(p);
        r.aggiungiPrenotazione(p1);
        r.aggiungiPrenotazione(p2);
        r.aggiungiPrenotazione(p3);
        assertEquals(10, r.copertiInRange(LocalDate.of(2016,06,10),LocalDate.of(2018,06,10) ));
    }

    @Test
    void maggiorNumeroCoperti(){
        r.aggiungiCliente("giacomo");
        r.aggiungiCliente("filippo");
        Prenotazione p = new Prenotazione("giacomo", LocalDate.of(2017,05,26), LocalDate.of(2016,06,10), 5);
        Prenotazione p1 = new Prenotazione("giacomo", LocalDate.of(2017,05,28), LocalDate.of(2018,06,10), 10);
        Prenotazione p2 = new Prenotazione("filippo", LocalDate.of(2017,05,26), LocalDate.of(2017,06,10), 20);
        Prenotazione p3 = new Prenotazione("filippo", LocalDate.of(2017,05,26), LocalDate.of(2017,06,10), 15);
        r.aggiungiPrenotazione(p);
        r.aggiungiPrenotazione(p1);
        r.aggiungiPrenotazione(p2);
        r.aggiungiPrenotazione(p3);
        assertEquals(LocalDate.of(2017,06,10), r.maggiorNumeroCoperti());
    }

    @Test
    void clientePiuCoperti(){
        r.aggiungiCliente("giacomo");
        r.aggiungiCliente("filippo");
        r.aggiungiCliente("giuseppe");
        r.aggiungiCliente("andrea");
        Prenotazione p = new Prenotazione("giuseppe", LocalDate.of(2017,05,26), LocalDate.of(2016,06,10), 5);
        Prenotazione p1 = new Prenotazione("giacomo", LocalDate.of(2017,05,28), LocalDate.of(2018,06,10), 10);
        Prenotazione p2 = new Prenotazione("andrea", LocalDate.of(2017,05,26), LocalDate.of(2017,06,10), 20);
        Prenotazione p3 = new Prenotazione("filippo", LocalDate.of(2017,05,26), LocalDate.of(2017,06,10), 15);
        r.aggiungiPrenotazione(p);
        r.aggiungiPrenotazione(p1);
        r.aggiungiPrenotazione(p2);
        r.aggiungiPrenotazione(p3);
        assertEquals("andrea", r.clientePiuCoperti());
    }
}
