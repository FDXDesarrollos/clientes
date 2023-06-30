package com.fdxdesarrollos.demo.Controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fdxdesarrollos.demo.models.entity.Cliente;
import com.fdxdesarrollos.demo.models.service.IClienteService;

@Controller
@SessionAttributes("cliente")
public class ClienteController {
	@Autowired
	//@Qualifier("clienteDaoJPA")
	//private IClienteDao clienteDao;
	private IClienteService clienteService;
	
	@RequestMapping(value={"/","/index"}, method=RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("titulo","Listado de Clientes");
		model.addAttribute("clientes",clienteService.findAll());
		return "index";
	}
	
	@RequestMapping(value="/nuevo")
	public String create(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "Nuevo Cliente");
		return "form";
	}
	
	@RequestMapping(value="/guardar", method=RequestMethod.POST)
	public String save(@Valid Cliente cliente, BindingResult result, Model model, @RequestParam("file") MultipartFile imagen, RedirectAttributes flash, SessionStatus status) { // Importante mantener este orden de argumentos
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Cliente");
			return "form";
		}
		
		if(!imagen.isEmpty()) {
			Path rootPath = Paths.get("uploads").resolve(imagen.getOriginalFilename());
			Path absolutePath = rootPath.toAbsolutePath();
			
			try {
				//byte[] bytes = imagen.getBytes();
				//Path rutaCompleta = Path.of(rootPath);
				//Files.write(absolutePath, bytes);
				Files.copy(imagen.getInputStream(), absolutePath);
				flash.addFlashAttribute("info", "Imagen subida correctamente '" + imagen.getOriginalFilename() + "'");
				cliente.setImagen(imagen.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String mensajeFlash = (cliente.getId() != null)?"Información actualizada":"Información registrada";
		
		clienteService.save(cliente);
		status.setComplete();
		
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:index";
	}
	
	@RequestMapping(value="/editar/{id}")
	public String edit(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Cliente cliente = null;
		
		if(id > 0) {
			cliente = clienteService.findOne(id);
			if(cliente == null) {
				flash.addFlashAttribute("error", "Cliente no encontrado!");
				return "redirect:/index";				
			}
		} else {
			flash.addFlashAttribute("error", "Verifique información!");
			return "redirect:/index";
		}
		
		model.put("cliente", cliente);
		model.put("titulo", "Editar Cliente");		
		return "form";
	}
	
	@RequestMapping(value="/eliminar/{id}")
	public String delete(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		if(id > 0) {
			clienteService.delete(id);
			flash.addFlashAttribute("success", "Información eliminada");
		}
		
		return "redirect:/index";
	}
	
	@GetMapping(value="/detalle/{id}")
	public String detalle(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Cliente cliente = clienteService.findOne(id);
		if(cliente == null) {
			flash.addFlashAttribute("error", "Registro no encontrado");
		}
		
		model.put("cliente", cliente);
		model.put("titulo", "Detalle de Cliente");
		return "detalle";
	}
	
	/**
	 * Personalizamos el Data Binding para todas las propiedades de tipo Date
	 * @param webDataBinder
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}*/
}
