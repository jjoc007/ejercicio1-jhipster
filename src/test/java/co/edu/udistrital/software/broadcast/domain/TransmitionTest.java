package co.edu.udistrital.software.broadcast.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.udistrital.software.broadcast.web.rest.TestUtil;

public class TransmitionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transmition.class);
        Transmition transmition1 = new Transmition();
        transmition1.setId(1L);
        Transmition transmition2 = new Transmition();
        transmition2.setId(transmition1.getId());
        assertThat(transmition1).isEqualTo(transmition2);
        transmition2.setId(2L);
        assertThat(transmition1).isNotEqualTo(transmition2);
        transmition1.setId(null);
        assertThat(transmition1).isNotEqualTo(transmition2);
    }
}
