package org.paddy.utils;

import org.junit.jupiter.api.Test;
import org.paddy.utils.ResponseStatusCodes.CodeEnum;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ResponseStatusCodesTest {
    public static final String EXPECTED_GET_404 = "The URL is unmapped in an existing @RestResource annotation.\n" +
            "The URL extension is unsupported.\n" +
            "The Apex class with the specified namespace couldn't be found.";
    public static final String EXPECTED_GET_406 = "The Content-Type property in the header was set to a value other than JSON or XML.\n" +
            "The header specified in the HTTP request is not supported.\n" +
            "The XML return type specified for format is unsupported.";
    public static final String EXPECTED_GET_415 = "The XML parameter type is unsupported.\n" +
            "The Content-Header Type specified in the HTTP request header is unsupported.";
    public static final String EXPECTED_POST_404 = "The URL is unmapped in an existing @RestResource annotation.\n" +
            "The URL extension is unsupported.\n" +
            "The Apex class with the specified namespace couldn't be found.";
    public static final String EXPECTED_POST_406 = "The Content-Type property in the header was set to a value other than JSON or XML.\n" +
            "The header specified in the HTTP request is not supported.\n" +
            "The XML return type specified for format is unsupported.";
    public static final String EXPECTED_POST_415 = "The XML parameter type is unsupported.\n" +
            "The Content-Header Type specified in the HTTP request header is unsupported.";

    @Test
    void getTest() {
        for (CodeEnum codeEnum : CodeEnum.values()) {
            int n = codeEnum.getNumber();
            String possibleCause = ResponseStatusCodes.getPossibleCause("GET", n);
            if (codeEnum.name().contains("GET")) {
                if (n == 200) {
                    assertThat(possibleCause).isEqualTo(CodeEnum.GET_200.getDescription());
                } else if (n == 400) {
                    assertThat(possibleCause).isEqualTo(CodeEnum.DELETE_GET_PATCH_POST_PUT_400.getDescription());
                } else if (n == 403) {
                    assertThat(possibleCause).isEqualTo(CodeEnum.DELETE_GET_PATCH_POST_PUT_403.getDescription());
                } else if (n == 404) {
                    assertThat(possibleCause).isEqualTo(EXPECTED_GET_404);
                } else if (n == 405) {
                    assertThat(possibleCause).isEqualTo(CodeEnum.DELETE_GET_PATCH_POST_PUT_405.getDescription());
                } else if (n == 406) {
                    assertThat(possibleCause).isEqualTo(EXPECTED_GET_406);
                } else if (n == 415) {
                    assertThat(possibleCause).isEqualTo(EXPECTED_GET_415);
                } else if (n == 500) {
                    assertThat(possibleCause).isEqualTo(CodeEnum.DELETE_GET_PATCH_POST_PUT_500.getDescription());
                } else {
                    assertThat(possibleCause).isEqualTo("");
                }
            } else if (codeEnum == CodeEnum.PATCH_200) {
                assertThat(possibleCause).isNotEqualTo(CodeEnum.PATCH_200.getDescription());
            } else {
                assertThat(possibleCause).isEqualTo("");
            }
        }
    }

    @Test
    void patchTest() {
        for (CodeEnum codeEnum : CodeEnum.values()) {
            int n = codeEnum.getNumber();
            String possibleCause = ResponseStatusCodes.getPossibleCause("PATCH", n);
            if (codeEnum.name().contains("PATCH")) {
                if (n == 204) {
                    assertThat(possibleCause).isEqualTo(CodeEnum.PATCH_204.getDescription());
                }
            }
        }
    }

    @Test
    void postTest() {
        for (CodeEnum codeEnum : CodeEnum.values()) {
            int n = codeEnum.getNumber();
            String possibleCause = ResponseStatusCodes.getPossibleCause("POST", n);
            if (codeEnum.name().contains("POST")) {
                if (n == 400) {
                    assertThat(possibleCause).isEqualTo(CodeEnum.DELETE_GET_PATCH_POST_PUT_400.getDescription());
                } else if (n == 403) {
                    assertThat(possibleCause).isEqualTo(CodeEnum.DELETE_GET_PATCH_POST_PUT_403.getDescription());
                } else if (n == 404) {
                    assertThat(possibleCause).isEqualTo(EXPECTED_POST_404);
                } else if (n == 405) {
                    assertThat(possibleCause).isEqualTo(CodeEnum.DELETE_GET_PATCH_POST_PUT_405.getDescription());
                } else if (n == 406) {
                    assertThat(possibleCause).isEqualTo(EXPECTED_POST_406);
                } else if (n == 415) {
                    assertThat(possibleCause).isEqualTo(EXPECTED_POST_415);
                } else if (n == 500) {
                    assertThat(possibleCause).isEqualTo(CodeEnum.DELETE_GET_PATCH_POST_PUT_500.getDescription());
                } else {
                    assertThat(possibleCause).isEqualTo("");
                }
            } else {
                assertThat(possibleCause).isEqualTo("");
            }
        }
    }

    @Test
    void nonExistentEnumTest() {
        CodeEnum codeEnum = mock(CodeEnum.class);
        when(codeEnum.getDescription()).thenCallRealMethod();
        String desc = codeEnum.getDescription();
        assertThat(desc).isEqualTo(CodeEnum.NO_DESCRIPTION_AVAILABLE);
    }
}
