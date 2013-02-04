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
import java.util.HashMap;


import net.sf.jinsim.PacketType;
import net.sf.jinsim.response.relay.HostListResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class ResponseFactory {
	public static final Log log = LogFactory.getLog(ResponseFactory.class);

    private HashMap<PacketType, Class<? extends InSimResponse>>             registeredTypes;

    static private ResponseFactory instance;

    private ResponseFactory() {
        registeredTypes = new HashMap<PacketType, Class<? extends InSimResponse>>(53);
        
        
        registeredTypes.put(PacketType.NONE, NoneResponse.class);
        registeredTypes.put(PacketType.VERSION, VersionResponse.class);
        registeredTypes.put(PacketType.CONNECTION_PLAYER_RENAMED, ConnectionPlayerRenameResponse.class);
        registeredTypes.put(PacketType.TINY, TinyResponse.class);
        registeredTypes.put(PacketType.SMALL, SmallResponse.class);
        registeredTypes.put(PacketType.STATE, StateResponse.class);
        registeredTypes.put(PacketType.CAMERA_POSITION, CameraPositionResponse.class);
        registeredTypes.put(PacketType.START_MULTIPLAYER, MultiplayerBeginResponse.class);
        registeredTypes.put(PacketType.MESSAGE_OUT, MessageResponse.class);
        registeredTypes.put(PacketType.HIDDEN_MESSAGE, HiddenMessageResponse.class);
        registeredTypes.put(PacketType.VOTE_NOTIFICATION, VoteNotificationResponse.class);
        registeredTypes.put(PacketType.RACE_START, RaceStartResponse.class);
        registeredTypes.put(PacketType.NEW_CONNECTION, NewConnectionResponse.class);
        registeredTypes.put(PacketType.CONNECTION_LEFT, ConnectionLeaveResponse.class);
        registeredTypes.put(PacketType.CONNECTION_PLAYER_RENAMED, ConnectionPlayerRenameResponse.class);
        registeredTypes.put(PacketType.NEW_PLAYER, NewPlayerResponse.class);
        registeredTypes.put(PacketType.PLAYER_PIT, PlayerPitsResponse.class);
        registeredTypes.put(PacketType.PLAYER_LEAVE, PlayerLeavingResponse.class);
        registeredTypes.put(PacketType.LAP, LapTimeResponse.class);
        registeredTypes.put(PacketType.SPLIT, SplitTimeResponse.class);
        registeredTypes.put(PacketType.PIT, PitStopResponse.class);
        registeredTypes.put(PacketType.PIT_FINISHED, PitStopFinishedResponse.class);
        registeredTypes.put(PacketType.PIT_LANE, PitLaneResponse.class);
        registeredTypes.put(PacketType.CAMERA_CHANGED, CameraChangedResponse.class);
        registeredTypes.put(PacketType.PENALTY, PenaltyResponse.class);
        registeredTypes.put(PacketType.TAKE_OVER_CAR, TakeOverCarResponse.class);
        registeredTypes.put(PacketType.FLAG, FlagResponse.class);
        registeredTypes.put(PacketType.PLAYER_FLAGS, PlayerFlagResponse.class);
        registeredTypes.put(PacketType.FINISHED_RACE, FinishedRaceResponse.class);
        registeredTypes.put(PacketType.RESULT_CONFIRMED, ResultResponse.class);
        registeredTypes.put(PacketType.REORDER, ReorderResponse.class);
        registeredTypes.put(PacketType.NODE_LAP, NodeLapInfoResponse.class);
        registeredTypes.put(PacketType.MULIT_CAR_INFO, MultiCarInfoResponse.class);
        registeredTypes.put(PacketType.CAR_RESET, CarResetResponse.class);
        registeredTypes.put(PacketType.BUTTON_FUNCTION,ButtonFunctionResponse.class);
        registeredTypes.put(PacketType.BUTTON_CLICKED, ButtonClickedResponse.class);
        registeredTypes.put(PacketType.BUTTON_TYPED, ButtonTypeResponse.class);
        registeredTypes.put(PacketType.RELAY_HOST_LIST_INFO, HostListResponse.class);
        registeredTypes.put(PacketType.AUTOCROSS_LAYOUT, AutocrossLayoutResponse.class);
        registeredTypes.put(PacketType.AUTOCROSS_HIT, AutocrossHit.class);
    }


	static public ResponseFactory getInstance() {
        if (instance == null) {
            instance = new ResponseFactory();
        }
        return instance;
    }

    public InSimResponse getPacketData(ByteBuffer buffer) throws UnhandledPacketTypeException, BufferUnderflowException, InstantiationException, IllegalAccessException {
    	InSimResponse insimResponse = null;
    	/*
    	if (buffer.limit() == 91) {
    		// is OutGauge message
    		insimResponse = new OutGaugeResponse();
    	} else if (buffer.limit() == 63) {
    		// is OutSim message
    		
    		log.debug("OutSim is not processed");
    	} else 
    	 */
    		if (buffer.limit() >= 3){
    		int packetId = buffer.get() & 0xFF;
            PacketType packetType = PacketType.getPacket(packetId);
            /*
            if (log.isDebugEnabled()) {
            	log.debug("Packet is of type " + packetType);
            }
            */
            Class<? extends InSimResponse> insimResponseClass = (Class<? extends InSimResponse>) registeredTypes.get(packetType);
            if (insimResponseClass == null) {
            	buffer.position(buffer.limit());
            	throw new UnhandledPacketTypeException(packetId +  ": is unkown");
            }
            insimResponse = (InSimResponse)insimResponseClass.newInstance();
    	} else {
    		if (log.isDebugEnabled()) {
    			String bufferBytes = "";
    			for (int i=0; i<buffer.limit(); i++) {
    				bufferBytes += buffer.get() + ", ";
    			}
    			log.debug("unknown packet: " + bufferBytes);
    		} else {
    			buffer.position(buffer.limit());
    		}
    	}
    	
    	if (insimResponse == null) {
    		throw new UnhandledPacketTypeException("Can not identify response packet");
    	}
    	try {
        insimResponse.construct(buffer);
    	} catch (BufferUnderflowException ex) {
    		log.error(ex);
    	}
    	/*
        if (log.isDebugEnabled()) {
        	log.debug("InSimResponse {packet size= " + (buffer.limit()+1) + "}: " + insimResponse);
        }
        */
        return insimResponse;
    }

}
