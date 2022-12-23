package up;


import org.atsign.client.api.AtClient;
import org.atsign.common.AtException;
import org.atsign.common.AtSign;
import org.atsign.common.KeyBuilders;
import org.atsign.common.Keys;
import org.atsign.common.Keys.AtKey;
import org.atsign.common.Keys.PublicKey;
import java.util.Base64;
import org.atsign.common.Keys.SharedKey;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import static up.constants.*;
 /**
  * Converts the bytes recorded from the default microphone and converts to a string, then pushes the string to the atclient server
  * Contains The Receive Audio method for both public and shared key 
  @author Thieu Nguyen
 */
public class ReceiveAudio extends AtException implements Runnable{
    AtClient atClient;
    Keys.AtKey key;
    boolean publicKey;
    PublicKey pk;
    SharedKey sk;
    AtSign atSign, SHARED_WITH;
    String s;
    String prev;
    SourceDataLine sourceDataLine;
    /**
     * 
     * @param theirAt the atSign string of the person sending audio
     * @param pk the public key password created by the sender
     */
    public ReceiveAudio(String  theirAt, String pk){
        super("atsign exception");
        this.atSign = new AtSign(theirAt);
        this.key = new KeyBuilders.PublicKeyBuilder(this.atSign).key(pk).build();
        this.pk = new KeyBuilders.PublicKeyBuilder(this.atSign).key(pk).build();
        this.publicKey = true;
        this.prev = null;
        AtSign MyAtsign = new AtSign("@22easy");
        try {
            atClient = AtClient.withRemoteSecondary("root.atsign.org:64", MyAtsign);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not get to at client");
        }
        try {
            this.sourceDataLine = (SourceDataLine) AudioSystem.getLine(SourceDataLineInfo);
            this.sourceDataLine.open(format);
            this.sourceDataLine.start();
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    /**
     * 
     * @param me Your Atsign
     * @param theirAt Their Atsign
     * @param sk The sharedkey created by the sender
     */
    public ReceiveAudio(String me, String theirAt, String sk){
        super("atsign exception");
        this.atSign = new AtSign(me);
        SHARED_WITH = new AtSign(theirAt);
         this.sk = new KeyBuilders.SharedKeyBuilder(atSign, SHARED_WITH).key(sk).build(); // key instance built on the AtServer
        
        this.publicKey = false;
        AtSign MyAtsign = new AtSign("@22easy");
        try {
            atClient = AtClient.withRemoteSecondary("root.atsign.org:64", MyAtsign);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not get to at client");
        }
        try {
            this.sourceDataLine = (SourceDataLine) AudioSystem.getLine(SourceDataLineInfo); 
            this.sourceDataLine.open(format);
            this.sourceDataLine.start();
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    /**
     * The run method here triggers the specified Receive Audio method to start recording, then triggers the while loop below to constantly push the data to the AtServer key.
     */
    public void run() {
        while (true){
            try{
                String data = null;
                if (this.publicKey){
                    String command = "plookup:bypassCache:true:" + this.pk.name + this.pk.sharedBy.toString(); // Code given by Jeremy to bypass errors
                    data = this.atClient.executeCommand(command, false).data; 
                }
                else {
                    data = this.atClient.get(sk).get(); // pulling the string from the AtSign
                }
                if (!data.equals(prev)) {
                prev = data;
                byte[] bytes = Base64.getDecoder().decode(data); // converting the string to a byte array
                System.out.println(bytes);
                sourceDataLine.write(bytes, 0, bytes.length); // plays the audio on default device.
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println("Could not get audio");
            }
            
        }
    }

    /**
     * Main function we use to test - you can input the atsigns and keys directly; we included an example in the method.
     * @param args
     */
    public static void main( String[] args )
    {
        
        ReceiveAudio receive = new ReceiveAudio("", "test3");
        Thread rec = new Thread (receive);
        rec.start();
    }
}