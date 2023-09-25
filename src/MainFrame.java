import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayDeque;
import java.util.Queue;

public class MainFrame extends JFrame implements ActionListener {
    public static Queue<String> dirsQueue = new ArrayDeque<>();

    public static int Slash_count(String f) {
        int count = 0;
        for (int i = 0; i < f.length(); i++) {
            if (f.charAt(i) == '/') {
                count++;
            }
        }
        return count;
    }

    public static FileSearchThread createNewThread() {
        FileSearchThread currentThread = null;
        if (!dirsQueue.isEmpty() && Slash_count(dirsQueue.peek()) <= Maxsub_counter) {
            currentThread = new FileSearchThread(dirsQueue.remove());
            currentThread.start();
        }

        return currentThread;
    }

    public static void getData(FileSearchThread currentThread, int th) {
        FilesAndDirs data = currentThread.getFound();
        if (find_subdirectories.isSelected()) {
            for (String file : data.getDirs()) {
                for (String f : dirformat)
                    if (file.contains(f)) {
                        if (th == 1) {
                            listModel1.addElement(file);
                        } else {
                            listModel2.addElement(file);
                        }
                        break;
                    }
            }
        }
        if (find_files.isSelected()) {
            for (String file : data.getFiles()) {
                for (String f : fileformat)
                    if (file.contains(f)) {
                        if (th == 1) {
                            listModel1.addElement(file);
                        } else {
                            listModel2.addElement(file);
                        }
                        break;
                    }
            }
        }
        dirsQueue.addAll(data.getDirs());
    }

    public static JButton search_file_button;
    public static JButton choose_file_button;
    public static JList<String> files_list_th1;
    public static JLabel tr1;
    public static JLabel tr2;

    public static JList<String> files_list_th2;

    public static JTextField path_input;
    public static JTextField directories_type;
    public static JTextField files_type;

    public static JToggleButton find_files;
    public static JLabel findfiles;
    public static JToggleButton find_subdirectories;
    public static JLabel findsubdirectories;
    public static JToggleButton search_in_subdirectories;

    public static JLabel SearchInSubdirectories;

    public static JLabel dirform;

    public static JLabel fileform;

    public static JSpinner depth_counter;

    public static JLabel depth;
    public static JDialog Wait;

    public static DefaultListModel<String> listModel1;
    public static DefaultListModel<String> listModel2;

    public static String[] fileformat;
    public static String[] dirformat;
    public static int Maxsub_counter;


