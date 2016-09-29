package Helper;

import java.util.Calendar;

/**
 * Created by henry on 2016-09-29.
 */
public class SearchExpression {
    static public String ConvertQuery(String query){
        String expression = "";

        String[] ors = query.split("or");
        for(int i = 0; i < ors.length; i++){
            String[] ands = ors[i].split("and");    // e.g. [a=3 and b>2], [a=1 and b<1]

            for(int j = 0; j < ands.length; j++){

            }
        }

        return expression;
    }


    static private String convertPart(String queryPart){
        String expressionPart = "";
        String[] arr = queryPart.split(">=");
        if(arr.length == 2){ // >=

        }
        else arr = queryPart.split("<=");
        if(arr.length == 2){ // <=

        }
        else arr = queryPart.split("=");
        if(arr.length == 2){ // =

        }
        else arr = queryPart.split(">");
        if(arr.length == 2){ // >

        }
        else arr = queryPart.split("<");
        if(arr.length == 2){ // <

        }
        else {
            //no type found..
        }

        return expressionPart;
    }

    static private String getField(String fieldName, String operator, String value){
        String field = fieldName + operator + value;
        switch (fieldName){
            case "gender":
                field = "substring(identity, 10, 1) mod 2 = " + (value.equals("man") ? 1 : 0); //YYYYMMDD XXGC <- G = gender
                break;
            case "age":
                field = String.format("(%s - substring(identity, 0, 5)) %s %s", Calendar.getInstance().get(Calendar.YEAR), operator, value);
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

                field = String.format("substring(identity, 5, 2) %s %s", operator, value);

                break;
            case "boattype":
                field = String.format("boat[@type %s '%s']", operator, value);
                break;
            case "boats":
                field = "count(boats/boat)";
                break;
            case "boatlength":
                field = "";
                break;
        }

        return field;
    }
}
