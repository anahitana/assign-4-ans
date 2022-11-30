package sim;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.awt.event.*;
import javax.swing.Timer.*;
import javax.swing.JTextField.*;
import java.awt.GridLayout;


/**
 * Write a description of class SimController here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SimController
{
    private static JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
    private JFrame frame;
    private Simulator sim;
    private Timer time;
    private JLabel statusLabel;
    private JTextField delaytxt;
    private JLabel population;
    
    
    public SimController(){
        sim = new Simulator();
        makeFrame();
    }
    
    private void runSim(){
        sim.simulateOneStep();
        population.setText(sim.getDetails());
    }
    
    private void reset(){
        sim.reset();
        population.setText(sim.getDetails());
    }
    
    private void makeMenuBar(JFrame frame){
        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);
        
        JMenu fileMenu = new JMenu("File");
        menubar.add(fileMenu);
        
        JMenuItem ldSettings = new JMenuItem("Load Settings");
        ldSettings.addActionListener(e -> openFile());
        fileMenu.add(ldSettings);
        
        JMenuItem svSettings = new JMenuItem("Save Settings");
        svSettings.addActionListener(e -> saveFile());
        fileMenu.add(svSettings);
        fileMenu.addSeparator();
        
        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(e -> quit());
        fileMenu.add(quit);
    }
    
    public void startTimer(){
        statusLabel.setText("Sim Running");
        time.start();
    }
    
    private void stopTimer(){
        statusLabel.setText("Sim NOT Running");
        time.stop();
    }
    
    private void oneStep(){
        statusLabel.setText("Sim Running");
        sim.simulateOneStep();
        statusLabel.setText("Sim NOT Running");
    }
    
    private void increaseDelay(){
        time.setDelay(time.getDelay()*2);
        delaytxt.setText(String.valueOf(time.getDelay()));
    }
    
    private void decreaseDelay(){
        time.setDelay(time.getDelay()/2);
        delaytxt.setText(String.valueOf(time.getDelay()));
    }
    
    private void openFile(){
        int returnVal = fileChooser.showOpenDialog(frame);
        
        if(returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File selectedFile = fileChooser.getSelectedFile();

        if(selectedFile != null) {
            JOptionPane.showMessageDialog(frame,
                    "Not implemented.",
                    "Not implemented.",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    
    private void saveFile(){
        int returnVal = fileChooser.showOpenDialog(frame);
        
        if(returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File selectedFile = fileChooser.getSelectedFile();

        if(selectedFile != null) {
            JOptionPane.showMessageDialog(frame,
                    "Not implemented.",
                    "Not implemented.",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    
    private void makeFrame(){
        population = new JLabel(sim.getDetails());
        time = new Timer(500, e -> runSim());
        frame = new JFrame("Sim Control");
        frame.setSize(500, 490);
        makeMenuBar(frame);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 4, 5, 10));
        panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        
        
        statusLabel = new JLabel("Sim running");
        panel.add(statusLabel);
        
        JButton r = new JButton("Run");
        r.addActionListener(b -> startTimer());
        panel.add(r);
        
        JButton s = new JButton("Stop");
        s.addActionListener(b -> stopTimer());
        panel.add(s);
        
        JButton stp = new JButton("Step");
        stp.addActionListener(b -> oneStep());
        panel.add(stp);
        
        JButton slw = new JButton("Slower");
        slw.addActionListener(b -> increaseDelay());
        panel.add(slw);
        
        JButton fst = new JButton("Faster");
        fst.addActionListener(b -> decreaseDelay());
        panel.add(fst);
        
        delaytxt = new JTextField(String.valueOf(time.getDelay()));
        panel.add(delaytxt);
        
        JButton runTo = new JButton("Run to");
        panel.add(runTo);
        
        JButton rst = new JButton("Reset");
        rst.addActionListener(b -> reset());
        panel.add(rst);
        
        
        panel.add(population);
        
        frame.add(panel);        
        frame.pack();
        frame.setVisible(true);
    }
    
    public void quit(){
        time.stop();
        sim.endSimulation();
        frame.setVisible(false);
        frame.dispose();
    }
    
    public Simulator getSimulator(){
        return sim;
    }
}
