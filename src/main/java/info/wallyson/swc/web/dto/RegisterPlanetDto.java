package info.wallyson.swc.web.dto;

import javax.validation.constraints.NotBlank;

public class RegisterPlanetDto {
    @NotBlank private String name;
    @NotBlank private String climate;
    @NotBlank private String terrain;

    public RegisterPlanetDto(@NotBlank String name, @NotBlank String climate, @NotBlank String terrain) {
        this.name = name;
        this.climate = climate;
        this.terrain = terrain;
    }

    public String getName() {
        return name;
    }

    public String getClimate() {
        return climate;
    }

    public String getTerrain() {
        return terrain;
    }
}
