package net.schwagereit.spring2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DataLoader implements CommandLineRunner {

    public static final String POLLEN_URL = "https://opendata.dwd.de/climate_environment/health/alerts/s31fg.json";
    private final Logger logger = LoggerFactory.getLogger( DataLoader.class );

    private final PollenPredictionRepository repository;

    @Autowired
    public DataLoader( final PollenPredictionRepository repository ) {
        this.repository = repository;
    }

    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Scheduled( cron = "0 15 11 * * *" ) // every day at 11:15
    //    @Scheduled(cron = "0 */30 * * * *") // every half hour
    //    @Scheduled(cron = "0 * * * * *") // every minute
    public void updateDatabase() {
        logger.info( "Update database..." );
        writePollenPredictionToDB();
    }

    @Override
    public void run( String... strings ) {
        logger.info( "Initialize database..." );
        writePollenPredictionToDB();
    }

    private void writePollenPredictionToDB() {
        PollenDao.Pollenflug pollenflug = getRestTemplate().getForObject(
                POLLEN_URL, PollenDao.Pollenflug.class );
        if ( pollenflug != null ) {
            PollenDao.Beifuss beifussPollen = pollenflug.getBeifussPollen();

            repository.save( new PollenPrediction( pollenflug.getTodayDate(), beifussPollen.today() ) );
            repository.save( new PollenPrediction( pollenflug.getTomorrowDate(), beifussPollen.tomorrow() ) );
            repository.save( new PollenPrediction( pollenflug.getDayAfterTomorrowDate(), beifussPollen.dayafterTo() ) );
            logger.info( "Data written for {}, {} and {}",
                    pollenflug.getTodayDate(), pollenflug.getTomorrowDate(), pollenflug.getDayAfterTomorrowDate() );
        }
    }
}
