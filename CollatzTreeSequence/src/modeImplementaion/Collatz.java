package modeImplementaion;

import java.util.*;
import java.io.*;
public class Collatz {
	private static int m = 8;
	private static PrintWriter forwardStream = null;
	private static PrintWriter backwardStream = null;
	private static LinkedList<Integer> linkedList = new LinkedList<Integer>();
	public static boolean forwardChaining(int nValue, int limitValue){
		
		try{
			forwardStream = new PrintWriter("Collatz Tree.csv");
			
			linkedList.add(m);
			forwardStream.println(linkedList);
			// forward chaining algorithm
			for (int i = 0; i < limitValue - 1; i++){
				m = linkedList.remove();
				if ((m % 2 == 0) && (m - 1) % 3 == 0){
					linkedList.add(2 * m);
					linkedList.add((m - 1) / 3);
				}
				else {
					linkedList.add(2 * m);
				}
				
				Collections.sort(linkedList, new Comparator<Integer>(){
					public int compare(Integer i1, Integer i2){
						return i1 - i2;
					}
				});
				forwardStream.println(linkedList);
				if (linkedList.contains(nValue) == true){
					forwardStream.close();
					return true;
				}
			} // end of forward chaining algorithm
		} catch(FileNotFoundException e1){
			System.out.println("Error: FileNotFoundException!");
		}
		forwardStream.close();
		return false;
	}
	
	public static boolean backwardChaining(int nValue, int limitValue){
		linkedList.add(nValue);
		try {
			backwardStream = new PrintWriter("Collatz Sequence.csv");
			backwardStream.println(linkedList);
			// backward chaining algorithm 
			for (int i = 0; i < limitValue - 1; i++){
				if (nValue % 2 == 0){
					linkedList.add(nValue / 2);
					nValue = linkedList.get(i + 1);
				}
				else {
					linkedList.add(nValue * 3 + 1);
					nValue = linkedList.get(i + 1);
				}
				Collections.sort(linkedList, new Comparator<Integer>(){
					public int compare(Integer i1, Integer i2){
						return i1 - i2;
					}
				});
				backwardStream.println(linkedList);
				if (linkedList.contains(1) == true){
					backwardStream.close();
					return true;
				}
			}// end of backward chaining algorithm
		} catch (FileNotFoundException e1){
			System.out.println("Error: FileNotFoundException!");
		}
		backwardStream.close();
		return false;
	}
}
