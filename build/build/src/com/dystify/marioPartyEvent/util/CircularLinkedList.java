package com.dystify.marioPartyEvent.util;

import java.util.LinkedList;

/**
 * Implements a circularly traversable, looping queue
 * @author Duemmer
 *
 * @param <E>
 */
public class CircularLinkedList<E> extends LinkedList<E> 
{
	private static final long serialVersionUID = -2379643692854985513L;
	private int totalReads = 0;
	private int nextIdx = 0;
	
	@Override public E peek() {
		if(size() > 0)
			return get(nextIdx);
		else
			return null;
	}
	
	
	/**
	 * gets the next element and advances the pointer. Does not technically remove anything from the list
	 */
	@Override public E poll() 
	{
		E next = peek();
		nextIdx = getNextIdx(nextIdx);
		totalReads++;
		return next;
	}
	
	
	private int getNextIdx(int curr) {
		curr++;
		if(curr >= size())
			curr = 0;
		return curr;
	}
	
	
	public void reset() {
		totalReads = 0;
		nextIdx = 0;
	}



	public int getTotalReads() {
		return totalReads;
	}



	public int getNextIdx() {
		return nextIdx;
	}
	
	
	
}
