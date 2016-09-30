package com.isaac.hydrantmap.common;

public enum HydrantForm {

	UndergroundHydrant("地下消火栓");

	private final String name;

	private HydrantForm(String s) {
        name = s;
    }

	public boolean equalsName(String otherName) {
		return (otherName == null) ? false : name.equals(otherName);
	}

	public String toString() {
		return this.name;
	}
}
