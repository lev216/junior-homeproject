package project.tests;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import project.db.CreditRequestDAO;
import project.model.ClientAccountant;
import project.model.CreditType;
import project.model.CreditWorker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SpringDAOTest {

    @Autowired
    public CreditRequestDAO requests;

    @Test
    public void smokeTest() {
        CreditWorker worker = requests.createCreditWorker("w1", "0");
        ClientAccountant accountant = requests.createClient("user", "0", "boo", 1234567891);
        requests.createCreditRequest(accountant, CreditType.INVESTMENT, 1000000, 12, 100000000, 100000, 1000000, 2000000);

    }

}
