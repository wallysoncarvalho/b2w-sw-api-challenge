package info.wallyson.swc.planet;

import com.fasterxml.jackson.core.type.TypeReference;
import info.wallyson.swc.web.dto.PageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetAllPlanetsIntegrationTest extends IntegrationTestBaseClass {

  @Test
  @DisplayName("Should get a list of planets with default values for page")
  void shouldGetPlanetsListWithDefaultValuesForPage() throws Exception {

    planetRepository.saveAll(PlanetObjectMother.getPlanets());

    var response =
        mockMvc
            .perform(get("/api/planet").queryParam("page", "1").queryParam("pageSize", "5"))
            .andExpect(status().isOk())
            .andReturn();

    var responseContent = response.getResponse().getContentAsString();

    var pageResponse =
        mapper.readValue(responseContent, new TypeReference<PageResponse<Planet>>() {});

    assertEquals(pageResponse.getTotal(), 2);
    assertEquals(pageResponse.getItems().size(), 2);
  }

  @Test
  @DisplayName("Should return bad request with invalid values for page and page size.")
  void shouldGetBadRequestWithInvalidPageParams() throws Exception {
    mockMvc
        .perform(get("/api/planet").queryParam("page", "-1").queryParam("pageSize", "2"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").exists())
        .andExpect(jsonPath("$.message").exists());
  }
}
