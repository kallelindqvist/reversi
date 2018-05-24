package se.kpod.reversi.ai;

import java.util.List;

import se.kpod.reversi.domain.Board;
import se.kpod.reversi.domain.Player;
import se.kpod.reversi.domain.Board.Color;
import se.kpod.reversi.domain.Tuple;

public class Minimizer extends Player{

	private Color myColor;
	private Color opponent;

	public Minimizer(Color myColor) {
		this.myColor = myColor;
		if(myColor.equals(Color.BLACK)) {
			opponent = Color.WHITE;
		} else {
			opponent = Color.BLACK;
		}
	}
	
	@Override
	public String nextMove(Board board) {
		List<Tuple<Integer, Integer>> possibleMoves = board.getPossibleMoves(myColor);

		int bestMoveValue = 64;
		Tuple<Integer, Integer> bestMove = possibleMoves.get(0);
		for(Tuple<Integer, Integer> move: possibleMoves) {
			board.place(move);
			
			int moveValue = board.getPossibleMoves(opponent).stream().map(pos -> board.getChanges(pos, opponent).size()).reduce(Integer::max).orElse(0);
			if(moveValue < bestMoveValue) {
				bestMoveValue = moveValue;
				bestMove = move;
			}
			board.regret();
		}
		return Board.tupleToNotation(bestMove);
	}

}
