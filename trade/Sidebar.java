package trade;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Sidebar extends JPanel{
    private JPanel display;                                                             //display panel for widget

    //constructor
    public Sidebar(){
        this.setPreferredSize(new Dimension(332, 0));
        this.setLayout(new BorderLayout());

        JPanel topborder = new JPanel();
        topborder.setPreferredSize(new Dimension(0, 30));
        topborder.setBackground(new Color(50, 50, 50));

        JPanel leftborder = new JPanel();
        leftborder.setPreferredSize(new Dimension(25, 0));
        leftborder.setBackground(new Color(50, 50, 50));

        JPanel rightborder = new JPanel();
        rightborder.setPreferredSize(new Dimension(25, 0));
        rightborder.setBackground(new Color(50, 50, 50));

        ImageIcon originalIcon = new ImageIcon("finnhub.png");
        Image img = originalIcon.getImage(); 
        Image resizedImg = img.getScaledInstance(originalIcon.getIconWidth()/40, originalIcon.getIconHeight()/40, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImg); 
        JLabel label = new JLabel(resizedIcon);  
        label.setPreferredSize(new Dimension(0, 30));

        JLabel bridge1 = new JLabel("");
        bridge1.setPreferredSize(new Dimension(0, 5));
        JLabel bridge2 = new JLabel("");
        bridge2.setPreferredSize(new Dimension(0, 5));

        JPanel botborder = new JPanel();
        botborder.setBackground(new Color(50, 50, 50));
        botborder.setLayout(new BorderLayout());
        botborder.setPreferredSize(new Dimension(0, 40));
        botborder.add(label, BorderLayout.CENTER);
        botborder.add(bridge1, BorderLayout.NORTH);
        botborder.add(bridge2, BorderLayout.SOUTH);

        this.add(topborder, BorderLayout.NORTH);
        this.add(rightborder, BorderLayout.EAST);
        this.add(leftborder, BorderLayout.WEST);
        this.add(botborder, BorderLayout.SOUTH);
        this.add(innerside(), BorderLayout.CENTER);
    }

    //inner sidebar
    private JPanel innerside(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        JLabel marketlabel = new JLabel("Market Data");
        marketlabel.setFont(new Font("Arial", Font.BOLD, 18));
        marketlabel.setForeground(Color.WHITE);
        marketlabel.setPreferredSize(new Dimension(0, 20));

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        String cdate = currentDate.format(formatter);
        JLabel datelabel = new JLabel(cdate);
        datelabel.setFont(new Font("Arial", Font.BOLD, 18));
        datelabel.setForeground(new Color(200, 200, 200));
        datelabel.setPreferredSize(new Dimension(0, 20));

        JLabel bridge = new JLabel("");
        bridge.setPreferredSize(new Dimension(0, 20));

        JPanel header = new JPanel();
        header.setBackground(new Color(50, 50, 50));
        header.setPreferredSize(new Dimension(0, 70));
        header.setLayout(new BorderLayout());
        header.add(marketlabel, BorderLayout.NORTH);
        header.add(datelabel, BorderLayout.CENTER);
        header.add(bridge, BorderLayout.SOUTH);

        JLabel searchtext = new JLabel("Search: ");
        searchtext.setForeground(new Color(200, 200, 200));

        JTextField textField = new JTextField(10);
        
        JPanel searchbar = new JPanel();
        searchbar.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 5));
        searchbar.setBackground(new Color(50, 50, 50));
        searchbar.add(searchtext);
        searchbar.add(textField);
        searchbar.setPreferredSize(new Dimension(0, 30));


        JPanel searchpanel = new JPanel();
        searchpanel.setLayout(new BorderLayout());
        searchpanel.setPreferredSize(new Dimension(0, 40));
        searchpanel.setBackground(new Color(50, 50, 50));
        searchpanel.add(searchbar, BorderLayout.SOUTH);

        JLabel symbol = new JLabel("My Symbols ⇕");
        symbol.setFont(new Font("Arial", Font.BOLD, 16));
        symbol.setForeground(Color.WHITE);
        symbol.setPreferredSize(new Dimension(0, 20));
    
        JLabel bottom = new JLabel("");
        bottom.setPreferredSize(new Dimension(0, 20));

        JPanel under = new JPanel();
        under.setPreferredSize(new Dimension(0, 80));
        under.setLayout(new BorderLayout());
        under.setBackground(new Color(50, 50, 50));
        under.add(symbol, BorderLayout.NORTH);
        under.add(searchpanel, BorderLayout.CENTER);
        under.add(bottom, BorderLayout.SOUTH);

        JPanel top = new JPanel();
        top.setPreferredSize(new Dimension(0, 150));
        top.setLayout(new BorderLayout());
        top.add(header, BorderLayout.NORTH);
        top.add(under, BorderLayout.SOUTH);

        display = new JPanel();
        display.setLayout(new BorderLayout());
        display.add(new Defaultwidget(), BorderLayout.CENTER);

        //responds to search bar
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e){
                handleTextChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e){
                handleTextChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e){
                handleTextChange();
            }

            private void handleTextChange() {
                String input = textField.getText();
                if (!input.isEmpty()){
                    updatewidgetcontent(new Updatewidget(input));
                } 
                else{
                    updatewidgetcontent(new Defaultwidget());
                }
            }
        });

        panel.add(top, BorderLayout.NORTH);
        panel.add(display, BorderLayout.CENTER);
        return panel;
    }

    //updates widget display
    private void updatewidgetcontent(JPanel newPanel){
        display.removeAll();  
        display.setLayout(new BorderLayout());
        display.add(newPanel, BorderLayout.CENTER);  
        display.revalidate();  
        display.repaint();   
    }
}