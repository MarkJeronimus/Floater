/*
 * This file is part of Floater.
 *
 * Floater is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Floater is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Floater.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.zomb.floater;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * @author Zom-B
 */
// Created 2014-03-17
@SuppressWarnings("AssignmentToStaticFieldFromInstanceMethod")
public class InstructionTableCellRenderer extends JLabel implements TableCellRenderer {
	private static BufferedImage img = null;
	private static Graphics2D    g   = null;
	private static ImageIcon     icon;

	public InstructionTableCellRenderer() {
		// Ensure that the background is visible
		setOpaque(true);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
	                                               int row, int column) {
		Instruction instruction = (Instruction)value;

		int width  = table.getColumnModel().getColumn(column).getWidth();
		int height = table.getRowHeight(row);

		if (img == null || img.getWidth() != width || img.getHeight() != height) {
			img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			icon = new ImageIcon(img);

			if (g != null) {
				g.dispose();
			}
			g = img.createGraphics();
		}

		g.setPaint(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setPaint(new Color(FloaterConstants.DOS16[instruction.opcode]));
		for (int i = 0; i <= instruction.param; i++) {
			g.fillRect(i * height, 0, height - 1, height - 1);
		}

		g.setPaint(instruction.opcode < 7 || instruction.opcode == 8 ? Color.WHITE : Color.BLACK);
		g.drawString(instruction.opcode < 2 ? "~" : Integer.toString(instruction.param + 1), 4, height / 2 + 5);

		setIcon(icon);
		return this;
	}
}
