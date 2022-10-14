package com.bfrancois.exempleformulaire;

import com.bfrancois.exempleformulaire.models.Pays;
import com.bfrancois.exempleformulaire.models.Utilisateur;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FenetreFormulaire extends JPanel {

    protected int defaultMargin = 10;

    protected JComboBox<String> selectCivilite;
    protected ChampsSaisie champsNom;
    protected ChampsSaisie champsPrenom;
    protected ChampsSaisie champsEmail;
    protected JComboBox<Pays> selectPays;
    protected ChampsSaisie champsAge;
    protected JCheckBox champsMarie;

    public FenetreFormulaire() {

        setSize(500,500);

        //ajout du layout de 5 zones (NORTH, SOUTH, EAST, WEST, CENTER)
        setLayout(new BorderLayout());

        //---------------- FORMULAIRE ------------------


        Box boxFormulaire = Box.createVerticalBox();
        //formulaire.setBorder(BorderFactory.createLineBorder(Color.RED));

        add(boxFormulaire, BorderLayout.CENTER);


        //---------------- LISTE CIVILITE ------------------

        String[] listeCivilites = {"Monsieur","Madame","Mademoiselle","Autre"};
        selectCivilite = new JComboBox<>(listeCivilites);

        boxFormulaire.add(HelperForm.generateField(
                "Civilité",selectCivilite));

        //---------------- CHAMPS TEXT : NOM ---------------

        champsNom = new ChampsSaisie("[\\p{L}\s'-]");
        boxFormulaire.add(
                HelperForm.generateField("Nom", champsNom)
        );

        //---------------- CHAMPS TEXT : PRENOM ---------------

        champsPrenom = new ChampsSaisie("[\\p{L}\s'-]");
        boxFormulaire.add(
                HelperForm.generateField("Prénom", champsPrenom)
        );

        //---------------- CHAMPS TEXT : EMAIL ---------------

        champsEmail = new ChampsSaisie("[a-zA-Z0-9@\\.-]");
        boxFormulaire.add(
                HelperForm.generateField("Email", champsEmail)
        );

        champsEmail.getTextField().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {

                if(champsEmail.getText().equals("")) {
                    champsEmail.erreur("Champs obligatoire");
                } else {

                    String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(champsEmail.getText());

                    if(!matcher.matches()){
                        champsEmail.erreur("Format invalide");
                    }
                }
            }
        });


        //---------------- LISTE PAYS ------------------
        Pays[] listePays = {
                new Pays("France", "FR", "fr.png"),
                new Pays("Royaume-unis", "GBR", "gb.png"),
                new Pays("Allemagne", "DE", "de.png")
        };

        selectPays = new JComboBox<>(listePays);

        selectPays.setRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

                Pays pays = (Pays)value;
                setText(pays.getNom());

                try {
                    //on charge l'image de drapeau correspond au pays
                    Image image = ImageIO.read(new File("src/main/resources/drapeaux/" + pays.getImage()));

                    //on redimensionne l'image
                    Image resizedImage = image.getScaledInstance(20, 16, Image.SCALE_SMOOTH);

                    setIconTextGap(10);

                    //on change l'icone du JLabel par l'image redimensionnée
                    setIcon(new ImageIcon(resizedImage));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                return this;
            }
        });

        boxFormulaire.add(
                HelperForm.generateField("Pays",selectPays)
        );


        //---------------- CHAMPS TEXT : AGE ---------------

        champsAge = new ChampsSaisie("[0-9]");
        boxFormulaire.add(
                HelperForm.generateField("Age", champsAge,50));

        //---------------- CHAMPS CHECKBOX : MARIE/PACSE ---------------

        champsMarie = new JCheckBox();
        boxFormulaire.add(
                HelperForm.generateField("Marie/pacse", champsMarie));


        //--------- BOUTON VALIDER FORMULAIRE -----------

        JButton boutonValider = new JButton("Enregistrer");

        boutonValider.addActionListener(e -> {

            boolean erreurNom = false;
            boolean erreurPrenom = false;
            boolean erreurAge = false;
            boolean erreurEmail = false;

            String message = "Le formulaire comporte des erreurs : ";

            champsNom.resetMessage();
            champsPrenom.resetMessage();

            //------ validateur nom -------
            if(champsNom.getText().equals("")) {
                erreurNom = true;
                message += "\n - Nom obligatoire,";
                champsNom.erreur("Champs obligatoire");
            }

            //------ validateur prenom -------
            if(champsPrenom.getText().equals("")) {
                erreurPrenom = true;
                message += "\n - Prénom obligatoire,";
                champsPrenom.erreur("Champs obligatoire");
            }

            //------ validateur age -------
            if(champsAge.getText().equals("")) {
                erreurAge = true;
                message += "\n - Age obligatoire,";
                champsAge.erreur("Champs obligatoire");
            } else {

                int age = Integer.parseInt(champsAge.getText());

                if(age <= 0 || age > 150) {
                    erreurAge = true;
                    message += "\n - Age avec une valeur impossible,";
                    champsAge.erreur("Valeur impossible");
                }
            }

            //------ validateur email -------
            if(champsEmail.getText().equals("")) {
                erreurEmail = true;
                message += "\n - Email obligatoire,";
                champsEmail.erreur("Champs obligatoire");
            } else {

                String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(champsEmail.getText());

                if(!matcher.matches()){
                    erreurEmail = true;
                    message += "\n - Email format invalide,";
                    champsEmail.erreur("Format invalide");
                }

            }

            //on supprime la dernière des virgules
            message = message.substring(0,message.length()-1);

            if(erreurNom || erreurPrenom || erreurAge || erreurEmail) {

                JOptionPane.showMessageDialog(
                        this,
                        message,
                        "Erreur de saisie",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                //----------- si il n'y a pas d'erreur --------

                Utilisateur nouvelUtilisateur = new Utilisateur(
                        (String)selectCivilite.getSelectedItem(),
                        champsNom.getText(),
                        champsPrenom.getText(),
                        champsEmail.getText(),
                        (Pays)selectPays.getSelectedItem(),
                        Integer.parseInt(champsAge.getText()),
                        champsMarie.isSelected()
                );

                ObjectOutputStream oos = null;

                try {
                    FileOutputStream fichier = new FileOutputStream("personne.eesc");

                    oos = new ObjectOutputStream(fichier);
                    oos.writeObject(nouvelUtilisateur);
                    oos.flush();
                    oos.close();

                    JOptionPane.showMessageDialog(
                            this,
                            "L'utilisateur " + nouvelUtilisateur.getNom() + " a bien été ajouté");

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Impossible d'enregistrer l'utilisateur");
                }
            }
        });



        //---------- BOUTONS DU BAS -------

        boutonValider.setSize(new Dimension(100, 30));

        add(
                HelperForm.generateRow(boutonValider,0,10,10,0, HelperForm.ALIGN_RIGHT),
                BorderLayout.SOUTH);

    }

    public void ouvrirFichier() {

        ObjectInputStream ois = null;

        try {
            FileInputStream fichier = new FileInputStream("personne.eesc");
            ois = new ObjectInputStream(fichier);
            Utilisateur utilisateurFichier = (Utilisateur)ois.readObject();

            //----- hydratation du formulaire ----

            selectCivilite.setSelectedItem(utilisateurFichier.getCivilite());
            champsNom.getTextField().setText(utilisateurFichier.getNom());
            champsPrenom.getTextField().setText(utilisateurFichier.getPrenom());
            champsEmail.getTextField().setText(utilisateurFichier.getEmail());
            selectPays.setSelectedItem(utilisateurFichier.getPays());
            champsAge.getTextField().setText(
                    String.valueOf(utilisateurFichier.getAge())
            );
            champsMarie.setSelected(utilisateurFichier.isMarie());

            ois.close();

        } catch (FileNotFoundException e) {
            System.out.println("Première fois qu'on ouvre l'application");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Impossible d'ouvrir le fichier");
        } catch (ClassNotFoundException | ClassCastException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Fichier corrompu");
        }
    }



}