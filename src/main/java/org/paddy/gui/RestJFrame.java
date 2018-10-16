package org.paddy.gui;
import org.paddy.rest.GetAccount;
import org.paddy.rest.GetContact;
import org.paddy.rest.ThreadCompleteListener;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.*;
import static java.awt.SystemColor.desktop;
public class RestJFrame extends JFrame implements ActionListener, MenuListener, WindowListener, ThreadCompleteListener {
    JDesktopPane desktopP;
    JMenuBar menuBar;
    JMenu menus;
    JMenuItem menuItems;
    GetAccount getAccount;
    GetContact getContact;
    Object[][] data;
    Map<String, String> accountsM;
    private static final String[] columnNames = {"Id", "Salutation", "Title", "Last Name", "First Name", "Email", "Phone", "Mobile"};
    Thread accT, contactT;
    boolean ready = false;
    public RestJFrame() throws HeadlessException {
        super("Salesforce REST requests");
        initComponents();
        addWindowListener(this);
        getAccount = new GetAccount();
        getAccount.setName("Account");
        getAccount.addListener(this);
        accT = new Thread(getAccount);
        accT.start();
    }
    private void initComponents() {
        int inset = 50;
        int width, height;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = screenSize.width - 2 * inset;
        height = screenSize.height -2 * inset;
        if(height * 2 < width) {
            width = screenSize.width/2 - 2 * inset;
        }
        //this.setSize(screenSize.width - inset * 2,screenSize.height - inset * 2);
        //this.setLocation(screenSize.width/2 - this.getSize().width/2, screenSize.height/2 - this.getSize().height/2);
        this.setPreferredSize(new Dimension(width, height));
        this.setBounds(inset, inset, width, height);
        this.setDefaultLookAndFeelDecorated(true);
        createMenu();
        createDesktop();
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void createMenu() {
        menuBar = new JMenuBar();
        Set<String> itemsS;
        Set<String> menusS = new HashSet<>();
        menusS = new LinkedHashSet<>(Arrays.asList("File", "Edit", "Objects"));
        int i=0;
        for (String menu : menusS) {
            menus = new JMenu(menu);
            switch(i) {
                case 0: itemsS = new LinkedHashSet<>(Arrays.asList("Open", "New", "Close", "Close All", "Exit"));
                    break;
                case 2: itemsS = new LinkedHashSet<>(Arrays.asList("Accounts", "Contacts", "Opportunities"));
                    break;
                default: itemsS = new HashSet<>();
                    break;
            }
            for (String entry : itemsS) {
                menuItems = new JMenuItem(entry);
                menuItems.addActionListener(this);
                if(entry.equals("Close All")) {

                }
                else {
                    menuItems.setEnabled(false);
                }
                menus.add(menuItems);
            }
            menus.addMenuListener(this);
            menuBar.add(menus);
            i++;
        }
        this.setJMenuBar(menuBar);
    }
    private void createDesktop() {
        desktopP = new JDesktopPane();
        desktopP.setBackground(desktop);
        this.setContentPane(desktopP);
    }
    /*
    private void addContactTableAccountByName(String searchString) {
        Object[][] data = GetContact.getContactsObjectByAccountName(searchString);
        if(data != null){
            String title = searchString + ": " + data.length + " entries";
            InternalFrame internalFrame = new InternalFrame(title, true, true, true, true, new Dimension(this.getWidth()/2, this.getHeight()/2));
            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            table.setFillsViewportHeight(true);
            internalFrame.add(scrollPane);
            desktopP.add(internalFrame);
        }
    }
    */
    private void addContactTable(String accountName) {
        if(data != null){
            String title = accountName + ": " + data.length + " entries";
            InternalFrame internalFrame = new InternalFrame(title, true, true, true, true, new Dimension(this.getWidth()/2, this.getHeight()/2));
            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            table.setFillsViewportHeight(true);
            internalFrame.add(scrollPane);
            desktopP.add(internalFrame);
        }
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Accounts")) {
        }
        else if(e.getActionCommand().equals("Contacts")) {
            for(String accountId : accountsM.keySet()) {
                ready = false;
                getContact = new GetContact(accountId, accountsM.get(accountId));
                getContact.setName("Contacts for Account: " + accountId);
                getContact.addListener(this);
                contactT = new Thread(getContact);
                contactT.setPriority(Thread.MIN_PRIORITY);
                contactT.start();
                //addContactTableByAccountId(id, accountsM.get(id));
                while(ready == false) {
                    try {
                        //System.out.println("Current Thread: " + Thread.currentThread().getId());
                        contactT.yield();
                        contactT.join();
                        //System.out.println("Current Thread: " + Thread.currentThread().getId());
                    }
                    catch (InterruptedException ie) {
                        System.err.println("InteruptedException: " + ie);
                    }
                }
            }
        }
        else if(e.getActionCommand().equals("Opportunities")) {
            System.out.println("Getting: " + e.getActionCommand());
        }
        else if(e.getActionCommand().equals("Close All")) {
            for(JInternalFrame internalFrame : desktopP.getAllFrames()) {
                desktopP.remove(internalFrame);
                internalFrame.dispose();
                revalidate();
                repaint();
            }
        }
        else {
            System.out.println("Not implemented: " + e.getActionCommand());
        }
    }
    public void menuSelected(MenuEvent e) {
        //System.out.println("menuSelected: " + getNenuText(e));
    }
    public void menuDeselected(MenuEvent e) {
        //System.out.println("menuDeselected: " + getNenuText(e));
    }
    public void menuCanceled(MenuEvent e) {
        System.out.println("menuCanceled: " + getNenuText(e));
    }
    private String getNenuText(MenuEvent e) {
        JMenu m = (JMenu) e.getSource();
        return m.getText();
    }
    public void windowClosing(WindowEvent e) {
        //System.out.println("windowClosing");
        this.dispose();
        System.exit(0);
    }
    public void windowDeactivated(WindowEvent e) {
        System.out.println("windowDeactivated");
    }
    public void windowActivated(WindowEvent e) {
        //System.out.println("windowActivated");
    }
    public void windowDeiconified(WindowEvent e) {
        System.out.println("windowDeiconified");
    }
    public void windowIconified(WindowEvent e) {
        System.out.println("windowIconified");
    }
    public void windowClosed(WindowEvent e) {
        System.out.println("windowClosed");
    }
    public void windowOpened(WindowEvent e) {
        //System.out.println("windowOpened");
    }
    public void notifyOfThreadComplete(Thread getThread) {
        //System.out.println("Thread complete: " + getThread.getName());
        if(getThread.getName().equals("Account")) {
            accountsM = getAccount.getAccountsM();
            getAccount.removeListener(this);
            JMenu m = menuBar.getMenu(menuBar.getMenuCount() - 1);
            for (int i = 0; i < m.getItemCount(); i++) {
                m.getItem(i).setEnabled(true);
            }
        }
        else if(getThread.getName().substring(0,7).equals("Contact")) {
            data = getContact.getObj();
            addContactTable(getContact.getAccountName());
            getContact.removeListener(this);
            ready = true;
        }
    }
}