    MainFrame() {
        this.setTitle("FileScan");
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(300, 300);

        this.setResizable(false);
        this.setSize(405, 620);
        this.getContentPane().setBackground(new Color(205, 245, 250, 255));
        ImageIcon mainIcon = new ImageIcon("folder.png");
        setIconImage(mainIcon.getImage());


        Border roundedBorder = new LineBorder(Color.BLACK, 2, false);
        search_file_button = new JButton("Search");
        search_file_button.setBounds(250, 30, 90, 35);
        search_file_button.addActionListener(this);
        search_file_button.setFocusPainted(false);
        search_file_button.setBackground(new Color(225, 243, 246, 255));
        search_file_button.setForeground(Color.BLACK);
        search_file_button.setFont(new Font("Arial", Font.BOLD, 14));
        search_file_button.setBorder(new CompoundBorder(roundedBorder, new EmptyBorder(3, 3, 3, 3)));
        search_file_button.setHorizontalTextPosition(JButton.CENTER);
        search_file_button.setVerticalTextPosition(JButton.CENTER);


        choose_file_button = new JButton("...");
        choose_file_button.setBounds(345, 30, 30, 35);
        choose_file_button.addActionListener(this);
        choose_file_button.setFocusPainted(false);
        choose_file_button.setBackground(new Color(225, 243, 246, 255));
        choose_file_button.setForeground(Color.BLACK);
        choose_file_button.setFont(new Font("Arial", Font.BOLD, 14));
        choose_file_button.setBorder(new CompoundBorder(roundedBorder, new EmptyBorder(3, 3, 3, 3)));
        choose_file_button.setHorizontalTextPosition(JButton.CENTER);
        choose_file_button.setVerticalTextPosition(JButton.CENTER);

        listModel1 = new DefaultListModel<>();
        files_list_th1 = new JList<>(listModel1);
        files_list_th1.setBorder(new CompoundBorder(roundedBorder, new EmptyBorder(3, 3, 3, 3)));
        files_list_th1.setForeground(Color.BLACK);
        files_list_th1.setBackground(new Color(225, 243, 246, 255));
        JScrollPane files_list_scroll = new JScrollPane(files_list_th1);
        files_list_scroll.setBounds(15, 225, 360, 160);

        tr1 = new JLabel("Flow 1:");
        tr1.setBounds(175, 205, 90, 14);
        tr1.setFont(new Font("Arial", Font.BOLD, 14));

        listModel2 = new DefaultListModel<>();
        files_list_th2 = new JList<>(listModel2);
        files_list_th2.setBorder(new CompoundBorder(roundedBorder, new EmptyBorder(3, 3, 3, 3)));
        files_list_th2.setForeground(Color.BLACK);
        files_list_th2.setBackground(new Color(225, 243, 246, 255));
        JScrollPane files_list_scroll2 = new JScrollPane(files_list_th2);
        files_list_scroll2.setBounds(15, 410, 360, 160);

        tr2 = new JLabel("Flow 2:");
        tr2.setBounds(175, 390, 90, 14);
        tr2.setFont(new Font("Arial", Font.BOLD, 14));

        dirform = new JLabel("Write the name of directories:");
        dirform.setBounds(17,80,300,13);
        dirform.setFont(new Font("Arial", Font.BOLD, 12));

        directories_type = new JTextField();
        directories_type.setBounds(15, 95, 225, 35);
        directories_type.setBorder(new CompoundBorder(roundedBorder, new EmptyBorder(3, 3, 3, 3)));
        directories_type.setFont(new Font("Arial", Font.BOLD, 12));
        directories_type.setText("");
        directories_type.setBackground(new Color(225, 243, 246, 255));

        path_input = new JTextField();
        path_input.setBounds(15, 30, 225, 35);
        path_input.setEnabled(false);
        path_input.setDisabledTextColor(Color.BLACK);
        path_input.setBorder(new CompoundBorder(roundedBorder, new EmptyBorder(3, 3, 3, 3)));
        path_input.setFont(new Font("Arial", Font.BOLD, 12));
        path_input.setText("Your directory");
        path_input.setBackground(new Color(225, 243, 246, 255));

        files_type = new JTextField();
        files_type.setBounds(15, 155, 225, 35);
        files_type.setBorder(new CompoundBorder(roundedBorder, new EmptyBorder(3, 3, 3, 3)));
        files_type.setFont(new Font("Arial", Font.BOLD, 12));
        files_type.setText("");
        files_type.setBackground(new Color(225, 243, 246, 255));

        fileform = new JLabel("Write format of files (.txt|.bin|...):");
        fileform.setBounds(17,140,300,13);
        fileform.setFont(new Font("Arial", Font.BOLD, 12));

        find_files = new JToggleButton();
        find_files.setBounds(250, 95, 14, 14);
        find_files.setBorder(new CompoundBorder(roundedBorder, new EmptyBorder(3, 3, 3, 3)));
        find_files.setFocusPainted(false);
        find_files.setBackground(new Color(225, 243, 246, 255));

        findfiles = new JLabel("Find files");
        findfiles.setBounds(270, 95, 190, 14);
        findfiles.setFont(new Font("Arial", Font.BOLD, 14));

        find_subdirectories = new JToggleButton();
        find_subdirectories.setBounds(250, 115, 14, 14);
        find_subdirectories.setBorder(new CompoundBorder(roundedBorder, new EmptyBorder(3, 3, 3, 3)));
        find_subdirectories.setFocusPainted(false);
        find_subdirectories.setBackground(new Color(225, 243, 246, 255));

        findsubdirectories = new JLabel("Find subd-ies");
        findsubdirectories.setBounds(270, 115, 190, 14);
        findsubdirectories.setFont(new Font("Arial", Font.BOLD, 14));

        search_in_subdirectories = new JToggleButton();
        search_in_subdirectories.setBounds(250, 135, 14, 14);
        search_in_subdirectories.addActionListener(this);
        search_in_subdirectories.setBorder(new CompoundBorder(roundedBorder, new EmptyBorder(3, 3, 3, 3)));
        search_in_subdirectories.setFocusPainted(false);
        search_in_subdirectories.setBackground(Color.WHITE);
        search_in_subdirectories.setBackground(new Color(225, 243, 246, 255));

        SearchInSubdirectories = new JLabel("Use subd-ies");
        SearchInSubdirectories.setBounds(270, 135, 190, 14);
        SearchInSubdirectories.setFont(new Font("Arial", Font.BOLD, 14));


        SpinnerModel deapth_count = new SpinnerNumberModel(1, 1, 20, 1);
        depth_counter = new JSpinner(deapth_count);
        depth_counter.setEnabled(false);
        depth_counter.setBounds(250, 155, 44, 35);
        depth_counter.setFont(new Font("Arial", Font.BOLD, 10));
        depth_counter.setBorder(new CompoundBorder(roundedBorder, new EmptyBorder(3, 3, 3, 3)));
        depth_counter.setBackground(new Color(205, 245, 250, 255));

        depth = new JLabel("Depth");
        depth.setBounds(300, 166, 90, 14);
        depth.setFont(new Font("Arial", Font.BOLD, 14));

//        Wait = new JDialog(this, "Please, wait",true);
//        Wait.getContentPane().setBackground(new Color(253, 253, 253, 255));
//        Wait.setEnabled(false);
//        JLabel waiting = new JLabel("Threads are working");
//        waiting.setFont(new Font("Arial", Font.BOLD, 10));
//        waiting.setHorizontalAlignment(SwingConstants.CENTER);
//        waiting.setVerticalAlignment(SwingConstants.CENTER);
//        Wait.setSize(150, 150);
//        Wait.setResizable(false);
//        Wait.setLocation(this.getX() + 135, this.getY() + 190);
//        ImageIcon waiticon = new ImageIcon("free-icon-settings-6904098.png");
//        Wait.setIconImage(waiticon.getImage());
//        Wait.add(waiting);


        this.add(search_file_button);
        this.add(choose_file_button);
        this.add(files_list_scroll);
        this.add(path_input);
        this.add(directories_type);
        this.add(files_type);
        this.add(find_subdirectories);
        this.add(search_in_subdirectories);
        this.add(depth_counter);
        this.add(findsubdirectories);
        this.add(SearchInSubdirectories);
        this.add(depth);
        this.add(tr1);
        this.add(tr2);
        this.add(files_list_scroll2);
        this.add(find_files);
        this.add(findfiles);
        this.add(dirform);
        this.add(fileform);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == choose_file_button) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int returnValue = fileChooser.showDialog(null, "Choose directory");
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedDirectory = fileChooser.getSelectedFile();
                path_input.setText(selectedDirectory.getAbsolutePath());
            }

        }
        if (e.getSource() == search_file_button) {
            fileformat = files_type.getText().split("\\s*\\|\\s*");
            dirformat = directories_type.getText().split("\\s*\\|\\s*");
            if (!find_files.isSelected() && !find_subdirectories.isSelected()) {
                return;
            }
            listModel1.clear();
            listModel2.clear();
            FileSearchThread firstThread = null;
            FileSearchThread secondThread = null;
            dirsQueue.add(path_input.getText());
            Maxsub_counter = Slash_count(path_input.getText());
            if (depth_counter.isEnabled()) {
                Maxsub_counter += (int) depth_counter.getValue();
            }
            this.setEnabled(false);
            while ((!dirsQueue.isEmpty() && Slash_count(dirsQueue.peek()) <= Maxsub_counter) || firstThread != null || secondThread != null) {
                if (firstThread == null) {
                    firstThread = createNewThread();
                } else if (!firstThread.isAlive()) {
                    getData(firstThread, 1);
                    firstThread = createNewThread();
                }

                if (secondThread == null) {
                    secondThread = createNewThread();
                } else if (!secondThread.isAlive()) {
                    getData(secondThread, 2);
                    secondThread = createNewThread();
                }
            }
            this.setEnabled(true);
            dirsQueue.clear();
        }
        if (e.getSource() == search_in_subdirectories) {
            depth_counter.setEnabled(search_in_subdirectories.isSelected());
        }
    }
}

