package com.versuchdrei.massfarming.utils;

import java.util.Iterator;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/**
 * a block iterator moving in the shape of a helix around the centered block, only moves on x and z coordinates
 * @author VersuchDrei
 * @version 1.0
 */
public class HelixIterator implements Iterator<Block> {
	
	private Block current;
	private final int maxSteps;
	private boolean zAxis = true;
	private int axisStep = 0;
	private int currentMaxSteps = 1;

	private final BlockFace xAxis1;
	private final BlockFace xAxis2;
	private final BlockFace zAxis1;
	private final BlockFace zAxis2;
	  
	public HelixIterator(final Block center, final int perimeter, final boolean positiveX, final boolean positiveZ) {
		this.current = center;
		this.maxSteps = perimeter - 1;
		if(positiveX) {
			xAxis1 = BlockFace.EAST;
			xAxis2 = BlockFace.WEST;
		} else {
			xAxis1 = BlockFace.WEST;
			xAxis2 = BlockFace.EAST;
		}
		if(positiveZ) {
			zAxis1 = BlockFace.SOUTH;
			zAxis2 = BlockFace.NORTH;
		} else {
			zAxis1 = BlockFace.NORTH;
			zAxis2 = BlockFace.SOUTH;
		}
	}
 
	@Override
	public boolean hasNext() {
		return this.currentMaxSteps <= this.maxSteps || this.axisStep != this.maxSteps;
	}
	
	@Override
	public Block next() {
		final boolean odd = (this.currentMaxSteps % 2 == 1);
		final BlockFace direction = odd ? (this.zAxis ? this.zAxis1 : this.xAxis1) : (this.zAxis ? this.zAxis2 : this.xAxis2);
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