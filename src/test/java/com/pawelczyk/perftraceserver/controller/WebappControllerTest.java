package com.pawelczyk.perftraceserver.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pawelczyk.perftraceserver.PerftraceserverApplication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author ania.pawelczyk
 * @since 07.09.2019.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PerftraceserverApplication.class)
@WebAppConfiguration
public class WebappControllerTest {

  private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

    @Test
  public void getWebapps_withNoWebappsAdded_returnsEmptyOk() throws Exception {
    this.mockMvc.perform(get("/webapps"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(CONTENT_TYPE))
            .andExpect(jsonPath("$", (empty())));
  }

  @Test
  public void addWebapp_returnsIsCreated() throws Exception {
    String webappJson = "{\"url\":\"perf-trace\"}";
    this.mockMvc.perform(post("/webapps")
            .contentType(CONTENT_TYPE)
            .content(webappJson))
            .andExpect(status().isCreated());
  }

  @Test
  public void addWebapp_withAdditionalUnimportantParameter_returnsIsCreated() throws Exception {
    String webappJson = "{\"url\":\"perf-trace\", \"otherUnimportantParameter\":\"otherParameter-trace\"}";
    this.mockMvc.perform(post("/webapps")
            .contentType(CONTENT_TYPE)
            .content(webappJson))
            .andExpect(status().isCreated());
  }

  @Test
  public void addWebapp_WithIncorrectParameter_returnsHttpUNPROCESSABLE_ENTITY() throws Exception {
    String webappJson = "{\"incorectParameter\":\"hejho\"}";
    this.mockMvc.perform(post("/webapps")
            .contentType(CONTENT_TYPE)
            .content(webappJson))
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  public void addWebappWithEmptyUrlParameter_returnsHttpUNPROCESSABLE_ENTITY() throws Exception {
    String webappJson = "{\"url\":\"\"}";
    this.mockMvc.perform(post("/webapps")
            .contentType(CONTENT_TYPE)
            .content(webappJson))
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  public void addAndGetWebapps() throws Exception {
    String webappJson = "{\"url\":\"perftrace\"}";

    // setup
    this.mockMvc.perform(post("/webapps")
            .contentType(CONTENT_TYPE)
            .content(webappJson))
            .andExpect(status().isCreated());

    // action
    this.mockMvc.perform(get("/webapps"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(CONTENT_TYPE))
            .andExpect(jsonPath("$", not(empty())))
            .andExpect(jsonPath("$[0].url", is("perftrace")));
  }
}