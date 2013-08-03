
public class Card <E extends Cardable>{
	private E item;

	public Card(E item){
		this.item = item;
	}

	public E getItem() {
		return item;
	}

	public void setItem(E item) {
		this.item = item;
	}
}
