

public class Keeper implements IntrigueCard{
	public enum KeeperFunction{
		RIGHT_SHOW_CARD,		//at the end of your turn, the player on your right must show a card
		STAY_ROOM,		//at the end of your turn
		RUMOR_UNANSWERED,   //replaces your dice roll
		CARD_SNIPE,   //When one player shows a card you may get to see that card too
		MOVE_START_SPACE,   //At the start of your turn, move anyone back to their start space
		MOVE_ANYWHERE,     //Instead of rolling the dice, move anywhere
		MOVE_EXTRA_SIX,   //after your dice roll, but before you move, add 6 to your dice roll
		TAKE_ANOTHER_TURN;   //at the end of your turn, take another turn
	}
	
	private KeeperFunction type;
	
	public Keeper(String num){
		int i = Integer.parseInt(num);
		
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
}
