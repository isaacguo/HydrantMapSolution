package com.isaac.hydrantmap.common;

public enum WayToAcquireWater {

	FromHydrant("消火栓取水");

	private final String name;

	private WayToAcquireWater(String s) {
		name = s;
	}

	public boolean equalsName(String otherName) {
		return (otherName == null) ? false : name.equals(otherName);
	}

	public String toString() {
		return this.name;
	}
}
