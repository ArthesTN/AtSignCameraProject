# AtSignCameraProject
Authors - Matt Glover & Thieu Nguyen

send video and audio over AtSign Server
Create a folder "keys" under the same directory as the pom.xml file. Put your atKey file under the "keys" folder. Go to the LaunchPage and get started
with sending and receiving with yourself!

Simple Example to send video with yourself. Put your AtSign in "Your AtSign" and "Their AtSign" fields, put "video" in key field, check for marked for
public key, then click send stream and receive stream

Each button triggers an AtClient creation for a specified Atsign, where you can create a string of data with a specifed key that is saved on the ATSign's server.

Each Class contains the code provided by the AtSign shown here; 
for public keys https://docs.atsign.com/sdk/java/public-key/
and shared keys https://docs.atsign.com/sdk/java/shared-key/

All the data methods are runnable - where the 'start' method triggers the code mentioned above, and the run method triggers the data pulling from the AtSign indefinitely.
An informal example
    
     Receive Video(TheirAtsign, yourATsign, keyname) {
     create AtClient(theirAtSign) 
      
        key = (TheirAtsign, yourATsign, keyname)
        start()
    run{
        while true {
            keyString = atclient.get(key)
            byte[] =   keyString converted to byte
            display byte
            repeat
        }
    }
}

