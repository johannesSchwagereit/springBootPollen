package net.schwagereit.spring2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * records to deserialize output from service
 */
public class PollenDao {

    @JsonIgnoreProperties( ignoreUnknown = true )
    public record Pollenflug(
            @JsonProperty( "last_update" ) String lastUpdate, Content[] content) {
        public String getTodayDate() {
            return lastUpdate().substring( 0, 10 );
        }

        public String getTomorrowDate() {
            return ( LocalDate.parse( getTodayDate(), DateTimeFormatter.ISO_DATE )
                    .plusDays( 1 ) ).format( DateTimeFormatter.ISO_DATE );
        }

        public String getDayAfterTomorrowDate() {
            return ( LocalDate.parse( getTodayDate(), DateTimeFormatter.ISO_DATE )
                    .plusDays( 2 ) ).format( DateTimeFormatter.ISO_DATE );
        }

        public Beifuss getBeifussPollen() {
            return Arrays.stream( content() )
                    .filter( x -> x.partregionName().equals( "Donauniederungen" ) )
                    .findFirst().orElseThrow().pollen().beifuss();
        }

    }

    @JsonIgnoreProperties( ignoreUnknown = true )
    public record Content(
            @JsonProperty( "partregion_name" ) String partregionName,
            @JsonProperty( "Pollen" ) Pollen pollen) {
    }

    @JsonIgnoreProperties( ignoreUnknown = true )
    public record Pollen(
            @JsonProperty( "Beifuss" ) Beifuss beifuss) {
    }

    @JsonIgnoreProperties( ignoreUnknown = true )
    public record Beifuss(String today, String tomorrow, @JsonProperty( "dayafter_to" ) String dayafterTo) {
    }

}
