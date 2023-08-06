package ru.ezuykow.socks.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.ezuykow.socks.dto.SocksTransferDto;
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

    public ResponseEntity<?> addSocks(SocksTransferDto incomeSocksDto) {
        if (IsTransferRequestDataIncorrect(incomeSocksDto)) {
            log.error("Incorrect income data!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        createNewPositionOrIncreaseExisted(incomeSocksDto);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> removeSocks(SocksTransferDto outcomeSocksDto) {
        if (IsTransferRequestDataIncorrect(outcomeSocksDto)) {
            log.error("Incorrect income data!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return removeSocksIfExist(outcomeSocksDto);
    }

    //-----------------API END-----------------

    private Integer findSocksCountByCriteriaAndOperation(
            String targetColor, Operation targetOperation, int targetCottonPart)
    {
        Integer result = 0;
        switch (targetOperation) {
            case EQUAL -> result = socksRepository.socksCountByColorAndCottonPartEquals(
                    targetColor, targetCottonPart);
            case MORETHAN -> result = socksRepository.socksCountByColorAndCottonPartMoreThan(
                    targetColor, targetCottonPart);
            case LESSTHAN -> result = socksRepository.socksCountByColorAndCottonPartLessThan(
                    targetColor, targetCottonPart);
        }
        return result;
    }

    private boolean IsTransferRequestDataIncorrect(SocksTransferDto socksTransferDto){
        return socksTransferDto.getColor().isBlank()
                || socksTransferDto.getCottonPart() < 0
                || socksTransferDto.getCottonPart() > 100
                || socksTransferDto.getQuantity() < 1;
    }

    private void createNewPositionOrIncreaseExisted(SocksTransferDto incomeSocksDto) {
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

    private ResponseEntity<?> removeSocksIfExist(SocksTransferDto outcomeSocksDto) {
        Optional<Socks> socksOpt =
                socksRepository.findByColorAndCottonPart(outcomeSocksDto.getColor().toUpperCase(),
                        outcomeSocksDto.getCottonPart());

        if (socksOpt.isPresent() && (socksOpt.get().getQuantity() >= outcomeSocksDto.getQuantity())) {
            Socks targetSocks = socksOpt.get();
            targetSocks.setQuantity(targetSocks.getQuantity() - outcomeSocksDto.getQuantity());
            checkSocksQuantity(targetSocks);
            return ResponseEntity.ok().build();
        } else {
            log.error("Outcome request error!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    private void checkSocksQuantity(Socks targetSocks) {
        if (targetSocks.getQuantity() == 0) {
            socksRepository.delete(targetSocks);
        } else {
            socksRepository.save(targetSocks);
        }
    }
}
