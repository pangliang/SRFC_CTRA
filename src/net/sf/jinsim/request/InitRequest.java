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

package net.sf.jinsim.request;

import java.nio.ByteBuffer;

import net.sf.jinsim.PacketType;



/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class InitRequest extends InSimRequest {

	public static short LOCAL = 4;
	public static short KEEP_MESSAGE_COLOR = 8;
	public static short RECEIVE_NODE_LAP = 16;
	public static short RECEIVE_MULTI_CAR_INFO = 32;

    private int           udpPort;                  // Local port that server communicates with
    private short           flags;                 // InSim configuration
    private short           interval;           // Number of seconds between tracking packets (NLP or MCI)
    private String          password;              // Admin password
    private char            prefix;
    private String          name;
	private boolean requestVersion;
    
    public InitRequest() {
        super(PacketType.INSIM_INITIALIZE, (byte)44);
    }
	
		
    public void assemble(ByteBuffer data) {
    	if (requestVersion) {
        	setRequestInfo((byte)1);
        } else {
        	setRequestInfo((byte)0);
        }
    	super.assemble(data);
        data.put((byte)0); // Zero
        data.putShort((short) udpPort);
        data.putShort(flags);
        data.put((byte)0); // spare
        data.put((byte)prefix);
        data.putShort(interval);
        assembleString(data, password, 16);
        assembleString(data, name, 16);
    }

    public String toString() {
        String retval = super.toString();

        retval += "Port: " + getPort() + "\n";
        retval += "Flags: " + getFlags() + "\n";
        retval += "Node lap interval: " + getInterval() + "\n";
        retval += "Password: " + getPassword() + "\n";

        return retval;
    }

    public short getFlags() {
        return flags;
    }

    public void setFlags(short flags) {
        this.flags = flags;
    }

    public void addFlag(int flag) {
        setFlags((byte) (getFlags() | flag));
    }

    public void removeFlag(int flag) {
        setFlags((byte) (getFlags() & ~flag));
    }

    public short getInterval() {
        return interval;
    }

    public void setInterval(short interval) {
        this.interval = interval;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return udpPort;
    }

    public void setUdpPort(int port) {
        this.udpPort = port;
    }


	public char getPrefix() {
		return prefix;
	}


	public void setPrefix(char prefix) {
		this.prefix = prefix;
	}

    public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public void setRequestVersion(boolean sendVersion) {
		this.requestVersion = sendVersion;
	}
}
