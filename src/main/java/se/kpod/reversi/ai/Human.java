package se.kpod.reversi.ai;

import java.util.Scanner;

import se.kpod.reversi.domain.Board;
import se.kpod.reversi.domain.Player;

public class Human extends Player{

	private Scanner reader;
	
	public Human() {
		reader = new Scanner(System.in);
	}
	
	@Override
	public String nextMove(Board board) {
		System.out.println("Whats your move: ");
		String notation = reader.nextLine();
		return notation;
	}
}
