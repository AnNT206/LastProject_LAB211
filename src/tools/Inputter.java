package tools;

import java.util.Scanner;

public class Inputter {

    private Scanner ndl;

    //constructor
    public Inputter() {
        this.ndl = new Scanner(System.in);
    }
    
    //Input String
    public String getString(String mess){
        System.out.print(mess);
        return ndl.nextLine().trim();
    }
    
    //Input Integer
    public int getInt(String mess){
        int kq = 0;
        String temp = getString(mess);
        if(Validation.isValid(temp, Validation.INTEGER_VALID)){
            kq = Integer.parseInt(temp);
        }
        return kq;
    }
    
    public int getIntOptional(String mess){
        String temp = getString(mess).trim();
        if(temp.isEmpty()){
            return Integer.MIN_VALUE;
        }
        if(Validation.isValid(temp, Validation.INTEGER_VALID)){
            return Integer.parseInt(temp);
        }
        System.out.println("Invalid number!");
        return getIntOptional(mess);
    }
    
    //Input Double
    public double getDouble(String mess){
        double kq = 0.0;
        String temp = getString(mess);
        if(Validation.isValid(temp, Validation.DOUBLE_VALID)){
            kq = Double.parseDouble(temp);
        }
        return kq;
    }
    
    public double getDoubleOptional(String mess){
        String temp = getString(mess).trim();
        if(temp.isEmpty()){
            return Double.NEGATIVE_INFINITY;
        }
        if(Validation.isValid(temp, Validation.DOUBLE_VALID)){
            return Double.parseDouble(temp);
        }
        System.out.println("Invalid number!");
        return getDoubleOptional(mess);
    }
    
    //Check data and loop if data is invalid
    public String inputAndLoop(String mess, String pattern, boolean isLoop){
        String result;
        boolean isInvalid;
        
        do {            
            result = getString(mess).trim();
            if(!isLoop && result.isEmpty()){
                return result;
            }
            
            isInvalid = !Validation.isValid(result, pattern);
            
            if(isInvalid && isLoop){
                System.out.println("Data is invalid! Re-enter...");
            }
        } while (isLoop && isInvalid);
        return result;
    }
    
}
