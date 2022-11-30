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
public class ControllerTest {
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


    @Test
    @Order(15)
    @GradeValue(2)
    public void runButton() {
        assertTrue(cIdents.containsKey("Run"));
        JButton b = (JButton) cIdents.get("Run");
        b.doClick();
        int step1 = sim.getStep();
        String status1 = sim.getDetails();
        sleep(1000);
        int step2 = sim.getStep();
        String status2 = sim.getDetails();
        assertNotEquals(step1, step2);
        assertNotEquals(status1, status2);
    }

    @Test
    @Order(16)
    @GradeValue(2)
    public void runStopButton() {
        assertTrue(cIdents.containsKey("Run"));
        JButton b = (JButton) cIdents.get("Run");
        b.doClick();
        int step1 = sim.getStep();
        String status1 = sim.getDetails();
        sleep(1000);
        assertTrue(cIdents.containsKey("Stop"));
        b = (JButton) cIdents.get("Stop");
        b.doClick();
        int step2 = sim.getStep();
        String status2 = sim.getDetails();
        assertNotEquals(step1, step2);
        assertNotEquals(status1, status2);
        step1 = sim.getStep();
        status1 = sim.getDetails();
        assertEquals(step1, step2);
        assertEquals(status1, status2);
    }

    private JLabel findStatusLabel(){
        for(Component jc : cIdents.values()){
            if(! ( jc instanceof JLabel) ) continue;
            JLabel jcl = (JLabel) jc;
            String labText = jcl.getText().toUpperCase();
            if( labText.contains("FOX") ) return jcl;
            if( labText.contains("RABBIT") ) return jcl;
            if( labText.contains("STATUS") ) return jcl;
        }
        return null;
    }

    private JLabel findRunningLabel() {
        for (String id : cIdents.keySet()) {
            if (!id.toUpperCase().contains("RUNNING")) continue;
            if (!id.toUpperCase().contains("SIM")) continue;
            if (!(cIdents.get(id) instanceof JLabel)) continue;
            return (JLabel) cIdents.get(id);
        }
        return null;
    }

    @Order(1)
    @GradeValue(1)
    @Test
    public void statusLabel() {
        assertNotNull(findStatusLabel());
    }

    @Order(1)
    @GradeValue(1)
    @Test
    public void runningLabel() {
        assertNotNull(findRunningLabel());
    }

    @Order(10)
    @GradeValue(2)
    @Test
    public void stepButton() {
        assertTrue(cIdents.containsKey("Step"));
        JButton b = (JButton) cIdents.get("Step");

        for( int i =0; i<10; i++) {
            b.doClick();
            int step1 = sim.getStep();
            String status1 = sim.getDetails();

            b.doClick();
            int step2 = sim.getStep();
            String status2 = sim.getDetails();

            assertEquals(step1 + 1, step2);
        }
    }

    @Test
    @Order(11)
    @GradeValue(2)
    public void stepStatusLabel() {
        assertTrue(cIdents.containsKey("Step"));
        JButton b = (JButton) cIdents.get("Step");
        JLabel status = findStatusLabel();

        for( int i =0; i<10; i++) {
            b.doClick();
            int step1 = sim.getStep();
            String status1 = sim.getDetails();
            assertTrue(status.getText().contains(Integer.toString(step1)));
            assertTrue(status.getText().contains(status1));

            b.doClick();
            int step2 = sim.getStep();
            String status2 = sim.getDetails();
            assertTrue(status.getText().contains(Integer.toString(step2)));
            assertTrue(status.getText().contains(status2));

            assertEquals(step1 + 1, step2);
        }
    }

    @Test
    @Order(19)
    @GradeValue(2)
    public void runStopRunningLabel() {
        JLabel running = findRunningLabel();
        String labText = running.getText().toUpperCase();
        assertTrue(labText.contains("NOT"));

        assertTrue(cIdents.containsKey("Run"));
        JButton b = (JButton) cIdents.get("Run");
        b.doClick();
        labText = running.getText().toUpperCase();
        assertFalse(labText.contains("NOT"));

        assertTrue(cIdents.containsKey("Stop"));
        b = (JButton) cIdents.get("Stop");
        b.doClick();

        labText = running.getText().toUpperCase();
        assertTrue(labText.contains("NOT"));;
    }

    @Order(23)
    @GradeValue(2)
    @Test
    public void fasterButton() {

        assertTrue(cIdents.containsKey("Run"));
        JButton b = (JButton) cIdents.get("Run");
        
        assertTrue(cIdents.containsKey("Stop"));
        JButton s = (JButton) cIdents.get("Stop");

        assertTrue(cIdents.containsKey("Faster"));
        JButton f = (JButton) cIdents.get("Faster");
        f.doClick();

        b.doClick();
        int step1 = sim.getStep();
        sleep(3000);
        int step2 = sim.getStep();

        f.doClick();
        f.doClick();

        int step3 = sim.getStep();
        sleep(3000);
        int step4 = sim.getStep();
        
        s.doClick();

        assertTrue( (step2-step1) * 2 < (step4 - step3), "Did not speed up enough");
    }

    @Order(24)
    @GradeValue(2)
    @Test
    public void slowerButton() {

        assertTrue(cIdents.containsKey("Run"));
        JButton b = (JButton) cIdents.get("Run");

        assertTrue(cIdents.containsKey("Stop"));
        JButton s = (JButton) cIdents.get("Stop");
        
        assertTrue(cIdents.containsKey("Slower"));
        JButton f = (JButton) cIdents.get("Slower");
        f.doClick();

        b.doClick();
        int step1 = sim.getStep();
        sleep(3000);
        int step2 = sim.getStep();

        f.doClick();
        f.doClick();

        int step3 = sim.getStep();
        sleep(3000);
        int step4 = sim.getStep();
        
        s.doClick();

        assertTrue( (step2-step1) > 2 * (step4 - step3), "Did not slow down enough");
    }
}

