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
public class ScreenModeRequest extends InSimRequest {

    private int set16Bit;   // set to choose 16-bit mode
    private int refreshRate; // refresh rate - zero for default
    private int width;      // Y Resolution, 0 = switch to windowed mode
    private int height;     // X Resolution, 0 = switch to windowed mode

    public ScreenModeRequest() {
    	super(PacketType.SCREEN_MODE, 20);
        setSet16Bit(0);
        setRefreshRate(0);
        setWidth(0);
        setHeight(0);
    }

    public void assemble(ByteBuffer data) {
        super.assemble(data);
        data.put((byte)0);
        data.putInt(getSet16Bit());
        data.putInt(getRefreshRate());
        data.putInt(getWidth());
        data.putInt(getHeight());
    }

    public String toString() {
      String retval = super.toString();
      
      retval += "16-bit: " + (getSet16Bit() == 1 ? "yes" : "no") + "\n";
      retval += "Refresh rate: " + getRefreshRate() + "\n";
      retval += "Resolution: " + getWidth() + "x" + getHeight() + "\n";
      
      return retval;
    }
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(int refreshRate) {
        this.refreshRate = refreshRate;
    }

    public int getSet16Bit() {
        return set16Bit;
    }

    public void setSet16Bit(int set16Bit) {
        this.set16Bit = set16Bit;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

}
