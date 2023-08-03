package ru.ezuykow.socks.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.ezuykow.socks.enums.Operation;
import ru.ezuykow.socks.repositories.SocksRepository;

/**
 * @author ezuykow
 */
@Service
@RequiredArgsConstructor
public class SocksService {

    private final SocksRepository socksRepository;

    //-----------------API START-----------------

    public ResponseEntity<String> findSocksCountByCriteria(
            String paramColor, String paramOperation, int paramCottonPart)
    {
        try {
            return ResponseEntity.ok(
                    findSocksCountByCriteriaAndOperation(paramColor.toUpperCase(),
                            Operation.valueOf(paramOperation.toUpperCase()),
                            paramCottonPart).toString());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //-----------------API END-----------------

    private Integer findSocksCountByCriteriaAndOperation(
            String targetColor, Operation targetOperation, int targetCottonPart)
    {
        Integer result = 0;
        switch (targetOperation) {
            case EQUAL -> result = socksRepository.countAllByColorAndCottonPartEquals(
                    targetColor, targetCottonPart);
            case MORETHAN -> result = socksRepository.countAllByColorAndCottonPartAfter(
                    targetColor, targetCottonPart);
            case LESSTHAN -> result = socksRepository.countAllByColorAndCottonPartLessThan(
                    targetColor, targetCottonPart);
        }
        return result;
    }
}
