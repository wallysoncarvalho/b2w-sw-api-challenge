package info.wallyson.swc.planet;

import info.wallyson.swc.planet.Planet;
import info.wallyson.swc.web.dto.RegisterPlanetDto;

import java.util.List;

class PlanetObjectMother {

  public static List<Planet> getPlanets() {
    return List.of(
        new Planet(null, "planet1", "climate", "terrain", 0),
        new Planet(null, "planet2", "climate", "terrain", 0));
  }

  public static RegisterPlanetDto getRegisterPlanetDto() {
    return new RegisterPlanetDto("Tatooine", "climate", "terrain");
  }
}
