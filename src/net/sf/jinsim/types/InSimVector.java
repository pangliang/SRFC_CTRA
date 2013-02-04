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

/**
 * Represents a three dimensional vector in LFS.
 * 
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 * @see net.sf.jinsim.response.CameraPositionResponse
 */
public class InSimVector {
    private int x;
    private int y;
    private int z;

    /**
     * Construct using a ByteBuffer.
     * 
     * @param buffer
     *            Buffer to use for object construction.
     * @throws BufferUnderflowException
     *             when the buffer doesn't have twelve more bytes left in it.
     */

    public InSimVector(ByteBuffer buffer) throws BufferUnderflowException {
        setX(buffer.getInt());
        setY(buffer.getInt());
        setZ(buffer.getInt());
    }

    /**
     * Construct using direct int values.
     * 
     * @param i
     *            distance along the x axis
     * @param j
     *            distance along the y axis
     * @param k
     *            distance along the z axis
     */
    public InSimVector(int i, int j, int k) {
        setX(i);
        setY(j);
        setZ(k);
    }

    public String toString() {
        return "InSimVector[" + x + ", " + y + ", " + z + "]";
    }

    /**
     * Get the x component of the vector
     * 
     * @return The x component of the vector where 65536 = 1 meter.
     */
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    /**
     * Get the y component of the vector
     * 
     * @return The y component of the vector where 65536 = 1 meter.
     */
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * Get the z component of the vector
     * 
     * @return The z component of the vector where 65536 = 1 meter.
     */
    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public double delta(InSimVector v2) {
        double retval = 0;

        double deltaX = (double)(v2.getX() - getX());
        double deltaY = (double)(v2.getY() - getY());
        double deltaZ = (double)(v2.getZ() - getZ());

        retval = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY) + (deltaZ * deltaZ));

        return retval;
    }
    
    public InSimVector add(InSimVector v2) {
        InSimVector retval = new InSimVector(getX() + v2.getX(), getY() + v2.getY(), getZ() + v2.getZ());
        
        return retval;
    }
}
