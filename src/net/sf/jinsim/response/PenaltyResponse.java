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
import net.sf.jinsim.PenaltyReason;



public class PenaltyResponse extends PlayerResponse {
	
	private byte oldPenalty;
	private byte newPenalty;
	private PenaltyReason reason;
	
	public PenaltyReason getReason() {
		return reason;
	}

	public PenaltyResponse() {
		super(PacketType.PENALTY);
	}
	
	public void construct(ByteBuffer buffer)
			throws BufferUnderflowException {

		super.construct(buffer);
		oldPenalty = buffer.get();
		newPenalty = buffer.get();
		reason = PenaltyReason.getPenaltyReason(buffer.get());
		buffer.position(buffer.position());
	}

	public byte getNewPenalty() {
		return newPenalty;
	}

	public void setNewPenalty(byte newPen) {
		this.newPenalty = newPen;
	}

	public byte getOldPenalty() {
		return oldPenalty;
	}

	public void setOldPenalty(byte oldPen) {
		this.oldPenalty = oldPen;
	}
	
	public String toString() {
		String result = super.toString();
		result += ", oldPenalty: " + oldPenalty ;
		result += ", newPenalty: " + newPenalty ;
		result += ", reason: " + reason ;
		return result;
	}

}
