/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package buddyListplugin.buddylist;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

/**
 *
 * @author salim
 */
    public class BuddyAdapter extends BuddyCellRenderer.Adapter {

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
