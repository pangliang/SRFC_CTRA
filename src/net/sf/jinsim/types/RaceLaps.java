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

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * The RaceLaps class holds information about the current lap as given in some response packets. It uses the class to hold more than
 * just the lap number, it also indicates if the laps are during practice, or if it's a time based race, rather than a lap based
 * race, the number of hours the race has been run.
 * 
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 * @see net.sf.jinsim.response.StateResponse
 * @see net.sf.jinsim.response.RaceStartResponse
 * 
 */
public class RaceLaps {
    boolean isPractice;
    boolean isTimedRace;
    int     laps;

    public RaceLaps(ByteBuffer buffer) throws BufferUnderflowException {
        byte readLap = buffer.get();

        if (readLap == 0) {
            isPractice = true;
            isTimedRace = false;
            laps = 0;
        } else if (readLap > 190) {
            isPractice = false;
            isTimedRace = true;
            laps = readLap - 190;
        } else if (readLap > 100) {
            isPractice = false;
            isTimedRace = false;
            laps = (readLap - 100) * 10 + 100;
        } else {
            isPractice = false;
            isTimedRace = false;
            laps = readLap;
        }
    }

    public String toString() {
        String retval = "";

        if (isPractice()) {
            retval += "Practice Session";
        } else if (isTimedRace()) {
            retval += getLaps() + " hours.";
        } else {
            retval += getLaps() + " laps.";
        }

        return retval;
    }

    public boolean isPractice() {
        return isPractice;
    }

    public void setPractice(boolean isPractice) {
        this.isPractice = isPractice;
    }

    public boolean isTimedRace() {
        return isTimedRace;
    }

    public void setTimedRace(boolean isTimedRace) {
        this.isTimedRace = isTimedRace;
    }

    public int getLaps() {
        return laps;
    }

    public void setLaps(int laps) {
        this.laps = laps;
    }
}
