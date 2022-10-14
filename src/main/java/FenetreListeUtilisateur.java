import com.bfrancois.exempleformulaire.FenetreFormulaire;
import com.bfrancois.exempleformulaire.HelperForm;
import com.bfrancois.exempleformulaire.models.Pays;
import com.bfrancois.exempleformulaire.models.Utilisateur;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class FenetreListeUtilisateur extends JFrame implements WindowListener {

    protected boolean themeSombreActif = true;
    public FenetreListeUtilisateur() {
        setSize(800,500);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(this);

        //ajout du panneau principal avec un layout de 5 zones
        // (NORTH, SOUTH, EAST, WEST, CENTER)
        JPanel panneau = new JPanel(new BorderLayout());
        setContentPane(panneau);

        //--------- BOUTON THEME -----------

        JButton boutonTheme = new JButton("Changer le theme");

        boutonTheme.addActionListener(
                e -> {
                    try {
                        if(themeSombreActif) {
                            UIManager.setLookAndFeel(new FlatLightLaf());
                        } else {
                            UIManager.setLookAndFeel(new FlatDarculaLaf());
                        }
                        SwingUtilities.updateComponentTreeUI(this);
                        themeSombreActif = !themeSombreActif;

                    } catch (UnsupportedLookAndFeelException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );


        FenetreFormulaire fenetreFormulaire = new FenetreFormulaire();

        JButton boutonAjoutUtilisateur = new JButton("Ajouter un utilisateur");
        boutonAjoutUtilisateur.addActionListener(
                e -> {
                    JOptionPane.showMessageDialog(
                            this,
                            fenetreFormulaire,
                            "Ajouter un utilisateur",
                            JOptionPane.PLAIN_MESSAGE);
                }
        );

        //---------- BOUTONS DU HAUT -------

        Box boxBoutonHaut = Box.createVerticalBox();

        boxBoutonHaut.add(
                HelperForm.generateRow(boutonTheme,10,10,0,0, HelperForm.ALIGN_RIGHT)
        );
        boxBoutonHaut.add(
                HelperForm.generateRow(boutonAjoutUtilisateur,10,0,0,10, HelperForm.ALIGN_LEFT)
        );

        panneau.add(boxBoutonHaut,BorderLayout.NORTH);

        //----------- TABLE UTILISATEUR ------------

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Civilité");
        model.addColumn("Nom");
        model.addColumn("Prénom");
        model.addColumn("Email");
        model.addColumn("Pays");
        model.addColumn("Age");
        model.addColumn("Marié");
        model.addColumn("Actions");



        JTable tableUtilisateur = new JTable(model);
        tableUtilisateur.setEnabled(false);

        panneau.add(new JScrollPane(tableUtilisateur),BorderLayout.CENTER);

        Utilisateur utilisateurTest = new Utilisateur(
                "Monsieur",
                "BANSEPT",
                "Franck",
                "bansept.franck@gmail.com",
                new Pays("FRANCE","FR","fr.png"),
                35,
                true);

        model.addRow(utilisateurTest.getLigneTableau());

        setVisible(true);
    }



    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        new FenetreListeUtilisateur();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        String[] choix = {"Oui", "Ne pas fermer l'application"};
        int choixUtilisateur = JOptionPane.showOptionDialog(
                this,
                "Voulez-vous vraiment fermer l'application",
                "Confirmer",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                choix,
                choix[1]);

        if(choixUtilisateur == JOptionPane.YES_OPTION) {
            System.exit(1);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }


}
