package se.kpod.reversi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import se.kpod.reversi.ai.MinMaxer;
import se.kpod.reversi.domain.Board;
import se.kpod.reversi.domain.Board.Color;
import se.kpod.reversi.domain.Game;
import se.kpod.reversi.domain.Tuple;

@Controller
@EnableAutoConfiguration
public class GameController {

	@Autowired
	Board board;
	@Autowired
	Game game;

	@Autowired
	MinMaxer minMaxer;

	@RequestMapping("/board")
	@ResponseBody
	int[][] home() {
		return board.getCells();
	}

	@PostMapping("/place")
	@ResponseBody
	boolean place(@RequestParam int x, @RequestParam int y) {
		boolean valid = board.place(new Tuple<Integer, Integer>(x, y));
		board.printBoard();

		if (valid) {
			while (board.getTurn() == Color.WHITE) {
				String move = minMaxer.nextMove(board);
				board.place(move);
				board.printBoard();
			}
		}

		return valid;
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(GameController.class, args);
	}
}
