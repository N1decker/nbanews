package ru.nidecker.nbanews.web.user;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.nidecker.nbanews.AbstractTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminControllerTest extends AbstractTest {

    public static final String USER_DETAILS = "user@yandex.ru";
    public static final String ADMIN_DETAILS = "admin@gmail.com";

    private static final String USERS = "/users";
    public static final String REST_URL = "/api/admin/users/";
    @Autowired
    private MockMvc mockMvc;

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    @Test
    @WithUserDetails(value = USER_DETAILS, userDetailsServiceBeanName = "userService")
    void usersPageForbidden() throws Exception {
        perform(get(USERS))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_DETAILS, userDetailsServiceBeanName = "userService")
    void userPageByAdmin() throws Exception {
        perform(get(USERS))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = USER_DETAILS, userDetailsServiceBeanName = "userService")
    void deleteUserForbidden() throws Exception {
        perform(post(REST_URL + Mockito.anyInt()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = USER_DETAILS, userDetailsServiceBeanName = "userService")
    void blockUserForbidden() throws Exception {
        perform(post(REST_URL + Mockito.anyInt() + "/block"))
                .andExpect(status().isForbidden());
    }

}