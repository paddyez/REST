package org.paddy.utils;

public class ResponseStatusCodes {
    private ResponseStatusCodes() {
    }

    public enum CodeEnum {
        GET_200(200) {
            @Override
            public String getDescription() {
                return CodeMsgStrings.THE_REQUEST_WAS_SUCCESSFUL.getMsgString();
            }
        },
        PATCH_200(200) {
            @Override
            public String getDescription() {
                return CodeMsgStrings.AND_THE_RETURN_TYPE_IS_NON_VOID.getMsgString();
            }
        },
        PATCH_204(204) {
            @Override
            public String getDescription() {
                return CodeMsgStrings.AND_THE_RETURN_TYPE_IS_VOID.getMsgString();
            }
        },
        DELETE_GET_PATCH_POST_PUT_400(400) {
            @Override
            public String getDescription() {
                return CodeMsgStrings.UNHANDLED_USER_EXCEPTION_OCCURRED.getMsgString();
            }
        },
        DELETE_GET_PATCH_POST_PUT_403(403) {
            @Override
            public String getDescription() {
                return CodeMsgStrings.DON_T_HAVE_ACCESS_TO_THE_SPECIFIED_APEX_CLASS.getMsgString();
            }
        },
        DELETE_GET_PATCH_POST_PUT_404_ANNOTATION(404) {
            @Override
            public String getDescription() {
                return CodeMsgStrings.URL_IS_UNMAPPED_IN_AN_EXISTING_REST_RESOURCE_ANNOTATION.getMsgString();
            }
        },
        DELETE_GET_PATCH_POST_PUT_404_URL_EXTENSION(404) {
            @Override
            public String getDescription() {
                return CodeMsgStrings.URL_EXTENSION_IS_UNSUPPORTED.getMsgString();
            }
        },
        DELETE_GET_PATCH_POST_PUT_404_CLASS_NOT_FOUND(404) {
            @Override
            public String getDescription() {
                return CodeMsgStrings.APEX_CLASS_WITH_THE_SPECIFIED_NAMESPACE_COULD_NOT_BE_FOUND.getMsgString();
            }
        },
        DELETE_GET_PATCH_POST_PUT_405(405) {
            @Override
            public String getDescription() {
                return CodeMsgStrings.REQUEST_METHOD_DOES_NOT_HAVE_A_CORRESPONDING_APEX_METHOD.getMsgString();
            }
        },
        DELETE_GET_PATCH_POST_PUT_406_CONTENT_TYPE(406) {
            @Override
            public String getDescription() {
                return CodeMsgStrings.THE_CONTENT_TYPE_PROPERTY_IN_THE_HEADER_WAS_SET_TO_A_VALUE_OTHER_THAN_JSON_OR_XML.getMsgString();
            }
        },
        DELETE_GET_PATCH_POST_PUT_406_HEADER_NOT_SUPPORTED(406) {
            @Override
            public String getDescription() {
                return CodeMsgStrings.HEADER_SPECIFIED_IN_THE_HTTP_REQUEST_IS_NOT_SUPPORTED.getMsgString();
            }
        },
        GET_PATCH_POST_PUT_406(406) {
            @Override
            public String getDescription() {
                return CodeMsgStrings.XML_RETURN_TYPE_SPECIFIED_FOR_FORMAT_IS_UNSUPPORTED.getMsgString();
            }
        },
        DELETE_GET_PATCH_POST_PUT_415_XML_NOT_SUPPORTED(415) {
            @Override
            public String getDescription() {
                return CodeMsgStrings.XML_PARAMETER_TYPE_IS_UNSUPPORTED.getMsgString();
            }
        },
        DELETE_GET_PATCH_POST_PUT_415_CONTENT_HEADER_NOT_SUPPORTED(415) {
            @Override
            public String getDescription() {
                return CodeMsgStrings.CONTENT_HEADER_TYPE_SPECIFIED_IN_THE_HTTP_REQUEST_HEADER_IS_UNSUPPORTED.getMsgString();
            }
        },
        DELETE_GET_PATCH_POST_PUT_500(500) {
            @Override
            public String getDescription() {
                return CodeMsgStrings.UNHANDLED_APEX_EXCEPTION_OCCURRED.getMsgString();
            }
        };
        private final int number;

        CodeEnum(int number) {
            this.number = number;
        }

        public static final String NO_DESCRIPTION_AVAILABLE = "No description available";
        public int getNumber() {
            return this.number;
        }

        public String getDescription() {
            return NO_DESCRIPTION_AVAILABLE;
        }
    }

    private enum CodeMsgStrings {
        AND_THE_RETURN_TYPE_IS_NON_VOID("The request was successful and the return type is non-void."),
        AND_THE_RETURN_TYPE_IS_VOID("The request was successful and the return type is void."),
        APEX_CLASS_WITH_THE_SPECIFIED_NAMESPACE_COULD_NOT_BE_FOUND("The Apex class with the specified namespace couldn't be found."),
        CONTENT_HEADER_TYPE_SPECIFIED_IN_THE_HTTP_REQUEST_HEADER_IS_UNSUPPORTED("The Content-Header Type specified in the HTTP request header is unsupported."),
        DON_T_HAVE_ACCESS_TO_THE_SPECIFIED_APEX_CLASS("You don't have access to the specified Apex class."),
        HEADER_SPECIFIED_IN_THE_HTTP_REQUEST_IS_NOT_SUPPORTED("The header specified in the HTTP request is not supported."),
        REQUEST_METHOD_DOES_NOT_HAVE_A_CORRESPONDING_APEX_METHOD("The request method doesn't have a corresponding Apex method."),
        THE_CONTENT_TYPE_PROPERTY_IN_THE_HEADER_WAS_SET_TO_A_VALUE_OTHER_THAN_JSON_OR_XML("The Content-Type property in the header was set to a value other than JSON or XML."),
        THE_REQUEST_WAS_SUCCESSFUL("The request was successful."),
        UNHANDLED_APEX_EXCEPTION_OCCURRED("An unhandled Apex exception occurred."),
        UNHANDLED_USER_EXCEPTION_OCCURRED("An unhandled user exception occurred."),
        URL_EXTENSION_IS_UNSUPPORTED("The URL extension is unsupported."),
        URL_IS_UNMAPPED_IN_AN_EXISTING_REST_RESOURCE_ANNOTATION("The URL is unmapped in an existing @RestResource annotation."),
        XML_PARAMETER_TYPE_IS_UNSUPPORTED("The XML parameter type is unsupported."),
        XML_RETURN_TYPE_SPECIFIED_FOR_FORMAT_IS_UNSUPPORTED("The XML return type specified for format is unsupported.");
        private final String msgString;

        CodeMsgStrings(String msgString) {
            this.msgString = msgString;
        }

        public String getMsgString() {
            return msgString;
        }
    }

    public static String getPossibleCause(String restMethod, int code) {
        StringBuilder possibleCause = new StringBuilder();
        for (CodeEnum codeEnum : CodeEnum.values()) {
            if (codeEnum.getNumber() == code && codeEnum.name().contains(restMethod)) {
                possibleCause.append(codeEnum.getDescription())
                        .append("\n");
            }
        }
        return possibleCause.toString().trim();
    }
}
