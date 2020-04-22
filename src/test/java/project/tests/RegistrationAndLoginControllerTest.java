package project.tests;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import project.db.CreditRequestDAO;
import project.model.ClientAccountant;
import project.model.ClientCreditRequest;
import project.model.CreditType;
import project.model.CreditWorker;
import project.web.WebConfiguration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class, WebConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WebAppConfiguration
public class RegistrationAndLoginControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CreditRequestDAO requests;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void registrationFormViewTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/")
        ).andExpect(status().isOk())
        .andExpect(view().name("registration"))
        .andReturn();
    }

    @Test
    public void loginFormAlreadyFilled() throws Exception {
        CreditWorker worker = requests.createCreditWorker("employee", "123");
        ClientAccountant accountant = requests.createClient("user", "123", "Dude", 1234567892);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                .param("loginField", "user")
                .param("passwordField", "123")
                .param("loginButton", "loginButton")

        ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/clientInterface"))
                .andReturn();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                .param("loginField", "employee")
                .param("passwordField", "123")
                .param("loginButton", "loginButton")
        ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/workerInterface"))
                .andReturn();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                .param("loginField", "boss")
                .param("passwordField", "123")
                .param("loginButton", "loginButton")
        ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bossInterface"))
                .andReturn();
    }

    @Test
    public void registrationFormAlreadyFilled() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/registration")
                .param("loginField", "user")
                .param("passwordField", "123")
                .param("typeField", "Client")
                .param("clientITNField", "1234567892")
                .param("clientNameField", "Dude")
                .param("registrationButton", "registrationButton")
        ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/clientInterface"))
                .andReturn();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/registration")
                .param("loginField", "employee")
                .param("passwordField", "123")
                .param("typeField", "Worker")
                .param("clientITNField", "")
                .param("clientNameField", "")
                .param("registrationButton", "registrationButton")
        ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/workerInterface"))
                .andReturn();
    }

}
