package card;

import java.util.*;

import main.Player;

/**
 * A class to represent a deck of holdable items, giving helper methods to
 * deal and shuffle the deck
 * @author scott
 *
 * @param <E>
 * 	
 */
public class Deck<E extends Holdable> implements Iterable<E> {

	private Stack<E> deck;

	public Deck() {
		deck = new Stack<E>();
	}

	/**
	 * adds all the holdables from a list into the deck so the last item on the 
	 * list is now on top of the deck
	 * @param list
	 * 		The items to add
	 */
	public void addAll(List<E> list) {
		for (E e : list) {
			push(e);
		}
	}

	/**
	 * Deals the deck out to a queue of players, makes sure the
	 * players are in the same order they came in as well
	 * @param players
	 * 		The players to deal the deck to
	 */
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

	/**
	 * Sets the right Clock card as deadly, only works when this is a Deck
	 * of intrigue cards though
	 */
	public void setDeadlyClock() {
		if (!(deck.get(0) instanceof IntrigueCard)) {
			return;
		}

		Clock c = null;
		for (E e : deck) {
			if (e instanceof Clock) {
				c = ((Clock) e);
				break;		//finds the first one (cause it works from the bottom of the stack up)
			}
		}
		
		if(c != null){
			c.setLast(true);
		}

	}

	/**
	 * Shuffles the deck
	 */
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
