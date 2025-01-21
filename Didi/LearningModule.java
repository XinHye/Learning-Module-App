import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LearningModule extends JFrame {

    private Font titleFont;
    private Font subtitleFont;
    private Font contentFont;
    private JPanel contentPanel;
    private List<Page> pages;
    private int currentPageIndex;
    private JButton prevButton, nextButton, doneButton;
    private JScrollPane scrollPane;

    public LearningModule() {
        setSize(600, 832);
        setTitle("Introduction To Programming");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        titleFont = new Font("Times New Roman", Font.BOLD, 25);
        subtitleFont = new Font("Times New Roman", Font.BOLD, 20);
        contentFont = new Font("Times New Roman", Font.PLAIN, 15);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.decode("#F0F3FF"));
        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);

        JPanel navigationPanel = new JPanel();
        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");
        doneButton = new JButton("Done");
        doneButton.setVisible(false);
        navigationPanel.add(prevButton);
        navigationPanel.add(nextButton);
        navigationPanel.add(doneButton);
        add(navigationPanel, BorderLayout.SOUTH);

        prevButton.addActionListener(e -> showPreviousPage());
        nextButton.addActionListener(e -> showNextPage());
        doneButton.addActionListener(e -> showCompletionMessage());

        pages = new ArrayList<>();
        currentPageIndex = 0;

        setVisible(true);
    }

    public void addPage(Page page) {
        pages.add(page);
    }

    public void setupPages() {
        try {
            // PAGE1
            // Assignment Operator
            ImageIcon imageIcon1 = new ImageIcon(getClass().getResource("/picLearningModule/AssignmentOperator.PNG"));
            String textContent1 = "\nAssignment Operator = \n\n" +
                    "-> Assignment operators are used to assign value to a variable\n" +
                    "-> The left side operand of the assignment operator is a variable and right side operand of the assignment operator is a value\n" +
                    "-> The value on the right side must be of the same data-type of variable on the left side otherwise the compiler will raise an error\n" +
                    "-> = is not the same as == !!!\n\n";
            ImagePage newImagePage1 = new ImagePage("Assignment Operator", "Assignment Operator =", textContent1, imageIcon1);
            addPage(newImagePage1);

            // PAGE2
            // Basic Arithmetic Operations
            List<String> files1 = new ArrayList<>();
            files1.add(getClass().getResource("/textLM/BasicAO.txt").getPath());
            addPage(new TextPage("Arithmetic Operations", "Basic Arithmetic Operators", files1));

            // PAGE3
            // Unary
            List<String> files2 = new ArrayList<>();
            files2.add(getClass().getResource("/textLM/UnaryArithmeticOperators.txt").getPath());
            addPage(new TextPage("Basic Arithmetic Operators", "Unary Arithmetic Operators", files2));

            // PAGE4
            // Binary
            ImageIcon imageIcon2 = new ImageIcon(getClass().getResource("/picLearningModule/BinaryAo.PNG"));
            String textContent2 = "\nBinary Arithmetic Operators\n\n" +
                    "-> Require two operands\n" +
                    "-> E.g., +, -, *, / and %\n" +
                    "-> Operands can be an integer or floating-point\n";
            ImagePage newImagePage2 = new ImagePage("Basic Arithmetic Operators", "Binary Arithmetic Operators", textContent2, imageIcon2);
            addPage(newImagePage2);

            // PAGE5
            // Increment & decrement
            List<String> files3 = new ArrayList<>();
            files3.add(getClass().getResource("/textLM/Increment&DecrementOperators.txt").getPath());
            addPage(new TextPage("Increment & Decrement Operators", "Explanation & Examples", files3));

            // PAGE6
            // CAO
            ImageIcon imageIcon3 = new ImageIcon(getClass().getResource("/picLearningModule/CAO.PNG"));
            String textContent3 = "\nCompound of Assignment Operators\n" +
                    "-> C supports compound assignment operators, which are obtained by combining some operators with the simple assignment operator =";
            ImagePage newImagePage3 = new ImagePage("Compound Assignment Operators", "Examples:", textContent3, imageIcon3);
            addPage(newImagePage3);

            // PAGE7
            // MATHLIBRARYFUNC
            ImageIcon imageIcon4 = new ImageIcon(getClass().getResource("/picLearningModule/MathLibraryFunc.PNG"));
            String textContent4 = "\nMathematical Library Functions\n" +
                    "-> Mathematical library functions are declared in standard header files\n" +
                    "-> The floating-point type function declarations are found in math.h header file\n" +
                    "   #include<math.h>\n" +
                    "-> The integer type mathematical functions are found in stdlib.h header file\n" +
                    "   #include<stdlib.h>\n";
            ImagePage newImagePage4 = new ImagePage("Built-in Mathematical Functions in C", "#include<math.h>\n#include<stdlib.h>", textContent4, imageIcon4);
            addPage(newImagePage4);

            // PAGE8
            // RANDOMNUMGENERATE
            List<String> files4 = new ArrayList<>();
            files4.add(getClass().getResource("/textLM/RandomNumGenerate.txt").getPath());
            addPage(new TextPage("Random Number Generation", null, files4));

            // PAGE9
            // RollingDice
            ImageIcon imageIcon5 = new ImageIcon(getClass().getResource("/picLearningModule/RollingDice.PNG"));
            String textContent5 = "\n->> Rolling a Six-Sided Dice\n" +
                    "-> A dice-rolling program that stimulates a six-sided dice would require random generation of integers from 1 to 6\n" +
                    "-> Let's develop a program to stimulate 20 rolls of a six-sided die and print the value of each roll.\n" +
                    "-> We use the remainder operator (%) in conjunction with rand as follows\n" +
                    "   -> 1+rand() % 6\n" + "  -> to produce integers in the range 1 to 6\n";
            ImagePage newImagePage5 = new ImagePage("Random Number Generation", "How it works?", textContent5, imageIcon5);
            addPage(newImagePage5);

            // PAGE10
            // Solution
            ImageIcon imageIcon6 = new ImageIcon(getClass().getResource("/picLearningModule/Solution.PNG"));
            ImagePage newImagePage6 = new ImagePage("Solution:", "Seeding Random Number Generation", null, imageIcon6);
            addPage(newImagePage6);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading content", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Ensure the first page is displayed initially
        showPage(0);
    }

    private void showPage(int index) {
        if (index < 0 || index >= pages.size()) {
            return;
        }

        contentPanel.removeAll();
        Page page = pages.get(index);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(Color.decode("#15F5BA"));

        JLabel titleLabel = new JLabel(page.getTitle());
        titleLabel.setFont(titleFont);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        titlePanel.add(titleLabel);

        if (page.getSubtitle() != null && !page.getSubtitle().isEmpty()) {
            JLabel subtitleLabel = new JLabel(page.getSubtitle());
            subtitleLabel.setFont(subtitleFont);
            subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            titlePanel.add(subtitleLabel);
        }
        titlePanel.setMaximumSize(new Dimension(600, 200));
        contentPanel.add(titlePanel);

        page.displayContent(contentPanel, contentFont);

        prevButton.setEnabled(currentPageIndex > 0);
        nextButton.setEnabled(currentPageIndex < pages.size() - 1);
        doneButton.setVisible(currentPageIndex == pages.size() - 1);
        nextButton.setVisible(currentPageIndex != pages.size() - 1);

        scrollPane.revalidate();
        scrollPane.repaint();
    }

    private void showPreviousPage() {
        if (currentPageIndex > 0) {
            currentPageIndex--;
            showPage(currentPageIndex);
        }
    }

    private void showNextPage() {
        if (currentPageIndex < pages.size() - 1) {
            currentPageIndex++;
            showPage(currentPageIndex);
        }
    }

    private void showCompletionMessage() {
        int option = JOptionPane.showConfirmDialog(this, "Well Done! You have completed this topic!", "Message", JOptionPane.DEFAULT_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LearningModule learningModule = new LearningModule();
            learningModule.setupPages();
        });
    }
}
