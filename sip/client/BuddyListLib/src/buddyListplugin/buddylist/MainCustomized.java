/*
 * Main.java
 *
 * Created on February 17, 2006, 4:00 PM
 */
package buddyListplugin.buddylist;

import buddyListplugin.buddylist.BuddyCellRenderer.Adapter;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

/**
 * @author Hans Muller (Hans.Muller@Sun.COM)
 */
public class MainCustomized {

    private JFrame mainFrame;

    private static class Buddy {

        String name;
        Adapter.Status status;
        String message;
        URL buddyIconURL;

        Buddy(String name, Adapter.Status status, String message, String buddyIcon) {
            this.name = name;
            this.status = status;
            this.message = message;
            this.buddyIconURL = (buddyIcon == null) ? null
                    : MainCustomized.class.getResource("/buddyListplugin/resources/samplebuddyicons/" + buddyIcon);
        }
    }

    private static class BuddyAdapter extends BuddyCellRenderer.Adapter {

        private final Map<URL, ImageIcon> iconCache = new HashMap<URL, ImageIcon>();

        private Buddy getBuddy() {
            return (Buddy) getValue();
        }

        public String getName() {
            return getBuddy().name;
        }

        public Status getStatus() {
            return getBuddy().status;
        }

        public String getMessage() {
            return getBuddy().message;
        }

        public ImageIcon getBuddyIcon() {
            URL url = getBuddy().buddyIconURL;
            if (url == null) {
                return null;
            }
            ImageIcon icon = iconCache.get(url);
            if (icon != null) {
                return icon;
            }
            icon = new ImageIcon(url);
            iconCache.put(url, icon);
            return icon;
        }
    }

    private JList initialize(String[] ignore) {
        try {
            String name = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(name);
        } catch (Exception e) {
            // TBD log a warning
        }


//	Buddy buddyList[] = {
//	    new Buddy("Quark", Adapter.Status.AWAY, "Away From My Nucleus (Never Idle)", null),
//	    new Buddy("Alfalfa", Adapter.Status.AWAY, "Away From My Desk", "alfalfa.jpg"),
//	    new Buddy("Aretha", Adapter.Status.ONLINE, null, "aretha.jpg"),
//	    new Buddy("Johnny", Adapter.Status.ONLINE, "Online (Idle 90 Seconds)", "carson.jpg"),
//	    new Buddy("Winston", Adapter.Status.ONLINE, "Working on Memoirs", "churchill.jpg"),
//	    new Buddy("Darth", Adapter.Status.AWAY, "I Am Your Father", "darth.jpg"),
//	    new Buddy("EraserHead", Adapter.Status.AWAY, "On Vacation", "eraserhead.jpg"),
//	    new Buddy("Felix", Adapter.Status.OFFLINE, null, "felix.jpg"),
//	    new Buddy("Marvin", Adapter.Status.AWAY, "Thinking ...", "marvin.jpg"),
//	    new Buddy("Mona", Adapter.Status.ONLINE, null, "mona.jpg"),
//	    new Buddy("Snark", Adapter.Status.ONLINE, null, null),
//            new Buddy("Salim", Adapter.Status.ONLINE, null, null),
//	};
//	JList buddyJList = new JList();
//	BuddyCellRenderer bcr = new BuddyCellRenderer(new BuddyAdapter());
//	buddyJList.setCellRenderer(bcr);
//        buddyJList.setListData(buddyList);
//	
        /*
         * use a List and Data Model
         */
        //List<PresenceVO> presenceL = new ArrayList<PresenceVO>();
//         PresenceVO pres1 = new PresenceVO();
//         pres1.setAddress_URI("sip:111@145.22.22.22");
//         PresenceVO pres2 = new PresenceVO();
//         pres2.setAddress_URI("sip:222@145.22.22.22");
        //presenceL.add(pres1);
        // presenceL.add(pres2);
        Buddy buddy1 = new Buddy("Quark", Adapter.Status.OFFLINE, "Away From My Nucleus (Never Idle)", null);
        Buddy buddy2 = new Buddy("Salim2", Adapter.Status.BUSY, "BUSY From My Nucleus (Never Idle)", null);
        Buddy buddy3 = new Buddy("nato", Adapter.Status.ONTHEPHONE, "ONTHEPHONE From My Nucleus (Never Idle)", null);
        DefaultListModel listModel = new DefaultListModel();
        listModel.addElement(buddy1);
        listModel.addElement(buddy2);
        listModel.addElement(buddy3);

        JList buddyJList = new JList();
        buddyJList.setModel(listModel);

        BuddyCellRenderer bcr = new BuddyCellRenderer(new BuddyAdapter());
        buddyJList.setCellRenderer(bcr);

        /* Set prototypeCellValue to force all cells (rows) to 
         * have the same height.  This is important because 
         * cells that correspond to an offline buddy only
         * contain one line of text and will end up be a 
         * little smaller (vertically) than the others.
         */
        buddyJList.setPrototypeCellValue(buddy1);

        Toolkit.getDefaultToolkit().setDynamicLayout(true);
        mainFrame = new JFrame("BuddyList");
        Container cp = mainFrame.getContentPane();
        cp.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(buddyJList);
        cp.add(scrollPane, BorderLayout.CENTER);
        return buddyJList;
    }

    private void show() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public static void main(final String[] args) {
        System.out.println("Main...");
        Runnable doCreateAndShowGUI = new Runnable() {
            public void run() {
                try {
                    MainCustomized app = new MainCustomized();
                    JList buddyJList = app.initialize(args);
                    app.show();
                    System.out.println("updating the Jlist...");

                    //update the buddyList: adding a new element
                    DefaultListModel listModel = (DefaultListModel) buddyJList.getModel();
                    Buddy buddy3 = new Buddy("Salim updated", Adapter.Status.AWAY, "Away From My Nucleus (Never Idle)", null);
                    listModel.addElement(buddy3);

                    //update the buddyList: updating element status
                    Object[] ObjArray = listModel.toArray();
                    for (Object obj : ObjArray) {
                        if (obj instanceof Buddy) {
                            Buddy buddyInst = (Buddy) obj;
                            System.out.println(buddyInst.toString());
                            if ("Salim updated".equals(buddyInst.name)) {
                                System.out.println("found Away, --> online");
                                buddyInst.status = Adapter.Status.ONLINE;
                                
                            }
                        }
                    }
                } catch (Exception e) {
                    // TBD log an error
                }
            }
        };
//        Thread thread = new Thread(doCreateAndShowGUI);
//        thread.start();
        SwingUtilities.invokeLater(doCreateAndShowGUI);
    }
}
