package Helper;

import java.util.Calendar;

/**
 * Created by henry on 2016-09-29.
 */
public class SearchExpression {
    static public String ConvertQuery(String query){
        query = query.replaceAll(" ", "").toLowerCase();
        String expression = "";

        String[] ors = query.split("or");
        for(int i = 0; i < ors.length; i++){
            String[] ands = ors[i].split("and");    // e.g. [a=3 and b>2], [a=1 and b<1]

            String a = "";
            for(int j = 0; j < ands.length; j++){
                a += convertPart(ands[j]) + (j < ands.length - 1 ? " and " : "");
            }
            expression += a + (i < ors.length - 2 ? " or " : "");
        }

        return String.format("//member[%s]", expression);
    }


    static private String convertPart(String queryPart){
        String expressionPart = "";
        String operator = "";
        String[] arr, types = new String[]{">=", "<=", "==", "=", ">", "<"};

        for(int i = 0; i< types.length; i++){
            arr = queryPart.split(types[i]);
            if(arr.length == 2){
                return getField(arr[0], (types[i].equals("==") ? "=": types[i]), arr[1].replaceAll("'", ""));
            }
        }
        //no operation found
        return "";
    }

    static private String getField(String fieldName, String operator, String value){
        String field = fieldName + operator + String.format("'%s'", value);
        String pre = (fieldName.contains("(") ? "(" : "");
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
                field = "";
                if(value.equals("jan") || value.equals("january"))  value = "01";
                else if(value.equals("feb") || value.equals("february")) value = "02";
                else if(value.equals("mar") || value.equals("march"))    value = "03";
                else if(value.equals("apr") || value.equals("april"))    value = "04";
                else if(value.equals("may"))                             value = "05";
                else if(value.equals("june"))                            value = "06";
                else if(value.equals("july"))                            value = "07";
                else if(value.equals("aug") || value.equals("august"))   value = "08";
                else if(value.equals("sep")||value.equals("september"))  value = "09";
                else if(value.equals("oct") || value.equals("october"))  value = "10";
                else if(value.equals("nov") || value.equals("november")) value = "11";
                else if(value.equals("dec") || value.equals("december")) value = "12";
                else value = "01"; //did not set propper month

                field = String.format("%ssubstring(identity, 5, 2) %s %s", pre, operator, value);

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
