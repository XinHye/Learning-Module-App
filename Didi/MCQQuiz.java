import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

/*interface QuizListener {
    void onQuizComplete(int correct_guesses, long totalTimeTaken);
}*/
public class MCQQuiz extends JFrame implements ActionListener {
    
    private QuizListener listener; 
    private JFrame frame; 
    
    String[] questions = {
        "int count = 3;\n   printf(\"%d\", count++);\n   printf(\"%d\", count);\n   What is the output?", 
        "What is the assignment operator for remainder?",
        "int count = 5;\n   printf(\"%d\", ++count);\n   printf(\"%d\", count);\n   What is the output?", 
        "What happens when the higher data type is converted to a lower data type?", 
        "Choose the correct syntax to do C-type casting or explicit conversion?", 
        "The function prototype for the function rand is in what library?"
    };
     

    String[][] options = {
        {"count = 3, count = 4", "count = 3, count = 3", "count = 4, count = 3", "count = 4, count = 4"},
        {"-=", "%=", "/=", "*="},
        {"count = 3, count = 4", "count = 3, count = 3", "count = 4, count  4", "count = 4, count = 3"},
        {"Nothing happens", "Data stays as higher data type", "Data will be truncated", "Data converted to lower data type"},
        {"(data type) variable_name", "[data type] variable_name", "{data type} variable_name", "variable_name (data type)"},
        {"math.h", "stdlib.h", "stdio.h", "string.h"}
    };
    char[] answers = {'A', 'B', 'C', 'C', 'A', 'B'};
    
    char guess;
    char answer;
    int index;
    int correct_guesses = 0;
    int total_questions = questions.length;
    int result;
    int seconds = 15;

    long[] timeTaken = new long[total_questions]; // Array to store time taken for each question

    JTextField textfield = new JTextField();
    JTextArea textarea = new JTextArea();
    JButton buttonA = new JButton();
    JButton buttonB = new JButton();
    JButton buttonC = new JButton();
    JButton buttonD = new JButton();
    JLabel answer_labelA = new JLabel();
    JLabel answer_labelB = new JLabel();
    JLabel answer_labelC = new JLabel();
    JLabel answer_labelD = new JLabel();
    JLabel time_label = new JLabel();
    JLabel seconds_left = new JLabel();

    Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            seconds--;
            seconds_left.setText(String.valueOf(seconds));
            if (seconds <= 0) {
                displayAnswer();
            }
            
