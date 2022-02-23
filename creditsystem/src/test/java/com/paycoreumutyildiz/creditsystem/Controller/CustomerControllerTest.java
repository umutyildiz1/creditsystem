package com.paycoreumutyildiz.creditsystem.Controller;

import com.paycoreumutyildiz.creditsystem.Model.Customer;
import com.paycoreumutyildiz.creditsystem.Service.concretes.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.paycoreumutyildiz.creditsystem.Exceptions.Handler.GenericExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerServiceImpl customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new GenericExceptionHandler())
                .build();
    }

    @Test
    void getAllCustomers() throws Exception{
        List<Customer> expectedCustomers = new ArrayList<>();
        Customer firstCustomer = new Customer
                (1L,"Customer1","Customer1",2000L,"11111111111",450,null);
        Customer secondCustomer = new Customer
                (2L,"Customer2","Customer2",4000L,"11111111112",505,null);
        expectedCustomers.add(firstCustomer);
        expectedCustomers.add(secondCustomer);

        when(customerService.getAllCustomers()).thenReturn(expectedCustomers);

        MockHttpServletResponse response = mockMvc.perform(get("/api/customer/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andReturn().getResponse();

        List<Customer> actual = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<List<Customer>>() {});

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertEquals(expectedCustomers.size(),actual.size());
        assertEquals(expectedCustomers.get(0),actual.get(0));
        assertEquals(expectedCustomers.get(1),actual.get(1));
    }

    @Test
    void getCustomer() throws Exception{
        Customer expected = new Customer
                (1L,"Customer1","Customer1",2000L,"11111111111",450,null);

        when(customerService.getCustomer(1L)).thenReturn(expected);

        MockHttpServletResponse response = mockMvc.perform(get("/api/customer/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Customer actual = new ObjectMapper().readValue(response.getContentAsString(),Customer.class);
        assertEquals(expected,actual);
    }

    @Test
    void createCustomer() throws Exception{
        Customer expected = new Customer
                (1L,"Customer1","Customer1",2000L,"11111111111",450,null);
        Mockito.doNothing().when(customerService).addCustomer(expected);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonCredit = ow.writeValueAsString(expected);

        MockHttpServletResponse response = mockMvc.perform(post("/api/customer/create")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCredit)).andDo(print()).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(customerService,times(1)).addCustomer(expected);
    }

    @Test
    void updateCustomer() throws Exception{
        Customer expected = new Customer
                (1L,"Customer1","Customer1",2000L,"11111111111",450,null);
        when(customerService.updateCustomer(expected)).thenReturn(expected);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonCredit = ow.writeValueAsString(expected);

        MockHttpServletResponse response = mockMvc.perform(put("/api/customer/update")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCredit)).andDo(print()).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(customerService,times(1)).updateCustomer(expected);
    }

    @Test
    void deleteCustomer() throws Exception{
        Boolean expected = true;
        when(customerService.deleteCustomer(1L)).thenReturn(expected);

        MockHttpServletResponse response = mockMvc.perform(delete("/api/customer/delete?sid=1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Boolean actual = new ObjectMapper().readValue(response.getContentAsString(),Boolean.class);
        assertEquals(expected,actual);
    }
}