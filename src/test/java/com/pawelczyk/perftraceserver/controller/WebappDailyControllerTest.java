package com.pawelczyk.perftraceserver.controller;

import com.pawelczyk.perftraceserver.PerftraceserverApplication;
import com.pawelczyk.perftraceserver.model.WebappDaily;
import com.pawelczyk.perftraceserver.repository.WebappDailyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author ania.pawelczyk
 * @since 12.09.2019.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PerftraceserverApplication.class)
@WebAppConfiguration
public class WebappDailyControllerTest {

  private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private WebappDailyRepository webappDailyRepository;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    webappDailyRepository.deleteAll();
  }

  @Test
  public void getWebappDaily_withNoEntitiesAdded_returnsListOfZero() throws Exception {
    this.mockMvc.perform(get("/api/users/total/day?date=2000-10-31T01:30:00.000-05:00"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(CONTENT_TYPE))
            .andExpect(jsonPath("$", not(empty())))
            .andExpect(jsonPath("$[0]", is(0)))
            .andExpect(jsonPath("$[23]", is(0)));
  }

  @Test
  public void saveAndGetWebappDaily_returnsAddedEntity() throws Exception {
    LocalDate date = LocalDate.of(2019, 9, 10);

    // save
    WebappDaily webappDaily = createEntity(date);
    webappDailyRepository.save(webappDaily);

    // read
    this.mockMvc.perform(get("/api/users/total/day?date=" + "2019-09-10T01:30:00.000-05:00"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(CONTENT_TYPE))
            .andExpect(jsonPath("$", not(empty())))
            .andExpect(jsonPath("$[0]", is(webappDaily.getUsersNumberHourly().get(0).intValue())));
  }

  private WebappDaily createEntity(LocalDate date) {
    List<Long> hours =  Arrays.stream(new Long[24])
            .map(zero -> (long) (Math.random() * 1000))
            .collect(Collectors.toList());
    return new WebappDaily(date, hours, hours);
  }
}