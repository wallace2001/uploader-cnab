package com.wallace.uploadcnab.web.controller;

import com.wallace.uploadcnab.domain.Operation;
import com.wallace.uploadcnab.dto.OperationDto;
import com.wallace.uploadcnab.dto.ResponseOperationDto;
import com.wallace.uploadcnab.fixture.OperationFixture;
import com.wallace.uploadcnab.oauth.NamedOAuthPrincipal;
import com.wallace.uploadcnab.repository.OperationRepository;
import com.wallace.uploadcnab.service.OperationService;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import java.io.*;
import java.security.Principal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfig.class)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = OperationController.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OperationControllerTest {
    private File FILE;

    private OperationFixture operationFixture;

    private MockHttpSession session;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy springSecurityFilter;

    @Mock
    private OperationRepository operationRepository;

    @MockBean
    private OperationService operationService;

    @InjectMocks
    private OperationController operationController;

    @BeforeEach
    void init() throws IOException {
        this.operationFixture = new OperationFixture();
        session = makeAuthSession("azeckoski", "USER");

        FILE = new ClassPathResource("CNAB.txt").getFile();
    }

    @Test
    void Should_returnHomePage_When_UserAuthenticated() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/operations").session(session))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
        assertTrue(content.contains("Uploader CNAB"));
    }

    @Test
    void Should_redirectHomePage_When_UploadFileSuccess() throws Exception {
        Operation operation = operationFixture.create();
        when(operationRepository.save(any())).thenReturn(operation);
        when(operationService.save(any())).thenReturn(operation);

        InputStream stream =  new FileInputStream(FILE);
        MockMultipartFile file = new MockMultipartFile("file", FILE.getName(), MediaType.TEXT_HTML_VALUE, stream);

        MockMvc mockMvc
                = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(multipart("/operations/upload").file(file))
                .andExpect(redirectedUrl("/operations"))
                .andExpect(status().isFound());
    }

    @Test
    void Should_NotRefreshPage_When_UploadFileError() throws Exception {
        Operation operation = operationFixture.create();
        when(operationRepository.save(any())).thenReturn(operation);
        when(operationService.save(any())).thenReturn(operation);

        File FILE_ERR = new ClassPathResource("FILE_ERR.pdf").getFile();

        InputStream stream =  new FileInputStream(FILE_ERR);
        MockMultipartFile file = new MockMultipartFile("file", FILE_ERR.getName(), MediaType.TEXT_HTML_VALUE, stream);

        MockMvc mockMvc
                = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(multipart("/operations/upload").file(file)
                        .contentType(MediaType.TEXT_HTML)
                )
                .andExpect(view().name("redirect:/operations"))
                .andReturn();
    }

    @Test
    void Should_returnListPage_When_listOperationsCalled() throws Exception {
        Operation operation_1 = operationFixture.create();
        Operation operation_2 = operationFixture.create();
        Operation operation_3 = operationFixture.create();
        List<Operation> operations = Arrays.asList(
                operation_1,
                operation_2,
                operation_3
        );
        Page<Operation> pagedOperations = new PageImpl<>(operations);

        List<OperationDto> operationDtos = operations.stream()
                .map(OperationDto::new)
                .toList();

        ResponseOperationDto responseOperationDto = new ResponseOperationDto();
        responseOperationDto.setOperations(operationDtos);
        responseOperationDto.setNumber(pagedOperations.getNumber());
        responseOperationDto.setTotal(100L);
        responseOperationDto.setTotalPages(pagedOperations.getTotalPages());
        responseOperationDto.setSize(pagedOperations.getSize());

        when(operationRepository.findAll(PageRequest.of(1, 5))).thenReturn(pagedOperations);
        when(operationRepository.findAll()).thenReturn(operations);

        when(operationService.findAll(any())).thenReturn(responseOperationDto);

        MvcResult result = this.mockMvc
                .perform(get("/operations/list").session(session))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
        assertTrue(content.contains("Lista de Operações"));
        assertTrue(content.contains(operation_1.getStoreOwner()));
    }

    @Test
    void Should_returnEmptyPage_When_listOperationsForVoids() throws Exception {
        List<Operation> operations = List.of();
        Page<Operation> pagedOperations = new PageImpl<>(operations);

        ResponseOperationDto responseOperationDto = new ResponseOperationDto();
        responseOperationDto.setOperations(List.of());
        responseOperationDto.setNumber(pagedOperations.getNumber());
        responseOperationDto.setTotal(100L);
        responseOperationDto.setTotalPages(pagedOperations.getTotalPages());
        responseOperationDto.setSize(pagedOperations.getSize());

        when(operationRepository.findAll(PageRequest.of(1, 5))).thenReturn(pagedOperations);
        when(operationRepository.findAll()).thenReturn(operations);

        when(operationService.findAll(any())).thenReturn(responseOperationDto);

        MvcResult result = this.mockMvc
                .perform(get("/operations/list").session(session))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
        assertTrue(content.contains("Opss!"));
    }

    public MockHttpSession makeAuthSession(String username, String... roles) {
        if (StringUtils.isEmpty(username)) {
            username = "azeckoski";
        }
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        Collection<GrantedAuthority> authorities = new HashSet<>();
        if (roles != null) {
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }
        //Authentication authToken = new UsernamePasswordAuthenticationToken("azeckoski", "password", authorities); // causes a NPE when it tries to access the Principal
        Principal principal = new NamedOAuthPrincipal(username, authorities);
        Authentication authToken = new UsernamePasswordAuthenticationToken(principal, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        return session;
    }
}