
import java.util.Scanner;                               

public class Calculator{
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        Main result = new Main();
        System.out.println("Input:");                   
        String expression = input.nextLine();           
        String answer = result.calc(expression);        

        System.out.println("Output:\n" + answer);        
    }
}

class Main {
    public static String calc(String input){
        boolean flag_roman = false;                      

        int result = 0;                                  
        String[] operators = {"+", "-", "/", "*"};
        String[] operators_for_split = {"\\+", "-", "/", "\\*"};


        String[] inputSplit = input.split("\\W");
        if (inputSplit.length > 2 ){
            throw new IllegalArgumentException ("т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");                             // Ловим, если не 3 элемента
        }
        int operator_index = -1;
        for (int i = 0; i < operators.length; i++) {
            if (input.contains(operators[i])) {
                operator_index = i;
            }
        }
        if (operator_index == -1) {
            throw new IllegalArgumentException ("т.к. строка не является математической операцией");
        }

        String a, b;
        a = input.split(operators_for_split[operator_index])[0];
        b = input.split(operators_for_split[operator_index])[1];

        int number_1, number_2;

        try {
            number_1 = Integer.parseInt(a);
            number_2 = Integer.parseInt(b);
        }  catch (IllegalArgumentException e) {
            if (Roman_to_arabian.getArabicValue(a) == -1 || Roman_to_arabian.getArabicValue(b) == -1) {
                throw new IllegalArgumentException ("т.к. используются одновременно разные системы счисления");
            }

            number_1 = Roman_to_arabian.getArabicValue(a);
            number_2 = Roman_to_arabian.getArabicValue(b);

            flag_roman = true;
        }

        if ((number_1 < 1) || (number_1 > 10) || (number_2 < 1) || (number_2 > 10)){
            throw new IllegalArgumentException ("т.к. введенные числа находятся не в диапазоне 1-10");                                     
        }

        //String sign = inputSplit[2];
        switch (operators[operator_index]) {
            case "+" -> result = number_1 + number_2;
            case "-" -> result = number_1 - number_2;
            case "*" -> result = number_1 * number_2;
            case "/" -> result = number_1 / number_2;
            default -> {
                throw new IllegalArgumentException ("т.к. строка не является математической операцией"); 
            }
        }
        String output;                                              
        if (flag_roman){
            if(result < 1){
                throw new IllegalArgumentException ("т.к. в римской системе нет отрицательных чисел");
            } else {
                output = Arabian_to_roman.toRoman(result);
            }
        } else {
            output = Integer.toString(result);
        }
        return output;
    }
}
/////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////
enum Roman_to_arabian
{
    I(1), II(2), III(3), IV(4), V(5), VI(6), VII(7), VIII(8), IX(9), X(10), XI(11), XII(12), XIII(13), XVI(14), XV(15);

    private int value;

    private Roman_to_arabian(int value) {
        this.value = value;
    }
    public int getValue()
    {
        return value;
    }
    public static int getArabicValue(String roman)
    {
        for (Roman_to_arabian numeral : values())
        {
            if (numeral.name().equals(roman)) {
                return numeral.getValue();
            }
        }
        return -1;
    }
    // return result;
}

///////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////

enum Arabian_to_roman
{
    I(1), IV(4), V(5), IX(9), X(10), XL(40), L(50), XC(90), C(100), CD(400), D(500), CM(900), M(1000);

    private final int value;

    private Arabian_to_roman(int value) {
        this.value = value;
    }
    private int getValue() {
        return value;
    }

    public static String toRoman(int number)
    {
        if (number <= 0 || number > 100) {
            throw new IllegalArgumentException("т.к. в римской системе нет отрицательных чисел");
        }

        StringBuilder result = new StringBuilder();
        Arabian_to_roman[] values = Arabian_to_roman.values();

        for (int i = values.length - 1; i >= 0; i--)
        {
            while (number >= values[i].value) {
                result.append(values[i]);
                number -= values[i].value;
            }
        }
        return result.toString();
    }
}
