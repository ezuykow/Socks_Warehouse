package ru.ezuykow.socks.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ezuykow.socks.dto.IncomeSocksDto;
import ru.ezuykow.socks.services.SocksService;

/**
 * @author ezuykow
 */
@RestController
@RequestMapping("/api/socks")
@RequiredArgsConstructor
public class SocksController {

    private final SocksService socksService;

    //-----------------API START-----------------

    @GetMapping("")
    public ResponseEntity<String> socksCountByCriteria(
            @RequestParam("color") String color,
            @RequestParam("operation") String operation,
            @RequestParam("cottonPart") int cottonPart)
    {
            return socksService.findSocksCountByCriteria(color, operation, cottonPart);
    }

    @PostMapping("/income")
    public ResponseEntity<?> addSocks(@RequestBody IncomeSocksDto incomeSocksDto) {
        return socksService.addSocks(incomeSocksDto);
    }

    @PostMapping("/outcome")
    public ResponseEntity<?> removeSocks(@RequestBody IncomeSocksDto incomeSocksDto) {
        return socksService.removeSocks(incomeSocksDto);
    }

    //-----------------API END-----------------

}
