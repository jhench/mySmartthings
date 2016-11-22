/**
 *  Copyright 2015 SmartThings
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
metadata {
	definition (name: "PC Sleep Control", namespace: "smartthings/testing", author: "jhench") {
		capability "Actuator"
		capability "Button"
		capability "Sensor"
        
        command "sleep"
        command "wake"
	}

	simulator {

	}
	tiles {
		standardTile("button", "device.button", width: 1, height: 1, canChangeIcon: true) {
			state "default", label: "", icon: "st.Entertainment.entertainment13", backgroundColor: "#ffffff"
		}
 		standardTile("sleep", "device.button", width: 1, height: 1, decoration: "flat") {
			state "default", label: "Sleep", backgroundColor: "#ffffff", action: "sleep"
		} 
 		standardTile("wake", "device.button", width: 1, height: 1, decoration: "flat") {
			state "default", label: "Wake Up", backgroundColor: "#ffffff", action: "wake"
		}          
		main "button"
		details(["button","sleep","wake"])
	}
}

def parse(String description) {
	
}

def sleep() {
//	sendEvent(name: "button", value: "sleep", data: [buttonNumber: "1"], descriptionText: "$device.displayName Sleep button was pushed", isStateChange: true)
//Manual Eventghost call
	def egHost = "192.168.0.42:78"
	def egRawCommand = "ST.PCPower.Sleep"
	def egRestCommand = java.net.URLEncoder.encode(egRawCommand)
	log.debug "processed sleep button from device $device.label"
	log.debug "egRestCommand:  $egRestCommand"
	sendHubCommand(new physicalgraph.device.HubAction("""GET /?$egRestCommand HTTP/1.1\r\nHOST: $egHost\r\n\r\n""", physicalgraph.device.Protocol.LAN))

} 

def wake() {
    def result = new physicalgraph.device.HubAction (
        "wake on lan 90E6BA82C48D",
        physicalgraph.device.Protocol.LAN,
        null,
        [secureCode: "111122223333"]
    )
    return result
}