package ru.ezuykow.socks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ezuykow.socks.entities.Socks;

import java.util.Optional;

/**
 * @author ezuykow
 */
@Repository
public interface SocksRepository extends JpaRepository<Socks, Integer> {

    Integer countAllByColorAndCottonPartEquals(String color, int cottonPart);

    Integer countAllByColorAndCottonPartAfter(String color, int cottonPart);

    Integer countAllByColorAndCottonPartLessThan(String color, int cottonPart);

    Optional<Socks> findByColorAndCottonPart(String color, int cottonPart);
}
