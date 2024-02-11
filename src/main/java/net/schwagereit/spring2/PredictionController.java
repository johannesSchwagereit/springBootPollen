package net.schwagereit.spring2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class PredictionController {
    private static final Logger log = LoggerFactory.getLogger( PredictionController.class );

    private final PollenPredictionRepository repository;

    PredictionController( PollenPredictionRepository repository ) {
        this.repository = repository;
    }

    @GetMapping( "/today" )
    public String today() {
        String todayDate = LocalDate.now().plusDays( 0 ).format( DateTimeFormatter.ISO_DATE );
        return getDataForThisDate( todayDate );
    }

    @GetMapping( "/tomorrow" )
    public String tomorrow() {
        String tomorrowDate = LocalDate.now().plusDays( 1 ).format( DateTimeFormatter.ISO_DATE );
        return getDataForThisDate( tomorrowDate );
    }

    private String getDataForThisDate( String date ) {
        log.info( "Get data for {}", date );
        return repository.findById( date )
                .orElseThrow( () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No data for " + date ) )
                .getWert();
    }
}