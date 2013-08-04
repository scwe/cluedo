
public class Card <E extends Cardable>{
	private E card;

	public Card(E card){
		this.card = card;
	}

	public E getCard() {
		return card;
	}

	public void setCard(E card) {
		this.card = card;
	}
}
