package com.paycoreumutyildiz.creditsystem.Controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.paycoreumutyildiz.creditsystem.Exceptions.Handler.GenericExceptionHandler;
import com.paycoreumutyildiz.creditsystem.Model.Credit;
import com.paycoreumutyildiz.creditsystem.Service.concretes.CreditServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
class CreditControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CreditServiceImpl creditService;

    @InjectMocks
    private CreditController creditController;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(creditController)
                .setControllerAdvice(new GenericExceptionHandler())
                .build();
    }

    @Test
    void getAllCredits() throws Exception {
        List<Credit> expectedCredits = new ArrayList<>();
        Credit firstCredit = new Credit(1L,"RED",0);
        Credit secondCredit = new Credit(2L,"ONAY",10000);
        expectedCredits.add(firstCredit);
        expectedCredits.add(secondCredit);

        when(creditService.getAllCredits()).thenReturn(expectedCredits);

        MockHttpServletResponse response = mockMvc.perform(get("/api/credit/all")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andReturn().getResponse();

        List<Credit> actual = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<List<Credit>>() {});

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertEquals(expectedCredits.size(),actual.size());
        assertEquals(expectedCredits.get(0),actual.get(0));
        assertEquals(expectedCredits.get(1),actual.get(1));
    }

    @Test
    void getCredit() throws Exception {
        Credit expected = new Credit(1L,"RED",0);

        when(creditService.getCredit(1L)).thenReturn(expected);

        MockHttpServletResponse response = mockMvc.perform(get("/api/credit/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Credit actual = new ObjectMapper().readValue(response.getContentAsString(),Credit.class);
        assertEquals(expected,actual);

    }

    @Test
    void createCredit() throws Exception {
        Credit credit = new Credit(1L,"RED",0);
        Mockito.doNothing().when(creditService).addCredit(credit);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonCredit = ow.writeValueAsString(credit);

        MockHttpServletResponse response = mockMvc.perform(post("/api/credit/create")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCredit)).andDo(print()).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(creditService,times(1)).addCredit(credit);

    }

    @Test
    void updateCredit() throws Exception{
        Credit credit = new Credit(1L,"RED",0);
        when(creditService.updateCredit(credit)).thenReturn(credit);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonCredit = ow.writeValueAsString(credit);

        MockHttpServletResponse response = mockMvc.perform(put("/api/credit/update")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCredit)).andDo(print()).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(creditService,times(1)).updateCredit(credit);
    }

    @Test
    void deleteCredit() throws Exception{
        Boolean expected = true;
        when(creditService.deleteCredit(1L)).thenReturn(expected);

        MockHttpServletResponse response = mockMvc.perform(delete("/api/credit/delete?sid=1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Boolean actual = new ObjectMapper().readValue(response.getContentAsString(),Boolean.class);
        assertEquals(expected,actual);
    }

    @Test
    void queryCredit() throws Exception {
        Map<String,String> expected = new HashMap<>();
        expected.put("message","RED");
        expected.put("creditLimit","0");

        when(creditService.queryCredit(1L)).thenReturn(expected);


        MockHttpServletResponse response = mockMvc.perform(get("/api/credit/query?sid=1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Map actual = new ObjectMapper().readValue(response.getContentAsString(),Map.class);
        assertEquals(expected,actual);
    }
}