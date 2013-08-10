package card;



public class Keeper implements IntrigueCard{
	public enum KeeperFunction{
		RIGHT_SHOW_CARD("At the end of your turn, the player on your right must show a card"),
		STAY_ROOM("Stay in your room and make another announcement"),
		RUMOR_UNANSWERED("Instead of responding to an Announcement, it remains unanswered"),
		CARD_SNIPE("You get to see a card that is being shown too"),   //When one player shows a card you may get to see that card too
		MOVE_START_SPACE("Move any player back to their start location"),   //At the start of your turn, move anyone back to their start space
		MOVE_ANYWHERE("Move anywhere instead of rolling the dice"),     //Instead of rolling the dice, move anywhere
		MOVE_EXTRA_SIX("Add 6 to your dice roll before you move"),   //after your dice roll, but before you move, add 6 to your dice roll
		TAKE_ANOTHER_TURN("At the end of your turn take another turn ");   //at the end of your turn, take another turn
		
		
		private String value;
		
		private KeeperFunction(String v){
			this.value = v;
		}
		
		public String toString(){
			return value;
		}
	}
	
	private KeeperFunction type;
	
	public Keeper(String num){
		int i = Integer.parseInt(num.trim());
		
		switch(i){
		case 1:
			type = KeeperFunction.RIGHT_SHOW_CARD;
			break;
		case 2:
			type = KeeperFunction.STAY_ROOM;
			break;
		case 3:
			type = KeeperFunction.RUMOR_UNANSWERED;
			break;
		case 4:
			type = KeeperFunction.CARD_SNIPE;
			break;
		case 5:
			type = KeeperFunction.MOVE_START_SPACE;
			break;
		case 6:
			type = KeeperFunction.MOVE_ANYWHERE;
			break;
		case 7:
			type = KeeperFunction.MOVE_EXTRA_SIX;
			break;
		case 8:
			type = KeeperFunction.TAKE_ANOTHER_TURN;
			break;
		}
	}
	
	public String toString(){
		return "Keeper Card: "+ type.toString();
	}

}
