package org.paddy.utils;

public class ResponseStatusCodes {
    public enum CodeEnum {
        GET_200(200) {
            @Override
            public String getDescription() {
                return "The request was successful.";
            }
        },
        PATCH_200(200) {
            @Override
            public String getDescription() {
                return "The request was successful and the return type is non-void.";
            }
        },
        PATCH_204(204) {
            @Override
            public String getDescription() {
                return "The request was successful and the return type is void.";
            }
        },
        DELETE_GET_PATCH_POST_PUT_400(400) {
            @Override
            public String getDescription() {
                return "An unhandled user exception occurred.";
            }
        },
        DELETE_GET_PATCH_POST_PUT_403(403) {
            @Override
            public String getDescription() {
                return "You don't have access to the specified Apex class.";
            }
        },
        DELETE_GET_PATCH_POST_PUT_404_ANNOTATION(404) {
            @Override
            public String getDescription() {
                return "The URL is unmapped in an existing @RestResource annotation.";
            }
        },
        DELETE_GET_PATCH_POST_PUT_404_URL_EXTENSION(404) {
            @Override
            public String getDescription() {
                return "The URL extension is unsupported.";
            }
        },
        DELETE_GET_PATCH_POST_PUT_404_CLASS_NOT_FOUND(404) {
            @Override
            public String getDescription() {
                return "The Apex class with the specified namespace couldn't be found.";
            }
        },
        DELETE_GET_PATCH_POST_PUT_405(405) {
            @Override
            public String getDescription() {
                return "The request method doesn't have a corresponding Apex method.";
            }
        },
        DELETE_GET_PATCH_POST_PUT_406_CONTENT_TYPE(406) {
            @Override
            public String getDescription() {
                return "The Content-Type property in the header was set to a value other than JSON or XML.";
            }
        },
        DELETE_GET_PATCH_POST_PUT_406_HEADER_NOT_SUPPORTED(406) {
            @Override
            public String getDescription() {
                return "The header specified in the HTTP request is not supported.";
            }
        },
        GET_PATCH_POST_PUT_406(406) {
            @Override
            public String getDescription() {
                return "The XML return type specified for format is unsupported.";
            }
        },
        DELETE_GET_PATCH_POST_PUT_415_XML_NOT_SUPPORTED(415) {
            @Override
            public String getDescription() {
                return "The XML parameter type is unsupported.";
            }
        },
        DELETE_GET_PATCH_POST_PUT_415_CONTENT_HEADER_NOT_SUPPORTED(415) {
            @Override
            public String getDescription() {
                return "The Content-Header Type specified in the HTTP request header is unsupported.";
            }
        },
        DELETE_GET_PATCH_POST_PUT_500(500) {
            @Override
            public String getDescription() {
                return "An unhandled Apex exception occurred.";
            }
        };

        public static final String NO_DESCRIPTION_AVAILABLE = "No description available";
        private final int number;

        CodeEnum(int number) {
            this.number = number;
        }

        public int getNumber() {
            return this.number;
        }

        public String getDescription() {
            return NO_DESCRIPTION_AVAILABLE;
        }
    }

    private ResponseStatusCodes() {
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
