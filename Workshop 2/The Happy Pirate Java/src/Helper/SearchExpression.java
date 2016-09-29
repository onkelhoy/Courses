package Helper;

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
        boolean type = ;
        if(checkType(queryPart, ">=")){

        }
        else if(checkType(queryPart, "<=")){

        }
        else if(checkType(queryPart, ">")){

        }
        else if(checkType(queryPart, "<")){

        }
        else if(checkType(queryPart, "=")){

        }
        else {
            //no type found..
        }

        return expressionPart;
    }

    static boolean checkType(String part, String type){
        String[] arr = part.split(type);
        return arr.length == 2;
    }
}
