package se.kpod.reversi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import se.kpod.reversi.ai.MinMaxer;
import se.kpod.reversi.domain.Board;
import se.kpod.reversi.domain.GameState;
import se.kpod.reversi.domain.Tuple;

@Controller
@EnableAutoConfiguration
public class GameController {

	@Autowired
	Board board;

	@RequestMapping("/board")
	@ResponseBody
	int[][] board() {
		return board.getState().getBoard();
	}

	@PostMapping("/reset")
	@ResponseBody
	void reset() {
		board.init();
	}

	@PostMapping("/resetTo")
	@ResponseBody
	void resetTo(@RequestParam int place) {
		List<String> moves = board.getState().getMoveHistory();
		if (place < 0 || place > moves.size()) {
			// Out of range
			return;
		}
		board.init();

		for (String move : moves.subList(0, place)) {
			board.place(move);
			board.getState().addMoveToHistory(move);
		}
	}

	@RequestMapping("/state")
	@ResponseBody
	GameState state() {
		return board.getState();
	}

	@PostMapping("/place")
	@ResponseBody
	GameState place(@RequestParam int x, @RequestParam int y) {
		Tuple<Integer, Integer> move = new Tuple<Integer, Integer>(x, y);
		boolean valid = board.place(move);
		board.printBoard();

		GameState state = board.getState();

		if (valid) {
			state.addMoveToHistory(Board.tupleToNotation(move));
		}

		return state;
	}

	@GetMapping("/move")
	@ResponseBody
	GameState move() {
		GameState state = board.getState();
		if (state.isGameOver()) {
			return state;
		}

		MinMaxer minMaxer = new MinMaxer(state.getCurrentTurn());

		String move = minMaxer.nextMove(board);
		board.place(move);
		board.printBoard();

		state = board.getState();
		state.addMoveToHistory(move);

		return state;
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(GameController.class, args);
	}
}
