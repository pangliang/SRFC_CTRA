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
import java.util.ArrayList;


import net.sf.jinsim.PacketType;
import net.sf.jinsim.types.CompCar;


/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class MultiCarInfoResponse extends InSimResponse {
    
    private byte               numberCompCarPackages;
    private ArrayList<CompCar> carInfoList = new ArrayList<CompCar>(); // car info for each player, max 8 per packet (8 CarInfo objects)

    MultiCarInfoResponse() {
        super(PacketType.MULIT_CAR_INFO);
    }

    public void construct(ByteBuffer buffer) throws BufferUnderflowException {
    	super.construct(buffer);
    	numberCompCarPackages = buffer.get();

    	for (int i = 0; i < numberCompCarPackages; i++) {
            carInfoList.add(new CompCar(buffer));
        }

    }
    
      

    public String toString() {
        String value = super.toString();

        value += "numberCompCarPackages: " + numberCompCarPackages;

        for (CompCar compCar : carInfoList) {
            value += "Car: " + compCar + ", ";
        }
        return value;
    }

    public void addCarInfo(CompCar info) {
        carInfoList.add(info);
    }

    public CompCar getCarInfoAt(int n) {
        return (CompCar) carInfoList.get(n);
    }

    public ArrayList<CompCar> getCarInfoList() {
        return carInfoList;
    }


}
