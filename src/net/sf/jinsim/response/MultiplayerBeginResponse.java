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

import java.nio.ByteBuffer;

import net.sf.jinsim.PacketType;



/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class MultiplayerBeginResponse extends InSimResponse {
	
	public static final String TYPE = "ISM";


  private byte   host; // 0 = guest / 1 = host
  private String name; // the name of the host joined or started (32 len)

  MultiplayerBeginResponse() {
  	super(PacketType.START_MULTIPLAYER);
  }

  public void construct(ByteBuffer buffer) {
  	super.construct(buffer);
  	buffer.position(buffer.position()+1);
    setHost(buffer.get());
    buffer.position(buffer.position()+3);
    setName(getString(buffer, 32));
  }

  public String toString() {
    String retval = super.toString();
    retval += "Type: " + (getHost() == 0 ? "guest" : "host") + "\n";
    retval += "Server: " + (getName().equals("") ? "Empty (not in mp mode)" : getName()) + "\n";
    return retval;
  }

  public byte getHost() {
    return host;
  }

  public void setHost(byte host) {
    this.host = host;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
