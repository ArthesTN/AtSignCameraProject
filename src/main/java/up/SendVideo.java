package up;
import org.atsign.client.api.AtClient;
import org.atsign.client.util.CameraUtil;
import org.atsign.common.AtException;
import org.atsign.common.AtSign;
import org.atsign.common.KeyBuilders;
import org.atsign.common.Keys;
import org.atsign.common.Keys.PublicKey;
import org.atsign.common.Keys.SharedKey;
import java.util.Base64;
import org.atsign.client.util.ImageUtil;
import com.github.sarxos.webcam.Webcam;

/**
 * SEND VIDEO CLASS - contains methods for taking the two atsigns plus key, then pushese the captured video to the atserver.
 * @author Theiu, Matt Glover
 */
public class SendVideo extends AtException implements Runnable{
    AtClient atClient;
    Keys.AtKey key;
    AtSign atSign;
    AtSign SHARED_WITH;
    String s;
    boolean publicKey;
    PublicKey pk;
    SharedKey sk;
    /**
     * Send Video method for public keys
     * Creates the atclient instance with the key, then opens the default camera to capture an image as bytes
     * The start() function triggers the run method which constantly uploads the byte/converted string to the Atserver
     * @param me String of Your Atsign
     * @param pk String of the public Key 
     * @author Thieu Nguyen, Matt Glover
     */
    public SendVideo (String me, String pk){
        super("atsign exception");
 
        this.atSign = new AtSign(me);
        this.key = new KeyBuilders.PublicKeyBuilder(this.atSign).key(pk).build();
        this.pk = new KeyBuilders.PublicKeyBuilder(this.atSign).key(pk).build();
        this.publicKey = true;
        try {
            atClient = AtClient.withRemoteSecondary("root.atsign.org:64", this.atSign);
            System.out.println("YOU ARE STREAMING!");
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not get to atclient");
        }
        

    }
    /**
     *  Send Video method for shared keys
     * Creates the atclient instance with the key, then opens the default camera to capture an image as bytes
     * The start() function triggers the run method which constantly uploads the byte/converted string to the Atserver
     * @param me String of your Atsign
     * @param you String of the Atsign you wish to receive the stream
     * @param sk the sharedkey string you create
     */
    public SendVideo (String me, String you, String sk){
        super("atsign exception");
        this.atSign = new AtSign(me);
        SHARED_WITH = new AtSign(you);
         this.sk = new KeyBuilders.SharedKeyBuilder(atSign, SHARED_WITH).key(sk).build();
        
        this.publicKey = false;
        try {
            atClient = AtClient.withRemoteSecondary("root.atsign.org:64", this.atSign);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not get to atclient");
        }
    }
    /**
     * Runnable method triggered by the SendVideo methods
     * Constantly takes the streamed image byte, converts it to a string, then pushes it to the AtServer's key instance.
     */
    public void run() {
        while (true){
            try{

            byte[] byteArray = ImageUtil.toByteArray(CameraUtil.getSingleImage()); // bytes of the streamed image
            this.s = Base64.getEncoder().encodeToString(byteArray); // string of converted byte
            if (this.publicKey){
                atClient.put(this.pk, s); 
            }
            else {
                atClient.put(this.sk, s);
            }
            Thread.sleep(120);
            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println("Could not get images");
            }
            
        }
    }
    public static void main( String[] args )
    {
        // string atsign, string key
        SendVideo sendVideo = new SendVideo("","demo");
        Thread svideo = new Thread(sendVideo);
        svideo.start();
        
    }
}