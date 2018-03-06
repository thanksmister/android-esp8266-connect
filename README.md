# ESP8266 Connect Application

An Android application that connects to an ESP8266 WiFi module through a shared access point and sends command to the server to turn on and off a light.  See the accompanying [Arduino project](https://github.com/thanksmister/arduino-ESP8266-connect) for the ESP8266 for software and hardware requirements to run the project. 

[Hackster Project](https://www.hackster.io/thanksmister/android-to-esp8266-comunication-a84f50)

# Hardware Requirements

- Android Device running Android SDK 15 or above
- ESP8266 and other hardware (see the [Arduino project](https://github.com/thanksmister/arduino-ESP8266-connect)).

# Software Requirements

- Android IDE if you wish to edit the code, otherwise side load the APK file from the release secton. 
- Android SDK 15 or above

# Setup

From the release section download the APK file from the Android Github repository release section and side load onto your mobile device.  If you have the ESP8266 project setup and running, the Android application will be able to automatically connect to the access point using the default IP address, access point name and password.   These can be changed in the application settings.  

Open the installed application on your device and select the "Connect" button.  This will connect to the access point with the default name and password.  If you wish to change these, you can do so under the settings from the drop down menu.  

Once you connect, you can use the "Send" buttons below the values to toggle on/off the LED light. Use the "Disconnect" button to disconnect from the access point and return reconnect to your previous WiFi connection.  

![ESP8266 Connect](https://github.com/thanksmister/android-esp8266-connect/blob/master/connect.png)


