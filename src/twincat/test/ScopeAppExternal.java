package twincat.test;

import java.awt.EventQueue;

import javax.swing.JFrame;

import twincat.app.ScopeFrame;

public class ScopeAppExternal extends JFrame {
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try { 
                    ScopeAppExternal frame = new ScopeAppExternal();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ScopeAppExternal() {
        ScopeFrame scopeFrame = new ScopeFrame();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 800);
        setContentPane(scopeFrame);
    }
}
