package joululabra.uno.domain;

public class Vuoro {
    private Integer nostokerrat;
    private boolean tehtyYksiSiirto;

    public Vuoro() {
        this.nostokerrat = 0;
        this.tehtyYksiSiirto = false;
    }

    public Integer getNostokerrat() {
        return nostokerrat;
    }

    public boolean onTehtyYksiSiirto() {
        return tehtyYksiSiirto;
    }

    public void asetaTehdyksiYksiSiirto() {
        this.tehtyYksiSiirto = true;
    }

    public void lisaaNostokerta() {
        this.nostokerrat++;
    }

    
}
