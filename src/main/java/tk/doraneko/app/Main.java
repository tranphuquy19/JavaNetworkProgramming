package tk.doraneko.app;

import tk.doraneko.commons.ConsoleColors;
import tk.doraneko.commons.JavaProcess;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author tranphuquy19@gmail.com
 * @since 23/08/2019
 */
public class Main {

    private static void getClasses() {
        for (Classs c : Classs.values()) {
            System.out.println(ConsoleColors.YELLOW_BRIGHT + "[" + c.getIndex() + "] " + c.getClassName() + ConsoleColors.RESET + ConsoleColors.PURPLE_BOLD + "\t#" + c.getClassPackage() + ConsoleColors.RESET);
        }
    }


    public static void main(String[] args) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            int options = 0;
            System.out.println(ConsoleColors.YELLOW + "If you find bugs. Congratulations, that's new feature @tranphuquy19" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN + " Cac bai lap trong mon lap trinh mang" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN + "Enter so am (-) de show code, Ctrl+C de stop chuong trinh" + ConsoleColors.RESET);
            getClasses();
            System.out.print(ConsoleColors.RED_BOLD + "Enter: " + ConsoleColors.RESET);

            try {
                options = sc.nextInt();
            } catch (InputMismatchException e) {
                continue;
            }
            if (options == 0 || options > (Classs.values().length)) {
                System.out.println(ConsoleColors.YELLOW_BRIGHT + "ERROR, Chon ngoai pham vi!" + ConsoleColors.RESET);
                continue;
            } else if (options < 0) {
                try {
                    JavaProcess.openCode(options * -1);
                } catch (IOException e) {
                }
            } else {
                try {
                    JavaProcess.exec(Classs.findByIndex(options).getClassEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
