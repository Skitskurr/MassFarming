package com.skitskurr.massfarming.utils;

import java.util.Iterator;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class HelixIterator implements Iterator<Block> {
	
	private Block current;
	private final int maxSteps;
	private boolean zAxis = true;
	private int axisStep = 0;
	private int currentMaxSteps = 1;
	  
	public HelixIterator(final Block center, final int radius) {
		this.current = center;
		this.maxSteps = 2 * radius;
	}
 
	@Override
	public boolean hasNext() {
		return this.currentMaxSteps <= this.maxSteps || this.axisStep != this.maxSteps;
	}
	
	@Override
	public Block next() {
		final boolean odd = (this.currentMaxSteps % 2 == 1);
		final BlockFace direction = odd ? (this.zAxis ? BlockFace.NORTH : BlockFace.EAST) : (this.zAxis ? BlockFace.SOUTH : BlockFace.WEST);
		if (++this.axisStep == this.currentMaxSteps) {
			this.axisStep = 0;
			if (this.zAxis) {
				this.zAxis = false;
			} else {
				this.zAxis = true;
				this.currentMaxSteps++;
			} 
		}
		
		return this.current = this.current.getRelative(direction);
	}
}