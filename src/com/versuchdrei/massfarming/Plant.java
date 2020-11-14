package com.versuchdrei.massfarming;

import org.bukkit.Material;

/**
 * a wrapper class for plant properties
 * @author VersuchDrei
 * @version 1.0
 */
public class Plant {
	
	private final Material result;
	private final boolean onlyOther;
	
	public Plant(final Material result) {
		this.result = result;
		this.onlyOther = false;
	}
	
	public Plant(final Material result, final boolean onlyOther) {
		this.result = result;
		this.onlyOther = onlyOther;
	}
	
	public Material getResult() {
		return this.result;
	}
	
	public boolean onlyOther() {
		return this.onlyOther;
	}

}
