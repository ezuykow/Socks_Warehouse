package ru.ezuykow.socks.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.ezuykow.socks.dto.SocksTransferDto;
import ru.ezuykow.socks.entities.Socks;
import ru.ezuykow.socks.repositories.SocksRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author ezuykow
 */
@ExtendWith(MockitoExtension.class)
class SocksServiceTest {

    @Mock
    private SocksRepository socksRepository;

    @InjectMocks
    private SocksService service;

    @Test
    public void findSocksCountByCriteriaShouldReturnBadRequestIfOperationIncorrect() {
        ResponseEntity<?> result = service.findSocksCountByCriteria("color", "INCORRECT!!!", 10);

        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void findSocksCountByCriteriaShouldCallRepoMethAndReturnOkWhenOperationIsEqual() {
        Integer testQuantity = 10;
        String testColor = "c";
        int testCottonPart = 50;
        String testingOperation = "equal";

        when(socksRepository.socksCountByColorAndCottonPartEquals(testColor.toUpperCase(), testCottonPart))
                .thenReturn(testQuantity);

        ResponseEntity<?> result = service.findSocksCountByCriteria(testColor, testingOperation, testCottonPart);

        verify(socksRepository, only()).socksCountByColorAndCottonPartEquals(testColor.toUpperCase(), testCottonPart);
        assertEquals(result.getBody(), testQuantity.toString());
    }

    @Test
    public void findSocksCountByCriteriaShouldCallRepoMethAndReturnOkWhenOperationIsMoreThan() {
        Integer testQuantity = 10;
        String testColor = "c";
        int testCottonPart = 50;
        String testingOperation = "moreThan";

        when(socksRepository.socksCountByColorAndCottonPartMoreThan(testColor.toUpperCase(), testCottonPart))
                .thenReturn(testQuantity);

        ResponseEntity<?> result = service.findSocksCountByCriteria(testColor, testingOperation, testCottonPart);

        verify(socksRepository, only()).socksCountByColorAndCottonPartMoreThan(testColor.toUpperCase(), testCottonPart);
        assertEquals(result.getBody(), testQuantity.toString());
    }

    @Test
    public void findSocksCountByCriteriaShouldCallRepoMethAndReturnOkWhenOperationIsLessThan() {
        Integer testQuantity = 10;
        String testColor = "c";
        int testCottonPart = 50;
        String testingOperation = "lessThan";

        when(socksRepository.socksCountByColorAndCottonPartLessThan(testColor.toUpperCase(), testCottonPart))
                .thenReturn(testQuantity);

        ResponseEntity<?> result = service.findSocksCountByCriteria(testColor, testingOperation, testCottonPart);

        verify(socksRepository, only()).socksCountByColorAndCottonPartLessThan(testColor.toUpperCase(), testCottonPart);
        assertEquals(result.getBody(), testQuantity.toString());
    }

    @Test
    public void addSocksShouldReturnBadRequestIfTransferDataIsIncorrect() {
        SocksTransferDto testDTO = new SocksTransferDto();
        testDTO.setColor("c");
        testDTO.setCottonPart(50);
        testDTO.setQuantity(-2);

        ResponseEntity<?> result = service.addSocks(testDTO);

        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void addSocksShouldSaveSocksAndReturnOk() {
        SocksTransferDto testDTO = new SocksTransferDto();
        testDTO.setColor("c");
        testDTO.setCottonPart(50);
        testDTO.setQuantity(2);

        when(socksRepository.findByColorAndCottonPart(anyString(), anyInt()))
                .thenReturn(Optional.empty());
        when(socksRepository.save(any())).thenReturn(null);

        ResponseEntity<?> result = service.addSocks(testDTO);

        verify(socksRepository, times(1))
                .findByColorAndCottonPart(anyString(), anyInt());
        verify(socksRepository, times(1)).save(any());
        assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void removeSocksShouldReturnBadRequestIfTransferDataIsIncorrect() {
        SocksTransferDto testDTO = new SocksTransferDto();
        testDTO.setColor("c");
        testDTO.setCottonPart(50);
        testDTO.setQuantity(-2);

        ResponseEntity<?> result = service.removeSocks(testDTO);

        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void removeSocksShouldReturnBadRequestIfSocksNotExisted() {
        SocksTransferDto testDTO = new SocksTransferDto();
        testDTO.setColor("c");
        testDTO.setCottonPart(50);
        testDTO.setQuantity(2);

        when(socksRepository.findByColorAndCottonPart(anyString(), anyInt()))
                .thenReturn(Optional.empty());

        ResponseEntity<?> result = service.removeSocks(testDTO);

        verify(socksRepository, times(1))
                .findByColorAndCottonPart(anyString(), anyInt());
        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void removeSocksShouldSaveSocksAndReturnOk() {
        SocksTransferDto testDTO = new SocksTransferDto();
        testDTO.setColor("c");
        testDTO.setCottonPart(50);
        testDTO.setQuantity(2);

        Socks testSocks = new Socks("C", 10, 50);

        when(socksRepository.findByColorAndCottonPart(anyString(), anyInt()))
                .thenReturn(Optional.of(testSocks));
        when(socksRepository.save(any())).thenReturn(null);

        ResponseEntity<?> result = service.removeSocks(testDTO);

        verify(socksRepository, times(1))
                .findByColorAndCottonPart(anyString(), anyInt());
        verify(socksRepository, times(1)).save(any());
        assertEquals(result.getStatusCode(), HttpStatus.OK);
    }
}