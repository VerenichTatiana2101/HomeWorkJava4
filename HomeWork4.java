import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

//данную задачу проще решать извлекая число, сделать расчёты и поместить результат в очередь)
//в столбик, так в столбик))

public class HomeWork4 {
    public static void main(String[] args) {
        // Даны два Deque, представляющие два целых числа.
        // Цифры хранятся в обратном порядке и каждый из их узлов содержит одну цифру.
        int num1 = inputNum();
        int num2 = inputNum();
        Deque<Integer> dequeNum1 = (getDeque(num1));
        System.out.println(dequeNum1);
        Deque<Integer> dequeNum2 = (getDeque(num2));
        System.out.println(dequeNum2);

        // Одно или два числа должны быть отрицательными.
        // вызов методов сложения с учётом размера и минуса
        if ((num1 > 0 && num2 > 0) || (num1 < 0 && num2 < 0)) {
            Deque<Integer> result = sumNumbers(dequeNum1, dequeNum2);
            System.out.println(result);
        } else if (num1 < 0 && num2 > 0 && dequeNum2.size() > dequeNum1.size()) {
            Deque<Integer> result2 = sumnegativNumbers(dequeNum2, dequeNum1);
            System.out.println(result2);
        } else if (num2 < 0 && dequeNum2.size() > dequeNum1.size()) {
            Deque<Integer> result2 = sumnegativNumbers(dequeNum2, dequeNum1);
            System.out.println("-" + result2);
        } else if (num2 < 0 && dequeNum2.size() < dequeNum1.size()) {
            Deque<Integer> result3 = sumnegativNumbers(dequeNum1, dequeNum2);
            System.out.println(result3);
        } else if (num1 < 0 && dequeNum2.size() < dequeNum1.size()) {
            Deque<Integer> result3 = sumnegativNumbers(dequeNum1, dequeNum2);
            System.out.println("-" + result3);
        }

        // умножение с учётом ввода отрицательного числа
        if (num1 < 0 ^ num2 < 0) {
            Deque<Integer> result4 = multiply(dequeNum1, dequeNum2);
            System.out.println("-" + result4);
        } else {
            Deque<Integer> result4 = multiply(dequeNum1, dequeNum2);
            System.out.println(result4);
        }

    }

    // метод запрашивает число у пользователя
    static int inputNum() {
        Scanner sc = new Scanner(System.in);
        {
            System.out.println("Enter a number: ");
            while (!sc.hasNextInt()) {
                System.out.println("Error! Please enter a number: ");
                sc.next();
            }
            int number = sc.nextInt();
            return number;
        }
    }

    // метод принимает целое число, сохраняет в Deque в обратном порядке
    // и каждый из их узлов содержит одну цифру
    static Deque<Integer> getDeque(int num) {
        Deque<Integer> numDeque = new ArrayDeque<>();
        if (num < 0)
            num *= (-1);
        while (num > 0) {
            numDeque.addLast(num % 10);
            num /= 10;
        }
        return numDeque;
    }

    // Сложите два числа и верните сумму в виде связанного списка.
    static Deque<Integer> sumNumbers(Deque<Integer> deque1, Deque<Integer> deque2) {
        Deque<Integer> res = new ArrayDeque<>();
        int inMind = 0;
        while (!deque1.isEmpty() || !deque2.isEmpty()) {
            int sum = inMind;
            if (!deque1.isEmpty()) {
                sum += deque1.pollFirst();
            }
            if (!deque2.isEmpty()) {
                sum += deque2.pollFirst();
            }
            res.addFirst(sum % 10);
            inMind = sum / 10;
        }
        if (inMind != 0) {
            res.addFirst(inMind);
        }
        return res;
    }

    static Deque<Integer> sumnegativNumbers(Deque<Integer> deque1, Deque<Integer> deque2) {
        Deque<Integer> result = new ArrayDeque<>();
        int inMind = 0;
        while (!deque1.isEmpty() || !deque2.isEmpty()) {
            //Если очередь deque не пуста, то из нее удаляется первый элемент 
            //с помощью метода removeFirst() и сохраняется в переменной num. 
            //В противном случае переменной num присваивается значение ноль.
            int num1 = deque1.isEmpty() ? 0 : deque1.removeFirst();
            int num2 = deque2.isEmpty() ? 0 : deque2.removeFirst();
            int diff = num1 - num2 - inMind;
            if (diff < 0) {
                diff += 10;
                inMind = 1;
            } else {
                inMind = 0;
            }
            result.addFirst(diff);
        }
        if (inMind > 0) {
            result.addFirst(inMind);
        }
        return result;
    }

    // 1) Умножьте два числа и верните произведение в виде связанного списка.
    static Deque<Integer> multiply(Deque<Integer> deque1, Deque<Integer> deque2) {
        // Создаем массив для хранения результата умножения
        int[] res = new int[deque1.size() + deque2.size()];
        // Для каждого элемента первой очереди
        for (int i = deque1.size() - 1; i >= 0; i--) {
            // Для каждого элемента второй очереди
            for (int j = deque2.size() - 1; j >= 0; j--) {
                // Вычисляем позиции в массиве результата
                int pos1 = i + j;
                int pos2 = i + j + 1;
                // Вычисляем произведение двух элементов и добавляем его к сумме
                // на соответствующей позиции в массиве результата
                int sum = deque1.peek() * deque2.peek() + res[pos2];
                res[pos2] = sum % 10;
                res[pos1] += sum / 10;
                // Перемещаем первый элемент второй очереди в конец очереди
                deque2.add(deque2.remove());
            }
            // Удаляем первый элемент первой очереди
            deque1.remove();
        }
        // Создаем новую очередь для хранения результата
        Deque<Integer> result = new LinkedList<>();
        // Добавляем все элементы из массива результата в новую очередь, пропуская нули
        // в начале
        for (int num : res) {
            if (!(result.isEmpty() && num == 0)) {
                result.add(num);
            }
        }
        return result;
    }
}
