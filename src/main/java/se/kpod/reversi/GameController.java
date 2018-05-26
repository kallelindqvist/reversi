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
import se.kpod.reversi.domain.GameState;
import se.kpod.reversi.domain.Tuple;

@Controller
@EnableAutoConfiguration
public class GameController {

	@Autowired
	Board board;

	@Autowired
	MinMaxer minMaxer;

	@RequestMapping("/board")
	@ResponseBody
	int[][] board() {
		return board.getCells();
	}

	@PostMapping("/reset")
	@ResponseBody
	void reset() {
		board.init();
	}

	@RequestMapping("/state")
	@ResponseBody
	GameState state() {
		return board.getState();
	}

	@PostMapping("/place")
	@ResponseBody
	GameState place(@RequestParam int x, @RequestParam int y) {
		boolean valid = board.place(new Tuple<Integer, Integer>(x, y));
		board.printBoard();

		if (valid) {
			while (!board.getState().isGameOver() && board.getState().getCurrentTurn() == Color.BLACK) {
				String move = minMaxer.nextMove(board);
				board.place(move);
				board.printBoard();
			}
		}

		return board.getState();
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(GameController.class, args);
	}
}
