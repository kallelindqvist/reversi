package se.kpod.reversi.domain;

import java.util.ArrayList;
import java.util.List;

import se.kpod.reversi.domain.Board.Color;

public class GameState {
	private Color currentTurn = Color.BLACK;
	private int blackScore = 0;
	private int whiteScore = 0;
	private List<Tuple<Integer, Integer>> currentlyPossibleMoves;
	private List<String> moveHistory = new ArrayList<String>();
	private boolean gameOver = false;

	public Color getCurrentTurn() {
		return currentTurn;
	}

	public void setCurrentTurn(Color currentTurn) {
		this.currentTurn = currentTurn;
	}

	public void updateScore(int blackScore, int whiteScore) {
		this.blackScore = blackScore;
		this.whiteScore = whiteScore;
	}

	public int getWhiteScore() {
		return whiteScore;
	}

	public void setWhiteScore(int whiteScore) {
		this.whiteScore = whiteScore;
	}

	public int getBlackScore() {
		return blackScore;
	}

	public void setBlackScore(int blackScore) {
		this.blackScore = blackScore;
	}

	public List<Tuple<Integer, Integer>> getCurrentlyPossibleMoves() {
		return this.currentlyPossibleMoves;
	}

	public void setCurrentlyPossibleMoves(List<Tuple<Integer, Integer>> currentlyPossibleMoves) {
		this.currentlyPossibleMoves = currentlyPossibleMoves;
	}

	public void addMoveToHistory(String move) {
		moveHistory.add(move);
	}

	public List<String> getMoveHistory() {
		return moveHistory;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;

	}

	public boolean isGameOver() {
		return this.gameOver;

	}
}
