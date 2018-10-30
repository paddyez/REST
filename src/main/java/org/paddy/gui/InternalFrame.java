package org.paddy.gui;
import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import java.awt.*;

public class InternalFrame extends JInternalFrame implements InternalFrameListener {
    private static int openFrameCount = 0;
    private static final int xOffset = 30, yOffset = 30;
    public static int getOpenFrameCount(){return openFrameCount;}
    public InternalFrame(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable, Dimension size) {
        super(title, resizable, closable, maximizable, iconifiable);
        openFrameCount++;
        this.pack();
        this.setVisible(true);
        this.setSize(size);
        this.setLocation(xOffset * openFrameCount, yOffset * openFrameCount);
        addInternalFrameListener(this);
    }
    @Override
    public void internalFrameOpened(InternalFrameEvent e) {

    }
    @Override
    public void internalFrameClosing(InternalFrameEvent e) {

    }
    @Override
    public void internalFrameClosed(InternalFrameEvent e) {
        String ftitle = e.getInternalFrame().getTitle();
        //System.out.println("Closing: " + ftitle);
        openFrameCount--;
    }
    @Override
    public void internalFrameIconified(InternalFrameEvent e) {

    }
    @Override
    public void internalFrameDeiconified(InternalFrameEvent e) {

    }
    @Override
    public void internalFrameActivated(InternalFrameEvent e) {

    }
    @Override
    public void internalFrameDeactivated(InternalFrameEvent e) {

    }
}
