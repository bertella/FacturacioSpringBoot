package com.underground.invoiceservice.commons;

import org.apache.commons.lang3.StringUtils;

public class Util {

    /**
     * Validaciones para obtener solo numeros
     */
    public static boolean isOnlyNumbers(String stringToEval){
        if (StringUtils.isBlank(stringToEval)){
            return false;
        }

        return StringUtils.isNotBlank(getOnlyNumbers(stringToEval));
    }

    public static String getOnlyNumbers(String stringToEval){
        return stringToEval.replaceAll("\\D","");
    }

    /**
     * Validaciones para obtener solo letras
     */
    public static boolean isOnlyLetters(String stringToEval){
        if (StringUtils.isBlank(stringToEval)){
            return false;
        }

        return StringUtils.isNotBlank(getOnlyLetters(stringToEval));
    }

    public static String  getOnlyLetters(String stringToEval){
        return stringToEval.replaceAll("[^a-zA-Z\\s]","");
    }
}