            long timeElapsed = 15000 - (seconds * 1000); // Calculate time taken in milliseconds
                timeTaken[index] = timeElapsed;
        }
    });

    public MCQQuiz(QuizListener listener, JFrame frame) {
        this.listener = listener;
        this.frame = frame;
        initializeUI();
        nextQuestion();
        /**frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 870);
        frame.getContentPane().setBackground(new Color(33, 25, 81));
        frame.setLayout(null);
        //frame.setResizable(false);
        frame.setLocationRelativeTo(null);**/
    }

    private void initializeUI() {
        frame.getContentPane().removeAll();
        frame.repaint();

        textfield.setBounds(0, 0, 600, 50);
        textfield.setBackground(new Color(33, 25, 81));
        textfield.setForeground(new Color(21, 245, 186));
        textfield.setFont(new Font("Ink Free", Font.BOLD, 18));
        textfield.setBorder(BorderFactory.createBevelBorder(1));
        textfield.setHorizontalAlignment(JTextField.CENTER);
        textfield.setEditable(false); 

        textarea.setBounds(0, 50, 600, 100);
        textarea.setLineWrap(true);
        textarea.setWrapStyleWord(true);
        textarea.setBackground(new Color(33, 25, 81));
        textarea.setForeground(new Color(21, 245, 186));
        textarea.setFont(new Font("MV Boli", Font.BOLD, 20));
        textarea.setBorder(BorderFactory.createBevelBorder(1));
        textarea.setEditable(false);

        buttonA.setBounds(40, 150, 70, 70);
        buttonA.setFont(new Font("MV Boli", Font.BOLD, 20));
        buttonA.setFocusable(false);
        buttonA.addActionListener(this);
        buttonA.setText("A"); 

        buttonB.setBounds(40, 225, 70, 70);
        buttonB.setFont(new Font("MV Boli", Font.BOLD, 20));
        buttonB.setFocusable(false);
        buttonB.addActionListener(this);
        buttonB.setText("B");

        buttonC.setBounds(40, 300, 70, 70);
        buttonC.setFont(new Font("MV Boli", Font.BOLD, 20));
        buttonC.setFocusable(false);
        buttonC.addActionListener(this);
        buttonC.setText("C");

        buttonD.setBounds(40, 375, 70, 70);
        buttonD.setFont(new Font("MV Boli", Font.BOLD, 20));
        buttonD.setFocusable(false);
        buttonD.addActionListener(this);
        buttonD.setText("D");

        answer_labelA.setBounds(135, 135, 500, 100);
        answer_labelA.setBackground(new Color(50, 50, 50));
        answer_labelA.setForeground(new Color(25, 255, 0));
        answer_labelA.setFont(new Font("MV Boli", Font.PLAIN, 20));

        answer_labelB.setBounds(135, 210, 500, 100);
        answer_labelB.setBackground(new Color(50, 50, 50));
        answer_labelB.setForeground(new Color(25, 255, 0));
        answer_labelB.setFont(new Font("MV Boli", Font.PLAIN, 20));

        answer_labelC.setBounds(135, 285, 500, 100);
        answer_labelC.setBackground(new Color(50, 50, 50));
        answer_labelC.setForeground(new Color(25, 255, 0));
        answer_labelC.setFont(new Font("MV Boli", Font.PLAIN, 20));

        answer_labelD.setBounds(135, 360, 500, 100);
        answer_labelD.setBackground(new Color(50, 50, 50));
        answer_labelD.setForeground(new Color(25, 255, 0));
        answer_labelD.setFont(new Font("MV Boli", Font.PLAIN, 20));

        seconds_left.setBounds(250, 500, 100, 50);
        seconds_left.setBackground(new Color(240, 243, 255));
        seconds_left.setForeground(new Color(255, 0, 0));
        seconds_left.setFont(new Font("Ink Free", Font.BOLD, 24));
        seconds_left.setBorder(BorderFactory.createBevelBorder(1));
        seconds_left.setOpaque(true);
        seconds_left.setHorizontalAlignment(JTextField.CENTER);
        seconds_left.setText(String.valueOf(seconds));

        time_label.setBounds(250, 470, 100, 25);
        time_label.setBackground(new Color(240, 243, 255));
        time_label.setForeground(new Color(255, 0, 0));
        time_label.setFont(new Font("MV Boli", Font.PLAIN, 16));
        time_label.setHorizontalAlignment(JTextField.CENTER);
        time_label.setText("timer");

        frame.add(time_label);
        frame.add(seconds_left);
        frame.add(answer_labelA);
        frame.add(answer_labelB);
        frame.add(answer_labelC);
        frame.add(answer_labelD);
        frame.add(buttonA);
        frame.add(buttonB);
        frame.add(buttonC);
        frame.add(buttonD);
        frame.add(textarea);
        frame.add(textfield);
        //frame.setVisible(true);
         
        //frame.repaint();
    }

    public void nextQuestion() {
        if (index >= total_questions) {
            results();
        } else {
            textfield.setText("Question " + (index + 7));
            textarea.setText(questions[index]);
            answer_labelA.setText(options[index][0]);
            answer_labelB.setText(options[index][1]);
            answer_labelC.setText(options[index][2]);
            answer_labelD.setText(options[index][3]);
            timer.start();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        buttonA.setEnabled(false);
        buttonB.setEnabled(false);
        buttonC.setEnabled(false);
        buttonD.setEnabled(false);

        if (e.getSource() == buttonA) {
            answer = 'A';
            if (answer == answers[index]) {
                correct_guesses++;
            }
        }
        if (e.getSource() == buttonB) {
            answer = 'B';
            if (answer == answers[index]) {
                correct_guesses++;
            }
        }
        if (e.getSource() == buttonC) {
            answer = 'C';
            if (answer == answers[index]) {
                correct_guesses++;
            }
        }
        if (e.getSource() == buttonD) {
            answer = 'D';
            if (answer == answers[index]) {
                correct_guesses++;
            }
        }
        // Record the time taken
        long timeElapsed = 15000 - (seconds * 1000); // Calculate time taken in milliseconds
        timeTaken[index] = timeElapsed;
        displayAnswer();
    }

    public void displayAnswer() {
        timer.stop();
        seconds = 15;
        seconds_left.setText(String.valueOf(seconds));


        buttonA.setEnabled(false);
        buttonB.setEnabled(false);
        buttonC.setEnabled(false);
        buttonD.setEnabled(false);

        if (answers[index] != 'A') answer_labelA.setForeground(new Color(255, 0, 0));
        if (answers[index] != 'B') answer_labelB.setForeground(new Color(255, 0, 0));
        if (answers[index] != 'C') answer_labelC.setForeground(new Color(255, 0, 0));
        if (answers[index] != 'D') answer_labelD.setForeground(new Color(255, 0, 0));

        Timer pause = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answer_labelA.setForeground(new Color(25, 255, 0));
                answer_labelB.setForeground(new Color(25, 255, 0));
                answer_labelC.setForeground(new Color(25, 255, 0));
                answer_labelD.setForeground(new Color(25, 255, 0));

                answer = ' ';
                seconds = 15;
                seconds_left.setText(String.valueOf(seconds));
                buttonA.setEnabled(true);
                buttonB.setEnabled(true);
                buttonC.setEnabled(true);
                buttonD.setEnabled(true);
                index++;
                nextQuestion();
            }
        });
        pause.setRepeats(false);
        pause.start();
    }

    public void results() {
        frame.getContentPane().removeAll();
        frame.repaint();

        // Calculate the total time taken
        long totalTimeTaken = 0;
        for (long time : timeTaken) {
            totalTimeTaken += time;
        }

        frame.repaint();

        // Notify the listener that the quiz is complete
        if (listener != null) {
            listener.onQuizComplete(correct_guesses, totalTimeTaken);
        }
    }
    
}