package info.wallyson.swc.mappers;

import info.wallyson.swc.planet.Planet;
import info.wallyson.swc.web.dto.RegisterPlanetDto;

public class PlanetMapper {

  public static Planet toPlanet(RegisterPlanetDto registerPlanetDto) {
    return new Planet(
        null,
        registerPlanetDto.getName(),
        registerPlanetDto.getClimate(),
        registerPlanetDto.getTerrain(), 0);
  }
}
