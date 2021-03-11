package info.wallyson.swc.planet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Component
class PlanetAppearanceSWAPI implements PlanetAppearance {
  private final Logger LOG = LoggerFactory.getLogger(PlanetAppearanceSWAPI.class);
  private final RestTemplate restTemplate = new RestTemplate();
  private final String swApiPlanet = "https://swapi.dev/api/planets/";

  @Override
  public int getMovieAppearance(String planetName) {
    var body = getPlanetSearchResponseBody(planetName);

    var planetsFound = parseResponseResults(body);

    var matchPlanet =
        planetsFound.stream()
            .filter(planet -> planet.getName().equalsIgnoreCase(planetName))
            .findFirst();

    return matchPlanet.map(SwapiPlanetResponse::getFilmsCount).orElse(0);
  }

  private String getPlanetSearchResponseBody(String planetName) {
    var uri =
        ServletUriComponentsBuilder.fromHttpUrl(swApiPlanet)
            .queryParam("search", planetName)
            .build()
            .toUriString();

    var response = restTemplate.exchange(uri, HttpMethod.GET, null, String.class);

    return response.getBody();
  }

  private List<SwapiPlanetResponse> parseResponseResults(String responseBody) {
    ObjectMapper mapper = new ObjectMapper();

    try {
      JsonNode jsonNode = mapper.readValue(responseBody, JsonNode.class);

      var planetsArrString = jsonNode.get("results");

      return mapper.readValue(planetsArrString.toString(), new TypeReference<>() {});

    } catch (Exception e) {
      LOG.error("Error parsing response from SW api.");
    }

    return Collections.emptyList();
  }
}
