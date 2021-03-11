package info.wallyson.swc.planet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeletePlanetIntegrationTest extends IntegrationTestBaseClass {
  private Planet savedPlanet;

  @BeforeEach
  void setUp() {
    var deletePlanet = new Planet(null, "delete_planet", "climate", "terrain", 0);
    savedPlanet = planetRepository.save(deletePlanet);
  }

  @Test
  @DisplayName("Should delete a planet by its ID")
  void shouldDeletePlanetById() throws Exception {

    assertTrue(planetRepository.findById(savedPlanet.getId()).isPresent());

    mockMvc
        .perform(delete("/api/planet/{id}", savedPlanet.getId()))
        .andExpect(status().isNoContent()).andReturn();

    assertFalse(planetRepository.findById(savedPlanet.getId()).isPresent());
  }
}
