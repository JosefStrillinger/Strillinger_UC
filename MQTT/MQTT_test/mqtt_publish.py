import paho.mqtt.client as mqtt
import time, sys

current_mode = False


# Diese paar Zeilen Code reichen aus um etwas an einen Channel zu senden
client = mqtt.Client('X12') #Der Parameter ist die client-ID, diese sollte möglichst eindeutig sein.
client.username_pw_set("josef","Jstrillinger22")
client.connect('127.0.0.1', port=2222) #Im Moment verwenden wir die lokale mosquitto Installation, spaeter durch die IP zu ersetzen

while True:
    client.publish("house/light", "ON" if not current_mode else "OFF")  #An den Channel house/light wird die Nachricht "ON" gesendet
    print("Published: " + "ON" if not current_mode else "OFF")
    current_mode = not current_mode
    #print(sys.path)
    time.sleep(1)
