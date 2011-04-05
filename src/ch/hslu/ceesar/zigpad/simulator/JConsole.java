package ch.hslu.ceesar.zigpad.simulator;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.io.*;
import java.util.LinkedList;
import javax.swing.*;

public class JConsole extends JFrame
{

    JTextArea textfeld = new JTextArea();
    LinkedList<Integer> keyBuffer = new LinkedList<Integer>();
    boolean startReading = false;

    public JConsole(String description)
    {
        super(description);
        add(textfeld);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 300));

        JScrollPane scrollPane = new JScrollPane(textfeld);
        getContentPane().add(scrollPane);
        Font font = new Font("Courier new", Font.ROMAN_BASELINE, 12);
        textfeld.setFont(font);

        textfeld.addKeyListener(new KeyListener() {

            public synchronized void keyTyped(KeyEvent e)
            {
                if (e.getSource() == textfeld && !startReading)
                {
                    int c = e.getKeyChar();
                    if (c == 8 && !keyBuffer.isEmpty()) keyBuffer.removeLast(); //wenn korrektur
                    else if (c != 8)keyBuffer.addLast(c);
                    if (c == 10) startReading = true;  //wennEnter gedrückt
                }
            }
            public void keyPressed(KeyEvent e) { }
            public void keyReleased(KeyEvent e) { }
        });

        redirectOutputStreams();
        redirectInputStreams();
        pack();
        setVisible(true);
    }

    private void redirectOutputStreams()
    {
        OutputStream out = new OutputStream() {

            @Override
            public void write(final int b) throws IOException
            {
                 SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                                char c = (char)((b < 0)?b+256:b); // achte auf öäü
                                textfeld.append(""+c);
                            }
                  });
            }
        };

        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(out, true));
    }

    private void redirectInputStreams()
    {

        InputStream in = new InputStream() {

            @Override
            public synchronized int read() throws IOException
            {
//                while (key == 0 || key == 8);
//                int k = key;
//                key = 0;
//                if (k == 10) key = -1;                 //wenn Enter gedrückt
//
//                return k;
                while(startReading == false);
                int key = -1;
                if (!keyBuffer.isEmpty())  key = keyBuffer.removeFirst();
                else startReading = false;

                return key;
            }
            
        };

        System.setIn(in);
    }


    public static void main(String[] args)
    {
        JConsole cons = new JConsole("System Console");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Console mit üäö-Support (korrekte Funktion nur ausserhalb der IDE)");
       
        String s = "";
        System.out.print("gib was ein:");
        try {  s = reader.readLine();}catch (IOException e){System.out.println(e.getMessage());}
        System.out.println("deine Eingabe: "+s);

        System.out.print("gib noch was ein:");
        try {  s = reader.readLine();}catch (IOException e){System.out.println(e.getMessage());}
        System.out.println("deine zweite Eingabe: "+s);

        System.out.print("gib 'exit' ein ");
        try {  s = reader.readLine();}catch (IOException e){System.out.println(e.getMessage());}
        if (s.equals("exit")) cons.dispose();
        else throw new RuntimeException("konnte nicht sauber schliessen");
        try { cons.dispose(); } catch (Exception e) { }
        
    }
}



 