package com.app.ktu.common;

import java.net.URI;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.assertj.core.api.Assertions.assertThat;

class LocationHeaderTest {

    @Test
    void getLocationHeaders_PathWithId_HttpHeaderWithLocationWithThatPathAndId() {
        // Setup
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        HttpHeaders expectedResult = new HttpHeaders();
        URI location = ServletUriComponentsBuilder.
          fromCurrentContextPath().
          path("path/0")
          .build()
          .toUri();
        expectedResult.setLocation(location);

        // Run the test
        final HttpHeaders result = LocationHeader.getLocationHeaders("path", 0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
