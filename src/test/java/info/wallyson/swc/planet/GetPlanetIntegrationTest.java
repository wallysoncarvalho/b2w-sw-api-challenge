package info.wallyson.swc.planet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetPlanetIntegrationTest extends IntegrationTestBaseClass {
  private Planet savedPlanet;

  @BeforeEach
  void setUp() {
    savedPlanet = planetRepository.save(PlanetObjectMother.getPlanets().get(0));
  }

  @Test
  @DisplayName("Should get a planet by it's ID")
  void shouldGetPlanetById() throws Exception {

    var response =
        mockMvc
            .perform(get("/api/planet/{id}", savedPlanet.getId()))
            .andExpect(status().isOk())
            .andReturn();

    var responseContent = response.getResponse().getContentAsString();
    var planetResponse = mapper.readValue(responseContent, Planet.class);

    assertEquals(savedPlanet, planetResponse);
  }

  @Test
  @DisplayName("Should get a planet by it's name")
  void shouldGetPlanetByName() throws Exception {
    var response =
        mockMvc
            .perform(get("/api/planet/name/{name}", savedPlanet.getName()))
            .andExpect(status().isOk())
            .andReturn();

    var responseContent = response.getResponse().getContentAsString();
    var planetResponse = mapper.readValue(responseContent, Planet.class);

    assertEquals(savedPlanet, planetResponse);
  }
}
