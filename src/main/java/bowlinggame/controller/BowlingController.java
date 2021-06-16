package bowlinggame.controller;

import bowlinggame.dto.Game;
import bowlinggame.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BowlingController {

    @Autowired
    GameService gameService;

    public Game game;

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public void startANewGame() {
        System.out.println("--A game of bowling--");
        game = new Game();
        gameService.startANewGame(game);
    }

    @RequestMapping(value = "/scoreboard", method = RequestMethod.GET)
    public ResponseEntity getScoreboard() {
        return ResponseEntity.ok(game);
    }
}
