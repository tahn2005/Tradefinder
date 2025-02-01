package trade;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

//todo
public class Updatemain extends JPanel{
    public Updatemain(ArrayList<Stock> stocks, ArrayList<Stock> goal){
        this.setLayout(new GridLayout(stocks.size(), 0));
        for(int i=0; i < stocks.size(); i++){
            this.add(new JLabel(stocks.get(i).getname()));
        }
    }
}