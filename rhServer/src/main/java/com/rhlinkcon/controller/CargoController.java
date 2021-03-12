package com.rhlinkcon.controller;

import com.rhlinkcon.payload.cargo.CargoRequest;
import com.rhlinkcon.payload.cargo.CargoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.CargoRepository;
import com.rhlinkcon.service.CargoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CargoController {

	@Autowired
	private CargoRepository cargoRepository;

	@Autowired
	private CargoService cargoService;

	@GetMapping("/cargos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<CargoResponse> getAllCargos(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "nome", defaultValue = AppConstants.DEFAULT_EMPTY) String nome) {
		return cargoService.getAllCargos(page, size, order, nome);
	}

	@GetMapping("/listaCargos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<CargoResponse> getAllCargos() {
		return cargoService.getAllCargos();
	}
	
	@GetMapping("/cargos/list/basico")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<CargoResponse> getAllCargosBasic() {
		return cargoService.getAllCargosBasic();
	}


	@GetMapping("/cargo/{cargoId}")
	public CargoResponse getCargoById(@PathVariable Long cargoId) {
		return cargoService.getCargoById(cargoId);
	}

	@PostMapping("/cargo")
	public ResponseEntity<?> createCargo(@Valid @RequestBody CargoRequest cargoRequest) {
		if (cargoRepository.existsByNome(cargoRequest.getNome())) {
			return Utils.badRequest(false, "O nome já está em uso!");
		}
		cargoService.createCargo(cargoRequest);
		return Utils.created(true, "Cargo criado com sucesso.");
	}

	@PutMapping("/cargo")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateCargo(@Valid @RequestBody CargoRequest cargoRequest) {
		if (cargoRepository.existsByNomeAndIdNot(cargoRequest.getNome(), cargoRequest.getId())) {
			return Utils.badRequest(false, "O nome já está em uso!");
		}
		cargoService.updateCargo(cargoRequest);
		return Utils.created(true, "Cargo atualizado com sucesso.");
	}

	@DeleteMapping("/cargo/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteCargo(@PathVariable("id") Long id) {
		cargoService.deleteCargo(id);
		return Utils.deleted(true, "Cargo deletado com sucesso.");
	}

	@GetMapping("/cargo/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<CargoResponse> getAllCargosFindByNome(@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return cargoService.getAllCargosFindByNome(search);
	}
}