package info.wallyson.swc.planet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface PlanetRepository extends MongoRepository<Planet, String> {
    Optional<Planet> getPlanetByName(String name);
    Page<Planet> findAll(Pageable pageable);
}
