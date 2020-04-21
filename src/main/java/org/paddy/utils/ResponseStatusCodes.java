package org.paddy.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
    private static final HashMap<String, String> RESPONSE_STATUS_CODES_DESC = new HashMap<>();
    private static final HashMap<String, Integer> RESPONSE_STATUS_CODES = new HashMap<>();

    static {
        RESPONSE_STATUS_CODES_DESC.put("GET_200", "The request was successful.");
        RESPONSE_STATUS_CODES_DESC.put("PATCH_200", "The request was successful and the return type is non-void.");
        RESPONSE_STATUS_CODES_DESC.put("PATCH_204", "The request was successful and the return type is void.");
        RESPONSE_STATUS_CODES_DESC.put("DELETE_GET_PATCH_POST_PUT_400", "An unhandled user exception occurred.");
        RESPONSE_STATUS_CODES_DESC.put("DELETE_GET_PATCH_POST_PUT_403", "You don't have access to the specified Apex class.");
        RESPONSE_STATUS_CODES_DESC.put("DELETE_GET_PATCH_POST_PUT_404_ANNOTATION", "The URL is unmapped in an existing @RestResource annotation.");
        RESPONSE_STATUS_CODES_DESC.put("DELETE_GET_PATCH_POST_PUT_404_URL_EXTENSION", "The URL extension is unsupported.");
        RESPONSE_STATUS_CODES_DESC.put("DELETE_GET_PATCH_POST_PUT_404_CLASS_NOT_FOUND", "The Apex class with the specified namespace couldn't be found.");
        RESPONSE_STATUS_CODES_DESC.put("DELETE_GET_PATCH_POST_PUT_405", "The request method doesn't have a corresponding Apex method.");
        RESPONSE_STATUS_CODES_DESC.put("DELETE_GET_PATCH_POST_PUT_406_CONTENT_TYPE", "The Content-Type property in the header was set to a value other than JSON or XML.");
        RESPONSE_STATUS_CODES_DESC.put("DELETE_GET_PATCH_POST_PUT_406_HEADER_NOT_SUPPORTED", "The header specified in the HTTP request is not supported.");
        RESPONSE_STATUS_CODES_DESC.put("GET_PATCH_POST_PUT_406", "The XML return type specified for format is unsupported.");
        RESPONSE_STATUS_CODES_DESC.put("DELETE_GET_PATCH_POST_PUT_415_XML_NOT_SUPPORTED", "The XML parameter type is unsupported.");
        RESPONSE_STATUS_CODES_DESC.put("DELETE_GET_PATCH_POST_PUT_415_CONTENT_HEADER_NOT_SUPPORTED", "The Content-Header Type specified in the HTTP request header is unsupported.");
        RESPONSE_STATUS_CODES_DESC.put("DELETE_GET_PATCH_POST_PUT_500", "An unhandled Apex exception occurred.");
        RESPONSE_STATUS_CODES.put("GET_200", GET_200);
        RESPONSE_STATUS_CODES.put("PATCH_200", PATCH_200);
        RESPONSE_STATUS_CODES.put("PATCH_204", PATCH_204);
        RESPONSE_STATUS_CODES.put("DELETE_GET_PATCH_POST_PUT_400", DELETE_GET_PATCH_POST_PUT_400);
        RESPONSE_STATUS_CODES.put("DELETE_GET_PATCH_POST_PUT_403", DELETE_GET_PATCH_POST_PUT_403);
        RESPONSE_STATUS_CODES.put("DELETE_GET_PATCH_POST_PUT_404_ANNOTATION", DELETE_GET_PATCH_POST_PUT_404_ANNOTATION);
        RESPONSE_STATUS_CODES.put("DELETE_GET_PATCH_POST_PUT_404_URL_EXTENSION", DELETE_GET_PATCH_POST_PUT_404_URL_EXTENSION);
        RESPONSE_STATUS_CODES.put("DELETE_GET_PATCH_POST_PUT_404_CLASS_NOT_FOUND", DELETE_GET_PATCH_POST_PUT_404_CLASS_NOT_FOUND);
        RESPONSE_STATUS_CODES.put("DELETE_GET_PATCH_POST_PUT_405", DELETE_GET_PATCH_POST_PUT_405);
        RESPONSE_STATUS_CODES.put("DELETE_GET_PATCH_POST_PUT_406_CONTENT_TYPE", DELETE_GET_PATCH_POST_PUT_406_CONTENT_TYPE);
        RESPONSE_STATUS_CODES.put("DELETE_GET_PATCH_POST_PUT_406_HEADER_NOT_SUPPORTED", DELETE_GET_PATCH_POST_PUT_406_HEADER_NOT_SUPPORTED);
        RESPONSE_STATUS_CODES.put("GET_PATCH_POST_PUT_406", GET_PATCH_POST_PUT_406);
        RESPONSE_STATUS_CODES.put("DELETE_GET_PATCH_POST_PUT_415_XML_NOT_SUPPORTED", DELETE_GET_PATCH_POST_PUT_415_XML_NOT_SUPPORTED);
        RESPONSE_STATUS_CODES.put("DELETE_GET_PATCH_POST_PUT_415_CONTENT_HEADER_NOT_SUPPORTED", DELETE_GET_PATCH_POST_PUT_415_CONTENT_HEADER_NOT_SUPPORTED);
        RESPONSE_STATUS_CODES.put("DELETE_GET_PATCH_POST_PUT_500", DELETE_GET_PATCH_POST_PUT_500);
    }

    private ResponseStatusCodes() {
    }

    public static String getPossibleCause(String restMethod, int code) {
        Set<String> possibleKeysS = getPossibleKeys(restMethod, code);
        StringBuilder possibleCause = new StringBuilder();
        for (String key : possibleKeysS) {
            possibleCause.append(RESPONSE_STATUS_CODES_DESC.get(key))
                    .append("\n");
        }
        return possibleCause.toString().trim();
    }

    private static Set<String> getPossibleKeys(String restMethod, int code) {
        Set<String> possibleKeysS = new HashSet<>();
        for (Map.Entry<String, Integer> entry: RESPONSE_STATUS_CODES.entrySet()) {
            if (entry.getValue() == code && entry.getKey().contains(restMethod)) {
                possibleKeysS.add(entry.getKey());
            }
        }
        return possibleKeysS;
    }
}
