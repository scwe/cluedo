package card;



public class Clock implements IntrigueCard{
	private Boolean last;
	
	public Clock(Boolean last){
		this.last = last;
	}

	public Boolean getLast() {
		return last;
	}

	public void setLast(Boolean last) {
		this.last = last;
	}
	
	public String toString(){
		return "Clock card, Deadly: "+last;
	}
}
