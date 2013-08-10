
public class Card implements Holdable{

	private Cardable card;

	public Card(Cardable card){
		this.card = card;
	}

	public Cardable getCard() {
		return card;
	}

	public void setCard(Cardable card) {
		this.card = card;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (card == null) {
			if (other.card != null)
				return false;
		} else if (!card.equals(other.card))
			return false;
		return true;
	}
	
	public String toString(){
		return "Card of "+card.toString();
	}
}
