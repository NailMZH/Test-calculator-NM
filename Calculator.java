import java.util.Scanner;


public class Calculator
{
    public static void main (String[]args)
    {
        while(true)
        {
            String[] operators = {"+", "-", "/", "*"};
            String[] operators_for_split = {"\\+", "-", "/", "\\*"};

            Scanner scn = new Scanner(System.in);
            System.out.print("\nВведите выражение: ");
            String line = scn.nextLine();

            if (line.split("\\W").length > 2) {
                throw new IllegalArgumentException ("т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
            }

            int operator_index = -1;

            for (int i = 0; i < operators.length; i++) {
                if (line.contains(operators[i])) {

                    operator_index = i;
                }
            }

            if (operator_index == -1) {
                throw new IllegalArgumentException("т.к. строка не является математической операцией");
            }

            String a, b;

            a = line.split(operators_for_split[operator_index])[0];
            b = line.split(operators_for_split[operator_index])[1];


            int number_1, number_2;
            boolean flag_roman = false;

            try {
                number_1 = Integer.parseInt(a);
                number_2 = Integer.parseInt(b);

            } catch (IllegalArgumentException e) {
                if (Roman_to_arabian1.getArabicValue(a) == -1 || Roman_to_arabian1.getArabicValue(b) == -1) {
                    throw new IllegalArgumentException("т.к. используются одновременно разные системы счисления");
                }

                number_1 = Roman_to_arabian1.getArabicValue(a);
                number_2 = Roman_to_arabian1.getArabicValue(b);

                flag_roman = true;
            }

            if (!((0 <= number_1 && number_1 <= 10) && (0 <= number_2 && number_2 <= 10)))
            {
                try {
                    throw new IOException();
                } catch (IOException e)
                {
                    System.out.println ("т.к. введенные числа находятся не в диапазоне 1-10");
                    break;
                }

            }

            int result;

            switch (operators[operator_index]) {
                case "+":
                    result = number_1 + number_2;
                    break;
                case "-":
                    result = number_1 - number_2;
                    break;
                case "*":
                    result = number_1 * number_2;
                    break;
                default:
                    result = number_1 / number_2;
                    break;
            }

            if (flag_roman)
            {
                System.out.println("Результат: " + Arabian_to_roman1.toRoman(result));
            } else
            {
                System.out.println("Результат: " + result);
            }
        }
    }
}

/////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////
enum Roman_to_arabian
{
    I(1), II(2), III(3), IV(4), V(5), VI(6), VII(7), VIII(8), IX(9), X(10), XI(11), XII(12), XIII(13), XVI(14), XV(15);

    private int value;

    Roman_to_arabian(int value)
    {
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
            if (numeral.name().equals(roman))
            {
                return numeral.getValue();
            }
        }
        return -1;
    }
}

///////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////

enum Arabian_to_roman
{
    I(1), IV(4), V(5), IX(9), X(10), XL(40), L(50), XC(90), C(100), CD(400), D(500), CM(900), M(1000);

    private final int value;

    Arabian_to_roman(int value)
    {
        this.value = value;
    }

    int getValue()
    {
        return value;
    }

    static String toRoman(int number)
    {
        if (number <= 0 || number > 3999)
        {
            throw new IllegalArgumentException("т.к. в римской системе нет отрицательных чисел");
        }

        StringBuilder result = new StringBuilder();
        Arabian_to_roman[] values = Arabian_to_roman.values();

        for (int i = values.length - 1; i >= 0; i--)
        {
            while (number >= values[i].value)
            {
                result.append(values[i]);
                number -= values[i].value;
            }
        }
        return result.toString();
    }
}


