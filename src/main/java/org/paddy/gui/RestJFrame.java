package org.paddy.gui;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.paddy.api.OAuth;
import org.paddy.rest.GetAccount;
import org.paddy.rest.GetContact;
import org.paddy.rest.PostAccount;
import org.paddy.utils.ConsoleColors;
import org.paddy.utils.NotifyingThread;
import org.paddy.utils.ThreadCompleteListener;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.awt.SystemColor.desktop;

public class RestJFrame extends JFrame implements ActionListener, MenuListener, WindowListener, ThreadCompleteListener {
    private static final Logger log = LogManager.getLogger(RestJFrame.class);
    private JDesktopPane desktopP;
    private JMenuBar mainMenuBar;
    private Map<String, String> accountsM;
    private final Map<String, String> configMap;
    private final Set<String> restCommandsS = new LinkedHashSet<>(Arrays.asList("DELETE", "GET", "PATCH", "POST", "PUT"));
    private static final String[] columnNames = {"Id", "Salutation", "Title", "Last Name", "First Name", "Email", "Phone", "Mobile"};
    private String selectedMenuS = "";
    private static final String YYYY_MM_DD_T_HH_MM_SS_SSSXXX = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    private static final String ACCOUNTS = "Accounts";
    private static final String ACTION_COMMAND = "Action command: {}";
    private static final String CLOSE_ALL = "Close all";

    public RestJFrame(Map<String, String> configMap) {
        super("Salesforce REST requests");
        this.configMap = configMap;
        initComponents();
        addWindowListener(this);
    }

