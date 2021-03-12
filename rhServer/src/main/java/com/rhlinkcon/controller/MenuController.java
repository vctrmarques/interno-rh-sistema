package com.rhlinkcon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.menu.MenuCategoriaResponse;
import com.rhlinkcon.payload.menu.MenuResponse;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.MenuService;
import com.rhlinkcon.util.AppConstants;

@RestController
@RequestMapping("/api")
public class MenuController {

	@Autowired
	private MenuService menuService;

	@GetMapping("/menus/sidebar")
	public List<MenuCategoriaResponse> listMenuUsuarioLogadoCategoria(@CurrentUser UserPrincipal currentUser) {
		return menuService.listMenuUsuarioLogadoCategoria(currentUser);
	}

	@GetMapping("/menus/papeis/gestao")
	public List<MenuCategoriaResponse> listMenuCategoriaPapelGestao() {
		return menuService.listMenuCategoriaPapelGestao();
	}

	@GetMapping("/menus")
	public List<MenuResponse> listMenus(@CurrentUser UserPrincipal currentUser) {
		return menuService.listMenus(currentUser);
	}

	@GetMapping("/menu/search")
	public List<MenuResponse> search(
			@RequestParam(value = "nomeSearch", defaultValue = AppConstants.DEFAULT_EMPTY) String nomeSearch,
			@CurrentUser UserPrincipal currentUser) {
		return menuService.search(nomeSearch, currentUser);
	}
}
