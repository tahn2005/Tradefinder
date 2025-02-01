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
public class Updatewidget extends JPanel{
    public Updatewidget(String text){
        this.setLayout(new BorderLayout());
        this.add(new JLabel(text), BorderLayout.CENTER);
    }
}