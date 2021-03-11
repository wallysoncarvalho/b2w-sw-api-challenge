package info.wallyson.swc.web;

import info.wallyson.swc.exception.ApiException;
import info.wallyson.swc.mappers.PageResponseMapper;
import info.wallyson.swc.mappers.PlanetMapper;
import info.wallyson.swc.planet.Planet;
import info.wallyson.swc.planet.PlanetComponent;
import info.wallyson.swc.web.dto.PageResponse;
import info.wallyson.swc.web.dto.RegisterPlanetDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.net.URI;

@Validated
@RestController
@RequestMapping(value = "/api/planet", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlanetController {
  private final PlanetComponent planetComponent;

  public PlanetController(PlanetComponent planetComponent) {
    this.planetComponent = planetComponent;
  }

  @GetMapping
  public ResponseEntity<PageResponse<Planet>> getAllPlanets(
      @RequestParam(defaultValue = "1") @Min(1) int page,
      @RequestParam(defaultValue = "5") @Min(5) @Max(20) int pageSize) {

    var pageableResult = planetComponent.getAllPlanetsPaginated(page-1, pageSize);

    return ResponseEntity.ok(PageResponseMapper.toPageResponse(pageableResult));
  }

  @PostMapping
  public ResponseEntity<?> registerPlanet(@RequestBody @Valid RegisterPlanetDto registerPlanetDto) {
    var planet = PlanetMapper.toPlanet(registerPlanetDto);

    var planetExists = planetComponent.getPlanetByName(registerPlanetDto.getName()).isPresent();

    if (planetExists)
      throw new ApiException(
          "Planet with name '" + registerPlanetDto.getName() + "' already exists!");

    var registeredPlanet = planetComponent.registerPlanet(planet);

    return ResponseEntity.created(getResourceURI(registeredPlanet.getId())).build();
  }

  private URI getResourceURI(String id) {
    return ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(id)
        .toUri();
  }

  @GetMapping("{id}")
  public ResponseEntity<Planet> getPlanetByID(@PathVariable String id) {
    var planetOptional = planetComponent.getPlanetByID(id);

    var planet =
        planetOptional.orElseThrow(
            () -> new ApiException("Planet with id '" + id + "' not found!"));

    return ResponseEntity.ok(planet);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> deletePlanetByID(@PathVariable String id) {
    planetComponent.removePlanetByID(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("name/{name}")
  public ResponseEntity<Planet> getPlanetByName(@PathVariable String name) {
    var planet =
        planetComponent
            .getPlanetByName(name)
            .orElseThrow(() -> new ApiException("Planet with name '" + name + "' not found!"));
    return ResponseEntity.ok(planet);
  }
}
