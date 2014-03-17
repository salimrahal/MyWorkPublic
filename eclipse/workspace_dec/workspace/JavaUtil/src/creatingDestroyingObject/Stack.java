package creatingDestroyingObject;

import java.util.Arrays;
import java.util.EmptyStackException;

//Can you spot the "memory leak"?
public class Stack {
	private Object[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	public Stack() {
		elements = new Object[DEFAULT_INITIAL_CAPACITY];
	}

	public void push(Object e) {
		ensureCapacity();
		elements[size++] = e;
	}
	//	public Object pop() {
	//		if (size == 0)
	//		throw new EmptyStackException();
	//		return elements[--size];
	//		}
	/*
	 * whenever a class manages its own memory, the programmer
	should be alert for memory leaks. Whenever an element is freed, any
	object references contained in the element should be nulled out.
	 */
	public Object pop() {
		if (size == 0)
			throw new EmptyStackException();
		Object obj = elements[--size];
		elements[size] = null; //// Eliminate obsolete reference
		return obj;
	}

	/**
	 * Ensure space for at least one more element, roughly doubling the capacity
	 * each time the array needs to grow.
	 */
	private void ensureCapacity() {
		if (elements.length == size)
			elements = Arrays.copyOf(elements, 2 * size + 1);
	}

	public Object[] getElements() {
		return elements;
	}

	public void setElements(Object[] elements) {
		this.elements = elements;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}