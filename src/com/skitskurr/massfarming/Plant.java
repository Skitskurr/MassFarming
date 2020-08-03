package com.skitskurr.massfarming;

import org.bukkit.Material;

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
