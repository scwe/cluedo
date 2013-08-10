package card;
import java.util.*;



public class Hand<E extends Holdable> extends AbstractList<E> implements List<E> {
	private ArrayList<E> data;
	
	public Hand(){
	}
	
	@Override
	public boolean add(E e){
		return data.add(e);
	}

	@Override
	public E get(int index) {
		return data.get(index);
	}
	
	@Override
	public E remove(int index){
		return data.remove(index);
	}
	
	@Override
	public boolean remove(Object o){
		return data.remove(o);
	}

	@Override
	public int size() {
		return data.size();
	}
	
	@Override
	public String toString(){
		StringBuilder s = new StringBuilder();
		
		for(E e: data){
			s.append(e.toString());
			s.append(", ");
		}
		
		s.setCharAt(s.length() - 2, ' ');
		return s.toString();
	}
}
