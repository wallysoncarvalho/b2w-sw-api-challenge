package info.wallyson.swc.planet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Service
public class PlanetComponent {
  private final PlanetRepository planetRepository;
  private final PlanetAppearance planetAppearance;

  public PlanetComponent(PlanetRepository planetRepository, PlanetAppearance planetAppearance) {
    this.planetRepository = planetRepository;
    this.planetAppearance = planetAppearance;
  }

  public Planet registerPlanet(Planet planet) {
    var appearanceOnMovies = planetAppearance.getMovieAppearance(planet.getName());

    planet.setId(null);
    planet.setMovieAppearances(appearanceOnMovies);
    return this.planetRepository.save(planet);
  }

  public Optional<Planet> getPlanetByID(@NotBlank String id) {
    return planetRepository.findById(id);
  }

  public void removePlanetByID(@NotBlank String id) {
    planetRepository.deleteById(id);
  }

  public Optional<Planet> getPlanetByName(@NotBlank String name) {
    return planetRepository.getPlanetByName(name);
  }

  public Page<Planet> getAllPlanetsPaginated(int page, int pageSize) {
    var pageDetails = PageRequest.of(page, pageSize, Sort.Direction.ASC, "name");
    return planetRepository.findAll(pageDetails);
  }
}
