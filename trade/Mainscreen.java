package trade;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Mainscreen extends JPanel{
    private JPanel optiondisplay;                                               //display panel for selecting user options
    private JPanel maindisplay;                                                 //display panel for main screen
    private JTextField user1;                                                   //text field for stock symbol entry
    private JTextField user2;                                                   //text field for number of shares entry
    private ArrayList<Stock> starting;                                          //current stock portfolio
    private ArrayList<Stock> goalstate;                                         //goal stock portfolio

    //constructor
    public Mainscreen(){
        starting = new ArrayList<Stock>();
        goalstate = new ArrayList<Stock>();

        this.setLayout(new BorderLayout());

        JPanel lborder = new JPanel();
        lborder.setPreferredSize(new Dimension(25, 0));
        lborder.setBackground(Color.BLACK);

        JPanel rborder = new JPanel();
        rborder.setPreferredSize(new Dimension(25, 0));
        rborder.setBackground(Color.BLACK);

        JPanel tborder = new JPanel();
        tborder.setPreferredSize(new Dimension(0, 30));
        tborder.setBackground(Color.BLACK);

        this.add(tborder, BorderLayout.NORTH);
        this.add(lborder, BorderLayout.WEST);
        this.add(rborder, BorderLayout.EAST);
        this.add(options(), BorderLayout.CENTER);
    }

    //initial main screen
    private JPanel maininit(){
        JPanel panel =  new JPanel();
        panel.setLayout(new GridLayout(3, 0));
        panel.setBackground(Color.BLACK);

        JLabel text = new JLabel("Portfolio PathFinder");
        text.setFont(new Font("Arial", Font.BOLD, 30));
        text.setForeground(new Color(200, 200, 200));
        
        JPanel textpanel = new JPanel();
        textpanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        textpanel.setBackground(Color.BLACK);
        textpanel.add(text);

        panel.add(new JLabel(""));
        panel.add(textpanel);
        panel.add(new JLabel(""));
        return panel;
    }

    //layout for current portfolio settings
    private JPanel options(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        JPanel newlabel = new JPanel();
        newlabel.setBackground(Color.BLACK);
        newlabel.setPreferredSize(new Dimension(0, 90));
        newlabel.setLayout(new GridLayout(3, 0));

        JLabel clabel = new JLabel("     Current Portfolio");
        clabel.setFont(new Font("Arial", Font.BOLD, 16));
        clabel.setForeground(Color.WHITE);
        newlabel.add(clabel);

        JButton addstock = new JButton("Add Stock");

        user1 = new JTextField(5);
        user2 = new JTextField(5);

        JPanel startstate = new JPanel();
        startstate.setBackground(Color.BLACK);
        startstate.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JLabel mysymbol = new JLabel("My Symbol:");
        mysymbol.setForeground(new Color(200, 200, 200));
        startstate.add(mysymbol);
        startstate.add(user1);
        JLabel myshares = new JLabel("  Shares:");
        myshares.setForeground(new Color(200, 200, 200));
        startstate.add(myshares);
        startstate.add(user2);
        startstate.add(new JLabel("  "));
        startstate.add(addstock);
        newlabel.add(startstate);
        
        JLabel glabel = new JLabel("     Goal Portfolio");
        glabel.setFont(new Font("Arial", Font.BOLD, 16));
        glabel.setForeground(Color.WHITE);
        newlabel.add(glabel);

        optiondisplay = new JPanel();
        optiondisplay.setLayout(new BorderLayout());
        optiondisplay.setPreferredSize(new Dimension(0, 80));
        optiondisplay.add(goalinit(), BorderLayout.CENTER);
        
        JPanel top = new JPanel();
        top.setPreferredSize(new Dimension(0, 200));
        top.setLayout(new BorderLayout());
        top.add(newlabel, BorderLayout.NORTH);
        top.add(optiondisplay, BorderLayout.CENTER);

        maindisplay = new JPanel();
        maindisplay.setLayout(new BorderLayout());
        maindisplay.add(maininit(), BorderLayout.CENTER);

        panel.add(top, BorderLayout.NORTH);
        panel.add(maindisplay, BorderLayout.CENTER);

        //responds to "Add Stock" button
        addstock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String u1 = user1.getText();
                String u2 = user2.getText();
                if((!u1.isEmpty()) && (!u2.isEmpty())){
                    starting.add(new Stock(u1, Integer.parseInt(u2)));
                    updatemaindisplay(new Updatemain(starting, goalstate));
                }
            }
        });

        return panel;
    }

    //update main screen
    private void updatemaindisplay(JPanel newPanel){
        maindisplay.removeAll();
        maindisplay.setLayout(new BorderLayout());
        maindisplay.add(newPanel, BorderLayout.CENTER);
        maindisplay.revalidate();  
        maindisplay.repaint(); 
    }

    //initial layout for goal portfolio settings selection
    private JPanel goalinit(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        JPanel opanel = new JPanel();
        opanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        opanel.setBackground(Color.BLACK);
       
        String[] options = {"Select", "Diversify Portfolio", "Grow Portfolio", "Both"};
        JComboBox<String> comboBox = new JComboBox<>(options);

        opanel.add(comboBox);
        
        JPanel bpanel = new JPanel();
        bpanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
        bpanel.setBackground(Color.BLACK);
        bpanel.setPreferredSize(new Dimension(0, 80));

        JButton resetbutton = new JButton("Reset");

        bpanel.add(resetbutton);
        
        //response to "Reset" screen
        resetbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user1.setText("");
                user2.setText("");
                starting.clear();
                goalstate.clear();
                updatemaindisplay(maininit());
            }
        });

        //updates user options screen depending on selection
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedItem = (String) comboBox.getSelectedItem();
                    if(selectedItem == "Diversify Portfolio"){
                        updateoption(goalupdate1());
                    }
                    else if(selectedItem == "Grow Portfolio"){
                        updateoption(goalupdate2());
                    }
                    else{
                        updateoption(goalupdate3());
                    }
                }
            }
        });

        panel.add(opanel, BorderLayout.CENTER);
        panel.add(bpanel, BorderLayout.SOUTH);
        return panel;
    }

    //layout for seletion "Diversify Portfolio"
    private JPanel goalupdate1(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel chooseoptions = new JPanel();
        chooseoptions.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        chooseoptions.setPreferredSize(new Dimension(0, 60));
        chooseoptions.setBackground(Color.BLACK);

        JLabel stocks = new JLabel("Stock:");
        stocks.setForeground(new Color(200, 200, 200));
        JLabel shares = new JLabel("Shares:");
        shares.setForeground(new Color(200, 200, 200));
        JLabel budget = new JLabel("Budget:");
        budget.setForeground(new Color(200, 200, 200));
        JLabel timeleft = new JLabel("Time:");
        timeleft.setForeground(new Color(200, 200, 200));

        JTextField stocktext = new JTextField(5);
        JTextField sharestext = new JTextField(5);
        JTextField budgettext = new JTextField(5);
        JTextField timetext = new JTextField(5);

        JButton addstock = new JButton("Add Stock");

        chooseoptions.add(stocks);
        chooseoptions.add(stocktext);
        chooseoptions.add(shares);
        chooseoptions.add(sharestext);
        chooseoptions.add(addstock);
        chooseoptions.add(budget);
        chooseoptions.add(budgettext);
        chooseoptions.add(timeleft);
        chooseoptions.add(timetext);

        JPanel bpanel = new JPanel();
        bpanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
        bpanel.setBackground(Color.BLACK);
        bpanel.setPreferredSize(new Dimension(0, 20));

        JButton resetbutton = new JButton("Reset");
        JButton findpath = new JButton("Find Path");

        bpanel.add(findpath);
        bpanel.add(resetbutton);
        
        //response for "Reset" button
        resetbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user1.setText("");
                user2.setText("");
                stocktext.setText("");
                sharestext.setText("");
                budgettext.setText("");
                timetext.setText("");
                starting.clear();
                goalstate.clear();
                updatemaindisplay(maininit());
                updateoption(goalinit());
            }
        });

        //response for "Find Path" button
        findpath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String u1 = budgettext.getText();
                String u2 = timetext.getText();
                if((starting.size() != 0) && (goalstate.size() != 0) && (!u1.isEmpty()) && (!u2.isEmpty())){
                    updatemaindisplay(loadscreen());
                }
            }
        });

        //response for "Add Stock" button
        addstock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String u1 = stocktext.getText();
                String u2 = sharestext.getText();
                if((!u1.isEmpty()) && (!u2.isEmpty())){
                    goalstate.add(new Stock(u1, Integer.parseInt(u2)));
                    updatemaindisplay(new Updatemain(starting, goalstate));
                }
            }
        });

        panel.add(chooseoptions, BorderLayout.NORTH);
        panel.add(bpanel, BorderLayout.CENTER);
        return panel;
    }

    //layout for selection "Grow Portfolio"
    private JPanel goalupdate2(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel chooseoptions = new JPanel();
        chooseoptions.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        chooseoptions.setPreferredSize(new Dimension(0, 60));
        chooseoptions.setBackground(Color.BLACK);

        JLabel worth = new JLabel("Portfolio Worth:");
        worth.setForeground(new Color(200, 200, 200));
        JLabel budget = new JLabel("Budget:");
        budget.setForeground(new Color(200, 200, 200));
        JLabel timeleft = new JLabel("Time:");
        timeleft.setForeground(new Color(200, 200, 200));

        JTextField worthtext = new JTextField(5);
        JTextField budgettext = new JTextField(5);
        JTextField timetext = new JTextField(5);

        chooseoptions.add(worth);
        chooseoptions.add(worthtext);
        chooseoptions.add(budget);
        chooseoptions.add(budgettext);
        chooseoptions.add(timeleft);
        chooseoptions.add(timetext);

        JPanel bpanel = new JPanel();
        bpanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
        bpanel.setBackground(Color.BLACK);
        bpanel.setPreferredSize(new Dimension(0, 20));

        JButton resetbutton = new JButton("Reset");
        JButton findpath = new JButton("Find Path");

        bpanel.add(findpath);
        bpanel.add(resetbutton);
        
        //response for "Reset" button
        resetbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user1.setText("");
                user2.setText("");
                worthtext.setText("");
                budgettext.setText("");
                timetext.setText("");
                starting.clear();
                goalstate.clear();
                updatemaindisplay(maininit());
                updateoption(goalinit());
            }
        });

        //response for "Find Path" button
        findpath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String u1 = worthtext.getText();
                String u2 = budgettext.getText();
                String u3 = timetext.getText();
                if((starting.size() != 0) && (!u1.isEmpty()) && (!u2.isEmpty()) && (!u3.isEmpty())){
                    updatemaindisplay(loadscreen());
                }
            }
        });

        panel.add(chooseoptions, BorderLayout.NORTH);
        panel.add(bpanel, BorderLayout.CENTER);
        return panel;
    }

    //layout for selection "Both"
    private JPanel goalupdate3(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel chooseoptions = new JPanel();
        chooseoptions.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        chooseoptions.setPreferredSize(new Dimension(0, 60));
        chooseoptions.setBackground(Color.BLACK);

        JLabel stocks = new JLabel("Stock:");
        stocks.setForeground(new Color(200, 200, 200));
        JLabel shares = new JLabel("Shares:");
        shares.setForeground(new Color(200, 200, 200));
        JLabel worth = new JLabel("Portfolio Worth:");
        worth.setForeground(new Color(200, 200, 200));
        JLabel budget = new JLabel("Budget:");
        budget.setForeground(new Color(200, 200, 200));
        JLabel timeleft = new JLabel("Time:");
        timeleft.setForeground(new Color(200, 200, 200));

        JTextField stocktext = new JTextField(5);
        JTextField sharestext = new JTextField(5);
        JTextField worthtext = new JTextField(5);
        JTextField budgettext = new JTextField(5);
        JTextField timetext = new JTextField(5);

        JButton addstock = new JButton("Add Stock");

        chooseoptions.add(stocks);
        chooseoptions.add(stocktext);
        chooseoptions.add(shares);
        chooseoptions.add(sharestext);
        chooseoptions.add(addstock);
        chooseoptions.add(worth);
        chooseoptions.add(worthtext);
        chooseoptions.add(budget);
        chooseoptions.add(budgettext);
        chooseoptions.add(timeleft);
        chooseoptions.add(timetext);

        JPanel bpanel = new JPanel();
        bpanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
        bpanel.setBackground(Color.BLACK);
        bpanel.setPreferredSize(new Dimension(0, 20));

        JButton resetbutton = new JButton("Reset");
        JButton findpath = new JButton("Find Path");

        bpanel.add(findpath);
        bpanel.add(resetbutton);
        
        //response for "Reset" button
        resetbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user1.setText("");
                user2.setText("");
                stocktext.setText("");
                sharestext.setText("");
                worthtext.setText("");
                budgettext.setText("");
                timetext.setText("");
                starting.clear();
                goalstate.clear();
                updatemaindisplay(maininit());
                updateoption(goalinit());
            }
        });

        //response to "Find Path" button
        findpath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String u1 = worthtext.getText();
                String u2 = budgettext.getText();
                String u3 = timetext.getText();
                if((starting.size() != 0) && (goalstate.size() != 0) && (!u1.isEmpty()) && (!u2.isEmpty()) && (!u3.isEmpty())){
                    updatemaindisplay(loadscreen());
                }
            }
        });

        //response to "Add Stock" button
        addstock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String u1 = stocktext.getText();
                String u2 = sharestext.getText();
                if((!u1.isEmpty()) && (!u2.isEmpty())){
                    goalstate.add(new Stock(u1, Integer.parseInt(u2)));
                    updatemaindisplay(new Updatemain(starting, goalstate));
                }
            }
        });

        panel.add(chooseoptions, BorderLayout.NORTH);
        panel.add(bpanel, BorderLayout.CENTER);
        return panel;
    }

    //updates user input options display
    private void updateoption(JPanel newPanel){
        optiondisplay.removeAll();
        optiondisplay.setLayout(new BorderLayout());
        optiondisplay.add(newPanel, BorderLayout.CENTER);
        optiondisplay.revalidate();  
        optiondisplay.repaint(); 
    }

    //loading screen
    private JPanel loadscreen(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new GridLayout(3, 0));

        JLabel text = new JLabel("Loading...");
        text.setFont(new Font("Arial", Font.BOLD, 30));
        text.setForeground(Color.WHITE);

        JPanel textpanel = new JPanel();
        textpanel.setBackground(Color.BLACK);
        textpanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        textpanel.add(text);

        panel.add(new JLabel(""));
        panel.add(textpanel);
        panel.add(new JLabel(""));
        return panel;
    }
}