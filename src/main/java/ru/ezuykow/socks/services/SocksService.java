package ru.ezuykow.socks.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.ezuykow.socks.dto.IncomeSocksDto;
import ru.ezuykow.socks.entities.Socks;
import ru.ezuykow.socks.enums.Operation;
import ru.ezuykow.socks.repositories.SocksRepository;

import java.util.Optional;

/**
 * @author ezuykow
 */
@Service
@RequiredArgsConstructor
@Slf4j
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
            log.error("Incorrect parameter \"operation\"!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public ResponseEntity<?> addSocks(IncomeSocksDto incomeSocksDto) {
        if (IsIncomeRequestDataIncorrect(incomeSocksDto)) {
            log.error("Incorrect income data!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        createNewPositionOrIncreaseExisted(incomeSocksDto);
        return ResponseEntity.ok().build();
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

    private boolean IsIncomeRequestDataIncorrect(IncomeSocksDto incomeSocksDto){
        return incomeSocksDto.getColor().isBlank()
                || incomeSocksDto.getCottonPart() < 0
                || incomeSocksDto.getCottonPart() > 100
                || incomeSocksDto.getQuantity() < 1;
    }

    private void createNewPositionOrIncreaseExisted(IncomeSocksDto incomeSocksDto) {
        Optional<Socks> socksOpt =
                socksRepository.findByColorAndCottonPart(incomeSocksDto.getColor().toUpperCase(),
                        incomeSocksDto.getCottonPart());
        socksOpt.ifPresentOrElse(s -> {
                    s.setQuantity(s.getQuantity() + incomeSocksDto.getQuantity());
                    socksRepository.save(s);
                },
                () -> socksRepository.save(
                        new Socks(incomeSocksDto.getColor().toUpperCase(),
                                incomeSocksDto.getQuantity(), incomeSocksDto.getCottonPart())));
    }
}
