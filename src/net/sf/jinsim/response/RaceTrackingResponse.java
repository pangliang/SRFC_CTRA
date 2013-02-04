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
 * Portions created by Rob Heiser are Copyright (C) 2006
 * Rob Heiser. All Rights Reserved.
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
 * The base class for responses from InSim related to race tracking. Race tracking is turned on during intialization with the {@link
 * net.sf.jinsim.request.InitRequest} type ("ISI"). It will periodically send information about the race to the client. All
 * responses of this type are acknowledged automatically with and {@link net.sf.request.AckRequest} with the proper value
 * field filled in.
 * 
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 * 
 */
abstract public class RaceTrackingResponse extends InSimResponse {
	
    /**
     * When set, indicates the driver side has "swapped". LFS defaults to a righthand drive, so if this flag is set the car has been
     * changed to lefthand drive.
     */
    static public final int PLAYER_FLAG_SWAPSIDE       = 1;
    /**
     * When set, indicates that the player has turned on the option for LFS to cut the throttle automatically during gear changes.
     */
    static public final int PLAYER_FLAG_GEAR_CHANGE_CUT  = 2;
    /**
     * When set, indicates the player has turned on the option for LFS to blip the throttle automatically during gear changes.
     */
    static public final int PLAYER_FLAG_GEAR_CHANGE_BLIP = 4;
    /**
     * When set, indicates the player has turned on automatic shifting.
     */
    static public final int PLAYER_FLAG_AUTOGEARS      = 8;
    /**
     * Undocumented reserved flag.
     */
    static public final int PLAYER_FLAG_SHIFTER     = 16;
    static public final int PLAYER_FLAG_REVERSED      = 32;
    /**
     * When set, indicates the player has turned on braking help.
     */
    static public final int PLAYER_FLAG_HELP_BRAKE     = 64;
    /**
     * When set, indicates the player has turned on throttle help.
     */
    static public final int PLAYER_FLAG_AXIS_CLUTCH  = 128;
    /**
     * Undocumented reserved flag.
     */
    static public final int PLAYER_FLAG_IN_PITS     = 256;
    /**
     * When set, indicates the player has turned on automatic clutch help while shifting.
     */
    static public final int PLAYER_FLAG_AUTO_CLUTCH     = 512;
    /**
     * When set, indicates the player is using a mouse as his controller.
     */
    static public final int PLAYER_FLAG_MOUSE          = 1024;
    /**
     * When set, indicates the player is using the keyboard as his controller, with no stabilization help.
     */
    static public final int PLAYER_FLAG_KEYBOARD_NO_HELP     = 2048;
    /**
     * When set, indicates the player is using the keyboard as his controller with stabilization help.
     */
    static public final int PLAYER_FLAG_KEYBOARD_STABILISED  = 4096;

    static public final int PLAYER_FLAG_CUSTOM_VIEW = 8192;
    
    

    static public final int CONFIRMATION_MENTIONED   = 1;
    static public final int CONFIRMATION_CONFIRMED   = 2;
    static public final int CONFIRMATION_PENALTY_DRIVE_THROUGH  = 4;
    static public final int CONFIRMATION_PENALTY_STOP_AND_GO  = 8;
    static public final int CONFIRMATION_PENALTY_TIME_30  = 16;
    static public final int CONFIRMATION_PENALTY_TIME_45  = 32;
    static public final int CONFIRMATION_DID_NOT_PIT = 64;
    
    static public final int CONFIRMATION_DISQUALIFIED = CONFIRMATION_PENALTY_DRIVE_THROUGH | CONFIRMATION_PENALTY_STOP_AND_GO | CONFIRMATION_DID_NOT_PIT;
    static public final int CONFIRMATION_TIME = CONFIRMATION_PENALTY_TIME_30 | CONFIRMATION_PENALTY_TIME_45;
	private byte id;

    RaceTrackingResponse(PacketType type) {
    	super(type);
    }

    
    
    
		@Override
		public void construct(ByteBuffer buffer) throws BufferUnderflowException {
			super.construct(buffer);
			id = buffer.get();
		}




		public byte getId() {
			return id;
		};

		public String toString() {
			return super.toString() + ", Unique ID: " + getId();
		}
   
}
