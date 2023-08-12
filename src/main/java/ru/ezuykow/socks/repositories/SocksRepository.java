package ru.ezuykow.socks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ezuykow.socks.entities.Socks;

import java.util.Optional;

/**
 * @author ezuykow
 */
@Repository
public interface SocksRepository extends JpaRepository<Socks, Integer> {

    @Query("SELECT IFNULL(SUM(s.quantity), 0) FROM Socks s WHERE s.color = :color AND s.cottonPart = :cottonPart")
    Integer socksCountByColorAndCottonPartEquals(String color, int cottonPart);

    @Query("SELECT IFNULL(SUM(s.quantity), 0) FROM Socks s WHERE s.color = :color AND s.cottonPart > :cottonPart")
    Integer socksCountByColorAndCottonPartMoreThan(String color, int cottonPart);

    @Query("SELECT IFNULL(SUM(s.quantity), 0) FROM Socks s WHERE s.color = :color AND s.cottonPart < :cottonPart")
    Integer socksCountByColorAndCottonPartLessThan(String color, int cottonPart);

    Optional<Socks> findByColorAndCottonPart(String color, int cottonPart);
}
