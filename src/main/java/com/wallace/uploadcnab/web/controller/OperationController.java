package com.wallace.uploadcnab.web.controller;

import com.wallace.uploadcnab.domain.Operation;
import com.wallace.uploadcnab.dto.ResponseOperationDto;
import com.wallace.uploadcnab.service.OperationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@RequestMapping("operations")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @GetMapping()
    public String home(HttpServletResponse response) {
        return "home";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attr) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));

            String line;
            while ((line = br.readLine()) != null) {
                Operation operation = new Operation(line);
                operationService.save(operation);
            }
            attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
            br.close();
        } catch (IOException e) {
            attr.addFlashAttribute("falha", "Erro ao realizar o upload do arquivo!");
        }

        return "redirect:/home";
    }

    @GetMapping("/list")
    public String listOperations(Model model,
                                 @RequestParam("page") Optional<Integer> page,
                                 @RequestParam("size") Optional<Integer> size
    ) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        ResponseOperationDto response = operationService.findAll(PageRequest.of(currentPage - 1, pageSize));

        if (response.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, response.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("data", response);
        return "lista";
    }
}
