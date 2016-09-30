package com.isaac.hydrantmap.common;

public enum HydrantStatus {
	Working("可用"),
	NotWorking("不可用"),
	Modified("改用");
	
	private final String name;       

    private HydrantStatus(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
       return this.name;
    }
}
