package trade;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

//visuals and user interface
public class Visuals{
    private JPanel original;                                            //original screen                                          
    private boolean threshold = false;                                  //for resizing
    
    //constructor
    public Visuals(){
        //initialize frame
        JFrame frame = new JFrame();
        frame.setTitle("Tradepath");    
        frame.setSize(1024, 768);              
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.setMinimumSize(new Dimension(700, 500));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int windowWidth = frame.getWidth();
        int windowHeight = frame.getHeight();
        int x = (screenSize.width - windowWidth) / 2;
        int y = (screenSize.height - windowHeight) / 2;
        frame.setLocation(x, y - 44);
        frame.setLayout(new BorderLayout());
        
        //initial screen
        Sidebar sidebar = new Sidebar();
        Mainscreen mainscreen = new Mainscreen();
        original = sidebar;
        frame.add(original, BorderLayout.WEST);
        frame.add(mainscreen, BorderLayout.CENTER);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = frame.getWidth();
                if (width < 800 && threshold == false) {
                    threshold = true;
                    frame.remove(original);
                } 
                else if(width >= 800 && threshold == true){
                    
                    threshold = false;
                    frame.add(original, BorderLayout.WEST);
                }
                frame.revalidate();  
                frame.repaint(); 
            }
        });

        //displays screen
        frame.setVisible(true);
    }

    //main function
    public static void main(String[] args) {
        Visuals window = new Visuals();
    }
}