/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is the JInSim Library.
 *
 * The Initial Developer of the Original Code is Rob Heiser.
 *
 * Portions created by the Initial Developer are Copyright (C) 2006
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 *
 *   Rob Heiser <jinsim@kerf.org>
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU General Public License Version 2 or later (the "GPL"), or
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 *
 * ***** END LICENSE BLOCK ***** */
package net.sf.jinsim.response;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;


import net.sf.jinsim.PacketType;
import net.sf.jinsim.types.Tyres;


public class PitStopResponse extends PlayerResponse {
	
	public static final int NOTHING = 1;
	public static final int STOP = 2;
	public static final int FRONT_DAMAGE = 4;
	public static final int FRONT_WHEEL = 8;
	public static final int LEFT_FRONT_DAMAGE = 16;
	public static final int LEFT_FRONT_WHEEL = 32;
	public static final int RIGHT_FRONT_DAMAGE = 64;
	public static final int RIGHT_FRONT_WHEEL = 128;
	public static final int REAR_DAMAGE = 256;
	public static final int REAR_WHEEL = 512;
	public static final int LEFT_REAR_DAMAGE = 1024;
	public static final int LEFT_REAR_WHEEL = 2048;
	public static final int RIGHT_REAR_DAMAGE = 4096;
	public static final int RIGHT_REAR_WHEEL = 8192;
	public static final int BODY_MINOR = 16386;
	public static final int BODY_MAJOR = 32768;
	public static final int SETUP = 65536;
	public static final int REFUEL = 131072;
	
	private int laps;
	private int playerFlags;
	private byte penalty;
	private byte numberOfPitstops;
	private Tyres tyres;
	private int work;	

	public PitStopResponse() {
		super(PacketType.PIT);
	}
	
	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		setLaps(buffer.getShort());
		setPlayerFlags(buffer.getShort());
		buffer.position(buffer.position()+1);
		setPenalty(buffer.get());
		setNumberOfPitstops(buffer.get());
		buffer.position(buffer.position()+1);
		setTyres(new Tyres(buffer));
		setWork(buffer.getInt());
		buffer.position(buffer.position()+4);
		
	}

	public Tyres getTyres() {
		return tyres;
	}

	public void setTyres(Tyres tyres) {
		this.tyres = tyres;
	}

	public int getWork() {
		return work;
	}

	public void setWork(int work) {
		this.work = work;
	}

	public int getLaps() {
		return laps;
	}

	public void setLaps(int laps) {
		this.laps = laps;
	}

	public int getPlayerFlags() {
		return playerFlags;
	}

	public void setPlayerFlags(int playerFlags) {
		this.playerFlags = playerFlags;
	}

	public byte getPenalty() {
		return penalty;
	}

	public void setPenalty(byte penalty) {
		this.penalty = penalty;
	}

	public byte getNumberOfPitstops() {
		return numberOfPitstops;
	}

	public void setNumberOfPitstops(byte numberOfPitstops) {
		this.numberOfPitstops = numberOfPitstops;
	}

	public boolean didNothing() {
		return (work & NOTHING) > 0;
	}
	
	public boolean didStop() {
		return (work & STOP) > 0;
	}

	public boolean didFrontDamage() {
		return (work & FRONT_DAMAGE) > 0;
	}
	
	public boolean didFrontWheel() {
		return (work & FRONT_WHEEL) > 0;
	}
	
	public boolean didLeftFrontDamage() {
		return (work & LEFT_FRONT_DAMAGE) > 0;
	}
	
	public boolean didLeftFrontWheel() {
		return (work & LEFT_FRONT_WHEEL) > 0;
	}
	
	public boolean didRightFrontDamage() {
		return (work & RIGHT_FRONT_DAMAGE) > 0;
	}
	
	public boolean didRightFrontWheel() {
		return (work & RIGHT_FRONT_WHEEL) > 0;
	}
	
	public boolean didRearDamage() {
		return (work & REAR_DAMAGE) > 0;
	}
	
	public boolean didRearWheel() {
		return (work & REAR_WHEEL) > 0;
	}
	
	public boolean didLeftRearDamage() {
		return (work & LEFT_REAR_DAMAGE) > 0;
	}
	
	public boolean didLeftRearWheel() {
		return (work & LEFT_REAR_WHEEL) > 0;
	}
	
	public boolean didRightRearDamage() {
		return (work & RIGHT_REAR_DAMAGE) > 0;
	}
	
	public boolean didRightRearWheel() {
		return (work & RIGHT_REAR_WHEEL) > 0;
	}
	
	public boolean didBodyMinor() {
		return (work & BODY_MINOR) > 0;
	}
	
	public boolean didBodyMajor() {
		return (work & BODY_MAJOR) > 0;
	}
	
	public boolean didSetup() {
		return (work & SETUP) > 0;
	}
	
	public boolean didRefuel() {
		return (work & REFUEL) > 0;
	}
	
  public String toString() {
	  return  super.toString() + 
	  		", laps: " + laps +
	  		", playerFlags: " + playerFlags +
	  		", penalty: " + penalty +
	  		", numberOfPitstops: " + numberOfPitstops +
	  		", tyres: " + tyres +
	  		", work: " + work;
  }
	
}
