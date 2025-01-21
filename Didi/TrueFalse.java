import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class TrueFalse extends JFrame implements ActionListener {

    private QuizListener listener;
    private JFrame frame; // Reference to the main frame

    String[] questions = {
        "All increment and decrement operators are unary operators.",
        "Value on the right side can be different data-type of variable on the left side.",
        "count = count + first NOT SIMILAR TO count += first. ",
        "The floating-point type function declarations are found in the math.h header file.",
        "floor(x) function returns the largest integer smaller than or equal to x.",
        "++count is the postfix form and count++ is the prefix form of the increment operator."
    };

    char[] answers = {
        'T',
        'F',
        'F',
        'T',
        'T',
        'F'
    };

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
    JButton buttonT = new JButton();
    JButton buttonF = new JButton();
    JLabel time_label = new JLabel();
    JLabel seconds_left = new JLabel();
    JTextField number_right = new JTextField();
    JTextField percentage = new JTextField();
    JTextArea score_message = new JTextArea(); // Changed to JTextArea

    Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            seconds--;
            seconds_left.setText(String.valueOf(seconds));
            if (seconds <= 0) {
                buttonT.setEnabled(false);
                buttonF.setEnabled(false);

                if (answers[index] == 'T') {
                    buttonT.setBackground(Color.GREEN);
                } else {
                    buttonF.setBackground(Color.GREEN);
                }
                buttonT.setOpaque(true);
                buttonT.repaint();
                buttonF.setOpaque(true);
                buttonF.repaint();
                // Record the time taken
                long timeElapsed = 15000 - (seconds * 1000); // Calculate time taken in milliseconds
                timeTaken[index] = timeElapsed;
                displayAnswer();
            }
        }
    });

    public TrueFalse(QuizListener listener, JFrame frame) {
        this.listener = listener;
        this.frame = frame;
        initializeUI();
        nextQuestion();
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

        buttonT.setBounds(80, 190, 160, 100);
        buttonT.setFont(new Font("MV Boli", Font.BOLD, 20));
        buttonT.setFocusable(false);
        buttonT.addActionListener(this);
        buttonT.setText("True");

        buttonF.setBounds(340, 190, 160, 100);
        buttonF.setFont(new Font("MV Boli", Font.BOLD, 20));
        buttonF.setFocusable(false);
        buttonF.addActionListener(this);
        buttonF.setText("False");

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
        time_label.setFont(new Font("MV Boli", Font.BOLD, 16));
        time_label.setHorizontalAlignment(JTextField.CENTER);
        time_label.setText("Timer");

        number_right.setBounds(200, 300, 200, 50);
        number_right.setBackground(new Color(131, 111, 255));
        number_right.setForeground(new Color(21, 245, 186));
        number_right.setFont(new Font("Ink Free", Font.BOLD, 24));
        number_right.setBorder(BorderFactory.createBevelBorder(1));
        number_right.setHorizontalAlignment(JTextField.CENTER);
        number_right.setEditable(false);

        percentage.setBounds(200, 360, 200, 50);
        percentage.setBackground(new Color(131, 111, 255));
        percentage.setForeground(new Color(21, 245, 186));
        percentage.setFont(new Font("Ink Free", Font.BOLD, 24));
        percentage.setBorder(BorderFactory.createBevelBorder(1));
        percentage.setHorizontalAlignment(JTextField.CENTER);
        percentage.setEditable(false);

        score_message.setBounds(200, 480, 300, 100); // Adjusted size for JTextArea
        score_message.setBackground(new Color(131, 111, 255));
        score_message.setForeground(new Color(21, 245, 186));
        score_message.setFont(new Font("Ink Free", Font.BOLD, 18));
        score_message.setBorder(BorderFactory.createBevelBorder(1));
        score_message.setLineWrap(true); // Enable text wrapping
        score_message.setWrapStyleWord(true); // Wrap at word boundaries
        score_message.setEditable(false);

        frame.add(time_label);
        frame.add(seconds_left);
        frame.add(buttonT);
        frame.add(buttonF);
        frame.add(textarea);
        frame.add(textfield);

        frame.repaint();
    }

    public void nextQuestion() {
        if (index >= total_questions) {
            results();
        } else {
            textfield.setText("Question " + (index + 1));
            textarea.setText(questions[index]);
            timer.start();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        buttonT.setEnabled(false);
        buttonF.setEnabled(false);

        if (e.getSource() == buttonT) {
            answer = 'T';
            if (answer == answers[index]) {
                correct_guesses++;
                buttonT.setBackground(Color.GREEN);
            } else {
                buttonT.setBackground(Color.RED);
            }
            buttonT.setOpaque(true);
            buttonT.repaint();
        }
        if (e.getSource() == buttonF) {
            answer = 'F';
            if (answer == answers[index]) {
                correct_guesses++;
                buttonF.setBackground(Color.GREEN);
            } else {
                buttonF.setBackground(Color.RED);
            }
            buttonF.setOpaque(true);
            buttonF.repaint();
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

        Timer pause = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonT.setBackground(null);
                buttonF.setBackground(null);
                buttonT.setEnabled(true);
                buttonF.setEnabled(true);
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

        //result = (int) ((correct_guesses / (double) total_questions) * 100);

        //textfield.setText("RESULTS!");
        //number_right.setText("(" + correct_guesses + "/" + total_questions + ")");
        //percentage.setText(result + "%");

        //frame.add(textfield);
        //frame.add(number_right);
        //frame.add(percentage);

        //String scoreMessageText = "You have completed the True/False quiz with a score of " + result + "%.\n"
                //+ "You got " + correct_guesses + " out of " + total_questions + " correct.";
        //score_message.setText(scoreMessageText);
        //frame.add(score_message);

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
