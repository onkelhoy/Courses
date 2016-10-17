package Helper;

import java.util.Random;

public class PersonNumberCheck {

    // private
    private String getFirstPart(){
        String y = "", m = "", d = "";
        Random rand = new Random();
        int _y = rand.nextInt(100);
        int _m = rand.nextInt(12);
        int tot = (_m == 1) ? 28: ((_m < 7) ? ((_m % 2 == 0) ? 31 : 30 ): ((_m % 2 != 0) ? 31 : 30));
        // orkar inte gå igenom.. de är bara för att få ut dagar i angiven månad.. (ej hänsyn till skottår)
        int _d = rand.nextInt(tot);

        _m += 1;
        _d += 1;

        y = (_y < 10) ? "0"+_y: _y + "";
        m = (_m < 10) ? "0"+_m: _m + "";
        d = (_d < 10) ? "0"+_d: _d + "";

        return y + m + d;
    }
    private String getSecondPart(String firstPart){
        Random rand = new Random();
        int[] personnummer = new int[10];//{rand.nextInt(10), rand.nextInt(10), rand.nextInt(10)};
        for(int i = 0; i < firstPart.length(); i++){
            //fyller listan först med start värden
            personnummer[i] = Character.getNumericValue(firstPart.charAt(i));
        }
        for(int i = 0; i < 3; i++){
            //fyller med N talen
            int x = rand.nextInt(10);
            firstPart += x;
            personnummer[i + 6] = x;
        }


        personnummer[9] = getLast(firstPart);
        String p = "";
        for(int i: personnummer){
            p += i;
        }
        int age = Integer.parseInt(p.substring(0, 1));

        String s = p.substring(0, 6) + p.substring(6, 10);
        return s;
    }
    private int getLast(String pnr){
        int[] personnummer = new int[10];//{rand.nextInt(10), rand.nextInt(10), rand.nextInt(10)};
        for(int i = 0; i < pnr.length(); i++){
            //fyller listan först med start värden
            personnummer[i] = Character.getNumericValue(pnr.charAt(i));
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

        return C;
    }

    // public
    public String GeneratePnr(){
        return Correct(getSecondPart(getFirstPart()));
    }
    public boolean Valid(String pnr){
        switch (pnr.length()){ //(swe)identity number can be 12 or 10 digits long
            case 12:
                return (getLast(pnr.substring(2))+"").equals(pnr.charAt(11)+"");
            case 10:
                return (getLast(pnr)+"").equals(pnr.charAt(9)+"");
            default://it can be YYMMDD-XXXC but... there should not be a ' - '..
                    //if(pnr.contains("-")) return Correct(pnr.replace("-", "")); // this would be the code for it..
                return false; //its not valid as its not even 10 or 12
        }
    }
    public String Correct(String pnr){
        pnr.replace("-", "");
        if (pnr.length() == 10){
            switch (pnr.charAt(0)){
                case '0':
                case '1':
                    pnr = "20"+pnr;
                    break;
                default:
                    pnr = "19"+pnr;
            }
        }

        if(!Valid(pnr)) pnr = GeneratePnr(); // it corrects it
        return pnr;
    }
}
