package com.rhlinkcon.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.rhlinkcon.model.Menu;
import com.rhlinkcon.model.MenuCategoriaEnum;
import com.rhlinkcon.model.Papel;
import com.rhlinkcon.payload.menu.MenuCategoriaResponse;
import com.rhlinkcon.payload.menu.MenuResponse;
import com.rhlinkcon.payload.papel.PapelResponse;
import com.rhlinkcon.repository.MenuRepository;
import com.rhlinkcon.repository.PapelRepository;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.util.Projecao;

@Service
public class MenuService {

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private PapelRepository papelRepository;

	public List<MenuResponse> search(String nomeSearch, UserPrincipal currentUser) {
		List<MenuResponse> itensResponses = new ArrayList<MenuResponse>();

		Boolean roleAdmin = currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

		if (roleAdmin) {
			menuRepository.findByNomeIgnoreCaseContainingAndAtivo(nomeSearch)
					.forEach(item -> itensResponses.add(new MenuResponse(item)));
		} else {
			List<BigInteger> menuIds = menuRepository.listMenuIdsUsuarioLogadoAndNomeSearch(currentUser.getId(),
					nomeSearch);

			if (!menuIds.isEmpty()) {
				List<Long> menuLongIds = new ArrayList<Long>();
				menuIds.forEach(menu -> menuLongIds.add(Long.parseLong(menu.toString())));
				List<Menu> menus = menuRepository.findByIdIn(menuLongIds);
				menus.forEach(menu -> itensResponses.add(new MenuResponse(menu)));
			}
		}

		return itensResponses;
	}

	public List<MenuResponse> listMenus(UserPrincipal currentUser) {
		List<Menu> menus = menuRepository.findByAtivoOrderByNomeAsc(true);
		List<MenuResponse> listMenuResponse = new ArrayList<>();
		for (Menu menu : menus) {
			listMenuResponse.add(new MenuResponse(menu));
		}
		return listMenuResponse;
	}

	public List<MenuCategoriaResponse> listMenuUsuarioLogadoCategoria(UserPrincipal currentUser) {
		List<MenuCategoriaEnum> categorias = Arrays.asList(MenuCategoriaEnum.values());

		Boolean roleAdmin = currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

		List<MenuCategoriaResponse> menuCategoriaResponseList = new ArrayList<MenuCategoriaResponse>();

		for (MenuCategoriaEnum menuCategoriaEnum : categorias) {
			List<MenuResponse> menuResponseList = new ArrayList<MenuResponse>();

			if (roleAdmin) {
				Optional<List<Menu>> menus = menuRepository.findByAtivoAndCategoriaOrderByNomeAsc(true,
						menuCategoriaEnum);
				if (menus.isPresent()) {
					menus.get().forEach(menu -> menuResponseList.add(new MenuResponse(menu)));
					menuCategoriaResponseList
							.add(new MenuCategoriaResponse(menuCategoriaEnum.getCategoria(), menuResponseList));
				}
			} else {
				List<BigInteger> menuIds = menuRepository.listMenuIdsUsuarioLogadoCategoria(currentUser.getId(),
						menuCategoriaEnum.toString());

				if (!menuIds.isEmpty()) {
					List<Long> menuLongIds = new ArrayList<Long>();
					menuIds.forEach(menu -> menuLongIds.add(Long.parseLong(menu.toString())));

					List<Menu> menus = menuRepository.findByIdIn(menuLongIds);
					menus.forEach(menuFilho -> menuResponseList.add(new MenuResponse(menuFilho)));
					menuCategoriaResponseList
							.add(new MenuCategoriaResponse(menuCategoriaEnum.getCategoria(), menuResponseList));
				}
			}
		}

		return menuCategoriaResponseList;

	}

	public List<MenuCategoriaResponse> listMenuCategoriaPapelGestao() {
		List<MenuCategoriaEnum> categorias = Arrays.asList(MenuCategoriaEnum.values());

		List<MenuCategoriaResponse> menuCategoriaResponseList = new ArrayList<MenuCategoriaResponse>();

		for (MenuCategoriaEnum categoria : categorias) {
			List<MenuResponse> menuResponseList = new ArrayList<MenuResponse>();

			Optional<List<Menu>> menus = menuRepository.findByAtivoAndCategoriaOrderByNomeAsc(true, categoria);
			if (menus.isPresent()) {

				for (Menu menu : menus.get()) {
					Optional<List<Papel>> papeis = papelRepository.findByMenu(menu);
					List<PapelResponse> listPapelResponse = new ArrayList<>();
					if (papeis.isPresent())
						for (Papel papel : papeis.get())
							listPapelResponse.add(new PapelResponse(papel, Projecao.BASICA));

					menuResponseList.add(new MenuResponse(menu, listPapelResponse));
				}
				menuCategoriaResponseList.add(new MenuCategoriaResponse(categoria.getCategoria(), menuResponseList));
			}
		}

		return menuCategoriaResponseList;

	}

}
