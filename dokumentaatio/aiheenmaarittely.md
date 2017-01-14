# Aiheenmäärittely

**Aihe:** Paskahousu

Paskahousua mukaileva korttipeli, jossa käyttäjä voi pelata tietokonetta vastaan. Pelissä tavoitteena on päästä korteista eroon ensimmäisenä.

Vastustaja on ohjelmoitu siten, että se laittaa pinoon pienimmän siihen sopivan kortin. Se kuitenkin säästelee kakkosia, jos jokin muu kortti sopii. Jos yksikään kortti ei sovi, vastustaja kokeilee onneaan pakasta tai nostaa pinon.

**Käyttäjät:** Pelaaja

**Pelaajan toimintoja:**
* Kortin valitseminen ja laittaminen pinoon
* Kortin nostaminen pakasta
* Kaikkien korttien nostaminen pinosta
* Vuoron antaminen seuraavalle
* Kädessä olevien korttien tarkastelu

## Luokkakaavio
![Luokkakaavio](/dokumentaatio/luokkakaavio4.png)

### Rakennekuvaus
Peli on toteutettu siten, että pelissä voisi olla useampi pelaaja. Tämä mahdollistaa pelin helpomman jatkokehittämisen. Nykyinen käyttöliittymä on kuitenkin koodattu siten, että pelissä on aina vain kaksi pelaajaa - käyttäjä ja vastustajana tietokone.

Peli tuntee suoraan kaikki mukana olevat Pelaajat, mutta Peli tuntee myös Siirtojenkäsittelijan kautta Vuorossa olevan Pelaajan. Vuoroon liittyy tietoja Pelaajan kyseisellä vuorolla tekemistä toiminnoista, joita käytetään tulostuksiin ja joiden avulla varmistetaan sääntöjen seuraaminen.

Luokka Siirtojenkäsittelijä tuntee kaikki luokat, joiden välillä liikutellaan kortteja. Siirtojenkasittelija vastaa kortttien liikuttelusta, mutta se tarkistaa ennen siirtoja Saannot-luokalta, onko haluttu siirto sallittu.

Luokat Kasi ja Pakka perivät Korttijoukon, koska ne vaativat lisätoiminnallisuutta. Pakka pitää sekoittaa ja korttien pitää olla järjestettyinä kädessä. Pinolla ei sen sijaan ole mitään lisätoiminnallisuutta verrattuna Kateen ja Pakkaan, joten siksi pino toteuttaa suoraan yläluokan.

Tekoaly käyttää Siirtojenkasittelijan metodeita korttien liikutteluun niiden pelaajien kohdalla, joille on asetettu tekoaly.

## Sekvenssikaavioita
![Sekvenssikaavio](/dokumentaatio/pelaaja-kokeilee-onnea.png)

![Sekvenssikaavio](/dokumentaatio/pelaaja-laittaa-kortin-pinoon-onnistuneesti.png)
