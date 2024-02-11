package net.schwagereit.spring2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@SpringBootTest
//@RunWith( SpringRunner.class )
//@ExtendWith( SpringExtension.class)
@DataJpaTest
public class PollenPredictionRepositoryTests {

    @Autowired
    private PollenPredictionRepository repository;

    @Test
    public void injectedComponentsAreNotNull() {
        assertThat( repository ).isNotNull();
    }

    @Test
    public void testSaveSeveralEntriesAndReload() {
        repository.save( new PollenPrediction( "2024-02-07", "0-2" ) );
        repository.save( new PollenPrediction( "2024-02-08", "1-2" ) );
        // double key value!
        repository.save( new PollenPrediction( "2024-02-09", "2-3" ) );
        repository.save( new PollenPrediction( "2024-02-09", "2-3" ) );
        Iterable<PollenPrediction> pollenPredictions = repository.findAll();
        assertEquals( 3, ( (Collection<?>) pollenPredictions ).size() );
        assertThat( repository ).isNotNull();
    }

    @Test
    public void testSaveOneEntryAndReload() {
        repository.save( new PollenPrediction( "2024-02-07", "0-2" ) );
        PollenPrediction prediction = repository.findByDatum( "2024-02-07" );
        assertThat( prediction.getWert() ).isEqualTo( "0-2" );
    }

}
