package up;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.SourceDataLine;
import org.atsign.common.AtSign;

/**
 * Class of all the constants used between each executable 
 * @author Thieu Nguyen
 */
public final class constants{
    private constants(){
    }
     /**
     * Root Server Destination to create AtClients + Keys
     */
    public static final String ROOT_URL = "root.atsign.org:64";

     /**
     *String of Atsign of person sending data
     */
    public static final String ATSIGN_STR_SHARED_BY = ""; 

     /**
     * String of AtSign of the recipient of the data
     */
    public static final String ATSIGN_STR_SHARED_WITH = ""; 

     /**
     * ATtSign object of data sender
     */
    public static final AtSign sharedBy = new AtSign(ATSIGN_STR_SHARED_BY); 

       /**
     * ATtSign object of data receiver
     */
    public static final AtSign sharedWith = new AtSign(ATSIGN_STR_SHARED_WITH);
    public static final boolean VERBOSE = true; 
    public static final String KEY_NAME_VIDEO = "video";
    public static final String KEY_NAME_AUDIO = "audio";

       /**
     * Length of time the AtServer will host the key (30 min default)
     */
    public static final int ttl = 30 * 60 * 1000; // 30 minutes
    public static final boolean status = true;
    public static final int sampleRate = 44100;
    public static final int channels = 2;
    public static final int sampleSize = 16;
    public static final boolean bigEndian = true;
    public static final AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
    /**
     * Audioformat specifies how the audio is recorded in the SendAudio class
     */
    public static final AudioFormat format = new AudioFormat(encoding, sampleRate, sampleSize, channels, (sampleSize / 8) * channels, sampleRate, bigEndian);
    public static final DataLine.Info TargetDataLineInfo = new DataLine.Info(TargetDataLine.class, format);
    public static final DataLine.Info SourceDataLineInfo = new DataLine.Info(SourceDataLine.class, format);
    
}