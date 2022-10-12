package com.bfrancois.exempleformulaire;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChampsSaisie extends Box{

    Character[] caracteresAutorises = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',' '};
    protected JTextField textField;
    protected JLabel icone = new JLabel();
    protected JLabel message = new JLabel();

    protected Border borderOriginal;

    protected ImageIcon checkIcon;
    protected ImageIcon errorIcon;

    protected String regex;

    public ChampsSaisie() {
        super(BoxLayout.Y_AXIS);
        textField = new JTextField();
        initialiser();

    }

    public ChampsSaisie(String expression) {
        super(BoxLayout.Y_AXIS);
        this.regex=expression;
        textField = new JTextField() {
            @Override
            protected void processKeyEvent(KeyEvent e) {

                //Si il supprime ou utilise les fleches on laisse le traitement standard
                if (e.getKeyCode() == KeyEvent.VK_RIGHT
                        || e.getKeyCode() == KeyEvent.VK_LEFT
                        || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    super.processKeyEvent(e);
                } else {
                    //on créait l'expression régulière (n'importe quel caractère ou espace)
                    Pattern regex = Pattern.compile(expression);
                    //on transforme le caractère saisi en chaine de texte
                    String lettre = String.valueOf(e.getKeyChar());
                    //on créait un objet matcher à partire de la regex et la lettre saisie
                    Matcher matcher = regex.matcher(lettre);
                    //on vérifie si la lettre correspond à la regex
                    if (matcher.matches()) {
                        super.processKeyEvent(e);
                    }
                }
            }
        };
        initialiser();

    }

    public void initialiser() {

        Box ligne1 = Box.createHorizontalBox();
        ligne1.add(textField);
        ligne1.add(icone);
        ligne1.add(Box.createHorizontalGlue());
        add(ligne1);

        Box ligne2 = Box.createHorizontalBox();
        ligne2.add(message);
        ligne2.add(Box.createHorizontalGlue());
        add(ligne2);

        borderOriginal = textField.getBorder();
        try {
            checkIcon = new ImageIcon(
                    ImageIO.read(new File("src/main/resources/icones/check.png"))
            );
            errorIcon = new ImageIcon(
                    ImageIO.read(new File("src/main/resources/icones/error.png"))
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar()=='a' ||  e.getKeyChar() == 'b');
                System.out.println();
            }
            @Override
            public void keyReleased(KeyEvent e) {
                resetMessage();
            }
        });

    }

    public void erreur(String texte) {
        textField.setBorder(BorderFactory.createLineBorder(Color.red));
        message.setForeground(Color.RED);
        message.setText(texte);
        icone.setIcon(errorIcon);
    }

    public void resetMessage() {
        textField.setBorder(borderOriginal);
        message.setText("");
        icone.setIcon(null);
    }

    public String getText() {
        return textField.getText();
    }

    public JTextField getTextField() {
        return textField;
    }

    public void setTextField(JTextField textField) {
        this.textField = textField;
    }

    public JLabel getIcone() {
        return icone;
    }

    public void setIcone(JLabel icone) {
        this.icone = icone;
    }

    public JLabel getMessage() {
        return message;
    }

    public void setMessage(JLabel message) {
        this.message = message;
    }
}

