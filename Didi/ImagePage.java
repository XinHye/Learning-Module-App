import javax.swing.*;

import java.awt.*;


public class ImagePage implements Page {
    private String title;
    private String subtitle;
    private String content;
    private ImageIcon image;

    public ImagePage(String title, String subtitle, String content, ImageIcon image) {
        this.title = title;
        this.subtitle = subtitle;
        this.content = content;
        this.image = resizeImageIcon(image, 390, 416);
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
        
        if (content != null && !content.isEmpty()) {
            JTextPane textPane = new JTextPane();
            textPane.setEditable(false);
            textPane.setFont(contentFont);
            textPane.setText(content);
        
            // Create a JPanel to hold the textPane
            JPanel textPanel = new JPanel(new BorderLayout());
            textPanel.add(textPane, BorderLayout.CENTER);
            textPanel.setPreferredSize(new Dimension(550, 50));
            textPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            textPanel.setBorder(null);
        
            // Add the textPanel to the contentPanel
            contentPanel.add(textPanel);
        }
        
        // Display image
        JLabel imageLabel = new JLabel(image);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(imageLabel);
        contentPanel.add(Box.createVerticalStrut(100));

    }

    private ImageIcon resizeImageIcon(ImageIcon imageIcon, int maxWidth, int maxHeight) {
        Image image = imageIcon.getImage();
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        // Calculate the new dimensions while preserving the aspect ratio
        if (width > maxWidth) {
            height = (maxWidth * height) / width;
            width = maxWidth;
        }
        if (height > maxHeight) {
            width = (maxHeight * width) / height;
            height = maxHeight;
        }

        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
}