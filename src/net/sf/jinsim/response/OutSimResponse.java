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

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;


import net.sf.jinsim.PacketType;
import net.sf.jinsim.types.InSimVector;


/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class OutSimResponse extends InSimResponse {

    private int   time;

    private InSimVector angularVelocity;

    private float heading;
    private float pitch;
    private float roll;

    private InSimVector acceleration;
    private InSimVector velocity;
    private InSimVector position;

    private int   id;

    public OutSimResponse() {
        super(PacketType.OUT_SIM);
    }

    public void construct(ByteBuffer buffer) throws BufferUnderflowException {
        buffer.rewind();

        setTime(buffer.getInt());

        angularVelocity = new InSimVector(buffer);
        heading = buffer.getFloat();
        pitch = buffer.getFloat();
        roll = buffer.getFloat();

        acceleration = new InSimVector(buffer);

        velocity = new InSimVector(buffer);

        position = new InSimVector(buffer);

        if (buffer.hasRemaining()) {
            setId(buffer.getInt());
        }

    }

    public String toString() {
        return "OutSim [time=" + getTime() + 
        	", angularVelocity= " + angularVelocity + ", heading= " + heading + ", pitch=" + pitch + ", roll=" + roll +
        	", acceleration=" + acceleration + ", velocity=" + velocity + ", position=" + position + ", id=" + id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

	public InSimVector getAngularVelocity() {
		return angularVelocity;
	}

	public float getHeading() {
		return heading;
	}

	public float getPitch() {
		return pitch;
	}

	public float getRoll() {
		return roll;
	}

	public InSimVector getAcceleration() {
		return acceleration;
	}

	public InSimVector getVelocity() {
		return velocity;
	}

	public InSimVector getPosition() {
		return position;
	}

}
