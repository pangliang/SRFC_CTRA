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
import java.util.ArrayList;

import net.sf.jinsim.PacketType;



/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @author Rene Pirringer (brilwing@liveforspeed.at)
 * @since 0.001
 */
public class ReorderResponse extends InSimResponse {
	
	private ArrayList<Byte> playerPositions = new ArrayList<Byte>(MAX_PLAYERS);
    private byte      numberPlayers;

    ReorderResponse() {
        super(PacketType.REORDER);
    }

    public void construct(ByteBuffer buffer) throws BufferUnderflowException {
        super.construct(buffer);
        numberPlayers = buffer.get();
    	for (int i = 1; i < MAX_PLAYERS; i++) {
            playerPositions.add(new Byte(buffer.get()));
        }
    }

    public String toString() {
        String value = super.toString();
        value += "Number of players: " + getNumberPlayers();
        value += ", positions: ";
        for(Byte position : playerPositions) {
        	value += position +  ", ";
        }
        return value;
    }

    public byte getPlayerPositionAt(int n) {
        return playerPositions.get(n).byteValue();
    }

    public ArrayList<Byte> getPlayerPositions() {
        return playerPositions;
    }


    public byte getNumberPlayers() {
        return this.numberPlayers;
    }

    public void setNumberPlayers(byte numPlayers) {
        this.numberPlayers = numPlayers;
    }


}
