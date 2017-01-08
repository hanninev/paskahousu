# Aiheenmäärittely

**Aihe:** Paskahousu

Toteutetaan paskahousua mukaileva korttipeli, jossa käyttäjä voi pelata tietokonetta vastaan. Pelissä tavoitteena on päästä korteista eroon ensimmäisenä.

Vastustaja on tyhmä ja laittaa pinoon ensimmäisen siihen sopivan kortin. Jos useampi vastustajan kädessä oleva kortti sopii pinoon, niin vastustaja vain arpoo niistä yhden. Jos yksikään kortti ei sovi, vastustaja kokeilee onneaan pakasta tai nostaa pinon.

Sovelluslogiikka ja domain-luokat ovat toteutettu siten, että pelissä voisi olla myös useampi pelaaja. Tämä mahdollistaa pelin jatkokehittämisen. Nykyinen käyttöliittymä on kovakoodattu siten, että pelissä on aina vain kaksi pelaajaa - käyttäjä ja vastustajana tietokone.

**Käyttäjät:** Pelaaja

**Pelaajan toimintoja:**
* Pelin aloittaminen
* Kortin valitseminen
* Kortin laittaminen pinoon
* Kortin nostaminen pakasta
* Kaikkien korttien nostaminen pinosta
* Kädessä olevien korttien tarkastelu

## Luokkakaavio
![Luokkakaavio](/dokumentaatio/luokkakaavio2.png)

## Sekvenssikaavioita
![Sekvenssikaavio](/dokumentaatio/sekvenssi1.png)

![Sekvenssikaavio](/dokumentaatio/sekvenssi2.png)
