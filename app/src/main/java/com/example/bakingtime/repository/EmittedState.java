package com.example.bakingtime.repository;

/**
 * A state api exposing the value of the given state holder.
 *
 * @author Kal Tadesse
 */
public abstract class EmittedState {

	public final int stateType;

	public EmittedState(int stateType) {
		this.stateType = stateType;
	}
}