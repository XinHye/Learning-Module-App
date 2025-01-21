import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class LearningApp {

    static JFrame frame = new JFrame("Main Menu");
    JButton learnButton, quizButton, leaderboardButton;
    JLabel infoLabel;
    JPanel backgroundPanel;

    public LearningApp() {
        frame.setSize(600, 870); // Adjusted to match smartphone size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        // Create a JPanel to set as content pane
        backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    // Load the background image
                    Image backgroundImage = ImageIO.read(new File("1.jpg"));
                    // Draw the image at the top-left corner of the panel
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        backgroundPanel.setLayout(null); // Using null layout for absolute positioning
        frame.setContentPane(backgroundPanel); // Set the custom panel as content pane

        // Main Menu label setup
        infoLabel = new JLabel("Main Menu");
        infoLabel.setBounds(0, 60, 600, 30); // Adjusted position and size
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setForeground(new Color(167, 194, 204)); // Adjusted text color
        // Increase font size
        Font labelFont = infoLabel.getFont();
        infoLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 30)); // Adjusted font size and style

        frame.add(infoLabel);

        infoLabel = new JLabel("Choose an option:");
        infoLabel.setBounds(0, 110, 600, 30); // Adjusted position and size
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setForeground(new Color(240, 243, 255)); // Adjusted text color
        frame.add(infoLabel);

        // Buttons setup
        learnButton = new JButton("Learn");
        learnButton.setBounds(175, 170, 250, 50); // Adjusted position and size
        Font buttonFont1 = new Font(learnButton.getFont().getName(), learnButton.getFont().getStyle(), 17);
        learnButton.setFont(buttonFont1);
        learnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLearningSection();
            }
        });
        frame.add(learnButton);

        quizButton = new JButton("Quiz");
        quizButton.setBounds(175, 230, 250, 50); // Adjusted position and size
        Font buttonFont2 = new Font(quizButton.getFont().getName(), quizButton.getFont().getStyle(), 17);
        quizButton.setFont(buttonFont2);
        quizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startQuiz();
            }
        });
        frame.add(quizButton);

        frame.setVisible(true);
    }

    public void openLearningSection() {
        //frame.dispose(); // Close the main menu frame
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LearningModule learningmodule = new LearningModule();
                learningmodule.setupPages();
            }
        });
    }

    public void startQuiz() {
        frame.dispose(); // Close the main menu frame
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new QuizModule();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LearningApp();
            }
        });
    }
}
