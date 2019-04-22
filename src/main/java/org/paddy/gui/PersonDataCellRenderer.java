package org.paddy.gui;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class PersonDataCellRenderer extends JLabel implements TableCellRenderer {
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row,
                                                   int col) {
        if (value != null) {
            String email = (String) value;
            //this.setForeground(Color.BLUE);
            this.setText("<html><a href=\"mailto:" + email + "\">" + email + "</a></html>");
        } else {
            Font font = this.getFont();
            Map<TextAttribute, Object> attibutes = new HashMap<>(font.getAttributes());
            //attibutes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            attibutes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_REGULAR);
            this.setFont(font.deriveFont(attibutes));
            this.setText("no email available");
        }
        return this;
    }
}
