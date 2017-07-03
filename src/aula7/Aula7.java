/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aula7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Guilherme
 */
public class Aula7 {

    public static boolean runDfa(HashMap<Integer, HashMap<Character, Integer>> dfa, String input, List<Integer> finalStates) 
    {		
        int state = 0;

            for (char c : input.toCharArray()) {
                    try {
                            HashMap<Character, Integer> transitions = dfa.get(state);
                            state = transitions.get(c);
                    } catch (Exception e) {
                            return false;
                    }
            }
            return finalStates.contains(state);
    }
	
    public static HashMap<Integer, HashMap<Character, Integer>> createDfa(String regex) 
    {
            HashMap<Integer, HashMap<Character, Integer>> dfa = new HashMap<>();
            int state = 0;
            int pos = 0;

            for (char c : regex.toCharArray()) {
                    HashMap<Character, Integer> transitions = new HashMap<>();

                    if (!dfa.containsKey(state)) {
                            dfa.put(state, transitions);
                    }
                    if (Character.isAlphabetic(c)) { 
                            dfa.get(state).put(c,  state + 1);
                            state++;
                    } 
                    else if (c == '*') //nenhuma ou varias vezes
                    {
                        dfa.remove(state);
                        state--;
                        dfa.get(state).put(regex.charAt(pos-1), state);     
                    }
                    else if (c == '+') //pelo menos uma vez
                    {
                        dfa.get(state).put(regex.charAt(pos-1), state);
                    }
                    else if (c == '?') // a?b => a ou b
                    {
                        dfa.get(state-1).put(regex.charAt(pos+1), state);
                        dfa.remove(state);
                        state--;
                    }
                    pos++;
            }
            return dfa;
    }

	public static void main(String[] args) {
		HashMap<Integer, HashMap<Character, Integer>> dfa = createDfa("a*b?cd*d");
		
		Iterator<HashMap<Character, Integer>> it = dfa.values().iterator();
		HashMap<Character, Integer> lastState = null;
                
		while (it.hasNext()) 
                    lastState = it.next();

		Iterator<Integer> it2 = lastState.values().iterator();
		int last = -1;
                
                
		while (it2.hasNext()) 
                    last = it2.next();

		ArrayList<Integer> finalStates = new ArrayList<>();
		finalStates.add(last);
		
		System.out.println(dfa.toString());
		System.out.println(runDfa(dfa, "bdd", finalStates));
	}
	
    
}
