package up;


import org.atsign.client.api.AtClient;
import org.atsign.client.util.CameraUtil;
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
import javax.sound.sampled.TargetDataLine;

import static up.constants.*;
/**
 * SEND AUDIO CLASS - contains the public/shared key SendAudio methods which convert recorded audio into a string sent to the user's atServer.
 * @author Thieu Nguyen
 */
class sendAudio extends AtException implements Runnable{
    AtClient atClient;
    Keys.AtKey key;
    boolean publicKey;
    PublicKey pk;
    SharedKey sk;
    AtSign atSign, SHARED_WITH;
    String s;
    TargetDataLine targetDataLine;
    /**
     *  Send Audio method for public keys
     * @param me String of your AtSign
     * @param pk String of the public key
     */
    public sendAudio(String me, String pk){
        super("atsign exception");
        this.atSign = new AtSign(me);
        this.key = new KeyBuilders.PublicKeyBuilder(this.atSign).key(pk).build();
        this.pk = new KeyBuilders.PublicKeyBuilder(this.atSign).key(pk).build();
        this.publicKey = true;
        try {
            atClient = AtClient.withRemoteSecondary("root.atsign.org:64", this.atSign);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not get to atclient");
        }
        try {
            this.targetDataLine = (TargetDataLine) AudioSystem.getLine(TargetDataLineInfo);
            this.targetDataLine.open(format);
            this.targetDataLine.start();
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    /**
     * Send Audio method for sharedkey
     * @param myAt string of your Atsign
     * @param theirAt String of the AtSign you wish to send audio to
     * @param sk string of the sharedkey password you create
     */
    public sendAudio(String myAt, String theirAt, String sk){
        super("atsign exception");
        this.atSign = new AtSign(myAt);
        SHARED_WITH = new AtSign(theirAt);
         this.sk = new KeyBuilders.SharedKeyBuilder(atSign, SHARED_WITH).key(sk).build();
        
        this.publicKey = false;
        try {
            atClient = AtClient.withRemoteSecondary("root.atsign.org:64", this.atSign);

        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not get to atclient");
        }
        /**
         * audio device opened below
         */
        try {
            this.targetDataLine = (TargetDataLine) AudioSystem.getLine(TargetDataLineInfo); 
            this.targetDataLine.open(format);
            this.targetDataLine.start();
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    /**
     * Runnable method triggered by the start() command in the SendAudio methods
     * constantly converts audio bytes into a string and sends it to the Atserver with the specified key
     */
    public void run() {
        while (true){
            try{
                byte[] bytebuffer = new byte[20000]; 
                this.targetDataLine.read(bytebuffer, 0, bytebuffer.length); //converts the audio into a byte array
                System.out.println(bytebuffer);
                this.s = Base64.getEncoder().encodeToString(bytebuffer); // converts byte array to string
                /**
                 * pushes string to the AtServer's key destination
                 */
                if (this.publicKey){
                    atClient.put(this.pk, s);


                }
                else {
                    atClient.put(this.sk, s);
                }
                Thread.sleep(3);
            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println("Could not get images");
            }
            
        }
    }


 /**
  * Main method for testing - includes an example instance
  * @param args
  */
    public static void main( String[] args )
    {
        
        sendAudio sendAudio = new sendAudio("", "audio");
        Thread sAudio = new Thread(sendAudio);
        sAudio.start();

    }
}


