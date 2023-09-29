package com.wallace.uploadcnab.web.controller;

import com.wallace.uploadcnab.domain.Operation;
import com.wallace.uploadcnab.dto.StockDto;
import com.wallace.uploadcnab.repository.OperationRepository;
import com.wallace.uploadcnab.service.OperationService;
import com.wallace.uploadcnab.service.StockService;
import jakarta.servlet.http.HttpServletResponse;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;


@Controller
@RequestMapping("operations")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @Autowired
    private StockService stockService;

    @Mock
    OperationRepository operationRepository;

    @GetMapping()
    public String home(HttpServletResponse response) {
        return "home";
    }

    @PostMapping("/upload")
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attr) {

        if (!Objects.requireNonNull(file.getOriginalFilename()).contains(".txt")) {
            attr.addFlashAttribute("error", "Arquivo não permitido.");
            return new ModelAndView("redirect:/operations");
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));

            String line;
            while ((line = br.readLine()) != null) {
                Operation operation = new Operation(line);
                operationService.save(operation);
            }
            attr.addFlashAttribute("success", "Informações do arquivo salvo com sucesso no nosso banco de dados.");
            br.close();
            return new ModelAndView("redirect:/operations");
        } catch (Exception e) {
            attr.addFlashAttribute("error", "Ocorreu um erro no sistema. Tente novamente mais tarde!");
            return new ModelAndView("redirect:/operations");
        }

    }

    @GetMapping("/list")
    public String listOperations(Model model) {
        List<StockDto> response = stockService.findAll();

        model.addAttribute("data", response);
        return "lista";
    }
}
