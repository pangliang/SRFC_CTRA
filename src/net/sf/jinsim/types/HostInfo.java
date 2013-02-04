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

package net.sf.jinsim.types;

import net.sf.jinsim.Track;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class HostInfo {
	
	private String  hostname;
	private Track track;
	private boolean passwordRequired;
	private boolean licensed;
	private int numberConnections;
	
	
	public HostInfo(String name, Track track, boolean licensed,
			boolean requirePassword, byte numberConnections) {
		
		this.hostname = name;
		this.track = track;
		this.licensed = licensed;
		this.passwordRequired = requirePassword;
		this.numberConnections = numberConnections;
	}
	
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public Track getTrack() {
		return track;
	}
	public void setTrack(Track track) {
		this.track = track;
	}
	public boolean isRequiresPassword() {
		return passwordRequired;
	}
	public void setRequiresPassword(boolean requiresPassword) {
		this.passwordRequired = requiresPassword;
	}
	public boolean isLicensed() {
		return licensed;
	}
	public void setLicensed(boolean licensed) {
		this.licensed = licensed;
	}

	public int getNumberConnections() {
		return numberConnections;
	}

	public void setNumberConnections(int numberConnections) {
		this.numberConnections = numberConnections;
	}
  
	public String toString() {
		return "HostInfo[hostname=" + hostname + ", track=" + track + ", licenced=" + licensed + ", requiresPassword=" + passwordRequired +
			", numberConnections= " + numberConnections + "]";
	}
  
}
