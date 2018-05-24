package se.kpod.reversi;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kpod.reversi.domain.Board;
import se.kpod.reversi.domain.Tuple;
import se.kpod.reversi.domain.Board.Color;

class PointTester {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test() {
		Board b = new Board();
		b.printBoard();
	}
	
	@Test
	void testInitScore() {
		Board b = new Board();
		assertEquals(2, b.getScore(Color.WHITE));
		assertEquals(2, b.getScore(Color.BLACK));
	}
	
	@Test
	void testOneMoveScore() {
		Board b = new Board();
		assertTrue(b.isLegalMove(new Tuple<Integer, Integer>(2,3), b.getTurn()));
		b.place(new Tuple<Integer, Integer>(2, 3));
		b.printBoard();
		assertEquals(1, b.getScore(Color.WHITE));
		assertEquals(4, b.getScore(Color.BLACK));
	}

	@Test
	void testTwoMoveScore() {
		Board b = new Board();
		assertTrue(b.isLegalMove(new Tuple<Integer, Integer>(4,5), b.getTurn()));
		b.place(new Tuple<Integer, Integer>(4, 5));
		b.place(new Tuple<Integer, Integer>(5, 5));
		b.printBoard();
		assertEquals(3, b.getScore(Color.WHITE));
		assertEquals(3, b.getScore(Color.BLACK));
	}
	
	@Test
	void testThreeMoveScore() {
		Board b = new Board();
		assertTrue(b.isLegalMove(new Tuple<Integer, Integer>(3,2), b.getTurn()));
		b.place(new Tuple<Integer, Integer>(3, 2));
		b.place(new Tuple<Integer, Integer>(2, 4));
		b.place(new Tuple<Integer, Integer>(4, 5));
		b.printBoard();
		assertEquals(2, b.getScore(Color.WHITE));
		assertEquals(5, b.getScore(Color.BLACK));
	}
	