    private void initComponents() {
        int inset = 50;
        int width;
        int height;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = screenSize.width - 2 * inset;
        height = screenSize.height - 2 * inset;
        if (height * 2 < width) {
            width = screenSize.width / 2 - 2 * inset;
        }
        //this.setSize(screenSize.width - inset * 2,screenSize.height - inset * 2);
        //this.setLocation(screenSize.width/2 - this.getSize().width/2, screenSize.height/2 - this.getSize().height/2);
        this.setPreferredSize(new Dimension(width, height));
        this.setBounds(inset, inset, width, height);
        setDefaultLookAndFeelDecorated(true);
        createMenu();
        createDesktop();
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void createMenu() {
        mainMenuBar = new JMenuBar();
        Set<String> itemsS;
        Set<String> menusS = new LinkedHashSet<>(Arrays.asList("Main", "Edit", ACCOUNTS, "Contacts", "Opportunities"));
        int i = 0;
        for (String menu : menusS) {
            JMenu menus = new JMenu(menu);
            switch (i) {
                case 0:
                    itemsS = new LinkedHashSet<>(Arrays.asList("OAuth", "New", "Close", CLOSE_ALL, "Exit"));
                    break;
                case 1:
                    itemsS = new HashSet<>();
                    break;
                default:
                    itemsS = restCommandsS;
                    break;
            }
            for (String entry : itemsS) {
                JMenuItem menuItems = new JMenuItem(entry);
                menuItems.addActionListener(this);
                if (entry.equals("POST") && menu.equals(ACCOUNTS)) {
                    // POST
                    log.info("TODO: POST Accounts needs to be implemented");
                } else if (entry.equals("GET") && menu.equals(ACCOUNTS)) {
                } else if (entry.equals("OAuth") && menu.equals("Main")) {
                } else {
                    menuItems.setEnabled(false);
                }
                menus.add(menuItems);
            }
            menus.addMenuListener(this);
            mainMenuBar.add(menus);
            i++;
        }
        this.setJMenuBar(mainMenuBar);
    }

    private void createDesktop() {
        desktopP = new JDesktopPane();
        desktopP.setBackground(desktop);
        this.setContentPane(desktopP);
    }

    private synchronized void addContactTable(Object[][] data, String accountName) {
        if (data != null) {
            String title = accountName + ": " + data.length + " entries";
            InternalFrame internalFrame = new InternalFrame(title, true, true, true, true, new Dimension(this.getWidth() / 2, this.getHeight() / 2));
            JTable table = new JTable(data, columnNames);
            PersonDataCellRenderer pDCR = new PersonDataCellRenderer();
            TableColumnModel tCM = table.getColumnModel();
            TableColumn tC = tCM.getColumn(5);
            tC.setCellRenderer(pDCR);
            JScrollPane scrollPane = new JScrollPane(table);
            table.setFillsViewportHeight(true);
            internalFrame.add(scrollPane);
            desktopP.add(internalFrame);
            if (InternalFrame.getOpenFrameCount() == 1) {
                JMenu m = mainMenuBar.getMenu(0);
                for (int i = 0; i < m.getItemCount(); i++) {
                    if (m.getItem(i).getText().equals(CLOSE_ALL)) {
                        m.getItem(i).setEnabled(true);
                    }
                }
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        final String BASE_URI = "baseURI";
        if (selectedMenuS.equals("Main")) {
            mainMenuAction(e);
        } else if (selectedMenuS.equals("Edit")) {
            log.info(ACTION_COMMAND, e.getActionCommand());
        } else if (selectedMenuS.equals(ACCOUNTS)) {
            accountMenuAction(e, BASE_URI);
        } else if (selectedMenuS.equals("Contacts")) {
            contactMenuAction(e, BASE_URI);
        } else if (e.getActionCommand().equals("Opportunities")) {
            log.info(ACTION_COMMAND, e.getActionCommand());
            if (e.getActionCommand().equals("GET")) {
                log.info("Getting: {}", e.getActionCommand());
            }
        } else {
            log.error("Not implemented action: {}", e.getActionCommand());
        }
    }

    private void mainMenuAction(ActionEvent e) {
        if (e.getActionCommand().equals("OAuth")) {
            OAuth oAuth = new OAuth(configMap);
            oAuth.login();
        } else if (e.getActionCommand().equals(CLOSE_ALL)) {
            for (JInternalFrame internalFrame : desktopP.getAllFrames()) {
                desktopP.remove(internalFrame);
                internalFrame.dispose();
            }
            revalidate();
            repaint();
            JMenu m = mainMenuBar.getMenu(0);
            for (int i = 0; i < m.getItemCount(); i++) {
                if (m.getItem(i).getText().equals(CLOSE_ALL)) {
                    m.getItem(i).setEnabled(false);
                }
            }
        } else {
            log.error("Not implemented main menu cction: {}", e.getActionCommand());
        }
    }

    private void accountMenuAction(ActionEvent e, String BASE_URI) {
        if (log.isInfoEnabled()) {
            log.info(ACTION_COMMAND, e.getActionCommand());
        }
        if (e.getActionCommand().equals("POST")) {
            PostAccount postAccount = new PostAccount(configMap.get(BASE_URI));
            postAccount.addListener(this);
            outputMsg("Thread post account starts");
            new Thread(postAccount).start();
        }
        if (e.getActionCommand().equals("GET")) {
            GetAccount getAccount = new GetAccount(configMap.get(BASE_URI));
            getAccount.setName("All Accounts");
            getAccount.addListener(this);
            outputMsg("Thread get account starts");
            new Thread(getAccount).start();
        }
    }

    private void contactMenuAction(ActionEvent e, String BASE_URI) {
        if (e.getActionCommand().equals("GET")) {
            for (Map.Entry<String, String> entry : accountsM.entrySet()) {
                GetContact getContact = new GetContact(configMap.get(BASE_URI), entry.getKey(), entry.getValue());
                getContact.setName("Contacts for Account: " + entry.getKey());
                getContact.addListener(this);
                //contactT.setPriority(Thread.MIN_PRIORITY);
                outputMsg("Thread get contact starts");
                new Thread(getContact).start();
            }
        }
    }

    public void menuSelected(MenuEvent e) {
        selectedMenuS = getMenuText(e);
    }

    public void menuDeselected(MenuEvent e) {
        // TODO
    }

    public void menuCanceled(MenuEvent e) {
        if (log.isInfoEnabled()) {
            log.info("menuCanceled: {}", getMenuText(e));
        }
    }

    private String getMenuText(MenuEvent e) {
        JMenu m = (JMenu) e.getSource();
        return m.getText();
    }

    public void windowClosing(WindowEvent e) {
        this.dispose();
        System.exit(0);
    }

    public void windowDeactivated(WindowEvent e) {
        if (log.isInfoEnabled()) {
            log.info("windowDeactivated");
        }
    }

    public void windowActivated(WindowEvent e) {
        // TODO
    }

    public void windowDeiconified(WindowEvent e) {
        if (log.isInfoEnabled()) {
            log.info("windowDeiconified");
        }
    }

    public void windowIconified(WindowEvent e) {
        if (log.isInfoEnabled()) {
            log.info("windowIconified");
        }
    }

    public void windowClosed(WindowEvent e) {
        if (log.isInfoEnabled()) {
            log.info("windowClosed");
        }
    }

    public void windowOpened(WindowEvent e) {
        // TODO
    }

    public void notifyOfThreadComplete(NotifyingThread notifyingThread) {
        String currentThreadClassName = notifyingThread.getClass().getSimpleName();
        if (currentThreadClassName.equals("GetAccount")) {
            outputMsg("Thread ger account ends");
            GetAccount gA = (GetAccount) notifyingThread;
            accountsM = gA.getAccountsM();
            gA.removeListener(this);
            JMenu m = mainMenuBar.getMenu(3);
            JMenuItem item;
            for (int i = 0; i < m.getItemCount(); i++) {
                item = m.getItem(i);
                if (item.getText().equals("GET")) {
                    m.getItem(i).setEnabled(true);
                }
            }
        }
        if (currentThreadClassName.equals("PostAccount")) {
            PostAccount pA = (PostAccount) notifyingThread;
            outputMsg("Thread post account ends");
            pA.removeListener(this);
        } else if (currentThreadClassName.equals("GetContact")) {
            outputMsg("Thread get contact ends");
            GetContact gC = (GetContact) notifyingThread;
            addContactTable(gC.getObj(), gC.getAccountName());
            gC.removeListener(this);
        }
    }

    private static void outputMsg(String msg) {
        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_T_HH_MM_SS_SSSXXX);
        Date resultdate = new Date(yourmilliseconds);
        if (log.isInfoEnabled()) {
            log.info("{}{}: {}{}", ConsoleColors.YELLOW, msg, sdf.format(resultdate), ConsoleColors.WHITE);
        }
    }
}
