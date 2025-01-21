import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

class TextPage implements Page {
    private String title;
    private String subtitle;
    private String content;

    public TextPage(String title, String subtitle, List<String> filePaths) {
        this.title = title;
        this.subtitle = subtitle;
        this.content = loadContentFromFiles(filePaths);
    }

    
    private String loadContentFromFiles(List<String> filePaths) {
        StringBuilder contentBuilder = new StringBuilder();
        for (String filePath : filePaths) {
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    contentBuilder.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return contentBuilder.toString();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSubtitle() {
        return subtitle;
    }           
        
    @Override
    public void displayContent(JPanel contentPanel, Font contentFont) {
        
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setFont(contentFont);
        textPane.setText(content);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(textPane, BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(300, 640));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setBorder(null);

        contentPanel.add(panel);
    }
}
 