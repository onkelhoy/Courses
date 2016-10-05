package Helper;

import java.util.Calendar;

/**
 * Created by henry on 2016-09-29.
 */
public class SearchExpression {
    static public String ConvertQuery(String query){
        query = query.replaceAll(" ", "");
        String expression = "";

        String[] ors = query.split("or");
        for(int i = 0; i < ors.length; i++){
            String[] ands = ors[i].split("and");    // e.g. [a=3 and b>2], [a=1 and b<1]

            String a = "";
            for(int j = 0; j < ands.length; j++){
                a += convertPart(ands[j]) + (j < ands.length - 1 ? " and " : "");
            }
            expression += a + (i < ors.length - 1 ? " or " : "");
        }

        return expression;
    }


    static private String convertPart(String queryPart){
        String expressionPart = "";
        String operator = "";
        String[] arr, types = new String[]{">=", "<=", "==", "=", ">", "<"};

        for(int i = 0; i< types.length; i++){
            arr = queryPart.split(types[i]);
            if(arr.length == 2){
                return getField(arr[0].toLowerCase(), (types[i].equals("==") ? "=": types[i]), arr[1].replaceAll("'", ""));
            }
        }
        //no operation found
        return "";
    }

    static private String getField(String fieldName, String operator, String value){
        String field = fieldName + operator + String.format("'%s'", value);
        String pre = fieldName.contains("(") ? fieldName.replaceAll("[\\w\\d]", "") : "";

        switch (fieldName.replaceAll("\\W", "")){
            case "password":
                return "";
            case "gender":
                field = pre + "substring(identity, 10, 1) mod 2 = " + (value.equals("man") ? 1 : 0); //YYYYMMDD XXGC <- G = gender
                break;
            case "age":
                field = String.format("%s(%s - substring(identity, 0, 5)) %s %s", pre, Calendar.getInstance().get(Calendar.YEAR), operator, value);
                break;
            case "month":
                String aft = value.contains(")") ? value.replaceAll("[\\w\\d]", "") : ""; //replaces all word and digit chars
                value = value.replaceAll("\\W", "");

                if(value.contains("jan")        || value.equals("1") || value.equals("01")) value = "01";
                else if(value.contains("feb")   || value.equals("2") || value.equals("02")) value = "02";
                else if(value.contains("mar")   || value.equals("3") || value.equals("03")) value = "03";
                else if(value.contains("apr")   || value.equals("4") || value.equals("04")) value = "04";
                else if(value.contains("may")   || value.equals("5") || value.equals("05")) value = "05";
                else if(value.contains("june")  || value.equals("6") || value.equals("06")) value = "06";
                else if(value.contains("july")  || value.equals("7") || value.equals("07")) value = "07";
                else if(value.contains("aug")   || value.equals("8") || value.equals("08")) value = "08";
                else if(value.contains("sep")   || value.equals("9") || value.equals("09")) value = "09";
                else if(value.contains("oct")   || value.equals("10"))                      value = "10";
                else if(value.contains("nov")   || value.equals("11"))                      value = "11";
                else if(value.contains("dec")   || value.equals("12"))                      value = "12";
                else value = "01"; //did not set propper month

                field = String.format("%ssubstring(identity, 5, 2) %s %s", pre, operator, value) + aft;
                break;
            case "boattype":
                field = String.format("%sboats/boat[@type = '%s']", pre, value);
                break;
            case "boats":
                field = String.format("%scount(boats/boat) %s %s", pre, operator, value);
                break;
            case "boatlength":
                field = String.format("%sboats/boat[@length %s '%s']", pre, operator, value);
                break;
            default:
                if(operator.equals("=") && value.contains("*")){
                    field = String.format("%s[contains(text(), '%s')]", fieldName, value.replace('*', ' ').replace(" ", ""));
                }
        }

        return field;
    }
}
