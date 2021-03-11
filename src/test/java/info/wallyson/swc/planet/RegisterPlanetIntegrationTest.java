package info.wallyson.swc.planet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegisterPlanetIntegrationTest extends IntegrationTestBaseClass {
  @Test
  @DisplayName("Should register a new planet with 2 occurrences on films")
  void shouldRegisterNewPlanetWithFilmOccurrences() throws Exception {
    var planetToBeRegistered = PlanetObjectMother.getRegisterPlanetDto();

    when(planetAppearance.getMovieAppearance(planetToBeRegistered.getName())).thenReturn(2);

    var response =
        mockMvc
            .perform(
                post("/api/planet")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(planetToBeRegistered)))
            .andExpect(status().isCreated())
            .andReturn();

    var savedPlanetOptional = planetRepository.getPlanetByName(planetToBeRegistered.getName());

    verify(planetAppearance, times(1)).getMovieAppearance(planetToBeRegistered.getName());

    assertTrue(savedPlanetOptional.isPresent());

    savedPlanetOptional.ifPresent(
        planet -> {
          var localizationHeader = response.getResponse().getHeader("Location");
          assertNotNull(localizationHeader);
          assertTrue(localizationHeader.contains("/api/planet/" + planet.getId()));

          assertEquals(planet.getName(), planetToBeRegistered.getName());
          assertEquals(planet.getClimate(), planetToBeRegistered.getClimate());
          assertEquals(planet.getTerrain(), planetToBeRegistered.getTerrain());
          assertEquals(planet.getMovieAppearances(), 2);
        });
  }

  @Test
  @DisplayName("Should return bad request for invalid request body")
  void shouldReturnBadRequestForInvalidRequestBody() throws Exception {
    mockMvc
        .perform(post("/api/planet").contentType(MediaType.APPLICATION_JSON).content(""))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Should return error when unable to connect to SW API")
  void shouldReturnErrorWhenUnableToConnectToSwApi() throws Exception {
    var planetToBeRegistered = PlanetObjectMother.getRegisterPlanetDto();

    when(planetAppearance.getMovieAppearance(planetToBeRegistered.getName()))
        .thenThrow(RestClientException.class);

    var response =
        mockMvc
            .perform(
                post("/api/planet")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(planetToBeRegistered)))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.status").exists())
            .andExpect(jsonPath("$.message").exists());
  }
}
