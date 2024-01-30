package g;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.File;

public class ImageProcessorFrame extends JFrame implements ActionListener, ChangeListener {
    private ImagePanel imagepanel;
    private Image image;
    private JFileChooser fileChooser;
    private File selectedFile;
    private JMenuItem openItem, saveItem, blackAndWhiteFilter, redBlueSwapFilter, RotateClockWiseFilter,
            greenBlueSwapFilter, brightnessFilter, cropFilter, blurFilter;
    private JSlider brightnessSlider;
    private JPanel sliderPanel;

    public ImageProcessorFrame() {
        super("Image Processor");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar menu = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu filters = new JMenu("Filters");

        openItem = new JMenuItem("Open Image");
        saveItem = new JMenuItem("Save Image");
        saveItem.setEnabled(false);

        blackAndWhiteFilter = new JMenuItem("Black and White");
        blackAndWhiteFilter.setToolTipText("Apply black and white filter to the image");
        blackAndWhiteFilter.setEnabled(false);

        redBlueSwapFilter = new JMenuItem("Red and Blue Swap");
        redBlueSwapFilter.setToolTipText("Apply red blue swap filter to the image");
        redBlueSwapFilter.setEnabled(false);

        RotateClockWiseFilter = new JMenuItem("Rotate Clockwise");
        RotateClockWiseFilter.setToolTipText("Apply rotate clockwise filter to the image");
        RotateClockWiseFilter.setEnabled(false);

        greenBlueSwapFilter = new JMenuItem("Green and Blue Swap");
        greenBlueSwapFilter.setToolTipText("Apply green blue swap filter to the image");
        greenBlueSwapFilter.setEnabled(false);

        brightnessFilter = new JMenuItem("Brightness");
        brightnessFilter.setToolTipText("Change the Brightness of the Image");
        brightnessFilter.setEnabled(false);

        blurFilter = new JMenuItem("Blur the Image");
        blurFilter.setToolTipText("Blurs image by replacing the color of the pixel with the average of the pixel colors around it");
        blurFilter.setEnabled(false);

        cropFilter = new JMenuItem("Crop");
        cropFilter.setToolTipText("Crop a Selected Region of the Image");
        cropFilter.setEnabled(false);

        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        blackAndWhiteFilter.addActionListener(this);
        redBlueSwapFilter.addActionListener(this);
        RotateClockWiseFilter.addActionListener(this);
        greenBlueSwapFilter.addActionListener(this);
        brightnessFilter.addActionListener(this);
        cropFilter.addActionListener(this);
        blurFilter.addActionListener(this);

        fileMenu.add(openItem);
        fileMenu.add(saveItem);

        filters.add(blackAndWhiteFilter);
        filters.add(redBlueSwapFilter);
        filters.add(RotateClockWiseFilter);
        filters.add(greenBlueSwapFilter);
        filters.add(brightnessFilter);
        filters.add(blurFilter);
        filters.add(cropFilter);

        menu.add(fileMenu);
        menu.add(filters);
        setJMenuBar(menu);

        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg", "png", "gif"));

        brightnessSlider = new JSlider(SwingConstants.HORIZONTAL, -200, 200, 0);
        brightnessSlider.setPaintTicks(true);
        brightnessSlider.setMinorTickSpacing(5);
        brightnessSlider.setMajorTickSpacing(50);
        brightnessSlider.addChangeListener(this);
        brightnessSlider.setVisible(false);

        setVisible(true);
        setSize(1280, 720);
        
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == openItem) {
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                this.image = new Image(selectedFile.getPath());
                this.imagepanel = new ImagePanel(this.image);

                setContentPane(this.imagepanel);
                revalidate();

                blackAndWhiteFilter.setEnabled(true);
                redBlueSwapFilter.setEnabled(true);
                RotateClockWiseFilter.setEnabled(true);
                greenBlueSwapFilter.setEnabled(true);
                saveItem.setEnabled(true);
                brightnessFilter.setEnabled(true);
                cropFilter.setEnabled(true);
                blurFilter.setEnabled(true);
            }
        }

        else if (source == saveItem) {
            JFileChooser saveFileChooser = new JFileChooser();
            saveFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = saveFileChooser.showSaveDialog(null);
            if (result == JFileChooser.CANCEL_OPTION) {
                JOptionPane.showMessageDialog(null, "Save Cancelled");
                return;
            }

            if (result == JFileChooser.APPROVE_OPTION) {
                File outputFile = new File(saveFileChooser.getSelectedFile(), "output.jpg");
                try {
                    image.writeImage(outputFile.getPath());
                    JOptionPane.showMessageDialog(null, "Image saved to  " + outputFile.getAbsolutePath());

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
                }
            }

        } else if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "You must open an image before applying afilter.", "Error",
                    JOptionPane.ERROR_MESSAGE);

        } else if (source == blackAndWhiteFilter) {
            this.image.blackAndWhiteFilter();
            this.imagepanel = new ImagePanel(image);
            setContentPane(this.imagepanel);
            revalidate();

        } else if (source == cropFilter){
            this.image.cropFilter(ImagePanel.x1, ImagePanel.y1, ImagePanel.x2, ImagePanel.y2);
            this.imagepanel = new ImagePanel(this.image);
            setContentPane(this.imagepanel);
            revalidate();

        } else if (source == blurFilter){
            this.image.blurFilter();
            this.imagepanel = new ImagePanel(this.image);
            setContentPane(this.imagepanel);
            revalidate();

        } else if (source == redBlueSwapFilter) {
            this.image.redBlueSwapFilter();
            this.imagepanel = new ImagePanel(image);
            setContentPane(this.imagepanel);
            revalidate();

        } else if (source == RotateClockWiseFilter) {

            this.image.rotateClockwiseFilter();
            this.imagepanel = new ImagePanel(image);
            setContentPane(this.imagepanel);
            revalidate();

        } else if (source == greenBlueSwapFilter) {
            this.image.greenBlueSwapFilter();
            this.imagepanel = new ImagePanel(this.image);
            setContentPane(this.imagepanel);
            revalidate();

        } else if (source == brightnessFilter) {
            brightnessSlider.setVisible(true);
            sliderPanel = new JPanel(new GridLayout(2, 1));
            sliderPanel.add(brightnessSlider);
            sliderPanel.add(imagepanel);
            setContentPane(sliderPanel);
            revalidate();
        } else if (source != brightnessFilter) {
            setContentPane(imagepanel);
            revalidate();
            brightnessSlider.setVisible(false);
        } 
    }

    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            image.brightnessFilter(source.getValue());
            this.imagepanel = new ImagePanel(image);
            sliderPanel = new JPanel(new GridLayout(2, 1));
            sliderPanel.add(brightnessSlider);
            sliderPanel.add(imagepanel);
            setContentPane(sliderPanel);
            revalidate();
        }
    }
}
