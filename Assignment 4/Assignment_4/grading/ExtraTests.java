package grading;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.*;
import sim.SimController;
import sim.Simulator;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GradingTestWatcher.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@GradeValue(24)
public class ExtraTests {
    Scanner scan = new Scanner(System.in);
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


    private JTextField findFieldWithVal(int lower, int upper){
        for(Component jc : cIdents.values()){
            if(! ( jc instanceof JTextField) ) continue;
            JTextField stf = (JTextField) jc;
            Integer val = null;
            try {       
                val = Integer.valueOf(stf.getText());
            } catch(NumberFormatException ne){
                val = null;
            } 
            if( val == null ) continue;
            if( val < lower ) continue;
            if( val > upper ) continue;
            return stf;
        }
        return null;
    }

    @Test
    @Order(33)
    @GradeValue(2)
    public void fasterFieldButton() {

        assertTrue(cIdents.containsKey("Faster"));
        JButton f = (JButton) cIdents.get("Faster");
        
        JTextField speedField = findFieldWithVal(50, 10000);
        
        int delay1 = Integer.valueOf(speedField.getText());
        f.doClick();
        int delay2 = Integer.valueOf(speedField.getText());
        f.doClick();
        int delay3 = Integer.valueOf(speedField.getText());
        
        assertTrue(delay1 > delay2);
        assertTrue(delay2 > delay3);
    }
    
    @Test
    @Order(34)
    @GradeValue(2)
    public void slowerFieldButton() {

        assertTrue(cIdents.containsKey("Slower"));
        JButton f = (JButton) cIdents.get("Slower");
        
        JTextField speedField = findFieldWithVal(50, 10000);
        
        int delay1 = Integer.valueOf(speedField.getText());
        f.doClick();
        int delay2 = Integer.valueOf(speedField.getText());
        f.doClick();
        int delay3 = Integer.valueOf(speedField.getText());
        
        assertTrue(delay1 < delay2);
        assertTrue(delay2 < delay3);
    }

}


