package edu.ucsb.cs156.spring.backenddemo.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;




import edu.ucsb.cs156.spring.backenddemo.services.PublicHolidayQueryService;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Slf4j
@RestClientTest(PublicHolidayQueryService.class)

public class PublicHolidayQueryServiceTests {

    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    private PublicHolidayQueryService publicHolidayQueryService;

    @Test
    public void test_getLocations() throws URISyntaxException, UnsupportedEncodingException, JsonProcessingException {
        String countryCode = "408";
        String year = "2001";

        String expectedURL = publicHolidayQueryService.ENDPOINT.replace("{year}", year)
                .replace("{countryCode}", countryCode);

        String fakeJsonResult = "{ \"fake\" : \"result\" }";

        this.mockRestServiceServer.expect(requestTo(expectedURL))
                .andExpect(header("Accept", MediaType.APPLICATION_JSON.toString()))
                .andExpect(header("Content-Type", MediaType.APPLICATION_JSON.toString()))
                .andRespond(withSuccess(fakeJsonResult, MediaType.APPLICATION_JSON));
        
        String actualResult = PublicHolidayQueryService.getJSON(year, countryCode);
        // log.info("actualResult= ",actualResult );
        assertEquals(fakeJsonResult, actualResult);
    }

}
