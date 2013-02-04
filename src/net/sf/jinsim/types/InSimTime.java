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

package net.sf.jinsim.types;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;

/**
 * InSimTime is a compressed time representation used in some InSim messages. It uses one byte for each time unit from hours down to
 * thousandths of a second.
 * 
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 * @see net.sf.jinsim.response.LapTimeResponse
 * @see net.sf.jinsim.response.ResultResponse
 * @see net.sf.jinsim.response.SplitTimeResponse
 * 
 */
public class InSimTime {

	private static DecimalFormat formatter = new DecimalFormat("00");
	private static DecimalFormat formatterThoutands = new DecimalFormat("00");
	private static DecimalFormat formatterOne = new DecimalFormat("0");
	
    private int time;
    
    /**
     * @param buffer
     *            The buffer to use for construction of an InSimTime object.
     * @throws java.nio.BufferUnderflowException
     */
    public InSimTime(ByteBuffer buffer) throws BufferUnderflowException {
    	time = buffer.getInt();
    }
    
    public InSimTime(int time) {
    	this.time = time;
    }

    public String toString() {
    	String result = "";
    	if (getHours()>0) {
    		result += formatter.format(getHours()) + ":";
    	}
    	if (result.length() > 0 || getMinutes()>0) {
    		result += formatter.format(getMinutes()) + ":";
    	}
    	if (result.length() > 0) {
    		result += formatter.format(getSeconds());
    	} else {
    		result += formatterOne.format(getSeconds());
    	}
        return result + "." + formatterThoutands.format(getThousandths()/10);
    }
    
    public static String toString(int time) {
    	return toString(time, false);
    }
    
    public static String toString(int time, boolean sign) {
    	if (!sign) {
    		return new InSimTime(time).toString();
    	}
    	if (time < 0) {
    		return "^2-" + new InSimTime(-time).toString();
    	}
    	return  "^1+" + new InSimTime(time).toString();
    }

    /**
     * Get the hours component
     * 
     * @return The value of the hours position.
     */
    public int getHours() {
        return time/3600000;
    }

    /**
     * Get the minutes component
     * 
     * @return The value of the minutes position.
     */
    public int getMinutes() {
    	return (time%3600000) / 60000;
    }


    /**
     * Get the seconds component
     * 
     * @return The value of the seconds position.
     */
    public int getSeconds() {
        return (time%60000) / 1000;
    }


    /**
     * Get the thousandths (of a second) component
     * 
     * @return The value of the thousandths (of a second) position.
     */
    public int getThousandths() {
        return time%1000;
    }

	public int getTime() {
		return time;
	}

}
