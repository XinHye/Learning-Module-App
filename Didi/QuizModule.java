import javax.swing.*;
import java.awt.*;

public class QuizModule implements QuizListener {

    private JFrame frame;
    private int currentQuiz = 0;
    private int totalCorrectGuesses = 0;
    private long totalTimeTaken = 0;

    public QuizModule() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 870);
        frame.getContentPane().setBackground(new Color(33, 25, 81));
        frame.setLayout(null);
        frame.setResizable(false);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        int x = (screenWidth - frameWidth) / 2;
        int y = (screenHeight - frameHeight) / 2;

        // Set frame location to center
        frame.setLocation(x, y);

        // Start the first quiz (TrueFalse)
        startNextQuiz();
        frame.setVisible(true);
    }

    @Override
    public void onQuizComplete(int correctGuesses, long totalTimeTaken) {
        this.totalCorrectGuesses += correctGuesses;
        this.totalTimeTaken += totalTimeTaken;

        currentQuiz++;
        startNextQuiz();
    }

    private void startNextQuiz() {
        frame.getContentPane().removeAll(); // Clear the frame content
        frame.revalidate();
        frame.repaint();

        if (currentQuiz == 0) {
            new TrueFalse(this, frame);
        } else if (currentQuiz == 1) {
            new MCQQuiz(this, frame);
        } else if (currentQuiz == 2) {
            new SAQuiz(this, frame);
        } else if (currentQuiz == 3) {
            new Matching(totalCorrectGuesses, totalTimeTaken, frame);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new QuizModule();
        });
    }
}
