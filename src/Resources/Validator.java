package Resources;

import javafx.scene.control.TextField;

import javax.swing.*;

public class Validator {

    //method to check if the value exists in the textfield
    public static boolean IsProvided(TextField textField, String msg) {
        boolean result = true;
        if (textField.getText() == null || textField.equals("")){
            result = false;
            JOptionPane.showMessageDialog(null, msg + "can't be empty", "Empty Fields Error",JOptionPane.ERROR_MESSAGE);
            textField.requestFocus();
        } else { result = true; }

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
}
