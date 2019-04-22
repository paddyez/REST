package org.paddy.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ResponseStatusCodes {
    private static final int GET_200 = 200;
    private static final int PATCH_200 = 200;
    private static final int PATCH_204 = 204;
    private static final int DELETE_GET_PATCH_POST_PUT_400 = 400;
    private static final int DELETE_GET_PATCH_POST_PUT_403 = 403;
    private static final int DELETE_GET_PATCH_POST_PUT_404_ANNOTATION = 404;
    private static final int DELETE_GET_PATCH_POST_PUT_404_URL_EXTENSION = 404;
    private static final int DELETE_GET_PATCH_POST_PUT_404_CLASS_NOT_FOUND = 404;
    private static final int DELETE_GET_PATCH_POST_PUT_405 = 405;
    private static final int DELETE_GET_PATCH_POST_PUT_406_CONTENT_TYPE = 406;
    private static final int DELETE_GET_PATCH_POST_PUT_406_HEADER_NOT_SUPPORTED = 406;
    private static final int GET_PATCH_POST_PUT_406 = 406;
    private static final int DELETE_GET_PATCH_POST_PUT_415_XML_NOT_SUPPORTED = 415;
    private static final int DELETE_GET_PATCH_POST_PUT_415_CONTENT_HEADER_NOT_SUPPORTED = 415;
    private static final int DELETE_GET_PATCH_POST_PUT_500 = 500;
    private static final HashMap<String, String> responseStatusCodesDesc = new HashMap<>();
    private static final HashMap<String, Integer> responseStatusCodes = new HashMap<>();

    static {
        responseStatusCodesDesc.put("GET_200", "The request was successful.");
        responseStatusCodesDesc.put("PATCH_200", "The request was successful and the return type is non-void.");
        responseStatusCodesDesc.put("PATCH_204", "The request was successful and the return type is void.");
        responseStatusCodesDesc.put("DELETE_GET_PATCH_POST_PUT_400", "An unhandled user exception occurred.");
        responseStatusCodesDesc.put("DELETE_GET_PATCH_POST_PUT_403", "You don't have access to the specified Apex class.");
        responseStatusCodesDesc.put("DELETE_GET_PATCH_POST_PUT_404_ANNOTATION", "The URL is unmapped in an existing @RestResource annotation.");
        responseStatusCodesDesc.put("DELETE_GET_PATCH_POST_PUT_404_URL_EXTENSION", "The URL extension is unsupported.");
        responseStatusCodesDesc.put("DELETE_GET_PATCH_POST_PUT_404_CLASS_NOT_FOUND", "The Apex class with the specified namespace couldn't be found.");
        responseStatusCodesDesc.put("DELETE_GET_PATCH_POST_PUT_405", "The request method doesn't have a corresponding Apex method.");
        responseStatusCodesDesc.put("DELETE_GET_PATCH_POST_PUT_406_CONTENT_TYPE", "The Content-Type property in the header was set to a value other than JSON or XML.");
        responseStatusCodesDesc.put("DELETE_GET_PATCH_POST_PUT_406_HEADER_NOT_SUPPORTED", "The header specified in the HTTP request is not supported.");
        responseStatusCodesDesc.put("GET_PATCH_POST_PUT_406", "The XML return type specified for format is unsupported.");
        responseStatusCodesDesc.put("DELETE_GET_PATCH_POST_PUT_415_XML_NOT_SUPPORTED", "The XML parameter type is unsupported.");
        responseStatusCodesDesc.put("DELETE_GET_PATCH_POST_PUT_415_CONTENT_HEADER_NOT_SUPPORTED", "The Content-Header Type specified in the HTTP request header is unsupported.");
        responseStatusCodesDesc.put("DELETE_GET_PATCH_POST_PUT_500", "An unhandled Apex exception occurred.");
        responseStatusCodes.put("GET_200", GET_200);
        responseStatusCodes.put("PATCH_200", PATCH_200);
        responseStatusCodes.put("PATCH_204", PATCH_204);
        responseStatusCodes.put("DELETE_GET_PATCH_POST_PUT_400", DELETE_GET_PATCH_POST_PUT_400);
        responseStatusCodes.put("DELETE_GET_PATCH_POST_PUT_403", DELETE_GET_PATCH_POST_PUT_403);
        responseStatusCodes.put("DELETE_GET_PATCH_POST_PUT_404_ANNOTATION", DELETE_GET_PATCH_POST_PUT_404_ANNOTATION);
        responseStatusCodes.put("DELETE_GET_PATCH_POST_PUT_404_URL_EXTENSION", DELETE_GET_PATCH_POST_PUT_404_URL_EXTENSION);
        responseStatusCodes.put("DELETE_GET_PATCH_POST_PUT_404_CLASS_NOT_FOUND", DELETE_GET_PATCH_POST_PUT_404_CLASS_NOT_FOUND);
        responseStatusCodes.put("DELETE_GET_PATCH_POST_PUT_405", DELETE_GET_PATCH_POST_PUT_405);
        responseStatusCodes.put("DELETE_GET_PATCH_POST_PUT_406_CONTENT_TYPE", DELETE_GET_PATCH_POST_PUT_406_CONTENT_TYPE);
        responseStatusCodes.put("DELETE_GET_PATCH_POST_PUT_406_HEADER_NOT_SUPPORTED", DELETE_GET_PATCH_POST_PUT_406_HEADER_NOT_SUPPORTED);
        responseStatusCodes.put("GET_PATCH_POST_PUT_406", GET_PATCH_POST_PUT_406);
        responseStatusCodes.put("DELETE_GET_PATCH_POST_PUT_415_XML_NOT_SUPPORTED", DELETE_GET_PATCH_POST_PUT_415_XML_NOT_SUPPORTED);
        responseStatusCodes.put("DELETE_GET_PATCH_POST_PUT_415_CONTENT_HEADER_NOT_SUPPORTED", DELETE_GET_PATCH_POST_PUT_415_CONTENT_HEADER_NOT_SUPPORTED);
        responseStatusCodes.put("DELETE_GET_PATCH_POST_PUT_500", DELETE_GET_PATCH_POST_PUT_500);
    }

    public static String getPossibleCause(String restMethod, int code) {
        Set<String> possibleKeysS = getPossibleKeys(restMethod, code);
        String possibleCause = "";
        for (String key : possibleKeysS) {
            possibleCause += responseStatusCodesDesc.get(key) + "\n";
        }
        return possibleCause.trim();
    }

    private static Set<String> getPossibleKeys(String restMethod, int code) {
        Set<String> possibleKeysS = new HashSet<>();
        for (String key : responseStatusCodes.keySet()) {
            if (responseStatusCodes.get(key).intValue() == code && key.contains(restMethod)) {
                possibleKeysS.add(key);
            }
        }
        return possibleKeysS;
    }
}
