package ar.edu.utn.frba.ddsi.climalert;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "climalert.scheduling.enabled=false")
class ClimalertServiceApplicationTests {

    @Test
    void contextLoads() {
    }
}
