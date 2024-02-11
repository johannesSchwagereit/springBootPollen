package net.schwagereit.spring2;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class PollenPrediction {

    @Id
    private String datum;
    private String wert;

    protected PollenPrediction() {}

    public PollenPrediction( String datum, String wert ) {
        this.datum = datum;
        this.wert = wert;
    }


    @Override
    public String toString() {
        return String.format(
                "PollenPrediction[date=%s, value='%s']",
                datum, wert );
    }

}