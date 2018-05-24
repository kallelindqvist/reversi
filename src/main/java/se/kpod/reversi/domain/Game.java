package se.kpod.reversi.domain;

import java.util.ArrayList;
import java.util.List;

import se.kpod.reversi.ai.MinMaxer;
import se.kpod.reversi.ai.Minimizer;
import se.kpod.reversi.domain.Board.Color;

public class Game {

	private static Player currentTurn;
	private static Board board;
	private static Player blackPlayer;
	private static Player whitePlayer;

	public static void main(String[] args) {
		board = new Board();
		whitePlayer = new Minimizer(Color.WHITE);
		blackPlayer = new MinMaxer(Color.BLACK);

		currentTurn = blackPlayer;
		loop();
	}

	public static void loop() {
		List<String> moves = new ArrayList<>();
		while (!board.getPossibleMoves(Color.BLACK).isEmpty() || !board.getPossibleMoves(Color.WHITE).isEmpty()) {
			if (board.getState().getCurrentTurn().equals(Color.BLACK)) {
				currentTurn = blackPlayer;
			} else {
				currentTurn = whitePlayer;
			}
			board.printBoard();
			System.out.println(String.format("%s players turn", board.getState().getCurrentTurn()));
			String move = currentTurn.nextMove(board);
			moves.add(move);
			board.place(move);
		}

		int blackPoints = board.getScore(Color.BLACK);
		int whitePoints = board.getScore(Color.WHITE);
		board.printBoard();
		System.out.println(String.format("Black player: %d, White plauer: %d. %s player wins", blackPoints, whitePoints,
				blackPoints > whitePoints ? "Black" : "White"));
		moves.forEach(move -> System.out.print(move + " "));
	}
}
