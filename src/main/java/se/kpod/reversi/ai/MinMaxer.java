package se.kpod.reversi.ai;

import java.util.List;

import org.springframework.stereotype.Component;

import se.kpod.reversi.domain.Board;
import se.kpod.reversi.domain.Board.Color;
import se.kpod.reversi.domain.Player;
import se.kpod.reversi.domain.Tuple;

@Component
public class MinMaxer extends Player {

	private Color myColor;
	private Color opponent;

	public MinMaxer() {
		this.myColor = Color.WHITE;
		opponent = Color.BLACK;
	}

	public MinMaxer(Color myColor) {
		this.myColor = myColor;
		if (myColor.equals(Color.BLACK)) {
			opponent = Color.WHITE;
		} else {
			opponent = Color.BLACK;
		}
	}

	@Override
	public String nextMove(Board board) {
		List<Tuple<Integer, Integer>> possibleMoves = board.getPossibleMoves(myColor);

		int bestMoveValue = 0;
		Tuple<Integer, Integer> bestMove = possibleMoves.get(0);
		for (Tuple<Integer, Integer> move : possibleMoves) {
			int points = board.getChanges(move, myColor).size();
			board.place(move);
			int moveValue = board.getPossibleMoves(opponent).stream().map(pos -> board.getChanges(pos, opponent).size())
					.reduce(Integer::max).orElse(0);
			if (points - moveValue > bestMoveValue) {
				bestMoveValue = moveValue;
				bestMove = move;
			}
			board.regret();
		}
		return Board.tupleToNotation(bestMove);
	}

}
