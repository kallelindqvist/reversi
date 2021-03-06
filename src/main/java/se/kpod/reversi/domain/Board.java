package se.kpod.reversi.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Board {

	public enum Color {
		BLANK(0), BLACK(1), WHITE(2), POSSIBLEMOVE(3);

		private int value;

		Color(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}

	private static int BOARD_SIZE = 8;

	private GameState gameState;

	private Color lastTurn = Color.BLACK;
	private int[][] boardBeforeMove;

	public Board() {
		init();

		printBoard();
	}

	public void init() {
		gameState = new GameState();

		gameState.setCurrentlyPossibleMoves(getPossibleMoves(gameState.getCurrentTurn()));
	}

	public void printBoard() {
		System.out.println("  A B C D E F G H");
		for (int i = 0; i < gameState.getBoard().length; i++) {
			System.out.print((i + 1) + " ");
			for (int j = 0; j < gameState.getBoard().length; j++) {
				if (gameState.getBoard()[i][j] == Color.BLACK.value) {
					System.out.print("● ");
				} else if (gameState.getBoard()[i][j] == Color.WHITE.value) {
					System.out.print("○ ");
				} else {
					System.out.print("  ");
				}
			}
			System.out.println(i);
		}
		System.out.println("  A B C D E F G H ");
	}

	public static void main() {
		Board b = new Board();
		b.printBoard();
	}

	public int getScore(Color scoreColor) {
		return Arrays.stream(gameState.getBoard()).flatMapToInt(Arrays::stream).mapToObj(i -> Color.values()[i])
				.filter(color -> color.equals(scoreColor)).mapToInt(e -> 1).sum();
	}

	public Color current(Tuple<Integer, Integer> pos) {
		return Color.values()[gameState.getBoard()[pos.y][pos.x]];
	}

	public boolean place(String notation) {
		return place(notationToPosition(notation));
	}

	public boolean place(Tuple<Integer, Integer> pos) {
		if (isLegalMove(pos, gameState.getCurrentTurn())) {
			boardBeforeMove = deepCopy();
			List<Tuple<Integer, Integer>> turns = getChanges(pos, gameState.getCurrentTurn());
			gameState.getBoard()[pos.y][pos.x] = gameState.getCurrentTurn().getValue();

			for (Tuple<Integer, Integer> t : turns) {
				gameState.getBoard()[t.y][t.x] = gameState.getCurrentTurn().value;
			}

			lastTurn = gameState.getCurrentTurn();
			List<Tuple<Integer, Integer>> blackMoves = getPossibleMoves(Color.BLACK);
			List<Tuple<Integer, Integer>> whiteMoves = getPossibleMoves(Color.WHITE);

			if (this.gameState.getCurrentTurn().equals(Color.WHITE)) {
				if (!blackMoves.isEmpty()) {
					this.gameState.setCurrentTurn(Color.BLACK);
					this.gameState.setCurrentlyPossibleMoves(blackMoves);
				}
			} else {
				if (!whiteMoves.isEmpty()) {
					this.gameState.setCurrentTurn(Color.WHITE);
					this.gameState.setCurrentlyPossibleMoves(whiteMoves);
				}
			}

			if (blackMoves.isEmpty() && whiteMoves.isEmpty()) {
				gameState.setGameOver(true);
			}
			gameState.setWhiteScore(getScore(Color.WHITE));
			gameState.setBlackScore(getScore(Color.BLACK));

			return true;
		}
		return false;

	}

	public void regret() {
		gameState.setBoard(boardBeforeMove);
		gameState.setCurrentTurn(lastTurn);
	}

	public int[][] deepCopy() {
		int[][] newBoard = new int[BOARD_SIZE][BOARD_SIZE];
		for (int i = 0; i < BOARD_SIZE; i++) {
			newBoard[i] = Arrays.copyOf(gameState.getBoard()[i], BOARD_SIZE);
		}
		return newBoard;
	}

	public boolean isLegalMove(String notation) {
		return !getChanges(notationToPosition(notation), gameState.getCurrentTurn()).isEmpty();
	}

	public boolean isLegalMove(Tuple<Integer, Integer> pos) {
		return !getChanges(pos, gameState.getCurrentTurn()).isEmpty();
	}

	public boolean isLegalMove(Tuple<Integer, Integer> pos, Color turn) {
		return !getChanges(pos, turn).isEmpty();
	}

	public static Tuple<Integer, Integer> notationToPosition(String notation) {
		if (!notation.matches("[a-h][1-8]")) {
			throw new RuntimeException("Invalid");
		}
		int x = notation.charAt(0) - 97;
		int y = notation.charAt(1) - 49;

		return new Tuple<Integer, Integer>(x, y);
	}

	public static String tupleToNotation(Tuple<Integer, Integer> tuple) {
		return new StringBuilder().append((char) (tuple.x + 97)).append((char) (tuple.y + 49)).toString();
	}

	public List<Tuple<Integer, Integer>> getChanges(Tuple<Integer, Integer> pos, Color turn) {
		if (!current(pos).equals(Color.BLANK)) {
			return Collections.emptyList();
		}
		Color opponant = Color.WHITE;
		if (turn.equals(Color.WHITE)) {
			opponant = Color.BLACK;
		}

		List<Tuple<Integer, Integer>> turns = new ArrayList<>();

		turns.addAll(checkLeft(pos, opponant));

		turns.addAll(checkRight(pos, opponant));

		turns.addAll(checkDown(pos, opponant));

		turns.addAll(checkUp(pos, opponant));

		// Diagonal top left
		turns.addAll(checkDiagonalUpLeft(pos, opponant));

		// Diagonal top right
		turns.addAll(checkDiagonalUpRight(pos, opponant));

		// Diagonal bottom left
		turns.addAll(checkDiagonalDownLeft(pos, opponant));

		// Diagonal bottom right
		turns.addAll(checkDiagonalDownRight(pos, opponant));
		return turns;
	}

	public GameState getState() {
		return gameState;
	}

	private Collection<? extends Tuple<Integer, Integer>> checkDiagonalUpLeft(Tuple<Integer, Integer> pos,
			Color opponant) {
		List<Tuple<Integer, Integer>> turns = new ArrayList<>();
		for (int i = pos.x - 1, j = pos.y - 1; i >= 0 && j >= 0; i--, j--) {
			Tuple<Integer, Integer> checkPos = new Tuple<>(i, j);
			if (current(checkPos).equals(opponant)) {
				turns.add(checkPos);
			} else if (!current(checkPos).equals(Color.BLANK)) {
				if (!turns.isEmpty()) {
					return turns;
				}
				break;
			} else {
				return Collections.emptyList();
			}
		}
		return Collections.emptyList();
	}

	private Collection<? extends Tuple<Integer, Integer>> checkDiagonalUpRight(Tuple<Integer, Integer> pos,
			Color opponant) {
		List<Tuple<Integer, Integer>> turns = new ArrayList<>();
		for (int i = pos.x + 1, j = pos.y - 1; i < BOARD_SIZE && j >= 0; i++, j--) {
			Tuple<Integer, Integer> checkPos = new Tuple<>(i, j);
			if (current(checkPos).equals(opponant)) {
				turns.add(checkPos);
			} else if (!current(checkPos).equals(Color.BLANK)) {
				if (!turns.isEmpty()) {
					return turns;
				}
				break;
			} else {
				return Collections.emptyList();
			}
		}
		return Collections.emptyList();
	}

	private Collection<? extends Tuple<Integer, Integer>> checkDiagonalDownLeft(Tuple<Integer, Integer> pos,
			Color opponant) {

		List<Tuple<Integer, Integer>> turns = new ArrayList<>();
		for (int i = pos.x - 1, j = pos.y + 1; i > 0 && j < BOARD_SIZE; i--, j++) {
			Tuple<Integer, Integer> checkPos = new Tuple<>(i, j);
			if (current(checkPos).equals(opponant)) {
				turns.add(checkPos);
			} else if (!current(checkPos).equals(Color.BLANK)) {
				if (!turns.isEmpty()) {
					return turns;
				}
				break;
			} else {
				return Collections.emptyList();
			}
		}
		return Collections.emptyList();

	}

	private Collection<? extends Tuple<Integer, Integer>> checkDiagonalDownRight(Tuple<Integer, Integer> pos,
			Color opponant) {
		List<Tuple<Integer, Integer>> turns = new ArrayList<>();
		for (int i = pos.x + 1, j = pos.y + 1; i < BOARD_SIZE && j < BOARD_SIZE; i++, j++) {
			Tuple<Integer, Integer> checkPos = new Tuple<>(i, j);
			if (current(checkPos).equals(opponant)) {
				turns.add(checkPos);
			} else if (!current(checkPos).equals(Color.BLANK)) {
				if (!turns.isEmpty()) {
					return turns;
				}
				break;
			} else {
				return Collections.emptyList();
			}
		}
		return Collections.emptyList();
	}

	private List<Tuple<Integer, Integer>> checkRight(Tuple<Integer, Integer> pos, Color opponant) {
		// Row right of
		List<Tuple<Integer, Integer>> turns = new ArrayList<>();
		for (int i = pos.x + 1; i < BOARD_SIZE; i++) {
			Tuple<Integer, Integer> checkPos = new Tuple<>(i, pos.y);
			if (current(checkPos).equals(opponant)) {
				turns.add(checkPos);
			} else if (!current(checkPos).equals(Color.BLANK)) {
				if (!turns.isEmpty()) {
					return turns;
				}
				break;
			} else {
				return Collections.emptyList();
			}
		}
		return Collections.emptyList();
	}

	private List<Tuple<Integer, Integer>> checkLeft(Tuple<Integer, Integer> pos, Color opponant) {
		// Row left of
		List<Tuple<Integer, Integer>> turns = new ArrayList<>();
		for (int i = pos.x - 1; i >= 0; i--) {
			Tuple<Integer, Integer> checkPos = new Tuple<>(i, pos.y);
			if (current(checkPos).equals(opponant)) {
				turns.add(checkPos);
			} else if (!current(checkPos).equals(Color.BLANK)) {
				if (!turns.isEmpty()) {
					return turns;
				}
				break;
			} else {
				return Collections.emptyList();
			}
		}
		return Collections.emptyList();
	}

	private List<Tuple<Integer, Integer>> checkUp(Tuple<Integer, Integer> pos, Color opponant) {
		// Row left of
		List<Tuple<Integer, Integer>> turns = new ArrayList<>();
		for (int i = pos.y - 1; i >= 0; i--) {
			Tuple<Integer, Integer> checkPos = new Tuple<>(pos.x, i);
			if (current(checkPos).equals(opponant)) {
				turns.add(checkPos);
			} else if (!current(checkPos).equals(Color.BLANK)) {
				if (!turns.isEmpty()) {
					return turns;
				}
				break;
			} else {
				return Collections.emptyList();
			}
		}
		return Collections.emptyList();
	}

	private List<Tuple<Integer, Integer>> checkDown(Tuple<Integer, Integer> pos, Color opponant) {
		// Row left of
		List<Tuple<Integer, Integer>> turns = new ArrayList<>();
		for (int i = pos.y + 1; i < BOARD_SIZE; i++) {
			Tuple<Integer, Integer> checkPos = new Tuple<>(pos.x, i);
			if (current(checkPos).equals(opponant)) {
				turns.add(checkPos);
			} else if (current(checkPos) != Color.BLANK) {
				if (!turns.isEmpty()) {
					return turns;
				}
				break;
			} else {
				return Collections.emptyList();
			}
		}
		return Collections.emptyList();
	}

	public List<Tuple<Integer, Integer>> getPossibleMoves(Color turn) {
		List<Tuple<Integer, Integer>> openPositions = new ArrayList<>();
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				Tuple<Integer, Integer> checkPos = new Tuple<>(i, j);
				if (current(checkPos).equals(Color.BLANK) && isLegalMove(checkPos, turn)) {
					openPositions.add(checkPos);
				}
			}
		}
		return openPositions;
	}
}
