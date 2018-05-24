package se.kpod.reversi.ai;

import java.util.List;

import se.kpod.reversi.domain.Board;
import se.kpod.reversi.domain.Player;
import se.kpod.reversi.domain.Board.Color;
import se.kpod.reversi.domain.Tuple;

public class Maximizer extends Player{

	private Color myColor;

	public Maximizer(Color myColor) {
		this.myColor = myColor;
	}
	
	@Override
	public String nextMove(Board board) {
		List<Tuple<Integer, Integer>> possibleMoves = board.getPossibleMoves(myColor);
		return possibleMoves.stream()
				.reduce((moveOne, moveTwo) -> board.getChanges(moveOne, myColor).size() > board.getChanges(moveTwo, myColor).size() ? moveOne: moveTwo)
				.map(Board::tupleToNotation)
				.orElseThrow(() -> new RuntimeException("Could not find a move"));
	}

}
