package info.wallyson.swc.planet;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@TypeAlias("Planet")
@Document(collection = "star-wars")
public class Planet {
  @Id private String id;

  @Indexed(unique = true, direction = IndexDirection.ASCENDING)
  @NotBlank
  private String name;

  @NotBlank private String climate;
  @NotBlank private String terrain;

  private Integer movieAppearances;

  public Planet() {}

  public Planet(String id, String name, String climate, String terrain, Integer movieAppearances) {
    this.id = id;
    this.name = name;
    this.climate = climate;
    this.terrain = terrain;
    this.movieAppearances = movieAppearances;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getClimate() {
    return climate;
  }

  public void setClimate(String climate) {
    this.climate = climate;
  }

  public String getTerrain() {
    return terrain;
  }

  public void setTerrain(String terrain) {
    this.terrain = terrain;
  }

  public Integer getMovieAppearances() {
    return movieAppearances;
  }

  public void setMovieAppearances(Integer movieAppearances) {
    this.movieAppearances = movieAppearances;
  }

  @Override
  public String toString() {
    return "Planet{"
        + "id='"
        + id
        + '\''
        + ", name='"
        + name
        + '\''
        + ", climate='"
        + climate
        + '\''
        + ", terrain='"
        + terrain
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Planet planet = (Planet) o;
    return id.equals(planet.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
