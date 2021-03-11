package info.wallyson.swc.planet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
class SwapiPlanetResponse {
    private String name;
    private List<String> films;

    public int getFilmsCount() {
        return  films.size();
    }

    public String getName() {
        return name;
    }

    public List<String> getFilms() {
        return films;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFilms(List<String> films) {
        this.films = films;
    }
}
