package com.ieee.atml.test.resource;

import java.util.ArrayList;
import java.util.Iterator;

public class Signal {
	private String name;
	private ArrayList<Action> actions;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Action> getActions() {
		return actions;
	}
	public void setActions(ArrayList<Action> actions) {
		this.actions = actions;
	}
	public Action getAction(String name){
		for (Iterator iterator = actions.iterator(); iterator.hasNext();) {
			Action action = (Action) iterator.next();
			if(action.getName().equals(name)){
				return action;
			}
		}
		return null;
	}
}