	@Test
	void fullGameDraw() {
		Board b = new Board();
		assertTrue(b.isLegalMove(new Tuple<Integer, Integer>(3,2), b.getTurn()));
		b.place(new Tuple<Integer, Integer>(2, 3));
		System.out.println("Move 1");
		b.printBoard();
		assertEquals(4, b.getScore(Color.BLACK));
		assertEquals(1, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(4, 2));
		System.out.println("Move 2");
		b.printBoard();
		assertEquals(3, b.getScore(Color.BLACK));
		assertEquals(3, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(5, 5));
		System.out.println("Move 3");
		b.printBoard();
		assertEquals(5, b.getScore(Color.BLACK));
		assertEquals(2, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(4, 5));
		System.out.println("Move 4");
		b.printBoard();
		assertEquals(4, b.getScore(Color.BLACK));
		assertEquals(4, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(5, 4));
		System.out.println("Move 5");
		b.printBoard();
		assertEquals(6, b.getScore(Color.BLACK));
		assertEquals(3, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(2, 4));
		System.out.println("Move 6");
		b.printBoard();
		assertEquals(5, b.getScore(Color.BLACK));
		assertEquals(5, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(5, 3));
		System.out.println("Move 7");
		b.printBoard();
		assertEquals(8, b.getScore(Color.BLACK));
		assertEquals(3, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(6,5));
		System.out.println("Move 8");
		b.printBoard();
		assertEquals(7, b.getScore(Color.BLACK));
		assertEquals(5, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(5, 6));
		System.out.println("Move 9");
		b.printBoard();
		assertEquals(10, b.getScore(Color.BLACK));
		assertEquals(3, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(6,4));
		System.out.println("Move 10");
		b.printBoard();
		assertEquals(6, b.getScore(Color.BLACK));
		assertEquals(8, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(3, 5));
		System.out.println("Move 11");
		b.printBoard();
		assertEquals(8, b.getScore(Color.BLACK));
		assertEquals(7, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(3, 2));
		System.out.println("Move 12");
		b.printBoard();
		assertEquals(7, b.getScore(Color.BLACK));
		assertEquals(9, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(5, 2));
		System.out.println("Move 13");
		b.printBoard();
		assertEquals(11, b.getScore(Color.BLACK));
		assertEquals(6, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(2, 2));
		System.out.println("Move 14");
		b.printBoard();
		assertEquals(9, b.getScore(Color.BLACK));
		assertEquals(9, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(7, 3));
		System.out.println("Move 15");
		b.printBoard();
		assertEquals(11, b.getScore(Color.BLACK));
		assertEquals(8, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(7, 4));
		System.out.println("Move 16");
		b.printBoard();
		assertEquals(9, b.getScore(Color.BLACK));
		assertEquals(11, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(6,3));
		System.out.println("Move 17");
		b.printBoard();
		assertEquals(11, b.getScore(Color.BLACK));
		assertEquals(10, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(7, 2));
		System.out.println("Move 18");
		b.printBoard();
		assertEquals(10, b.getScore(Color.BLACK));
		assertEquals(12, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(4, 1));
		System.out.println("Move 19");
		b.printBoard();
		assertEquals(12, b.getScore(Color.BLACK));
		assertEquals(11, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(5, 1));
		System.out.println("Move 20");
		b.printBoard();
		assertEquals(11, b.getScore(Color.BLACK));
		assertEquals(13, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(3, 1));
		System.out.println("Move 21");
		b.printBoard();
		assertEquals(15, b.getScore(Color.BLACK));
		assertEquals(10, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(3, 0));
		System.out.println("Move 22");
		b.printBoard();
		assertEquals(12, b.getScore(Color.BLACK));
		assertEquals(14, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(6,2));
		System.out.println("Move 23");
		b.printBoard();
		assertEquals(14, b.getScore(Color.BLACK));
		assertEquals(13, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(2, 0));
		System.out.println("Move 24");
		b.printBoard();
		assertEquals(11, b.getScore(Color.BLACK));
		assertEquals(17, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(1, 5));
		System.out.println("Move 25");
		b.printBoard();
		assertEquals(13, b.getScore(Color.BLACK));
		assertEquals(16, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(2, 1));
		System.out.println("Move 26");
		b.printBoard();
		assertEquals(10, b.getScore(Color.BLACK));
		assertEquals(20, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(1, 3));
		System.out.println("Move 27");
		b.printBoard();
		assertEquals(12, b.getScore(Color.BLACK));
		assertEquals(19, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(4, 7));
		System.out.println("Move 28");
		b.printBoard();
		assertEquals(11, b.getScore(Color.BLACK));
		assertEquals(21, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(5, 0));
		System.out.println("Move 29");
		b.printBoard();
		assertEquals(15, b.getScore(Color.BLACK));
		assertEquals(18, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(4, 6));
		System.out.println("Move 30");
		b.printBoard();
		assertEquals(13, b.getScore(Color.BLACK));
		assertEquals(21, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(1, 2));
		System.out.println("Move 31");
		b.printBoard();
		assertEquals(15, b.getScore(Color.BLACK));
		assertEquals(20, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(4, 0));
		System.out.println("Move 32");
		b.printBoard();
		assertEquals(12, b.getScore(Color.BLACK));
		assertEquals(24, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(1, 0));
		System.out.println("Move 33");
		b.printBoard();
		assertEquals(17, b.getScore(Color.BLACK));
		assertEquals(20, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(2, 6));
		System.out.println("Move 34");
		b.printBoard();
		assertEquals(16, b.getScore(Color.BLACK));
		assertEquals(22, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(6, 0));
		System.out.println("Move 35");
		b.printBoard();
		assertEquals(19, b.getScore(Color.BLACK));
		assertEquals(20, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(2, 5));
		System.out.println("Move 36");
		b.printBoard();
		assertEquals(18, b.getScore(Color.BLACK));
		assertEquals(22, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(3, 7));
		System.out.println("Move 37");
		b.printBoard();
		assertEquals(20, b.getScore(Color.BLACK));
		assertEquals(21, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(1, 4));
		System.out.println("Move 38");
		b.printBoard();
		assertEquals(17, b.getScore(Color.BLACK));
		assertEquals(25, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(0, 5));
		System.out.println("Move 39");
		b.printBoard();
		assertEquals(22, b.getScore(Color.BLACK));
		assertEquals(21, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(6, 1));
		System.out.println("Move 40");
		b.printBoard();
		assertEquals(19, b.getScore(Color.BLACK));
		assertEquals(25, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(3, 6));
		System.out.println("Move 41");
		b.printBoard();
		assertEquals(23, b.getScore(Color.BLACK));
		assertEquals(22, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(1, 6));
		System.out.println("Move 42");
		b.printBoard();
		assertEquals(19, b.getScore(Color.BLACK));
		assertEquals(27, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(5, 7));
		System.out.println("Move 43");
		b.printBoard();
		assertEquals(28, b.getScore(Color.BLACK));
		assertEquals(19, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(0, 2));
		System.out.println("Move 44");
		b.printBoard();
		assertEquals(22, b.getScore(Color.BLACK));
		assertEquals(26, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(0, 4));
		System.out.println("Move 45");
		b.printBoard();
		assertEquals(26, b.getScore(Color.BLACK));
		assertEquals(23, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(0, 3));
		System.out.println("Move 46");
		b.printBoard();
		assertEquals(22, b.getScore(Color.BLACK));
		assertEquals(28, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(0, 1));
		System.out.println("Move 47");
		b.printBoard();
		assertEquals(29, b.getScore(Color.BLACK));
		assertEquals(22, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(6, 6));
		System.out.println("Move 48");
		b.printBoard();
		assertEquals(26, b.getScore(Color.BLACK));
		assertEquals(26, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(7, 0));
		System.out.println("Move 49");
		b.printBoard();
		assertEquals(30, b.getScore(Color.BLACK));
		assertEquals(23, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(7, 1));
		System.out.println("Move 50");
		b.printBoard();
		assertEquals(28, b.getScore(Color.BLACK));
		assertEquals(26, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(6, 7));
		System.out.println("Move 51");
		b.printBoard();
		assertEquals(36, b.getScore(Color.BLACK));
		assertEquals(19, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(7, 6));
		System.out.println("Move 52");
		b.printBoard();
		assertEquals(31, b.getScore(Color.BLACK));
		assertEquals(25, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(7, 7));
		System.out.println("Move 53");
		b.printBoard();
		assertEquals(36, b.getScore(Color.BLACK));
		assertEquals(21, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(7, 5));
		System.out.println("Move 54");
		b.printBoard();
		assertEquals(34, b.getScore(Color.BLACK));
		assertEquals(24, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(0, 7));
		System.out.println("Move 55");
		b.printBoard();
		assertEquals(37, b.getScore(Color.BLACK));
		assertEquals(22, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(0, 6));
		System.out.println("Move 56");
		b.printBoard();
		assertEquals(35, b.getScore(Color.BLACK));
		assertEquals(25, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(1, 7));
		System.out.println("Move 57");
		b.printBoard();
		assertEquals(41, b.getScore(Color.BLACK));
		assertEquals(20, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(2, 7));
		System.out.println("Move 58"); // Black
		b.printBoard();
		assertEquals(39, b.getScore(Color.BLACK));
		assertEquals(23, b.getScore(Color.WHITE));
		
		b.place(new Tuple<Integer, Integer>(0, 0));
		System.out.println("Move 59"); // Black again
		b.printBoard();
		assertEquals(34, b.getScore(Color.BLACK));
		assertEquals(29, b.getScore(Color.WHITE));
		b.place(new Tuple<Integer, Integer>(1, 1));
		System.out.println("Move 60");
		b.printBoard();
		assertEquals(32, b.getScore(Color.BLACK));
		assertEquals(32, b.getScore(Color.WHITE));
		
		
		
	}
}
