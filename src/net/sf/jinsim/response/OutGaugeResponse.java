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



/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class OutGaugeResponse extends InSimResponse {

    /**	
     * Atmospheric pressure constant to convert from BAR to pounds pressure
     */
    private static final float ATMOSPHERIC_PRESSURE = 14.7f;
    /**
     * Constant for converting from m/s to mph
     */
    private static final float METERS_PER_SECOND_TO_MPH_CONSTANT = 2.2360248447205f;
    
    static public final int OG_SHIFTLIGHT = 1;
    static public final int OG_FULLBEAM   = 2;
    static public final int OG_HANDBRAKE  = 4;
    static public final int OG_PITSPEED   = 8;
    static public final int OG_TC         = 16;
    static public final int OG_HEADLIGHTS = 32;
    static public final int OG_SIGNAL_L   = 64;
    static public final int OG_SIGNAL_R   = 128;
    static public final int OG_REDLINE    = 256;
    static public final int OG_OILWARN    = 512;
    static public final int OG_1          = 1024;
    static public final int OG_2          = 2048;
    static public final int OG_3          = 4096;
    static public final int OG_4          = 8192;
    static public final int OG_KM         = 16384;
    static public final int OG_BAR        = 32768;

    private int             time;                 // time in milliseconds (to check order)
    private String          car;                  // Car name (4 len)
    private short           flags;                // Combination of OG_ flags above
    private byte            gear;                 // Reverse:0, Neutral:1, First:2...
    private float           speed;                // M/S
    private float           rpm;                  // RPM
    private float           turbo;                // BAR
    private float           waterTemperatur;            // C
    private float           fuel;                 // 0 to 1
    private float           oilPressure;          // BAR
    private float           throttle;             // 0 to 1
    private float           brake;                // 0 to 1
    private float           clutch;               // 0 to 1
    private String          display1;             // Usually Fuel (16 len)
    private String          display2;             // Usually Settings (16 len)
    private int             id;                   // (optional ID - if specified in cfg.txt)

    public OutGaugeResponse() {
    	super(PacketType.OUT_GAUGE);
    }

    public void construct(ByteBuffer buffer) throws BufferUnderflowException {
    	
        buffer.rewind();

        setTime(buffer.getInt());

        setCar(getString(buffer, 4));
        setFlags(buffer.getShort());
        setGear(buffer.get());
        buffer.position(buffer.position()+1);
        setSpeed(buffer.getFloat());
        setRpm(buffer.getFloat());
        setTurbo(buffer.getFloat());
        setWaterTemperatur(buffer.getFloat());
        setFuel(buffer.getFloat());
        setOilPressure(buffer.getFloat());
        buffer.position(buffer.position()+12);
        setThrottle(buffer.getFloat());
        setBrake(buffer.getFloat());
        setClutch(buffer.getFloat());
        setDisplay1(getString(buffer, 16));
        setDisplay2(getString(buffer, 16));

        if (buffer.hasRemaining()) {
            setId(buffer.getInt());
        }
    }

    public String toString() {
    	return "OutGaugeResponse[" +
    		"time=" + time + 
    		" car=" + getCar()+
    		" flags=" + getFlags() + 
    		" gear=" + getGear() +
       		" speed=" + getSpeed() + 
       		" rpm=" + getRpm() +
       		" turbo=" + getTurbo() +
        	" waterTemperature=" + getWaterTemperatur() +
        	" fuel=" + getFuel() +
        	" oilPressure=" + getOilPressure() +
        	" throttle=" + getThrottle() +
        	" brake=" + getBrake() +
        	" clutch=" + getClutch() +
        	" display1=" + getDisplay1() +
        	" display2=" + getDisplay2() +
        	" id=" + getId() + "]";
    }

    public float getBrake() {
        return brake;
    }

    public void setBrake(float brake) {
        this.brake = brake;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public float getClutch() {
        return clutch;
    }

    public void setClutch(float clutch) {
        this.clutch = clutch;
    }

    public String getDisplay1() {
        return display1;
    }

    public void setDisplay1(String display1) {
        this.display1 = display1;
    }

    public String getDisplay2() {
        return display2;
    }

    public void setDisplay2(String display2) {
        this.display2 = display2;
    }

    public short getFlags() {
        return flags;
    }

    public void setFlags(short flags) {
        this.flags = flags;
    }

    public float getFuel() {
        return fuel;
    }

    public void setFuel(float fuel) {
        this.fuel = fuel;
    }

    public byte getGear() {
        return gear;
    }

    public void setGear(byte gear) {
        this.gear = gear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getOilPressure() {
        return oilPressure;
    }

    public void setOilPressure(float oilPressure) {
        this.oilPressure = oilPressure;
    }

    public float getRpm() {
        return rpm;
    }

    public void setRpm(float rpm) {
        this.rpm = rpm;
    }


    public float getSpeed() {
        return speed;
    }
    
    public float getSpeedInMilesPerHour() {
        return speed * METERS_PER_SECOND_TO_MPH_CONSTANT;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getThrottle() {
        return throttle;
    }

    public void setThrottle(float throttle) {
        this.throttle = throttle;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public float getTurbo() {
        return turbo;
    }

    public float getTurboInPoundsPressure() {
        return turbo * ATMOSPHERIC_PRESSURE;
    }
    
    public void setTurbo(float turbo) {
        this.turbo = turbo;
    }

    public float getWaterTemperatur() {
        return waterTemperatur;
    }

    public void setWaterTemperatur(float waterTemp) {
        this.waterTemperatur = waterTemp;
    }

}
