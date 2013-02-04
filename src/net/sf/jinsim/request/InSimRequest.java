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

package net.sf.jinsim.request;

import java.beans.Encoder;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import net.sf.jinsim.Encoding;
import net.sf.jinsim.PacketType;



/**
 * An abstract class for client requests to a LFS server.
 * 
 * @author rob
 * @since 0.001
 * 
 */
abstract public class InSimRequest {
	
	//private static Log log = LogFactory.getLog(InSimRequest.class);
	
    
    private PacketType   type;
    protected int         size;
    protected byte      requestInfo;

    public InSimRequest(PacketType type, int size) {
      this.type = type;
      this.size = size;
      this.requestInfo = 0;
    }

    /**
     * Get the type of request this is, as defined in the InSim.txt documentation.
     * 
     * @return a String representation of the packet type, as defined in the InSim.txt documentation.
     */
    public PacketType getType() {
        return type;
    }

    public void assemble(ByteBuffer byteBuffer) {
    	byteBuffer.clear();
        byteBuffer.put((byte)size);
        byteBuffer.put((byte)type.getId());
        byteBuffer.put(requestInfo);
    }

    public String toString() {
        return "InSimRequest of type " + type + ", requestInfo: " + requestInfo;
    }

	public void assembleString(ByteBuffer data, String string, int length) {
		byte[] destination = new byte[length];
		if (string != null) {
			byte[] stringData = Encoding.encodeString(string);
			int copySize = stringData.length;
			if (copySize > length-1) {
				copySize = length-1;
			}
			System.arraycopy(stringData, 0, destination, 0, copySize);
		}
		data.put(destination);
	}

	public void setRequestInfo(byte requestInfo) {
		this.requestInfo = requestInfo;
	}

	public int getSize() {
		return size;
	}
    
}
