package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Ramika Nimtharu
 */

public class PixelRuler extends JFrame {

    private int mouseX, mouseY;
    private int cursorPos = 100;
    private boolean isHorizontal = true;

    public PixelRuler() {
        setUndecorated(true);
        setAlwaysOnTop(true);
        setSize(1000, 120);
        setLocationRelativeTo(null);
        setBackground(new Color(30, 30, 30, 200));
        setIconImage(new ImageIcon(getClass().getResource("/image/logo.png")).getImage());

        JPanel rulerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (isHorizontal) {
                    g2.setColor(new Color(40, 40, 40));
                    g2.fillRect(0, 40, getWidth(), 60);

                    g2.setColor(Color.LIGHT_GRAY);
                    for (int i = 0; i < getWidth(); i += 5) {
                        int tickHeight;
                        if (i % 50 == 0) {
                            tickHeight = 25;
                            g2.setFont(new Font("Arial", Font.PLAIN, 10));
                            g2.drawString(String.valueOf(i), i + 2, 35);
                        } else if (i % 10 == 0) {
                            tickHeight = 15;
                        } else {
                            tickHeight = 7;
                        }
                        g2.drawLine(i, 40, i, 40 + tickHeight);
                    }

                    g2.setColor(new Color(255, 102, 0));
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.drawLine(cursorPos, 10, cursorPos, 110);
                    g2.drawString(String.valueOf(cursorPos), cursorPos + 5, 105);

                } else {
                    g2.setColor(new Color(40, 40, 40));
                    g2.fillRect(40, 0, 60, getHeight());

                    g2.setColor(Color.LIGHT_GRAY);
                    for (int i = 0; i < getHeight(); i += 5) {
                        int tickWidth;
                        if (i % 50 == 0) {
                            tickWidth = 25;
                            g2.setFont(new Font("Arial", Font.PLAIN, 10));
                            g2.drawString(String.valueOf(i), 12, i + 12);
                        } else if (i % 10 == 0) {
                            tickWidth = 15;
                        } else {
                            tickWidth = 7;
                        }
                        g2.drawLine(40, i, 40 + tickWidth, i);
                    }

                    g2.setColor(new Color(255, 102, 0));
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.drawLine(10, cursorPos, 110, cursorPos);
                    g2.drawString(String.valueOf(cursorPos), 10, cursorPos - 5);
                }
            }
        };

        rulerPanel.setOpaque(false);
        rulerPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        rulerPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                setLocation(x - mouseX, y - mouseY);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                cursorPos = isHorizontal ? e.getX() : e.getY();
                repaint();
            }
        });

        add(rulerPanel);

        JPopupMenu menu = new JPopupMenu();

        JMenuItem rotateItem = new JMenuItem("Rotate (Horizontal / Vertical)");
        rotateItem.addActionListener(e -> {
            isHorizontal = !isHorizontal;
            if (isHorizontal) {
                setSize(1000, 120);
            } else {
                setSize(120, 1000);
            }
            repaint();
        });

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));

        menu.add(rotateItem);
        menu.addSeparator();
        menu.add(exitItem);
        rulerPanel.setComponentPopupMenu(menu);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PixelRuler().setVisible(true));
    }
}
