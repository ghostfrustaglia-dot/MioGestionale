package com.example.demo;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdottoRepository prodottoRepository;

    // --- DASHBOARD ---
    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("totaleClienti", clienteRepository.count());
        model.addAttribute("totaleProdotti", prodottoRepository.count());
        return "dashboard";
    }

    // --- CLIENTI ---
    @GetMapping("/clienti")
    public String listaClienti(Model model, @RequestParam(required = false) String keyword) {
        List<Cliente> lista = (keyword != null && !keyword.isEmpty()) ?
                clienteRepository.findByNomeContainingIgnoreCase(keyword) : clienteRepository.findAll();
        model.addAttribute("listaClienti", lista);
        return "index";
    }

    @PostMapping("/clienti/aggiungi")
    public String aggiungiCliente(@RequestParam String nome, @RequestParam String email, @RequestParam String telefono) {
        clienteRepository.save(new Cliente(nome, email, telefono));
        return "redirect:/clienti";
    }

    @PostMapping("/clienti/elimina")
    public String eliminaCliente(@RequestParam Long id) {
        clienteRepository.deleteById(id);
        return "redirect:/clienti";
    }

    // --- IMPORTAZIONE ---
    @PostMapping("/clienti/importa")
    public String importaClienti(@RequestParam("file") MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] dati = linea.split(",");
                if (dati.length >= 3) {
                    clienteRepository.save(new Cliente(dati[0].trim(), dati[1].trim(), dati[2].trim()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/clienti";
    }

    // --- ESPORTAZIONE (CORRETTA) ---
    @GetMapping("/clienti/esporta")
    public void esportaClienti(HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment; filename=clienti_export.txt");

        PrintWriter writer = response.getWriter();
        writer.println("NOME,EMAIL,TELEFONO"); // Intestazione compatibile per una futura ri-importazione

        List<Cliente> tutti = clienteRepository.findAll();
        for (Cliente c : tutti) {
            writer.println(c.getNome() + "," + c.getEmail() + "," + c.getTelefono());
        }
        writer.flush();
        writer.close();
    }

    // --- MAGAZZINO ---
    @GetMapping("/magazzino")
    public String listaProdotti(Model model) {
        model.addAttribute("listaProdotti", prodottoRepository.findAll());
        return "magazzino";
    }

    @PostMapping("/magazzino/aggiungi")
    public String aggiungiProdotto(@RequestParam String nome, @RequestParam int quantita, @RequestParam double prezzo) {
        prodottoRepository.save(new Prodotto(nome, quantita, prezzo));
        return "redirect:/magazzino";
    }

    @PostMapping("/magazzino/elimina")
    public String eliminaProdotto(@RequestParam Long id) {
        prodottoRepository.deleteById(id);
        return "redirect:/magazzino";
    }
}