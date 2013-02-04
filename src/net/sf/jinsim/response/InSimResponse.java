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

import net.sf.jinsim.Encoding;
import net.sf.jinsim.PacketType;
import net.sf.jinsim.UDPChannel;



/**
 * An abstract class to represent a response from LFS. A response is defined as any packet sent from LFS to the InSim client.
 * 
 * @author Rob Heiser (jinsim@kerf.org)
 * @author Rene Pirringer (brilwing@liveforspeed.at)
 * @since 0.001
 * 
 */
abstract public class InSimResponse {

    protected static final int MAX_PLAYERS = 32;
	protected PacketType packetType;
    protected byte requestInfo;

    public byte getRequestInfo() {
			return requestInfo;
		}

		public InSimResponse(PacketType packetType) {
    	this.packetType = packetType;
    }
    
    /**
     * @param buffer
     *            A ByteBuffer that will be turned into a specific type of response. This ByteBuffer usually is the ByteBuffer
     *            contained within the UDP packet sent to the client. A notable exception to this rule is the {@link ErrorResponse}
     *            packet that is constructed by {@link UDPChannel} when an error occurs while listening for packets. These types of
     *            "fake" responses are created to communicate with the {@link net.sf.UDPClient}, which expects an object that
     *            can resolve to an InSimResponse type.
     * 
     * @return The fully constructed response.
     * @throws BufferUnderflowException
     */
    public void construct(ByteBuffer buffer) throws BufferUnderflowException {
    	requestInfo = buffer.get();
    }

    public String toString() {
        return "[" + getClass().getName() + "]";
    }

    /**
     * Get the response type.
     * 
     * @return The type of response this is; the four character type identifiers as defined in the InSim.txt documentation.
     */
    public PacketType getPacketType() {
        return packetType;
    }
 
    
    protected byte[] getBytes(ByteBuffer buffer, int size) {
		byte[] data = new byte[size];
		buffer.get(data);
		return data;
    }
    
	protected String getString(ByteBuffer buffer, int size) {
		byte[] data = new byte[size];
		buffer.get(data);
		//return (new String(data)).trim();
		return Encoding.decodeString(data);
	}
}
