import javax.swing.*;
import java.awt.*;


interface Page {
    String getTitle();
    String getSubtitle();
    void displayContent(JPanel contentPanel, Font contentFont);
}
