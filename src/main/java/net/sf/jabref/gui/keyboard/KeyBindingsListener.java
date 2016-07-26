package net.sf.jabref.gui.keyboard;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * respond to grabKey and display the key binding
 */
public class KeyBindingsListener extends KeyAdapter {

    private final KeyBindingTable table;
    boolean isDeleteKey = false;

    public KeyBindingsListener(KeyBindingTable table) {
        this.table = table;
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        // first check if anything is selected if not the return
        final int selRow = table.getSelectedRow();
        boolean isAnyRowSelected = selRow >= 0;
        if (!isAnyRowSelected) {
            return;
        }

        final String modifier = KeyEvent.getKeyModifiersText(evt.getModifiers());

        // VALIDATE code and modifier
        // all key bindings must have a modifier: ctrl alt etc
        if ("".equals(modifier)) {
            int kc = evt.getKeyCode();
            boolean isFunctionKey = (kc >= KeyEvent.VK_F1) && (kc <= KeyEvent.VK_F12);
            boolean isEscapeKey = kc == KeyEvent.VK_ESCAPE;
            isDeleteKey = kc == KeyEvent.VK_DELETE;
            if (!(isFunctionKey || isEscapeKey || isDeleteKey)) {
                return; // need a modifier except for function, escape and delete keys
            }
        }

        int code = evt.getExtendedKeyCode();
        // second key cannot be a modifiers
        if (code == KeyEvent.VK_TAB
                || code == KeyEvent.VK_BACK_SPACE
                || code == KeyEvent.VK_ENTER
                || code == KeyEvent.VK_SPACE
                || code == KeyEvent.VK_CONTROL
                || code == KeyEvent.VK_SHIFT
                || code == KeyEvent.VK_ALT) {
            return;
        }

        // COMPUTE new key binding
        String newKey;
        String keyCode = KeyBindingPreferences
        if ("".equals(modifier)) {
            newKey = keyCode;
        } else {
            newKey = modifier.toLowerCase().replace("+", " ") + " " + keyCode;
        }

        // SHOW new key binding
        //find which key is selected and set its value
        table.setValueAt(newKey, selRow, 1);
        table.revalidate();
        table.repaint();
    }
}
