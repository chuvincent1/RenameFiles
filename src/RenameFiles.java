/*
Vincent Chu
Rename Files Program
Personal Project
*/

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class RenameFiles extends JFrame
{
    private JPanel displayPanel;
    private JLabel label;
    private JLabel label2;
    
    private JMenuBar menuBar;
    private JMenu optionsMenu;
    private JMenuItem resetItem;
    private JMenuItem exitItem;
    
    private JPanel buttonPanel;
    private JButton renameButton;
    
    private JPanel pathPanel;
    private JLabel pathLabel;
    private JTextField pathField;
    
    private JPanel namePanel;
    private JLabel newNameLabel;
    private JTextField nameField;
    
    public RenameFiles()
    {
        setTitle("Rename Files Program");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(450,225));
        
        buildDisplayPanel();
        add(displayPanel);
        
        pack();
        setVisible(true);
    }
    
    private void buildMenuBar()
    {
        menuBar = new JMenuBar();
        
        buildOptionsMenu();
        
        menuBar.add(optionsMenu);
        setJMenuBar(menuBar);
    }

    
    private void buildDisplayPanel()
    {
        displayPanel = new JPanel();
        displayPanel.setLayout(new FlowLayout());
        String labelText = "Note: Program will prompt for file path and rename";
        String labelText2 = "all files in said folder based on original file names.";
        label = new JLabel(labelText);
        label2 = new JLabel(labelText2);
        
        buildMenuBar();
        buildPathPanel();
        buildNamePanel();
        buildButtonPanel();
        
        displayPanel.add(label);
        displayPanel.add(label2);
        displayPanel.add(pathPanel);
        displayPanel.add(namePanel);
        displayPanel.add(buttonPanel);
    }
    
    private void buildOptionsMenu()
    {
        optionsMenu = new JMenu("Options");
        optionsMenu.setMnemonic(KeyEvent.VK_O);
        
        resetItem = new JMenuItem("Reset All");
        resetItem.setMnemonic(KeyEvent.VK_R);
        resetItem.addActionListener(new ResetListener());
        
        exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic(KeyEvent.VK_E);
        exitItem.addActionListener(new ExitListener());
        
        optionsMenu.add(resetItem);
        optionsMenu.add(exitItem);
    }
    
    private void buildPathPanel()
    {
        pathLabel = new JLabel("Enter File Path: ");
        pathField = new JTextField(25);
        
        pathPanel = new JPanel();
        pathPanel.add(pathLabel);
        pathPanel.add(pathField);
    }
    
    private void buildNamePanel()
    {
        newNameLabel = new JLabel("Enter New File Names: ");
        nameField = new JTextField(21);
        
        namePanel = new JPanel();
        namePanel.add(newNameLabel);
        namePanel.add(nameField);
    }
    
    private void buildButtonPanel()
    {
        buttonPanel = new JPanel();
        renameButton = new JButton("Alphabetical Rename");
        renameButton.addActionListener(new ButtonListener());
        
        buttonPanel.add(renameButton);
    }
    
    private void buildRenameFiles()
    {        
        String path = pathField.getText();  
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        Arrays.sort(listOfFiles); //Sorts based on ASCII which causes issues
        
        String name = nameField.getText();
        
        for (int i = 0; i < listOfFiles.length; i++)
        {
            String finalName = "";
            String num = "";
            int fileNum = i+1;
            if (listOfFiles[i].isFile())
            {
                if (fileNum < 10)
                {
                    num =  "000" + String.valueOf(i+1);
                    finalName = name + "_" + num;
                }
                
                else if (fileNum >= 10 && fileNum < 100)
                {
                    num = "00" + String.valueOf(i+1);
                    finalName = name + "_" + num;
                }
                else if (fileNum >= 100 && fileNum < 1000)
                {
                    num = "0" + String.valueOf(i+1);
                    finalName = name + "_" + num;
                }
                else if (fileNum >= 1000 && fileNum < 10000 )
                {
                    num = String.valueOf(i+1);
                    finalName = name + "_" + num;  
                }
                //String finalName = name + "_" + String.valueOf(i+1);
                
                String[] split;
                split = listOfFiles[i].getName().split("\\.");
                String fileType = "." + split[1];
                
                File f = new File(path + "\\" + listOfFiles[i].getName());
                f.renameTo(new File(path + "\\" + finalName + fileType));
            }
        }
    }
    
    private class ExitListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String message = "Are you sure you want to quit?";
            if (JOptionPane.showConfirmDialog(new JFrame(), message, "Dialog",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                System.exit(0);
            }
            else
            {
            }
        }
    }
    
     private class ResetListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            pathField.setText(null);
            nameField.setText(null);
        }
    }
        
    private class ButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                buildRenameFiles();
                
                String message = "Success! Files renamed.";
                JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            catch (Exception ex)
            {
                String message = "Error! Issue with path given. Please clear entries and try again.";
                JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public static void main(String[] args)
    {
        new RenameFiles();
    }
}
