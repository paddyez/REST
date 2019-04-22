package org.paddy.gui;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import java.awt.*;

public class InternalFrame extends JInternalFrame implements InternalFrameListener {
    private static int openFrameCount = 0;
    private static final int X_OFFSET = 30, Y_OFFSET = 30;

    public static int getOpenFrameCount() {
        return openFrameCount;
    }

    private static void setOpenFrameCount() {
        openFrameCount--;
    }

    public InternalFrame(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable, Dimension size) {
        super(title, resizable, closable, maximizable, iconifiable);
        this.pack();
        this.setVisible(true);
        this.setSize(size);
        this.setLocation(X_OFFSET * openFrameCount, Y_OFFSET * openFrameCount);
        openFrameCount++;
        addInternalFrameListener(this);
    }

    @Override
    public void internalFrameOpened(InternalFrameEvent e) {
        // do nothing
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent e) {
        String ftitle = e.getInternalFrame().getTitle();
        //System.out.println("Closing: " + ftitle);
    }

    @Override
    public void internalFrameClosed(InternalFrameEvent e) {
        setOpenFrameCount();
    }

    @Override
    public void internalFrameIconified(InternalFrameEvent e) {
        // do nothing
    }

    @Override
    public void internalFrameDeiconified(InternalFrameEvent e) {
        // do nothing
    }

    @Override
    public void internalFrameActivated(InternalFrameEvent e) {
        // do nothing
    }

    @Override
    public void internalFrameDeactivated(InternalFrameEvent e) {
        // do nothing
    }
}
