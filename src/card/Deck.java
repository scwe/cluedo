package card;

import java.util.*;

import main.Player;

public class Deck<E extends Holdable> implements Iterable<E> {

	private Stack<E> deck;

	public Deck() {
		deck = new Stack<E>();
	}

	public void addAll(List<E> list) {
		for (E e : list) {
			push(e);
		}
	}

	public void deal(Queue<Player> players) {

		Player startPlayer = players.peek();

		while (!isEmpty()) { // while there are still cards, take a player off the turn queue
			Player p = players.poll();

			p.addCard(pop()); // and add a card to its hand then put it on the back

			players.offer(p);
		}

		while (players.peek() != startPlayer) { // to make sure the queue order remains the same
			players.offer(players.poll()); // take that person off and put them at the back of the queue
		}
	}

	public void setDeadlyClock() {
		if (!(deck.get(0) instanceof IntrigueCard)) {
			return;
		}

		Clock c = null;
		for (E e : deck) {
			if (e instanceof Clock) {
				c = ((Clock) e);
			}
		}
		
		if(c != null){
			c.setLast(true);
		}

	}

	public void shuffle() {
		Collections.shuffle(deck);
	}

	public void push(E e) {
		deck.push(e);
	}

	public E peek() {
		return deck.peek();
	}

	public E pop() {
		return deck.pop();
	}

	@Override
	public Iterator<E> iterator() {
		return deck.iterator();
	}

	public void remove(Card room) {
		deck.remove(room);
	}

	public boolean isEmpty() {
		return deck.isEmpty();
	}
}
