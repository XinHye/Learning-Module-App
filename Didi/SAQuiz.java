import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class SAQuiz extends JFrame implements ActionListener {
    
    private QuizListener listener; 
    private JFrame frame;  

    String[] questions = {
        "Preset sequence of \"random\" numbers.",
        "What are the two built-in Mathematical Functions in C?",
        "What is the function that returns the absolute value of x, where x is an integer?",
        "What is the syntax to get a random number between 1 and 6?",
        "The way to convert a variable from one data type to another data type is called _______.",
        "What is the function that returns a \"random\" number between 0 and RAND_MAX?"
    };
    
    

    String[] answers = {"pseudorandom", "math.h, stdlib.h", "abs(x)", "1+(rand()%6)", "Type casting", "rand"};

    int index = 0;
    int correct_guesses = 0;
    int total_questions = questions.length;
    int result;
    int seconds = 15;
    
    long[] timeTaken = new long[total_questions]; // Array to store time taken for each question

    JTextField textfield = new JTextField();
    JTextArea textarea = new JTextArea();
    JTextField answerField = new JTextField();
    JLabel feedbackLabel = new JLabel();
    JButton submitButton = new JButton("Submit");
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

    public SAQuiz(QuizListener listener, JFrame frame) {
        this.listener = listener;
        this.frame = frame;
        initializeUI();
        nextQuestion();
    } 

    private void initializeUI(){
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
        textarea.setForeground(new Color(240, 245, 186));
        textarea.setFont(new Font("MV Boli", Font.PLAIN, 20));
        textarea.setBorder(BorderFactory.createBevelBorder(1));
        textarea.setEditable(false);

        answerField.setBounds(150, 150, 350, 50);
        answerField.setFont(new Font("MV Boli", Font.PLAIN, 20));

        feedbackLabel.setBounds(150, 210, 350, 50);
        feedbackLabel.setFont(new Font("MV Boli", Font.PLAIN, 20));
        feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);

        submitButton.setBounds(250, 270, 150, 50);
        submitButton.setFont(new Font("MV Boli", Font.BOLD, 20));
        submitButton.setFocusable(false);
        submitButton.addActionListener(this);

        seconds_left.setBounds(250, 500, 100, 50);
        seconds_left.setBackground(new Color(240, 243, 255));
        seconds_left.setForeground(new Color(255, 0, 0));
        seconds_left.setFont(new Font("Ink Free", Font.BOLD, 20));
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
        frame.add(answerField);
        frame.add(feedbackLabel);
        frame.add(submitButton);
        frame.add(textarea);
        frame.add(textfield);
        timer.start();
    }

    public void nextQuestion() {
        if (index >= total_questions) {
            results();
        } else {
            textfield.setText("Question " + (index + 13));
            textarea.setText(questions[index]);
            feedbackLabel.setText("");
            timer.start();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String userAnswer = answerField.getText().trim();
            if (userAnswer.equalsIgnoreCase(answers[index])) {
                correct_guesses++;
                feedbackLabel.setForeground(Color.GREEN);
                feedbackLabel.setText("Correct!");
            } else {
                feedbackLabel.setForeground(Color.RED);
                feedbackLabel.setText("Answer: " + answers[index]);
            }
            displayAnswer();
        }
    }

    public void displayAnswer() {
        timer.stop();
        seconds = 15; 
        seconds_left.setText(String.valueOf(seconds));

        Timer pause = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answerField.setText("");
                seconds_left.setText(String.valueOf(seconds));
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
