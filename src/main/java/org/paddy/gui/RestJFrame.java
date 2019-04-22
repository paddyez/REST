package org.paddy.gui;

import org.paddy.rest.GetAccount;
import org.paddy.rest.GetContact;
import org.paddy.rest.PostAccount;
import org.paddy.utils.ConsoleColors;
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
    private JDesktopPane desktopP;
    private JMenuBar mainMenuBar;
    private JMenu menus;
    private JMenuItem menuItems;
    private GetAccount getAccount;
    private PostAccount postAccount;
    private Map<String, String> accountsM;
    private final Set<String> restCommandsS = new LinkedHashSet<>(Arrays.asList("DELETE", "GET", "PATCH", "POST", "PUT"));
    private static final String[] columnNames = {"Id", "Salutation", "Title", "Last Name", "First Name", "Email", "Phone", "Mobile"};
    private Thread accT, contactT;
    private String selectedMenuS = "";
    Map<String, String> configMap;
    private static final String YYYY_MM_DD_T_HH_MM_SS_SSSXXX = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    public RestJFrame(Map<String, String> configMap) throws HeadlessException {
        super("Salesforce REST requests");
        this.configMap = configMap;
        initComponents();
        addWindowListener(this);
    }

    private void initComponents() {
        int inset = 50;
        int width, height;
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
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createMenu() {
        mainMenuBar = new JMenuBar();
        Set<String> itemsS;
        Set<String> menusS = new LinkedHashSet<>(Arrays.asList("File", "Edit", "Accounts", "Contacts", "Opportunities"));
        int i = 0;
        for (String menu : menusS) {
            menus = new JMenu(menu);
            switch (i) {
                case 0:
                    itemsS = new LinkedHashSet<>(Arrays.asList("Open", "New", "Close", "Close All", "Exit"));
                    break;
                case 1:
                    itemsS = new HashSet<>();
                    break;
                default:
                    itemsS = restCommandsS;
                    break;
            }
            for (String entry : itemsS) {
                menuItems = new JMenuItem(entry);
                menuItems.addActionListener(this);
                if (entry.equals("POST") && menu.equals("Accounts")) {
                    // POST
                    System.out.println("TODO: POST Accounts needs to be implemented");
                } else if (entry.equals("GET") && menu.equals("Accounts")) {
                    // GET
                    System.out.println("TODO: GET Accounts needs to be implemented");
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
                    if (m.getItem(i).getText().equals("Close All")) {
                        m.getItem(i).setEnabled(true);
                    }
                }
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (selectedMenuS.equals("File")) {
            if (e.getActionCommand().equals("Close All")) {
                for (JInternalFrame internalFrame : desktopP.getAllFrames()) {
                    desktopP.remove(internalFrame);
                    internalFrame.dispose();
                }
                revalidate();
                repaint();
                JMenu m = mainMenuBar.getMenu(0);
                for (int i = 0; i < m.getItemCount(); i++) {
                    if (m.getItem(i).getText().equals("Close All")) {
                        m.getItem(i).setEnabled(false);
                    }
                }
            }
        }
        if (selectedMenuS.equals("Edit")) {
            System.out.println("Action command: " + e.getActionCommand());
        } else if (selectedMenuS.equals("Accounts")) {
            System.out.println("Action command: " + e.getActionCommand());
            if (e.getActionCommand().equals("POST")) {
                postAccount = new PostAccount(configMap.get("baseURI"));
                postAccount.addListener(this);
                accT = new Thread(postAccount);
                outputMsg("Thread post account starts");
                accT.start();
            }
            if (e.getActionCommand().equals("GET")) {
                getAccount = new GetAccount(configMap.get("baseURI"));
                getAccount.setName("Account");
                getAccount.addListener(this);
                accT = new Thread(getAccount);
                outputMsg("Thread get account starts");
                accT.start();
            }
        } else if (selectedMenuS.equals("Contacts")) {
            if (e.getActionCommand().equals("GET")) {
                GetContact getContact;
                for (String accountId : accountsM.keySet()) {
                    getContact = new GetContact(configMap.get("baseURI"), accountId, accountsM.get(accountId));
                    getContact.setName("Contacts for Account: " + accountId);
                    getContact.addListener(this);
                    contactT = new Thread(getContact);
                    //contactT.setPriority(Thread.MIN_PRIORITY);
                    outputMsg("Thread get contact starts");
                    contactT.start();
                }
            }
        } else if (e.getActionCommand().equals("Opportunities")) {
            System.out.println("Action command: " + e.getActionCommand());
            if (e.getActionCommand().equals("GET")) {
                System.out.println("Getting: " + e.getActionCommand());
            }
        } else {
            System.out.println("Not implemented: " + e.getActionCommand());
        }
    }

    public void menuSelected(MenuEvent e) {
        selectedMenuS = getMenuText(e);
    }

    public void menuDeselected(MenuEvent e) {
    }

    public void menuCanceled(MenuEvent e) {
        System.out.println("menuCanceled: " + getMenuText(e));
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
        System.out.println("windowDeactivated");
    }

    public void windowActivated(WindowEvent e) {
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
    }

    public void notifyOfThreadComplete(Thread notifyingThread) {
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
        System.out.println(ConsoleColors.YELLOW + msg + ": " + sdf.format(resultdate) + ConsoleColors.WHITE);
    }
}
