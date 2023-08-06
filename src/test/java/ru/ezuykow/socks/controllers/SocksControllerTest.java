package ru.ezuykow.socks.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ezuykow.socks.dto.SocksTransferDto;
import ru.ezuykow.socks.services.SocksService;

import static org.mockito.Mockito.*;

/**
 * @author ezuykow
 */
@ExtendWith(MockitoExtension.class)
class SocksControllerTest {

    @Mock
    private SocksService socksService;

    @InjectMocks
    private SocksController controller;

    @Test
    public void socksCountByCriteriaShouldCallServiceMethod() {
        String testColor = "c";
        String testOperation = "p";
        int testCottonPart = 50;

        when(socksService.findSocksCountByCriteria(
                testColor, testOperation, testCottonPart))
                .thenReturn(null);

        controller.socksCountByCriteria(testColor, testOperation, testCottonPart);

        verify(socksService, times(1)).findSocksCountByCriteria(
                testColor, testOperation, testCottonPart);
    }

    @Test
    public void addSocksShouldCallServiceMethod() {
        SocksTransferDto testSTD = new SocksTransferDto();

        when(socksService.addSocks(testSTD)).thenReturn(null);

        controller.addSocks(testSTD);

        verify(socksService, times(1)).addSocks(testSTD);
    }

    @Test
    public void removeSocksShouldCallServiceMethod() {
        SocksTransferDto testSTD = new SocksTransferDto();

        when(socksService.removeSocks(testSTD)).thenReturn(null);

        controller.removeSocks(testSTD);

        verify(socksService, times(1)).removeSocks(testSTD);
    }
}