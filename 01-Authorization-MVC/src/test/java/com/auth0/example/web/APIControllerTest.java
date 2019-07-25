package com.auth0.example.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests to verify that our endpoints are properly secured.
 */
@WebMvcTest(APIController.class)
@RunWith(SpringRunner.class)
public class APIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    @SuppressWarnings("unused")
    private JwtDecoder jwtDecoder;

    @Test
    public void testPublicEndpoint() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/public"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        
        assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testPrivateEndpointReturnsUnauthorizedWhenNoUser() throws Exception {
        mockMvc.perform(get("/api/private"))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "testUser")
    public void testPrivateEndpointReturnsOkWhenAuthorized() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/private"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testPrivateScopedEndpointReturnsUnauthorizedWhenNoUser() throws Exception {
        mockMvc.perform(get("/api/private-scoped"))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "testUser")
    public void testPrivateScopedEndpointReturnsForbiddenWhenNoScopes() throws Exception {
        mockMvc.perform(get("/api/private-scoped"))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "testUser", authorities = {"SCOPE_read:messages"})
    public void testPrivateScopedEndpointReturnsOkWhenProperScopes() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/private-scoped"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(username = "testUser", authorities = {"SCOPE_write:messages"})
    public void testPrivateScopedEndpointReturnsForbiddenWhenIncorrectScopes() throws Exception {
        mockMvc.perform(get("/api/private-scoped"))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
    }
}
