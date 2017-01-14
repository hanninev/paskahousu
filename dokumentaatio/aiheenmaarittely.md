# Aiheenmäärittely

**Aihe:** Paskahousu

Toteutetaan paskahousua mukaileva korttipeli, jossa käyttäjä voi pelata tietokonetta vastaan. Pelissä tavoitteena on päästä korteista eroon ensimmäisenä.

Vastustaja ohjelmoidaan siten, että se laittaa pinoon pienimmän siihen sopivan kortin, kuitenkin siten, että se säästelee kakkosia. Jos yksikään kortti ei sovi, vastustaja kokeilee onneaan pakasta tai nostaa pinon.

**Käyttäjät:** Pelaaja

**Pelaajan toimintoja:**
* Pelin aloittaminen
* Kortin valitseminen
* Kortin laittaminen pinoon
* Kortin nostaminen pakasta
* Kaikkien korttien nostaminen pinosta
* Kädessä olevien korttien tarkastelu

## Luokkakaavio
![Luokkakaavio](/dokumentaatio/luokkakaavio3.png)

### Rakennekuvaus
Sovelluslogiikka ja domain-luokat ovat toteutettu siten, että pelissä voisi olla myös useampi pelaaja. Tämä mahdollistaa pelin jatkokehittämisen. Nykyinen käyttöliittymä on kuitenkin koodattu siten, että pelissä on aina vain kaksi pelaajaa - käyttäjä ja vastustajana tietokone.

Peli tuntee suoraan kaikki mukana olevat Pelaajat, mutta Peli tuntee myös Siirtojenkäsittelijan kautta Vuorossa olevan Pelaajan. Vuoroon liittyy tietoja Pelaajan kyseisellä vuorolla tekemistä toiminnoista, joita käytetään esimerkiksi tulostuksiin.

Luokka Siirtojenkäsittelijä tuntee kaikki luokat, joiden välillä liikutellaan kortteja. Siirtojenkasittelija vastaa kortttien liikuttelusta, mutta se tarkistaa ennen siirtoja Saannot-luokalta, onko haluttu siirto sallittu.

Luokat Kasi ja Pakka perivät Korttijoukon, koska ne vaativat lisätoiminnallisuutta. Pakka pitää sekoittaa ja korttien pitää olla järjestettyinä kädessä. Pinolla ei sen sijaan ole mitään lisätoiminnallisuutta verrattuna Kateen ja Pakkaan, joten siksi pino toteuttaa suoraan yläluokan.

Tekoaly käyttää Siirtojenkasittelijan metodeita korttien liikutteluun.

## Sekvenssikaavioita
![Sekvenssikaavio](/dokumentaatio/sekvenssi1.png)

![Sekvenssikaavio](/dokumentaatio/sekvenssi2.png)
