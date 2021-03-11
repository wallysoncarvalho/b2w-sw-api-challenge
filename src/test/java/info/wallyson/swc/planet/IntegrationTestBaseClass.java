package info.wallyson.swc.planet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class IntegrationTestBaseClass {
  @Autowired MockMvc mockMvc;
  @Autowired
  PlanetRepository planetRepository;
  ObjectMapper mapper = new ObjectMapper();

  @MockBean
  PlanetAppearance planetAppearance;

  @AfterEach
  void cleanDatabase() {
    planetRepository.deleteAll();
  }
}
