package ru.ezuykow.socks.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ezuykow.socks.services.SocksService;

/**
 * @author ezuykow
 */
@RestController
@RequestMapping("/api/socks")
@Slf4j
@RequiredArgsConstructor
public class SocksController {

    private final SocksService socksService;

    @GetMapping("")
    public ResponseEntity<String> socksCountByCriteria(
            @RequestParam("color") String color,
            @RequestParam("operation") String operation,
            @RequestParam("cottonPart") int cottonPart)
    {
            return socksService.findSocksCountByCriteria(color, operation, cottonPart);
    }

}
