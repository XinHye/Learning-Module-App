import java.awt.event.*;
import java.awt.*;
import javax.swing.Timer;
import javax.swing.*;
import java.util.List;
import java.util.*;

public class Matching extends JFrame implements ActionListener{
    private QuizListener listener;
    private JFrame frame; // Reference to the main frame
    

    String[] questions = {
        "pow(2,3)",
        "Seeds the random number generator",
        "ceil(4.0)",
        "floor(-5.7)",
        "Integer data type functions.",
        "ceil(4.2)"
    };

    String[] answers = {
        "8",
        "srand(time(NULL))",
        "4.0",
        "-6.0",
        "<stdlib.h>",
        "5.0"
    };

    String[] options = {
        " ", "<math.h>", "8", "<stdlib.h>",
        "srand(time(NULL))", "-5.0", "rand()", "5.0",
        "-6.0", "4.0", "6", "9"
    };

    int correct_guesses = 0;
    int total_questions = questions.length;
    int index = 0;
    int seconds = 15;

    long[] timeTaken = new long[total_questions]; // Array to store time taken for each question

    //static JFrame frame = new JFrame();
    JTextField textfield = new JTextField();
    JTextArea textarea = new JTextArea();
    JComboBox<String>[] comboBoxes = new JComboBox[total_questions];
    JButton nextButton = new JButton("Next");
    JTextField number_right = new JTextField();
    JTextField percentage = new JTextField();
    JTextArea score_message = new JTextArea(); // Changed to JTextArea
    JLabel seconds_left = new JLabel();
    JLabel time_label = new JLabel(); 

    Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            seconds--;
            seconds_left.setText(String.valueOf(seconds));
            if (seconds <= 0) {
                checkAnswers();
                Timer pause = new Timer(2000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showResults();
                    }
                });
                pause.setRepeats(false);
                pause.start();
            }
        }
    });

    private int correctGuessesFromAll;
    private long totalTimeTakenFromAll;
    static Leaderboard leaderboard = new Leaderboard();

    public Matching(int correctGuesses, long totalTimeTaken, JFrame frame) {
        this.correctGuessesFromAll = correctGuesses;
        this.totalTimeTakenFromAll = totalTimeTaken;
        this.frame = frame;
        initializeUI();
    }

    private void initializeUI() {
        frame.getContentPane().removeAll();
        frame.repaint();

        textfield.setBounds(0, 0, 600, 70); // Adjusted size
        textfield.setBackground(new Color(33, 25, 81));
        textfield.setForeground(new Color(21, 245, 186));
        textfield.setFont(new Font("Ink Free", Font.BOLD, 14)); // Adjusted font size
        textfield.setBorder(BorderFactory.createBevelBorder(1));
        textfield.setHorizontalAlignment(JTextField.CENTER);
        textfield.setEditable(false);
        textfield.setText("Match the following questions with a correct answer:");

        frame.add(textfield);

        for (int i = 0; i < total_questions; i++) {
            JLabel questionLabel = new JLabel((i + 19) + ". " + questions[i]);
            questionLabel.setBounds(10, 90 + (i * 80), 350, 50); // Adjusted size and position
            questionLabel.setForeground(new Color(21, 245, 186));
            questionLabel.setFont(new Font("MV Boli", Font.BOLD, 14)); // Adjusted font size

            comboBoxes[i] = new JComboBox<>(options);
            comboBoxes[i].setBounds(400, 90 + (i * 80), 160, 50); // Adjusted size and position
            comboBoxes[i].setFont(new Font("MV Boli", Font.BOLD, 12)); // Adjusted font size

            frame.add(questionLabel);
            frame.add(comboBoxes[i]);
        }

        nextButton.setBounds(180, 650, 200, 50); // Adjusted size and position
        nextButton.setFont(new Font("MV Boli", Font.BOLD, 16)); // Adjusted font size
        nextButton.addActionListener(this);
        frame.add(nextButton);

        seconds_left.setBounds(230, 590, 100, 50); // Adjusted size and position
        seconds_left.setBackground(new Color(240, 243, 255));
        seconds_left.setForeground(new Color(255, 0, 0));
        seconds_left.setFont(new Font("Ink Free", Font.BOLD, 24)); // Adjusted font size
        seconds_left.setBorder(BorderFactory.createBevelBorder(1));
        seconds_left.setOpaque(true);
        seconds_left.setHorizontalAlignment(JTextField.CENTER);
        seconds_left.setText(String.valueOf(seconds));
        frame.add(seconds_left);

        time_label.setBounds(230, 560, 100, 25); // Adjusted size and position
        time_label.setBackground(new Color(240, 243, 255));
        time_label.setForeground(new Color(255, 0, 0));
        time_label.setFont(new Font("MV Boli", Font.BOLD, 15)); // Adjusted font size
        time_label.setHorizontalAlignment(JTextField.CENTER);
        time_label.setText("Timer");
        frame.add(time_label);
        //frame.repaint();

        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextButton) {
            checkAnswers();
            Timer pause = new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showResults();
                }
            });
            pause.setRepeats(false);
            pause.start();
        }
    }

    public void checkAnswers() {
        correct_guesses = 0;
        for (int i = 0; i < total_questions; i++) {
            if (comboBoxes[i].getSelectedItem().toString().equals(answers[i])) {
                correct_guesses++;
                comboBoxes[i].setBorder(BorderFactory.createLineBorder(Color.GREEN, 2)); // Correct answer
            } else {
                comboBoxes[i].setBorder(BorderFactory.createLineBorder(Color.RED, 2)); // Incorrect answer
            }
        }

        // Record the time taken for each question
        long timeElapsed = 15000 - (seconds * 1000); // Calculate time taken in milliseconds
        timeTaken[index] = timeElapsed;

        timer.stop();
    }

    public void showResults() {
        frame.getContentPane().removeAll();
        frame.repaint();

        textfield.setText("RESULTS!");
        textfield.setBounds(0, 0, 600, 50); // Adjusted size
        frame.add(textfield);

        int totalCorrectGuesses = correctGuessesFromAll + correct_guesses;
        int totalQuestions = total_questions + 18; // Assuming there are 6 questions in TrueFalse quiz

        number_right.setBounds(200, 300, 200, 50); // Adjusted size and position
        number_right.setBackground(new Color(131, 111, 255));
        number_right.setForeground(new Color(21, 245, 186));
        number_right.setFont(new Font("Ink Free", Font.BOLD, 24)); // Adjusted font size
        number_right.setBorder(BorderFactory.createBevelBorder(1));
        number_right.setHorizontalAlignment(JTextField.CENTER);
        number_right.setEditable(false);
        number_right.setText("Correct: " + totalCorrectGuesses + "/" + totalQuestions);

        int percentage_score = (int) ((totalCorrectGuesses / (double) totalQuestions) * 100);
        percentage.setBounds(200, 360, 200, 50); // Adjusted size and position
        percentage.setBackground(new Color(131, 111, 255));
        percentage.setForeground(new Color(21, 245, 186));
        percentage.setFont(new Font("Ink Free", Font.BOLD, 24)); // Adjusted font size
        percentage.setBorder(BorderFactory.createBevelBorder(1));
        percentage.setHorizontalAlignment(JTextField.CENTER);
        percentage.setEditable(false);
        percentage.setText(percentage_score + "%");

        score_message.setBounds(150, 420, 300, 100);
        score_message.setBackground(new Color(131, 111, 255));
        score_message.setForeground(new Color(21, 245, 186));
        score_message.setFont(new Font("Ink Free", Font.BOLD, 18));
        score_message.setBorder(BorderFactory.createBevelBorder(1));


        // Calculate total time taken
        long totalTime = totalTimeTakenFromAll;
        for (long time : timeTaken) {
            totalTime += time;
        }

        // Convert total time taken to seconds and display
        double totalTimeInSeconds = totalTime / 1000.0;
        String totalTimeMessage = String.format("Time taken: %.1f seconds", totalTimeInSeconds);
        score_message.setText(totalTimeMessage + "\n\n"); 

        String message;
        if (percentage_score >= 80) {
            message = "Outstanding!";
        } else if (percentage_score >= 60) {
            message = "That's good!";
        } else if (percentage_score >= 40) {
            message = "Good try!";
        } else if (percentage_score >= 20) {
            message = "You can do better!";
        } else {
            message = "Don't give up!";
        }
        score_message.append(message);

        // Update leaderboard
        leaderboard.updateScore("Player", percentage_score, totalTimeInSeconds);

        // Display leaderboard button
        JButton leaderboardButton1 = new JButton("View Highest Score");
        leaderboardButton1.setBounds(175, 525, 250, 50);
        leaderboardButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayLeaderboardByScore(frame);
            }
        });
        
        JButton leaderboardButton2 = new JButton("View Fastest to Complete");
        leaderboardButton2.setBounds(175, 580, 250, 50);
        leaderboardButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayLeaderboardByCompletionTime(frame);
            }
        });

        // Display leaderboard button
        JButton MainMenuButton = new JButton("Back to Main Menu");
        MainMenuButton.setBounds(200, 635, 200, 50);
        MainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BacktoMainMenu();
            }
        });

        frame.add(leaderboardButton1);
        frame.add(leaderboardButton2);
        frame.add(MainMenuButton);

        frame.add(number_right);
        frame.add(percentage);        
        frame.add(score_message);
        frame.repaint();
    }

    // Method to display the leaderboard by highest score
    private void displayLeaderboardByScore(JFrame frame) {
        StringBuilder leaderboardText = new StringBuilder();
        List<Map.Entry<String, Integer>> leaderboardList = leaderboard.getLeaderboardByScore();
        for (int i = 0; i < leaderboardList.size(); i++) {
            leaderboardText.append((i + 1)).append(". ").append(leaderboardList.get(i).getKey()).append(": ").append(leaderboardList.get(i).getValue()).append("\n");
        }
        JOptionPane.showMessageDialog(frame, leaderboardText.toString(), "Highest Score", JOptionPane.PLAIN_MESSAGE);
    }

    private static void displayLeaderboardByCompletionTime(JFrame frame) {
        StringBuilder leaderboardText = new StringBuilder();
        List<Map.Entry<String, Double>> leaderboardList = leaderboard.getLeaderboardByCompletionTime();
        for (int i = 0; i < leaderboardList.size(); i++) {
            leaderboardText.append((i + 1)).append(". ").append(leaderboardList.get(i).getKey()).append(": ")
                          .append(leaderboardList.get(i).getValue()).append(" seconds, Score: ")
                          .append(leaderboard.getScore(leaderboardList.get(i).getKey())).append("\n");
        }
        JOptionPane.showMessageDialog(frame, leaderboardText.toString(), "Fastest to Complete", JOptionPane.PLAIN_MESSAGE);
    }

    public void BacktoMainMenu() {
        frame.dispose(); // Close the main menu frame
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LearningApp();
            }
        });
    }
}
