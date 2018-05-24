package se.kpod.reversi;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import se.kpod.reversi.domain.Board;
import se.kpod.reversi.domain.Tuple;

public class BoardTester {
	@Test
	void initPossibleMoves() {
		Board b = new Board();
		List<Tuple<Integer, Integer>> expected = new ArrayList<>();
		expected.add(new Tuple<Integer, Integer>(2, 3));
		expected.add(new Tuple<Integer, Integer>(3, 2));
		expected.add(new Tuple<Integer, Integer>(4, 5));
		expected.add(new Tuple<Integer, Integer>(5, 4));

		List<Tuple<Integer, Integer>> moves = b.getPossibleMoves(b.getState().getCurrentTurn());
		assertEquals(b.getState().getCurrentlyPossibleMoves(), moves);
		
		assertEquals(expected, moves);

	}

	@Test
	void legalMoves() {
		Board b = new Board();
		assertFalse(b.isLegalMove(new Tuple<Integer, Integer>(3, 3)));
		assertFalse(b.isLegalMove(new Tuple<Integer, Integer>(3, 4)));
		assertFalse(b.isLegalMove(new Tuple<Integer, Integer>(4, 3)));
		assertFalse(b.isLegalMove(new Tuple<Integer, Integer>(4, 4)));
		assertFalse(b.isLegalMove(new Tuple<Integer, Integer>(0, 0)));

		assertFalse(b.isLegalMove(new Tuple<Integer, Integer>(5, 3)));
		assertTrue(b.isLegalMove(new Tuple<Integer, Integer>(5, 4)));

		assertTrue(b.isLegalMove(new Tuple<Integer, Integer>(2, 3)));
		assertFalse(b.isLegalMove(new Tuple<Integer, Integer>(2, 4)));

		assertTrue(b.isLegalMove(new Tuple<Integer, Integer>(3, 2)));
		assertFalse(b.isLegalMove(new Tuple<Integer, Integer>(4, 2)));

		assertFalse(b.isLegalMove(new Tuple<Integer, Integer>(3, 5)));
		assertTrue(b.isLegalMove(new Tuple<Integer, Integer>(4, 5)));

		b.place(new Tuple<Integer, Integer>(4, 5));
		assertTrue(b.isLegalMove(new Tuple<Integer, Integer>(5, 5)));
		b.printBoard();
		b.place(new Tuple<Integer, Integer>(5, 5));
		b.printBoard();
	}
	
	
}
