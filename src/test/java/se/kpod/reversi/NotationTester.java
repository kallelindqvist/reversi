package se.kpod.reversi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kpod.reversi.domain.Board;
import se.kpod.reversi.domain.Board.Color;
import se.kpod.reversi.domain.Tuple;

class NotationTester {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testSimpleNotation() {
		assertEquals(Board.notationToPosition("a1"), new Tuple<Integer, Integer>(0, 0));
		assertEquals(Board.notationToPosition("h8"), new Tuple<Integer, Integer>(7, 7));
	}

	@Test
	void testPlayWithNotation() {
		Board b = new Board();
		b.place("d3");
		b.printBoard();
		assertEquals(4, b.getScore(Color.BLACK));
		assertEquals(1, b.getScore(Color.WHITE));
	}

	@Test
	void testPlayGameWithNotationAndVerification() {
		String[] moves = new String[] { "c4", "e3", "f4", "c5", "d2", "c3", "b4", "d3", "d6" };
		int[] blackPoints = new int[] { 4, 3, 5, 3, 5, 4, 8, 7, 13 };
		int[] whitePoints = new int[] { 1, 3, 2, 5, 4, 6, 3, 5, 0 };
		Board b = new Board();
		for (int i = 0; i < moves.length; i++) {
			b.place(moves[i]);
			System.out.println("Move " + (i + 1));
			b.printBoard();
			assertEquals(blackPoints[i], b.getScore(Color.BLACK));
			assertEquals(whitePoints[i], b.getScore(Color.WHITE));
		}
	}

	@Test
	void testPlayGameWithNotation() {

		String[] moves = new String[] { "c4", "e3", "f5", "c5", "c6", "d6", "c3", "d3", "e6", "f6", "f3", "f4", "f7",
				"c7", "b8", "b7", "a8", "b6", "a7" };

		Board b = new Board();
		for (int i = 0; i < moves.length; i++) {
			b.place(moves[i]);
		}
		assertEquals(23, b.getScore(Color.BLACK));
		assertEquals(0, b.getScore(Color.WHITE));
	}

	@Test
	void testPlayLongGameWithNotation() {

		String[] moves = new String[] { "c4", "e3", "f6", "e6", "f5", "c5", "f4", "g5", "c6", "g6", "e2", "d2", "f2",
				"c3", "d3", "f3", "g4", "d6", "d7", "e7", "h5", "h6", "e8", "f8", "g3", "f7", "c7", "e1", "f1", "c2",
				"b3", "b5", "b4", "b6", "a6", "a5", "a4", "a2", "a7", "d8", "c8", "h3", "h4", "d1", "g8", "g1", "g2",
				"g7", "b1", "c1", "h1", "h2", "h8", "h7", "a3", "a8", "b8", "b7", "b2", "a1" };

		Board b = new Board();
		for (int i = 0; i < moves.length; i++) {
			b.place(moves[i]);
		}
		assertEquals(33, b.getScore(Color.BLACK));
		assertEquals(31, b.getScore(Color.WHITE));
	}

}
