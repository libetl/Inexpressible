package org.toilelibre.libe.inexpressible;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import org.toilelibre.libe.inexpressible.find.FindByGeneratingEachPossibleExpression;
import org.toilelibre.libe.inexpressible.find.FindByStrategies;
import org.toilelibre.libe.inexpressible.find.IFind;
import org.toilelibre.libe.inexpressible.find.StubFind;

public class SwingInterface {

    private static class JListUpdater implements Observer {

        @Override
        public void update (final Observable o, final Object arg) {
            ((DefaultListModel<String>) SwingInterface.list.getModel ()).addElement (arg.toString ());
        }

    }

    private static class SwingAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = -3196082895403331701L;

        public SwingAction () {
            this.putValue (Action.NAME, "SwingAction");
            this.putValue (Action.SHORT_DESCRIPTION, "Some short description");
        }

        @Override
        public void actionPerformed (final ActionEvent e) {
            final int digit = (Integer) SwingInterface.spinnerDigit.getValue ();
            final int numTerms = (Integer) SwingInterface.spinnerNumTerms.getValue ();
            IFind finder = new StubFind ();
            SwingInterface.list.setModel (new DefaultListModel<String> ());
            if (SwingInterface.rdbtnFindAnExpression.isSelected ()) {
                finder = new FindByStrategies ();
            } else if (SwingInterface.rdbtnGenerateEachPossible.isSelected ()) {
                finder = new FindByGeneratingEachPossibleExpression ();
            }
            final IFind theFinder = finder;
            new Thread () {
                @Override
                public void run () {
                    SwingInterface.lblResult.setText ("Result : ?");
                    final List<String> found = theFinder.find (digit, numTerms, new JListUpdater ());
                    SwingInterface.lblResult.setText ("Result : " + found.size ());
                }
            }.start ();
        }
    }

    /**
     * Launch the application.
     */
    public static void main (final String [] args) {
        EventQueue.invokeLater (new Runnable () {
            @Override
            public void run () {
                SwingInterface.startGUI ();
            }
        });
    }

    private static JLabel        lblResult;
    private static JList<String> list;
    private static JRadioButton  rdbtnFindAnExpression;
    private static JRadioButton  rdbtnGenerateEachPossible;

    private static JSpinner      spinnerDigit;

    private static JSpinner      spinnerNumTerms;

    /**
     * Create the frame.
     */
    private SwingInterface () {
    }

    public static void startGUI () {
        final JFrame frame = new JFrame ();
        frame.setVisible (true);
        frame.setTitle ("Lowest integer without combination");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.setBounds (100, 100, 664, 429);
        final JPanel contentPane = new JPanel ();
        contentPane.setBorder (new EmptyBorder (5, 5, 5, 5));
        frame.setContentPane (contentPane);
        contentPane.setLayout (new BoxLayout (contentPane, BoxLayout.Y_AXIS));

        final JPanel panel2 = new JPanel ();
        contentPane.add (panel2);

        final JLabel lblFindTheLowest = new JLabel ("Find the lowest integer which cannot be expressed by a mathematical expression");
        panel2.add (lblFindTheLowest);
        lblFindTheLowest.setAlignmentY (Component.TOP_ALIGNMENT);

        final JPanel panel = new JPanel ();
        contentPane.add (panel);
        panel.setLayout (new GridLayout (4, 2, 0, 0));

        final JLabel lblNumber = new JLabel ("Number :");
        panel.add (lblNumber);

        SwingInterface.spinnerDigit = new JSpinner ();
        panel.add (SwingInterface.spinnerDigit);
        SwingInterface.spinnerDigit.setModel (new SpinnerNumberModel (new Integer (9), new Integer (1), null, new Integer (1)));

        final JLabel lblNewLabel = new JLabel ("Terms");
        panel.add (lblNewLabel);

        SwingInterface.spinnerNumTerms = new JSpinner ();
        SwingInterface.spinnerNumTerms.setModel (new SpinnerNumberModel (new Integer (9), new Integer (1), null, new Integer (1)));
        panel.add (SwingInterface.spinnerNumTerms);

        final JLabel lblMethod = new JLabel ("Method");
        panel.add (lblMethod);

        final JPanel panel1 = new JPanel ();
        panel.add (panel1);
        panel1.setLayout (new BoxLayout (panel1, BoxLayout.Y_AXIS));

        final ButtonGroup btnGroup = new ButtonGroup ();

        SwingInterface.rdbtnGenerateEachPossible = new JRadioButton ("Generate each possible expression");
        btnGroup.add (SwingInterface.rdbtnGenerateEachPossible);
        panel1.add (SwingInterface.rdbtnGenerateEachPossible);

        SwingInterface.rdbtnFindAnExpression = new JRadioButton ("Find an expression for each integer from 0");
        SwingInterface.rdbtnFindAnExpression.setSelected (true);
        btnGroup.add (SwingInterface.rdbtnFindAnExpression);
        panel1.add (SwingInterface.rdbtnFindAnExpression);

        final JLabel lblNewLabel1 = new JLabel ("Start");
        panel.add (lblNewLabel1);

        final JButton btnNewButton = new JButton ("Go");
        btnNewButton.addActionListener (new SwingAction ());
        panel.add (btnNewButton);

        SwingInterface.list = new JList<String> ();
        SwingInterface.list.setAutoscrolls (true);
        final JScrollPane scrollPane = new JScrollPane (SwingInterface.list, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setAutoscrolls (true);

        final JPanel panel3 = new JPanel ();
        contentPane.add (panel3);
        panel3.add (scrollPane);

        final JPanel panel4 = new JPanel ();
        contentPane.add (panel4);

        SwingInterface.lblResult = new JLabel ("Result : ?");
        panel4.add (SwingInterface.lblResult);

    }
}
