package up;
import javax.swing.*;
import java.awt.event.*;
import up.*;

/**
 * LAUNCH PAGE FOR PROGRAM
 * 4 Buttons in total - Send/Receive Video or Audio - 2 Textfields for AtSigns, plus a checkbox to toggle publickey sharing.
 * @author Matt Glover
 */
public class LaunchPage implements ActionListener {
   // Creates teh JFrame with the 4 options (send/receive video or audio), the public key toggle, and the textfeilds to type in the ATSigns and the key
    JFrame frame = new JFrame();
    /**
     * Send Audio Button
     */
    public JButton sAButton = new JButton("Send Audio");   
   
    /*
    * Receive Audio Button
    */
    public JButton rAButton = new JButton("Receive Audio");
    
    /**
     * Send Video Button
     */
     public JButton sButton = new JButton("Send Stream");
     
     /**
     * Receive Video Button
     */
     public JButton rButton = new JButton("View Stream");
   
     ImageIcon image = new ImageIcon(); // holds the background image

    /**
     * Your AtSign
     */
    public JTextField atMe;
     /**
     * Their AtSign
     */
    public JTextField atTheirs;
     /**
     * They Key for each user
     */
    public JTextField key;
    JLabel atMELabel = new JLabel("Your Atsign");
    JLabel atTheirLabel = new JLabel("Sender AtSign");
    JLabel atKey = new JLabel();
    JCheckBox checkbox = new JCheckBox();
     LaunchPage() {
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setSize(1000, 1000);
         frame.setTitle("Camera Stream");
 
    
         sButton.setBounds(100, 650, 120, 50); 
         sButton.addActionListener(this);
    
         rButton.setBounds(100, 700, 120, 50);
          rButton.addActionListener(this);
         
          sAButton.setBounds(220, 650, 120, 50);
          sAButton.addActionListener(this);
  
          rAButton.setBounds(220, 700, 120, 50);
          rAButton.addActionListener(this);
    
         image = new ImageIcon(getClass().getResource("atcam2.png"));
         JLabel label = new JLabel(image);
         label.setBounds(760, 100, 400, 400);
         atMELabel = new JLabel("Your AtSign");
         atMELabel.setBounds(20, 360, 300, 300);
   
   atTheirLabel = new JLabel("Their AtSign");
   atTheirLabel.setBounds(20, 490, 200, 90);
   /**
    * ATSIGN OF PROGRAM USER
    */
   atMe = new JTextField();
   atMe.setBounds(100, 500, 125, 20);
   
   atTheirs = new JTextField();
   atTheirs.setBounds(100, 525, 125, 20);
   
   atKey = new JLabel("@Key here");
   atKey.setBounds(20, 515, 100, 90);
   key = new JTextField();
   key.setBounds(100, 550, 125, 20);
  
   checkbox = new JCheckBox(); 
   checkbox.setText("Mark for public key");
     checkbox.setFocusable(false);
   checkbox.setBounds(245, 545, 200, 30);
  
  
    frame.add(sAButton);
    frame.add(rAButton);
    frame.add(checkbox);
    frame.add(key);
    frame.add(atKey);
    frame.add(atTheirLabel);
    frame.add(atMELabel);
    frame.add(atTheirs);
    frame.add(atMe);
    frame.add(sButton);
    frame.add(rButton);
    frame.add(label);
    frame.setVisible(true);
     
     }
  
     @Override   
     /**
      * The method takes the ATSigns + key strings as parameters for which ever action chose.
      * Once a button is clicked, the program also determines if the public key option is checked.
      */
     public void actionPerformed(ActionEvent e) {
         String  myAt = atMe.getText();
         String theirAt = atTheirs.getText();
         String newKey = key.getText();
         
         if (e.getSource()==sButton) { // 
  
             if(checkbox.isSelected() == true ){
             
                System.out.println(myAt + " " + newKey);
                SendVideo sendVideo = new SendVideo( myAt,newKey);
                Thread svideo = new Thread(sendVideo);
                svideo.start();
                
             }
             else {
                SendVideo sendVidSK = new SendVideo( myAt, theirAt, newKey);
              Thread svideo = new Thread(sendVidSK);
              svideo.start();
             
             }
  
         }
         else if (e.getSource()==rButton) {
           
             if(checkbox.isSelected() == true ){
  
              ReceiveVideo receiveVideo= new ReceiveVideo(theirAt, newKey);
     
              Thread rVideo = new Thread(receiveVideo);
              rVideo.start();
             }
             else {
              ReceiveVideo receiveVideo= new ReceiveVideo( myAt, theirAt, newKey);
     
              Thread rVideo = new Thread(receiveVideo);
              rVideo.start();
             }
         }
          
         else if (e.getSource()==sAButton) {
           
             if(checkbox.isSelected() == true ){
  
              sendAudio sendAudio = new sendAudio(myAt, newKey);
              Thread sAudio = new Thread(sendAudio);
              sAudio.start();
             }
             else {
              sendAudio sendAudio = new sendAudio(myAt, theirAt, newKey);
              Thread sAudio = new Thread(sendAudio);
              sAudio.start();
             }
         }
         else if (e.getSource()==rAButton) {
           
             if(checkbox.isSelected() == true ){
  
                 ReceiveAudio receive = new ReceiveAudio(theirAt, newKey);
                 Thread rec = new Thread (receive);
                 rec.start();
             }
             else {
              ReceiveAudio receive = new ReceiveAudio(myAt, theirAt, newKey);
              Thread rec = new Thread (receive);
              rec.start();
             }
         }
         
  
     }
     /**
      * Opens new Launch Frame
      * 
      */
     public static void main (String args[]){ 
      LaunchPage lp = new LaunchPage(); // begin the program!
     }
  }
  