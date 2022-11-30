package grading;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.*;
import javax.swing.JDialog;
import java.io.File;
import java.io.IOException;

import java.io.BufferedReader;
import java.nio.file.*;

import sim.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.*;

@ExtendWith(GradingTestWatcher.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@GradeValue(24)
public class MenuTest {
    SimController simC;
    Simulator sim;
    JFrame mjf;

    HashMap<String,Component> cIdents;
    List<Component> allComponents;
    private int dupno = 0;

    void pause()
    {
        // System.out.print("Press any key to continue . . . ");
        // scan.nextLine();
        JOptionPane.showMessageDialog(mjf,"pausing");
    }

    void sleep(int ms)
    {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    @BeforeEach
    public void setup(){
        simC = new SimController();
        sim = simC.getSimulator();

        Frame[] jf = JFrame.getFrames();

        // get all components

        Optional<Frame> scf = Arrays.stream(jf).filter(f->f.getTitle().equals("Sim Control")).findAny();
        mjf = (JFrame) scf.get();
        allComponents = SwUtils.getAllComponents(mjf);

        // Move from allComponents to Hashmap; decide what to identify them as
        cIdents = new HashMap<>();
        for(Component c: allComponents){
            String ident = SwUtils.cIdent(c);
            if( cIdents.containsKey(ident) ) ident = "duplicate" + dupno++;
            Component old =  cIdents.putIfAbsent(ident, c);
        }

    }

    @AfterEach
    public void teardown(){
        mjf.setTitle("Dead Controller"); // prevent re-activation
        simC.quit();

    }

    JDialog mydialog=null;
    @Test
    @Order(42)
    @GradeValue(1)
    public void loadCancel() {
        //javax.swing.Timer t = new Timer(10, ()-> menuItem24B);

        
        // for(String s : cIdents.keySet() ) System.out.println(s);
        assertTrue(cIdents.containsKey("File"));
        JMenu fm = (JMenu) cIdents.get("File");
        JMenuItem lsd = (JMenuItem) fm.getMenuComponent(0);
        assertEquals("Load Settings", lsd.getText());

        javax.swing.Timer t = new javax.swing.Timer(1000, e -> {
                        for(Window f: mjf.getOwnedWindows() ) {
                            // System.out.println(f.getClass());
                            if( f instanceof JDialog)
                                mydialog = (JDialog) f;
                        }
                        //System.out.println(mydialog.getTitle());
  
                        List<Component> clist = SwUtils.getAllComponents(mydialog);
                        for(Component c : clist ){
                            if( c instanceof JButton ){
                                JButton cb = (JButton) c;
                                if( cb.getText() != null && cb.getText().equals("Cancel") ){
                                    //System.out.println("Found Cancel Button");
                                    clearDialog();
                                    cb.doClick();
                                }
                                    
                            }
                        }
                        // kill the dialog here
                        mydialog.dispose();

                });
        t.setRepeats(false);
        t.start();
        lsd.doClick();
        // get the file picker

    }
    
    @Test
    @Order(43)
    @GradeValue(1)
    public void saveOpen() {
        //javax.swing.Timer t = new Timer(10, ()-> menuItem24B);

        
        // for(String s : cIdents.keySet() ) System.out.println(s);
        assertTrue(cIdents.containsKey("File"));
        JMenu fm = (JMenu) cIdents.get("File");
        JMenuItem lsd = (JMenuItem) fm.getMenuComponent(1);
        assertEquals("Save Settings", lsd.getText());

        javax.swing.Timer t = new javax.swing.Timer(1000, e -> {
                        for(Window f: mjf.getOwnedWindows() ) {
                            // System.out.println(f.getClass());
                            if( f instanceof JDialog)
                                mydialog = (JDialog) f;
                        }
                        //System.out.println(mydialog.getTitle());
  
                        List<Component> clist = SwUtils.getAllComponents(mydialog);
                        for(Component c : clist ){
                            if( c instanceof JButton ){
                                JButton cb = (JButton) c;
                                if( cb.getText() != null && cb.getText().equals("Open") ){
                                    //System.out.println("Found Open Button");
                                    clearDialog();
                                    cb.doClick();
                                }
                                    
                            }
                        }
                        // kill the dialog here
                        mydialog.dispose();

                });
        t.setRepeats(false);
        t.start();
        lsd.doClick();
        // get the file picker

    }
    @Test
    @Order(44)
    @GradeValue(2)
    public void openWithDialog() throws IOException {
        //javax.swing.Timer t = new Timer(10, ()-> menuItem24B);

        
        // for(String s : cIdents.keySet() ) System.out.println(s);
        assertTrue(cIdents.containsKey("File"));
        JMenu fm = (JMenu) cIdents.get("File");
        JMenuItem lsd = (JMenuItem) fm.getMenuComponent(1);
        assertEquals("Save Settings", lsd.getText());

        javax.swing.Timer t = new javax.swing.Timer(1000, e -> {
                        JButton cb = null;
                        for(Window f: mjf.getOwnedWindows() ) {
                            // System.out.println(f.getClass());
                            if( f instanceof JDialog)
                                mydialog = (JDialog) f;
                        }
                        //System.out.println(mydialog.getTitle());
  
                        List<Component> clist = SwUtils.getAllComponents(mydialog);
                        for(Component c : clist ){
                            if( c instanceof JFileChooser ){
                                JFileChooser myChooser = (JFileChooser) c;
                                myChooser.setSelectedFile(new File("Buddy"));
                            }
                            if( c instanceof JButton ){
                                cb = (JButton) c;
                                if( cb.getText() != null && cb.getText().equals("Open") ){
                                    
                                    //System.out.println("Found Open Button");
                                    break; 
                                }
                                    
                            }
                        }
                        
                        clearDialog();
                        
                        if(cb.getText().equals("Open")) cb.doClick(); /* open the file */


                });
        t.setRepeats(false);
        t.start();
        lsd.doClick();
        assertTrue(wasClearDialog);
    
        // get the file picker

    }
    
    private boolean wasClearDialog = false;
    private void clearDialog(){
        javax.swing.Timer t = new javax.swing.Timer(1000, e -> {
                JButton cb = null;
                for(Window f: mjf.getOwnedWindows() ) {
                    // System.out.println(f.getClass());
                    if( f instanceof JDialog)
                        mydialog = (JDialog) f;
                }
                // System.out.println(mydialog.getTitle());
                if( mydialog == null ) return;
                wasClearDialog = true;
                List<Component> clist = SwUtils.getAllComponents(mydialog);
                for(Component c: clist){
                    if( c instanceof JButton ){
                        cb = (JButton) c;
                        cb.doClick();
                    }
                }
         });
         t.setRepeats(false);
         t.start();
    }
}

