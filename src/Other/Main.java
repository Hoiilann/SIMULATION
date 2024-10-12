package Other;

import java.util.Scanner;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws ClassCastException{
        System.out.println("Добро пожаловать в симуляцию! В данной симуляции находятся овца и волк.");
        System.out.println("Волк выживает, если съедает овцу. Овца же выживает, " +
                "если съедает всю траву на поле или волк умирает от голода.");
        System.out.println("Также симуляция заканчивается, если у волка или овцы нет доступных ходов.");
        System.out.println("Чтобы начать симуляцию, введите сначала длину поля, enter, его ширину");
        int rightNumber = 0;
        int length=0, width=0;
        Scanner scan = new Scanner(System.in);
        while (rightNumber == 0) {
            length = scan.nextInt();
            width = scan.nextInt();
            if (length<=1 || width<=1 || length*width<18){
                System.out.println("На таком поле симуляция невозможна!");
            } else {
                rightNumber=1;
            }
        }
        rightNumber = 0;
        Board board = new Board(length,width);
        board.setDefaultEntitiesPositions();
        Renderer renderer = new Renderer();
        renderer.render(board,length,width);
        Simulation simulation = new Simulation(board,renderer,length,width);
        while (Simulation.endOfTheSimulation==0){
            simulation.entityAction();
            if (Simulation.endOfTheSimulation==0) {
                while (rightNumber == 0) {
                    System.out.println("Введите цифру. Продолжить симуляцию на ход - 0. Закончить симуляцию - 1");
                    Simulation.endOfTheSimulation = scan.nextInt();
                    if (Simulation.endOfTheSimulation != 0 && Simulation.endOfTheSimulation != 1) {
                        System.out.println("Вы ввели неправильную цифру!");
                    } else {
                        rightNumber = 1;
                    }
                }
                rightNumber=0;
            }
        }
        scan.close();
        System.out.println("hello");
    }
}