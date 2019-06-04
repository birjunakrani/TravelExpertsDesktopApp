package Resources;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import javax.swing.*;

public class Validator {

    //method to check if the value exists in the textfield
    public static boolean IsProvided(TextField textField, String msg) {
        boolean result = true;
        if (textField.getText() == null || textField.getLength()==0){
       // if (textField.getText() == null || textField.equals("")){
            result = false;
            JOptionPane.showMessageDialog(null, msg + "can not be empty", "Empty Fields Error",JOptionPane.ERROR_MESSAGE);
            textField.requestFocus();
        }
        return result;
    }

    //method to check if the value entered in the textfiled is Integer
    public static boolean IsInt(TextField textField, String msg) {
        boolean result = true;
        try{
            int i = Integer.parseInt(textField.getText());
            result = true;

        }catch (NumberFormatException | NullPointerException ex){
            result = false;
            JOptionPane.showMessageDialog(null, msg + "must be whole number", "Empty Fields Error",JOptionPane.ERROR_MESSAGE);
            textField.requestFocus();
        }
        return result;
    }

    //method to check if the value entered in the textfiled is integer & positive
    public static boolean IsNonNegative(TextField textField, String msg) {
        boolean result = true;
        try{
            int i = Integer.parseInt(textField.getText());
            if(i<0)
            {
                result = false;
            } else result = true;
        }catch (NumberFormatException | NullPointerException ex){
            result = false;
            JOptionPane.showMessageDialog(null, msg + "must be positive whole number", "Empty Fields Error",JOptionPane.ERROR_MESSAGE);
            textField.requestFocus();
        }
        return result;
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////
   /* Author: Mahda Kazemian
    Date: May 28,2019
    Course Module : PROJ-207-OSD - Threaded Project
    Assignment : Team1- Workshop6
    purpose: validation for textFields and comboBox*/

////method to check if the value entered in the textField is specific letter
public static boolean IsLetter(TextField textField, String msg) {
    boolean result = true;

    if (!(textField.getText().matches("B") || textField.getText().matches("L") ||textField.getText().matches("G"))){
        result = false;
        JOptionPane.showMessageDialog(null, msg + "must be B ,G or L", "Type Error", JOptionPane.ERROR_MESSAGE);
        textField.requestFocus();
    }

    return result;
}
////method to check the comboBox is not null


    public static boolean IsSelected(ComboBox comboBox, String msg) {
        boolean result = true;

        if (comboBox.getValue()== null){
            result = false;
            JOptionPane.showMessageDialog(null, msg + "can not be empty", "Empty Error", JOptionPane.ERROR_MESSAGE);
            comboBox.requestFocus();
        }

        return result;
    }

///method to check the DatePicker is not null

    public static boolean DatePicked(DatePicker datePicker, String msg) {
        boolean result = true;

        if (datePicker.getValue()== null){
            result = false;
            JOptionPane.showMessageDialog(null, msg + "can not be empty", "Empty Error", JOptionPane.ERROR_MESSAGE);
            datePicker.requestFocus();
        }

        return result;
    }


    //method to check the packageId is specific number
    public static boolean SpecificId(TextField textField, String msg) {
        boolean result = true;

        if (!(textField.getText().matches("1") || textField.getText().matches("2") ||textField.getText().matches("3")
                ||textField.getText().matches("4") ||textField.getText().matches("5"))){
            result = false;
            JOptionPane.showMessageDialog(null, msg + "must be a number between 1 to 5", "Entry Error", JOptionPane.ERROR_MESSAGE);
            textField.requestFocus();
        }

        return result;
    }
}
