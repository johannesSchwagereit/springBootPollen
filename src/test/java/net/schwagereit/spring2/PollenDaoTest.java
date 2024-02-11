package net.schwagereit.spring2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class PollenDaoTest {

    @Test
    void readJsonAndDeserialize() throws IOException {
        Path filePath = Path.of("src/test/resources/pollen.json");
        String json = Files.readString(filePath);

        ObjectMapper objectMapper = new ObjectMapper();
        PollenDao.Pollenflug pollenflug = objectMapper.readValue( json, PollenDao.Pollenflug.class );
        // check date
        assertThat( pollenflug.getTodayDate() ).isEqualTo( "2024-02-08" );
        assertThat( pollenflug.getTomorrowDate() ).isEqualTo( "2024-02-09" );
        // check data
        assertThat(pollenflug.content()).hasSize( 27 );
        assertThat( pollenflug.content()[0].partregionName() )
                .isEqualTo( "Inseln und Marschen" );
        assertThat( pollenflug.content()[0].pollen().beifuss().today() )
                .isEqualTo( "0" );

        // get Beifuss for Donauniederungen only
        PollenDao.Beifuss beifussPollen = pollenflug.getBeifussPollen();
        assertThat( beifussPollen.today() ).isEqualTo( "0" );
        assertThat( beifussPollen.tomorrow() ).isEqualTo( "0" );
    }

}