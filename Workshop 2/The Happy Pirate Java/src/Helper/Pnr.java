package Helper;

import java.util.Random;
import java.util.Scanner;

public class Pnr {

    public String personnummer = "";
    public String gender = "";

    public Pnr(boolean controll){
        String first = getFirstPart(controll);
        personnummer = getSecondPart(first);
    }

    public static void main(String[] args) {
        Pnr	b = new Pnr(false);
        Pnr a = new Pnr(false);
        //man kan inte kontrolera b�da (sdamtidigt).. blir fel med scanner
        //.. skulle de vara c# skulle de inte varit ngra problem..
        Gender(a);
        Gender(b);
        System.out.println(Pnr.areEqual(a, b));
    }
    public static boolean isCorrect(){
        //har redan tagit h�nsyn till datum..
        //har redan fixat med sista siffran
        //d�rmed vet jag att det �r ett korrekt svenskt personnummer
        return true;
    }
    public static String getFirstPart(boolean controll){
        String y = "", m = "", d = "";
        if(controll){
            Scanner scan = new Scanner(System.in);
            System.out.print("�ret du �r f�dd (��): ");
            y = scan.nextLine();
            System.out.print("M�naden du �r f�dd (MM): ");
            m = scan.nextLine();
            System.out.print("Dagen du �r f�dd (DD): ");
            d = scan.nextLine();
            scan.close();
        }
        else {//fall man vill randomisera allt
            Random rand = new Random();
            int _y = rand.nextInt(100);
            int _m = rand.nextInt(12);
            int tot = (_m == 1) ? 28: ((_m < 7) ? ((_m % 2 == 0) ? 31 : 30 ): ((_m % 2 != 0) ? 31 : 30));
            // orkar inte g� igenom.. de �r bara f�r att f� ut dagar i angiven m�nad.. (ej h�nsyn till skott�r)
            int _d = rand.nextInt(tot);

            _m += 1;
            _d += 1;

            y = (_y < 10) ? "0"+_y: _y + "";
            m = (_m < 10) ? "0"+_m: _m + "";
            d = (_d < 10) ? "0"+_d: _d + "";
        }

        return y + m + d;
    }
    public static void Gender(Pnr a){
        a.gender = (Character.getNumericValue(a.personnummer.charAt(9)) % 2 == 0) ? "feminint" : "maskulint";
        System.out.println("k�net p� personnummret: " + a.personnummer + " �r " + a.gender);
    }
    public static boolean areEqual(Pnr a, Pnr b){
        //det �r lite b�kigt..
        //vill du kanske ha classerna som object (som de egentligen skall vara)
        //s� att man skapar tv� Pnr ?

        return a.personnummer.equals(b.personnummer);
    }
    public static String getSecondPart(String firstPart){
		/*
		 * denna f�ljer svensk m�tning
		 *
		 * N �r random
		 * det �r C som betyder n�got
		 *
		 * � � M M D D - N N N
		 * 2 1 2 1 2 1 - 2 1 2
		 *
		 * multicerar dessa (de �vre me de undre)
		 * blir talen tv�siffrigt splittras de upp
		 * sedan summeras de.. tar ut rest av % 10..
		 * 10 subrtaheras med det givna tal och C �r best�mt
		 */

        Random rand = new Random();
        int[] personnummer = new int[10];//{rand.nextInt(10), rand.nextInt(10), rand.nextInt(10)};
        for(int i = 0; i < firstPart.length(); i++){
            //fyller listan f�rst med start v�rden
            personnummer[i] = Character.getNumericValue(firstPart.charAt(i));
        }
        for(int i = 0; i < 3; i++){
            //fyller med N talen
            personnummer[i + 6] = rand.nextInt(10);
        }


        int sum = 0;
        for(int i = 0; i < 9; i++){
            if(i % 2 == 0){
                //j�mt.. 2
                int t = personnummer[i] * 2;
                if(t >= 10){
                    sum += Character.getNumericValue(("" + t).charAt(0));
                    sum += Character.getNumericValue(("" + t).charAt(1));
                }
                else {
                    sum += t;
                }
            }
            else {
                //ojmt.. 1
                sum += personnummer[i];
            }
        }
        int C = 10 - (sum %= 10);
        personnummer[9] = C;
        String p = "";
        for(int i: personnummer){
            p += i;
        }
        int age = Integer.parseInt(p.substring(0, 1));

        String s = p.substring(0, 6) + "-" + p.substring(6, 10);
        return s;
    }

}
